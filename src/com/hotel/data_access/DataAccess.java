package com.hotel.data_access;

import java.util.List;

import com.hotel.entity.Account;
import com.hotel.entity.CheckInInfo;
import com.hotel.entity.Order;
import com.hotel.entity.Room;
import com.hotel.entity.RoomAndCheckIn;
import com.hotel.entity.RoomCharge;
import com.hotel.entity.RoomColor;
import com.hotel.entity.RoomPrice;
import com.hotel.entity.User;

/**
 * @author christopher
 *
 */
public abstract  class DataAccess
{

	public static DataAccess getDataAccess()
	{
		try
		{
			return (DataAccess) Class.forName("com.hotel.data_access.DataAccessFromSqlite").newInstance();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	
	/*-----------------------------------RoomCharge-----------------------------------------*/
	
	
	/**
	 * @param cid			指定-1 去掉过滤
	 * @param datebegin		指定 null 去掉过滤
	 * @param endDate		指定 null 去掉过滤
	 * @return				返回指定入住信息，某段时间的房费。返回的List是通过 RoomCharge 的 date 字段 从小到大排序的order by julianday(date) asc
	 * @throws MyException
	 */
	public abstract List<RoomCharge> getChargeItemByDate(int cid,String datebegin,String endDate)throws MyException;
	
	/**
	 * @param cid			指定-1 去掉过滤
	 * @param datebegin		指定 null 去掉过滤
	 * @param endDate		指定 null 去掉过滤
	 * @return				返回指定入住信息，某段时间的房费。返回的List是通过 RoomCharge 的 date 字段 从小到大排序的order by julianday(date) asc
	 * @throws MyException
	 */
	public abstract double sumChargeItemByDate(int cid,String datebegin,String endDate)throws MyException;
	
	/**
	 * @param charge  只有 id 可以不指定，其他字段全部要指定
	 * @throws MyException
	 */
	public abstract void addChargeItem(RoomCharge charge)throws MyException;
	
	
	/**
	 * @param rcId
	 * @param type   		房费类型（全天，半天，小时）
	 * @param balance		房费
	 * @throws MyException
	 */
	public abstract void updateChargeItem(int rcId,int type,double balance)throws MyException;
	
	/**
	 * @param rcId			删除指定 id 的 房费记录
	 * @throws MyException
	 */
	public abstract void delChargeItem(int rcId)throws MyException;
	
	/**
	 * @param cid			删除对应入住信息 的所有 房费记录
	 * @throws MyException
	 */
	public abstract void delChargeItemByCheckId(int cid)throws MyException;
	/*----------------------------------------account-----------------------------------------*/
	
	/**
	 * @param account 入账， account的genTime 字段自动生成 其他必须指定
	 */
	public abstract void addAccount(Account account)throws MyException;
	
	/**
	 * @param cId  			入住id    指定-1 去掉过滤
	 * @param type			方式（现金 银行卡） 指定 -1 去掉过滤  1代表现金 0 代表银行卡
	 * @param isConsume		是否为消费   -1 代表去掉过滤，0 代表 false 1 代表 true
	 * @param beginDate 			入账开始日期    指定 null 去掉过滤
	 * @param endDate 				入账结束日期    指定 null 去掉过滤
	 * @return
	 * @throws MyException
	 */
	public abstract List<Account> getAccountByFilter(int cId,int type,int isConsume,String beginDate,String endDate)throws MyException; 
	
	/**
	 * @param cId  			入住id    指定-1 去掉过滤
	 * @param type			方式（现金 银行卡） 指定 -1 去掉过滤  1代表现金 0 代表银行卡
	 * @param isConsume		是否为消费   -1 代表去掉过滤，0 代表 false 1 代表 true
	 * @param beginDate 			入账开始日期    指定 null 去掉过滤
	 * @param endDate 				入账结束日期    指定 null 去掉过滤
	 * @return
	 * @throws MyException
	 */
	public abstract double sumAccountByFilter(int cId,int type,int isConsume,String beginDate,String endDate)throws MyException; 
	
	/**
	 * @param id	根据id删除 账目
	 * @return
	 * @throws MyException
	 */
	public abstract void delAccount(int id)throws MyException; 
	
	
	/**
	 * @param id
	 * @return 如果没有返回 一个 非法 Account 其 id 字段为无效标识
	 * @throws MyException
	 */
	public abstract Account getAccountById(int id)throws MyException;
	
	
	
	/*----------------------------------------room-----------------------------------------*/
	/**
	 * @param room  指定除了 cId 以外的所用字段
	 * @throws MyException
	 */
	public abstract void addRoom(Room room) throws MyException;
	/**
	 * @param id
	 * @throws MyException
	 */
	public abstract void deleteRoom(int id) throws MyException;
	/**
	 * @param id			
	 * @param description	说明
	 * @param type			类型
	 * @throws MyException
	 */
	public abstract void updatePartRoom(int id,String description,int type) throws MyException;
	/**
	 * @return 返回获取的list 不会是null
	 * @throws MyException
	 */
	public abstract List<Room> getAllRoom() throws MyException;
	/**
	 * @param id
	 * @return	如果 存在返回Room对象  否则返回一个非法对象 id字段为无效标识 StaticData.NOT_AVALIABLE
	 * @throws MyException
	 */
	public abstract Room getRoomById(int id) throws MyException;
	
	
	/**
	 * @param rId
	 * @param state	更新房间状态，（空房间状态 脏房，干净房，不可用房切换）并且将cid也会置空！！ ,如state 为无效值，将默认为干净房
	 * @throws MyException
	 */
	public abstract void updateRoomState(int rId,int state)throws MyException;
	
	
	
	/**
	 * @param rid  获取该room的入住信息号  房间号不存在 或者无入住信息 将返回 0
	 * @throws MyException
	 */
	public abstract int getRoomCheckId(int rid)throws MyException;
	
	/**
	 * @param cid 房间关联入住信息
	 * @throws MyException
	 */
	public abstract void updateRoomCheckId(int rid,int cid)throws MyException;
	
	
	/**
	 * 返回符合要求的在住的房间数目
	 * @param type		房间类型	-1去掉过滤
	 * @param enterDate	入住日期	null去掉过滤
	 * @param outDate	将要退房的日期 null去掉过滤	
	 * @return	过滤在住房间
	 * @throws MyException
	 */
	public abstract int countFilteredCheckedRoom(int type,String enterDate,String outDate)throws MyException;
	
	/**
	 * 返回符合要求的在住的房间列表
	 * @param type		房间类型	-1去掉过滤
	 * @param enterDate	入住日期	null去掉过滤
	 * @param outDate	将要退房的日期 null去掉过滤	
	 * @return	过滤在住房间
	 * @throws MyException
	 */
	public abstract List<RoomAndCheckIn> getFilteredCheckedRoom(int type,String enterDate,String outDate)throws MyException;

	/**
	 * @param type 指定-1 去掉过滤器 
	 * @param date	（outTime 大于 date的房间数）
	 * @return	返回从目前来看已经入住的房中，将会续住到 date 指定的日期 的房间数
	 * @throws MyException
	 */
	public abstract int countContinueCheckInRoomMoreThanSomeDay(int type,String date)throws MyException;
	
	
	/**
	 * @param type 指定 -1 去掉过滤器
	 * @return	  返回退房日期小于某天的所有房间 
	 * @throws MyException
	 */
	public abstract int countExpireRoom(int type,String date)throws MyException;
	
	
	/**
	 * 计算 某类型房间的个数  
	 * @param type
	 * @param includeNotAvaliable	是否包括不可用房
	 * @return
	 * @throws MyException
	 */
	public abstract int countRoomNumByType(int type,boolean includeNotAvaliable)throws MyException;
	
	
	/**
	 * @param type
	 * @param state
	 * @return   返回指定类型的空房 列表   state 只能是 脏 房 干净房，可用房，以及不可用房的id标示，如果为其他 将会返回对应类型的所用房间
	 * @throws MyException
	 */
	public abstract List<Room>  getEmptyRoom(int type,int state)throws MyException;

	/**
	 * @param type
	 * @param state
	 * @return   返回指定类型的空房 列表   state 只能是 脏 房 干净房，以及不可用房的id标示，如果为其他 将会返回所有对应类型空房
	 * @throws MyException
	 */
	public abstract int  countEmptyRoom(int type,int state)throws MyException;
	
	/*----------------------------------------order_data-----------------------------------------*/
	
	/**
	 * @param order  加入数据库，注意order的 id 以及 orderTime 两个字段由数据库自动生成！
	 * @throws MyException
	 */
	public abstract void addOrder(Order order) throws MyException;
	/**
	 * @param id
	 * @throws MyException
	 */
	public abstract void deleteOrder(int id) throws MyException;
	/**
	 * @param room 可以修改除了 id 之外的全部字段
	 * @throws MyException 
	 */
	public abstract void updateOrder(Order order) throws MyException;
	/**
	 * @return
	 * @throws MyException
	 */
	public abstract List<Order> getAllOrder() throws MyException;
	
	
	/**
	 * @param id
	 * @return	 如果不存在返回一个非法对象 id字段为无效标识 StaticData.NOT_AVALIABLE
	 */
	public abstract Order getOrderById(int id) throws MyException;

	
	/**
	 * @param id
	 * @param info	修改info 字段决定该订单是否有效，以及其他信息 来自 Oder类的静态字段
	 * @return
	 * @throws MyException
	 */
	public abstract void updateOrderInfo(int id,int info)throws MyException;
	
	
	/**
	 * @param id 判断指定订单 是否为今天入住
	 * @return 
	 * @throws MyException
	 */
	public abstract boolean orderIsToDay(int id)throws MyException;
	
	/**
	 * @param roomType  房间类型过滤器  指定-1去掉过滤
	 * @param info		 订单类型过滤器  指定3去掉过滤（Order静态字段）		
	 * @param enterDate	入住时间过滤器  指定null 去掉过滤
	 * @param orderDate 下单时间过滤  指定 null  去掉过滤
	 * @return
	 * @throws MyException
	 */
	public abstract int countFilteredOrder(int roomType,int info,String enterDate,String orderDate)throws MyException;
	
	/**
	 * @param roomType  房间类型过滤器  指定-1去掉过滤
	 * @param info		 订单类型过滤器  指定3去掉过滤（Order静态字段）		
	 * @param enterDate	入住时间过滤器  指定null 去掉过滤
	 * @param orderDate 下单时间过滤  指定 null  去掉过滤
	 * @return
	 * @throws MyException
	 */
	public abstract List<Order> getFilteredOrder(int roomType,int info,String enterDate,String orderDate)throws MyException;
	
	
	/**
	 * @param type 指定-1 去掉过滤器
	 * @param date 
	 * @return 返回 有效（今天或者以后入住且入住日期小于 date，还未入住的订单）且算出的退房时间 大于 某日的订单个数      
	 * @throws MyException
	 */
	public abstract int countAvalidateOrderOutDateMoreThanSomeDay(int type,String date) throws MyException;
	
	
	/**
	 * @param roomType  删除某类型房间的 所有订单（当删除房间类型时的级联删除）
	 * @throws MyException
	 */
	public abstract void delOrderByRoomType(int roomType)throws MyException;
	
	/*----------------------------------------check_in-----------------------------------------*/
	
	/**
	 * @param ci enterTime(自动生成),isCheckOut(默认为false)其他字段必须指定
	 * @throws MyException 
	 */
	public abstract void addCheckIn(CheckInInfo ci) throws MyException;
	/**
	 * @param id
	 * @throws MyException
	 */
	public abstract void deleteCheckIn(int id) throws MyException;
	/**
	 * @param ci 应当指定全部字段
	 * @throws MyException
	 */
	public abstract void updateCheckIn(CheckInInfo ci) throws MyException;
	/**
	 * @return	返回值不会为 null
	 * @throws MyException
	 */
	public abstract List<CheckInInfo> getAllCheckIn() throws MyException;
	/**
	 * @param id
	 * @return	如果不存在 返回 的是null
	 * @throws MyException
	 */
	public abstract CheckInInfo getCheckInById(int id) throws MyException;
	
	
	/**
	 * @param ci 更新部分CheckInInfo (orderId,name,phoneNumber,numberPeople,description,isHourRoom)
	 * @throws MyException
	 */
	public abstract void updateCheckInPart(CheckInInfo ci)throws MyException;
	
	
	/**
	 * @param cid
	 * @return 获取退房的时间，如果未退房那么代表应该退房的日期，如果已经退房代表当时退房的具体时间 
	 * @throws MyException
	 */
	public abstract String getCheckInOutDate(int cid)throws MyException;
	
	/**
	 * @param cid			入住信息号
	 * @param outDate 		将要续住到退房的日期
	 * @throws MyException
	 */
	public abstract void updateCheckInOutDate(int cid,String outDate)throws MyException;
	
	
	/**
	 * @return 获取check_in 表中最大id 本意是想获取最近一次加入的入住信息生成的 id
	 * @throws MyException
	 */
	public abstract int getMaxCheckId()throws MyException;
	
	/**
	 * @param cid	退房，更新入住信息
	 *  （房费正好才能退房，此部分逻辑放在 js 端，此处balance不做任何更新）
	 * @throws MyException
	 */
	public abstract void updateCheckOut(int cid)throws MyException;
	
	/**
	 * @param cid
	 * @return
	 * @throws MyException
	 */
	public abstract double getBalance(int cid)throws MyException;
	
	public abstract void updateBalance(int cid,double balance)throws MyException;
	
		
	/**
	 * @param enterDateBegin
	 * @param enterDateEnd
	 * @param outDateBegin
	 * @param outDateEnd
	 * @return		两个时间段 过滤器 （返回指定时间段入住，指定时间段退房的入住信息）
	 * @throws MyException
	 */
	public abstract List<CheckInInfo> getCheckInByFilter(String enterDateBegin,String enterDateEnd,String outDateBegin,String outDateEnd) throws MyException;
	
	
	
	/*----------------------------------------color-----------------------------------------*/
	/**
	 * @return 返回六种状态的颜色，以 房间状态号从小到大排序
	 * @throws MyException 
	 */
	public abstract List<RoomColor> getAllColor() throws MyException;
	
	/**
	 * @param state 用状态号 获取 RoomColor 对象
	 * @return
	 * @throws MyException
	 */
	public abstract RoomColor getRoomColorByState(int state)throws MyException;
	
	
	
	
	
	/*----------------------------------------price-----------------------------------------*/
	/**
	 * @return
	 * @throws MyException
	 */
	public abstract List<RoomPrice> getAllPrice() throws MyException;
	
	
	/**
	 * 获取第一个 roomPrice 对象  不存在返回一个非法对象 id字段为无效标识 StaticData.NOT_AVALIABLE
	 * @return
	 * @throws MyException
	 */
	public abstract RoomPrice getFirstRoomPrice() throws MyException;
	
	
	/**
	 * @param type 通过type  获取 RoomPrice 对象  不存在返回一个非法对象 id字段为无效标识 StaticData.NOT_AVALIABLE
	 * @return	如果 type无效 返回null
	 * @throws MyException
	 */
	public abstract RoomPrice getPriceByType(int type)throws MyException;
	
	/**
	 * 获取 对应房间类型的 房间数  (已被抛弃 （抛弃了 roomType 的 num 字段，依然可用，只是程序不再使用 ）取而代之的是 countRoomNumByType 函数)
	 * @param type	type 指定 -1 返回所用房间数
	 * @return		获取指定类型房间的总数（不包括 不可用房）
	 * @throws MyException
	 */
	public abstract int getRoomNumByType(int type)throws MyException;
	
	
	public abstract void addRoomType(RoomPrice rp)throws MyException;
	
	
	public abstract void delRoomType(int id)throws MyException;
	
	
	/**
	 * @param rp  除了 num 字段 其他全部指定
	 * @throws MyException
	 */
	public abstract void updateRoomType(RoomPrice rp)throws MyException;
	
	
	/**
	 * 更新一下 该类型房间的 房间数
	 * @param type
	 * @throws MyException
	 */
	public abstract void updateRoomTypeNum(int type)throws MyException;
	
	
	/**
	 * 	不存在返回一个非法对象 id字段为无效标识 StaticData.NOT_AVALIABLE
	 * @param name
	 * @return
	 * @throws MyException
	 */
	public abstract RoomPrice getRomTypeByName(String name)throws MyException;
	
	/*----------------------------------------login-----------------------------------------*/
	/**
	 * @param name
	 * @param psw
	 * @return 登录成功返回 0，密码错误返回1 ，用户不存在返回2,如果登录成功 user 中应当 获取 level 值
	 */
	public abstract int login(User user);
	
	public abstract String getSuperManagerPsw();
	
	
	
	/*-----------------------------------transaction-----------------------------------------*/
	
	/**
	 * 换房的操作
	 * @param rid
	 * @param tagetId
	 * @return 返回成功与否 或者抛异常
	 * @throws Exception
	 */
	public abstract boolean transactionForSwitchRoom(int rid,int tagetId) throws Exception;
	
	
	/**
	 * @param ci ci 字段除了时间 以及 id 其他字段必须填写完整！
	 * @param banlancetype
	 * @return
	 * @throws Exception
	 */
	public abstract boolean transactionForCheckIn(CheckInInfo ci,int banlancetype) throws Exception;
	
	
	/**
	 * 退房 事务
	 * @param roomId
	 * @return
	 * @throws Exception
	 */
	public abstract boolean transactionForCheckOut(int roomId) throws Exception;
	
	
	/**
	 * 关联新订单 修改 入住信息 事务
	 * @param cif
	 * @param modifyOrderId
	 * @param outDate
	 * @return
	 * @throws Exception
	 */
	public abstract boolean transactionForUpdateCheckInInfoOrder(CheckInInfo cif,int modifyOrderId,String outDate)throws Exception;
	
	
	
	
	
}