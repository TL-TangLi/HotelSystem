package com.hotel.datamanage;

import java.util.Calendar;
import java.util.List;

import com.hotel.data_access.DataAccess;
import com.hotel.data_access.MyException;
import com.hotel.entity.Account;
import com.hotel.entity.CheckInInfo;
import com.hotel.entity.Order;
import com.hotel.entity.Room;
import com.hotel.entity.RoomAndCheckIn;
import com.hotel.entity.RoomCharge;
import com.hotel.entity.RoomColor;
import com.hotel.entity.RoomPrice;
import com.hotel.entity.User;
import com.hotel.staticdata.StaticData;

public class Manager
{
	
	private DataAccess da;
	private Manager()
	{
		da = DataAccess.getDataAccess();
	}
	private static Manager instance = new Manager();
	public static Manager getManager()
	{
		return instance;
		
	}
	
	private String dealMyException(Exception e)
	{
		e.printStackTrace();
		return "操作失败数据库异常,请重试！";
	}
	
	/*-----------------------------------入住 以及入住后操作-----------------------------------------*/
	
	/**
	 * 入住，需要修改房间状态，关联入住信息，加入入住信息
	 * @param ci
	 * @param days			入住天数
	 * @param balanceType 
	 * @return
	 */
	public String checkIn(CheckInInfo ci,int days,int balanceType)	//ci 已经包含了roomId 在提交时。form表单指定的
	{
		
		try
		{
			//设置续住 outTime 
			Calendar now  = Calendar.getInstance();
			
			//如果是小时房，那么 outTime 应当是当天退房，！days 字段将无效 (当天退房),并且不允许关联订单
			if(ci.isHourRoom)
			{
				days = 0;
				ci.orderId = 0;
			}
			else
			{
				//有订单，days 无效，outTime 由 订单上入住天数决定！ 而散客入住，days指定天数有效！
				if(ci.orderId != 0)	
					days = da.getOrderById(ci.orderId).days;
			}
			now.add(Calendar.DAY_OF_YEAR,days);
			ci.outTime =  StaticData.formatDate(now.get(Calendar.YEAR),now.get(Calendar.MONTH)+1,now.get(Calendar.DAY_OF_MONTH));
			
//			da.addCheckIn(ci);												//数据库添加 入住信息	
//			int maxCheckId = da.getMaxCheckId();							//获取刚刚加入的信息id
//			da.updateRoomCheckId(ci.rId,maxCheckId);							// 用房间关联入住信息
//			if(ci.orderId > 0)	
//				da.updateOrderInfo(ci.orderId,Order.ORDER_TYPE_CHECKED);	//将订单修改为已入住
//			da.addAccount(new Account(ci.rId,maxCheckId,"入住时首次入账",balanceType,false,ci.balance,null,0));
			
			if(da.transactionForCheckIn(ci,balanceType))
				return "入住成功，请核对入账，如果不对，请刷新查看，甚至重新入住！";
			else
				return "入住失败，检测网络重新尝试！";
			
		}
		catch (Exception e)
		{
			return dealMyException(e);
		}
		
	}
	
	/**
	 * 退房  客户端  必须要房费正好才能 提交 退房，此部分逻辑 放在 js 中
	 * @param roomId  
	 * @return 
	 */
	public  String checkOut(int roomId)
	{
		try
		{
//			int cid = da.getRoomCheckId(roomId);						//获取入住信息 号
//			double balance = da.sumAccountByFilter(cid,-1,0,null,null) - da.sumAccountByFilter(cid,-1,1,null,null);	//计算房费
//			da.updateBalance(cid,balance);								//入住信息 balance 字段 记录总房费
//			da.updateCheckOut(cid);										//修改入住信息 （已退房，以及退房时间）
//			da.updateRoomState(roomId,StaticData.ROOM_STATE_DIRTY);		//修改房间的状态
			if(da.transactionForCheckOut(roomId))
				return "退房成功！";
			else
				return "退房失败请检测网络，重试！";
		}
		catch (Exception e)
		{
			return dealMyException(e);
		}
		
	}
	
	/**
	 * 入账 和消费 操作
	 * @param roomId	
	 * @param banlance
	 * @return
	 */
	public  String addBalanceAndConsume(Account account)
	{
		
		try
		{
			da.addAccount(account);
			
			if(account.isConsume)
				return "本次消费为："+(account.balance)+"请刷新核对！如有误，请调整！";
			else
				return "本次入账为："+(account.balance)+"请刷新核对！如有误，请调整！";
		}
		catch (MyException e)
		{
			return dealMyException(e);
		}
	}
	
	
	/**
	 * @param roomId 房间号
	 * @param state	  房间将要改成的状态（空房间之间的切换从）state 参数是StaticData中的静态字段
	 * @return
	 */
	public  String changeRoomState(int roomId,int state)
	{
		try
		{
			//改为不可用房，数据库中对应类型房间数量减一 。从不可用房改为可用房，类型数量将加 一
			Room room = getRoomById(roomId);
			da.updateRoomState(roomId,state);		//修改状态

			da.updateRoomTypeNum(room.type);		//更新统计
			
			return "更新成功！";
		}
		catch (MyException e)
		{
			return dealMyException(e);
		}
		
	}
	
	
	/**
	 * @param cif 更新CheckInInfo 部分字段    如果关系到订单将会修改订单状态
	 * @param cif		
	 * @param modifyOrderId			///原本的订单号
	 * @return
	 */
	public String updateCheckInInfoPart(CheckInInfo cif,int modifyOrderId)
	{
		try
		{
			cif.id = da.getRoomCheckId(cif.rId);		//此时的cif id 为空，必须要获取才能够操作改变入住信息
			
			//修改了订单号 
			if(modifyOrderId != cif.orderId)
			{
				Order order = da.getOrderById(cif.orderId);
				if(order == null || order.id == StaticData.NOT_VALIDATE)
					return "指定的订单不存在，请重新操作！";
				
				//outTime (房间到期时间)将叠加新订单上的入住天数
				String outDate = da.getCheckInOutDate(cif.id);
				outDate = StaticData.dateAdd(outDate,order.days);
				if(outDate == null)
					return "订单数据有误，操作不成功！";
				
//				//只是将以前的订单的 info 改为可用
//				if(modifyOrderId != 0)
//					da.updateOrderInfo(modifyOrderId,Order.ORDER_TYPE_AVALIDATE);
//				//当前订单 改变 info 为 已入住
//				da.updateOrderInfo(cif.orderId,Order.ORDER_TYPE_CHECKED);
//				//更行outTime
//				da.updateCheckInOutDate(cif.id,outDate);
				
				if(da.transactionForUpdateCheckInInfoOrder(cif,modifyOrderId,outDate))
					return "更新成功";
				else
					return "更新失败，请检查网络，重试！";
				
			}
			else
			{
				da.updateCheckInPart(cif);
				return "更新成功！";
			}
		}
		catch (Exception e)
		{
			return dealMyException(e);
		}
		
	}
	
	
	/**
	 * @param roomId	续住房间
	 * @param days  续住天数
	 * @return
	 */
	public String continueCheckIn(int roomId,int days)
	{
		try
		{
			int cid = da.getRoomById(roomId).cId;
			String date = da.getCheckInOutDate(cid);
			date = StaticData.dateAdd(date,days);
			if(date == null)
				return "数据库数据有误，续住不成功！";
			
			da.updateCheckInOutDate(cid,date);
			return "续住成功";
		}
		catch (MyException e)
		{
			return dealMyException(e);
		}
		
	}
	
 	
 	/**
 	 * @param rId
 	 * @param targetId
 	 * @return
 	 */
 	public synchronized String switchRoom(int rId,int targetId)
	{
		try
		{
			if(da.transactionForSwitchRoom(rId,targetId)== false)												//如果现在 房间对应  入住信息号 无效，那么提醒刷新
				return "换房失败，房间用户有所变更，刷新后操作！";		
			return "换房成功！";
		}
		catch (Exception e)
		{
			return dealMyException(e);
		}
		
	}
 	
 	
 	/*-----------------------------------基本增删改-----------------------------------------*/
 	
 	public String updateOrder(Order order)
	{
		try
		{
			order.outDate  = StaticData.dateAdd(order.enterDate,order.days);
			da.updateOrder(order);
			return "更新成功！";
		}
		catch (MyException e)
		{
			return dealMyException(e);
		}
	}

 	public String delOrder(int id)
 	{
 		try
		{
			da.deleteOrder(id);
			return "删除成功！";
		}
		catch (MyException e)
		{
			return dealMyException(e);
		}
 	}
 	
 	public String addOrder(Order order)
	{
		try
		{
			order.outDate  = StaticData.dateAdd(order.enterDate,order.days);
			//如果定n间房那么将产生 n 个订单！
			for(int i = 0 ;i<order.rooms;i++)
			{
				da.addOrder(order);
			}
			return "添加成功！";
		}
		catch (MyException e)
		{
			return dealMyException(e);
		}
	}
	
 	
 	
  	public String addRoomType(RoomPrice rp)
 	{
 		try
		{
 			if(da.getRomTypeByName(rp.description).id != StaticData.NOT_VALIDATE)
 				return "该房间类型已经存在，请重新指定！";
 			
 			da.addRoomType(rp);
			return "添加成功！";
		}
		catch (MyException e)
		{
			return dealMyException(e);
		}
 	}
 	
 	public String delRoomType(int type)
 	{
 		try
		{
 			int num = da.countRoomNumByType(type,true);				//必须包括不可用房  （否则当只有不可用房的时候，将会删除该房间类型，然后产生错误）
 			if(num  != 0)
 				return "该类型房间还有"+num+"个房间请将该类型房间删除完再删除该类型！";
 			else{
 				
 				da.delRoomType(type);
 				//级联删除订单
 				da.delOrderByRoomType(type);
 			}
			return "删除成功！";
		}
		catch (MyException e)
		{
			return dealMyException(e);
		}
 	}
 	
 	public String updateRoomType(RoomPrice rp)
 	{
 		try
		{
			da.updateRoomType(rp);
			return "修改成功！";
		}
		catch (MyException e)
		{
			return dealMyException(e);
		}
 	}
 	
 	
 	public String addRoom(Room room)
 	{
 		try
		{
 			if(da.getRoomById(room.id)!= null)
 				return "房间号已经存在，请重新指定！";
 			
 			da.addRoom(room);						//触发器 自动 增加一次统计
 			da.updateRoomTypeNum(room.type);		//刷新一次统计 
			return "添加成功！";
		}
		catch (MyException e)
		{
			return dealMyException(e);
		}
 	}
 	
 	public String deleteRoom(int rid)
 	{
 		try
		{
 			Room room = da.getRoomById(rid);	//触发器 自动 减少一次统计
 			da.deleteRoom(rid);
 			da.updateRoomTypeNum(room.type);	//刷新一次统计
			return "删除成功！";
		}
		catch (MyException e)
		{
			return dealMyException(e);
		}
 	}
 	
 	public synchronized String updatePartRoom(int id,String description,int newType,int oldType)
 	{
 		try
		{
 			da.updatePartRoom(id,description,newType);
 			
 			da.updateRoomTypeNum(newType);			//更新统计 增加一个
 			da.updateRoomTypeNum(oldType);			//更新统计 减少一个
 			
 			notifyAll();
			return "修改成功！";
		}
		catch (MyException e)
		{
			return dealMyException(e);
		}
 	}
 	
 	public void delAccountItem(int id)
 	{
 		try
		{
			da.delAccount(id);
		}
		catch (MyException e)
		{
			e.printStackTrace();
		}
 	}
 	
 	
 	public String delCheckIn(int id)
 	{
 		try
		{
			da.deleteCheckIn(id);
			return "删除成功！";
		}
		catch (MyException e)
		{
			return dealMyException(e);
		}

 	}
 	
 	public void updateCheckInBalance(int cId)
 	{
 		try
		{
			da.updateBalance(cId,sumChargeItemByFilter(cId,null,null));
		}
		catch (MyException e)
		{
			e.printStackTrace();
		}
 	}
 	
 	public  void addChargeItem(RoomCharge charge)
 	{
 		try
		{
			da.addChargeItem(charge);
		}
		catch (MyException e)
		{
			e.printStackTrace();
		}
 	}
	
	public  void updateChargeItem(int rcId,int type,double balance)
	{
		try
		{
			da.updateChargeItem(rcId,type,balance);
		}
		catch (MyException e)
		{
			e.printStackTrace();
		}
	}
 	
	public void delOneChargeItem(int rcId)
	{
		try
		{
			da.delChargeItem(rcId);
		}
		catch (MyException e)
		{
			e.printStackTrace();
		}
	}
	
	public void delChargeItemByCheckInId(int cid)
	{
		try
		{
			da.delChargeItemByCheckId(cid);
		}
		catch (MyException e)
		{
			e.printStackTrace();
		}
	}
 	
	/*-----------------------------------///过滤查找-----------------------------------------*/
 	
 	public List<RoomCharge> getChargeItemByFilter(int cid,String beginDate,String endDate)
 	{
 		try
		{
			return da.getChargeItemByDate(cid,beginDate,endDate);
		}
		catch (MyException e)
		{
			e.printStackTrace();
			return null;
		}
 	}
 	
 	public double sumChargeItemByFilter(int cid,String beginDate,String endDate)
 	{
 		try
 		{
 			return da.sumChargeItemByDate(cid,beginDate,endDate);
 		}
 		catch (MyException e)
 		{
 			e.printStackTrace();
 			return 0;
 		}
 	}
 	
   	public List<CheckInInfo> getCheckInInfoByFilter(String enterDateBegin,String enterDateEnd,String outDateBegin,String outDateEnd)
 	{
 		try
		{
			return da.getCheckInByFilter(enterDateBegin, enterDateEnd, outDateBegin,outDateEnd);
		}
		catch (MyException e)
		{
			e.printStackTrace();
			return null;
		}
 	}
 	
 	public List<Account> getAccountByFilter(int cId,int type,int isConsume,String beginDate,String endDate)
 	{
 		try
		{
			return da.getAccountByFilter(cId,type,isConsume,beginDate,endDate);
		}
		catch (MyException e)
		{
			e.printStackTrace();
			return null;
		}
 	}
 	
 
 	public double sumAccountByFilter(int cId, int type, int isConsume,String beginDate, String endDate)
 	{
 		try
		{
			return da.sumAccountByFilter(cId,type,isConsume,beginDate,endDate);
		}
		catch (MyException e)
		{
			e.printStackTrace();
			return 0;
		}
 	}
 	
 	
 	public List<Order> getOrderByFilter(int type,int info,String enterDate,String orderDate)
 	{
 		try
		{
			return da.getFilteredOrder(type,info,enterDate,orderDate);
		}
		catch (MyException e)
		{
			e.printStackTrace();
			return null;
		}
 	}
 	
 	public int countOrderByfilter(int type,int info,String enterDate,String orderDate)
 	{
 		try
		{
			return da.countFilteredOrder(type,info,enterDate,orderDate);
		}
		catch (MyException e)
		{
			e.printStackTrace();
			return 0;
		}
 	}
 	
 	
 	
 	public int countAvalidateOrderOutDateMoreThanSomeDay(int type ,String date)
 	{
 		try
		{
 			return da.countAvalidateOrderOutDateMoreThanSomeDay(type,date);
		}
		catch (MyException e)
		{
			e.printStackTrace();
			return 0;
		}
 	}
 	
 	public int countContinueCheckInRoomMoreThanSomeDay(int type,String date)
 	{
 		try
		{
 			return  da.countContinueCheckInRoomMoreThanSomeDay(type,date);
 			
		}
		catch (MyException e)
		{
			e.printStackTrace();
			return 0;
		}
 	}
 	
 	
	/**
 	 * @param type	指定为-1 忽略过滤器
 	 * @param date
 	 * @return  	计算某类房间 在某天 的剩余数量 （可用房 - 续住 - 待入住）
 	 */
 	public int countLeftRoom(int type,String date)
 	{
 		try
		{
 			//续住到那天的房间数
 			int continueRoom =  da.countContinueCheckInRoomMoreThanSomeDay(type,date);
 			//会入住到那天的 订单数
 			int orderRoom = da.countAvalidateOrderOutDateMoreThanSomeDay(type,date);
 			return  da.countRoomNumByType(type,false) - (orderRoom+continueRoom);
 			
		}
		catch (MyException e)
		{
			e.printStackTrace();
			return 0;
		}
 	}
 	
 	
 	public int countExpireRoom(int type,String date)
 	{
 		try
		{
 			return  da.countExpireRoom(type,date);
 			
		}
		catch (MyException e)
		{
			e.printStackTrace();
			return 0;
		}
 	}
 	
 	
 	
  	public int countRoomByFilter(int type ,String enterDate,String outDate)
 	{
 		try
		{
			return da.countFilteredCheckedRoom(type,enterDate,outDate);
		}
		catch (MyException e)
		{
			e.printStackTrace();
			return 0;
		}
 		
 	}
 	
 	public List<RoomAndCheckIn> getRoomByFilter(int type ,String enterDate,String outDate)
 	{
 		try
		{
			return da.getFilteredCheckedRoom(type,enterDate,outDate);
		}
		catch (MyException e)
		{
			e.printStackTrace();
			return null;
		}
 		
 	}
 	
 	
 	
 	public List<Room> getEmptyRoom(int type,int state)
 	{
 		try
		{
			return da.getEmptyRoom(type,state);
		}
		catch (MyException e)
		{
			e.printStackTrace();
			return null;
		}
 	}

 	public int countEmptyRoom(int type,int state)
 	{
 		try
		{
			return da.countEmptyRoom(type,state);
		}
		catch (MyException e)
		{
			e.printStackTrace();
			return 0;
		}
 	}
 	
 	/*-----------------------------------getBy-----------------------------------------*/
 	
 	/**
	 * @return  若数据库有异常返回 null
	 */
 	public List<Room> getAllRoom()
	{
		try
		{
			return da.getAllRoom();
		}
		catch (MyException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	

 	public List<RoomPrice> getAllRoomPrice()
 	{
 		try
		{
			return da.getAllPrice();
		}
		catch (MyException e)
		{
			e.printStackTrace();
			return null;
		}
 		
 	}
 	
 	public RoomPrice getFirstRoomPrice()
 	{
 		try
		{
			return da.getFirstRoomPrice();
		}
		catch (MyException e)
		{
			e.printStackTrace();
			return null;
		}
 	}
 	
 	
  	public RoomPrice getRoomPriceByType(int type)
 	{
 		try
		{
			return da.getPriceByType(type);
		}
		catch (MyException e)
		{
			e.printStackTrace();
			return null;
		}
 		
 	}
 	
 	public RoomColor getRoomColorByState(int state)
 	{
 		try
		{
			return da.getRoomColorByState(state);
		}
 		catch (MyException e)
		{
			e.printStackTrace();
			return null;
		}
 	}
 	
	public CheckInInfo getCheckInById(int cId)
	{
		try
		{
			return da.getCheckInById(cId);
		}
		catch (MyException e)
		{
			e.printStackTrace();
			return null;
			
		}
		
	}
	
	public Room getRoomById(int rid)
	{
		
		try
		{
			return da.getRoomById(rid);
		}
		catch (MyException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public Order getOrderById(int id)
	{
		try
		{
			return da.getOrderById(id);
		}
		catch (MyException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * @param rid
	 * @return 获取该room的入住信息号 如果异常 或者房间号不存在 或者无入住信息 将返回 0
	 */
	public int getCheckIdByRoom(int rid)
	{
		try
		{
			return da.getRoomCheckId(rid);
		}
		catch (MyException e)
		{
			e.printStackTrace();
			return 0;
		}
	}
	
	
	public String getSuperMangerPsw()
	{
		return da.getSuperManagerPsw();
	}
	
	public int login(User user)
	{
		return da.login(user);
	}
}
