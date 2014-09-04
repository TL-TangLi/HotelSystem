package com.hotel.mainaction;

import java.util.ArrayList;
import java.util.List;

import com.hotel.datamanage.Manager;
import com.hotel.entity.Account;
import com.hotel.entity.AccountAndCheckIn;
import com.hotel.entity.ChargeAndCheckIn;
import com.hotel.entity.CheckInInfo;
import com.hotel.entity.Room;
import com.hotel.entity.RoomCharge;
import com.hotel.entity.RoomState;
import com.hotel.staticdata.StaticData;
import com.opensymphony.xwork2.ActionSupport;

public class BalanceManagerAction extends ActionSupport
{
	private static final long serialVersionUID = -5185177812556999600L;
	
	Manager  manager = Manager.getManager();
	int roomId;
	List<RoomCharge> listRoomCharge;			//房费列表
	List<List<Account>> listListAccount;		//账单列表
	public String beginDate;
	public String endDate;
	public double roomChargeSum = 0; 			//房费总额
	public double consumeSum = 0;				//消费总额
	public double addBalanceSum = 0;			//入账总额
	public int checkId;
	public int type = -1;  //默认情况  不筛选入账类型
	
	
	public double getRoomChargeSum()
	{
		return roomChargeSum;
	}
	public void setRoomChargeSum(double roomChargeSum)
	{
		this.roomChargeSum = roomChargeSum;
	}
	public List<RoomCharge> getListRoomCharge()
	{
		return listRoomCharge;
	}
	public void setListRoomCharge(List<RoomCharge> listRoomCharge)
	{
		this.listRoomCharge = listRoomCharge;
	}
	public int getType()
	{
		return type;
	}
	public void setType(int type)
	{
		this.type = type;
	}
	public int getCheckId()
	{
		return checkId;
	}
	public void setCheckId(int checkId)
	{
		this.checkId = checkId;
	}
	public String getBeginDate()
	{
		return beginDate;
	}
	public void setBeginDate(String beginDate)
	{
		this.beginDate = beginDate;
	}
	public String getEndDate()
	{
		return endDate;
	}
	public void setEndDate(String endDate)
	{
		this.endDate = endDate;
	}
	public double getConsumeSum()
	{
		return consumeSum;
	}
	public void setConsumeSum(double consumeSum)
	{
		this.consumeSum = consumeSum;
	}
	public double getAddBalanceSum()
	{
		return addBalanceSum;
	}
	public void setAddBalanceSum(double addBalanceSum)
	{
		this.addBalanceSum = addBalanceSum;
	}
	public List<List<Account>> getListListAccount()
	{
		return listListAccount;
	}
	public void setListListAccount(List<List<Account>> listListAccount)
	{
		this.listListAccount = listListAccount;
	}
	public int getRoomId()
	{
		return roomId;
	}
	public void setRoomId(int roomId)
	{
		this.roomId = roomId;
	}
	
	
	/*-----------------------------------刷新房价记录-----------------------------------------*/
	private  void refreshRoomCharge()
	{
		//构建 一个 RoomState 看似毫无意义，这里的作用只是利用构造函数，调用updateRoomChargeItemAndGetRoomCharge() 刷新房费。
		Room room  = manager.getRoomById(roomId);
		if(room != null && room.id != StaticData.NOT_VALIDATE)
			 new RoomState(room,manager.getRoomPriceByType(room.type),manager.getCheckInById(room.cId));
	}
	
	/*-----------------------------------requestDelAccountAction-----------------------------------------*/
	List<Integer> delIds = new ArrayList<Integer>();;
	List<Integer> delChargeIds = new ArrayList<Integer>();
	
	public List<Integer> getDelChargeIds()
	{
		return delChargeIds;
	}
	public void setDelChargeIds(List<Integer> delChargeIds)
	{
		this.delChargeIds = delChargeIds;
	}
	public List<Integer> getDelIds()
	{
		return delIds;
	}
	public void setDelIds(List<Integer> delIds)
	{
		this.delIds = delIds;
	}
	
	public String delPsw;
 	public String getDelPsw()
	{
		return delPsw;
	}
	public void setDelPsw(String delPsw)
	{
		this.delPsw = delPsw;
	}
	private void delAccounts()
	{
		for(Integer id:delIds)
		{
			manager.delAccountItem(id);
		}
		for(Integer id:delChargeIds)
		{
			manager.delOneChargeItem(id);
		}
	}
	
	//（未退房 删除 账目）
	public String requestDelAccountInRoom()
	{
		delAccounts();
		
		refreshRoomCharge();
		return "requestDelAccountInRoomActionSuccess";
	}
	
	//账目总结中删
	public String requestDelAccountInSum()
	{
		delAccounts();
		return "requestDelAccountInSumActionSuccess";
	}
	
	// （已退房 删除账目）
	public String requestDelAccountInHadCheckOut()
	{
		delAccounts();
		return "requestDelAccountInHadCheckOutActionSuccess";
	}
	
	/*-----------------------------------requestBalanceDetailAction-----------------------------------------*/
	
	private void getBalanceDetail(int checkId)
	{
		if(beginDate != null &&beginDate.equals(""))
			beginDate =null;
		if(endDate!= null &&endDate.equals(""))
			endDate =null;
		
		listListAccount = new ArrayList<List<Account>>();
		
		//入账链表
		listListAccount.add(manager.getAccountByFilter(checkId,type,0,beginDate,endDate));
		
		//消费链表  （消费链表没有 消费类型 应当指定 为 -1）
		listListAccount.add(manager.getAccountByFilter(checkId,-1,1,beginDate,endDate));
	
		//房费列表
		listRoomCharge = manager.getChargeItemByFilter(checkId,beginDate,endDate);
		
		for(Account account :listListAccount.get(0))
			addBalanceSum += account.balance;
		
		for(Account account :listListAccount.get(1))
			consumeSum += account.balance;
		
		for(RoomCharge rc:listRoomCharge)
			roomChargeSum += rc.charge;
	}
	//从房间 访问  以及 从 入住信息统计中访问（客户未退房）
	public String requestBalanceDetail()
	{
		//即便是给出了 checkId 也要先通过房间查询，防止 已经退房
		int temp = manager.getCheckIdByRoom(roomId);
		
		//说明已经退房 或者异常
		if(temp <= 0)
			return requestBalanceDetailHadCheckOut();
		else
			checkId = temp;  	//这一步。。不是多余的（正常情况下 temp 就等于checkId）

		
		getBalanceDetail(checkId);
		
		//刷新房费
		refreshRoomCharge();
		
		//更新balance (不是必要的 只是尽量保持 房费为最新！！)
		manager.updateCheckInBalance(checkId);
		
		return "requestBalanceDetailSuccess";
		
	}
	
	
	public CheckInInfo cif;
	public CheckInInfo getCif()
	{
		return cif;
	}
	public void setCif(CheckInInfo cif)
	{
		this.cif = cif;
	}
	//从入住信息统计中双击访问 (该客户已经退房)
	public String requestBalanceDetailHadCheckOut()
	{
		getBalanceDetail(checkId);
		
		cif = manager.getCheckInById(checkId);
		
		//说明 在某个页面正好在这一刻删除 了该条入住信息  或者是数据库异常
		if(cif == null || cif.id == StaticData.NOT_VALIDATE)
			return "requestBalanceDetailHadCheckOutFaild";
		
		cif.allAddBalance = manager.sumAccountByFilter(cif.id,-1,0,null,null);
		cif.allConsume = manager.sumAccountByFilter(cif.id,-1,1,null,null);
		
		return "requestBalanceDetailHadCheckOutSuccess";
	}
	
	
	
	/*-----------------------------------requestqueryAccountSumAction-----------------------------------------*/
	public List<List<AccountAndCheckIn>> listListAccountAndCheckIn;
	public List<ChargeAndCheckIn> listChargeAndCheckIn;
	
	public List<ChargeAndCheckIn> getListChargeAndCheckIn()
	{
		return listChargeAndCheckIn;
	}
	public void setListChargeAndCheckIn(List<ChargeAndCheckIn> listChargeAndCheckIn)
	{
		this.listChargeAndCheckIn = listChargeAndCheckIn;
	}
	public List<List<AccountAndCheckIn>> getListListAccountAndCheckIn()
	{
		return listListAccountAndCheckIn;
	}
	public void setListListAccountAndCheckIn(
			List<List<AccountAndCheckIn>> listListAccountAndCheckIn)
	{
		this.listListAccountAndCheckIn = listListAccountAndCheckIn;
	}
	public String requestqueryAccountSum()
	{
		if(beginDate.equals(""))
			beginDate =null;
		if(endDate.equals(""))
			endDate =null;
		
		listListAccountAndCheckIn = new ArrayList<List<AccountAndCheckIn>>();
		listListAccount = new ArrayList<List<Account>>();
		
		
		//入账链表
		listListAccount.add(manager.getAccountByFilter(-1,type,0,beginDate,endDate));
		
		//消费链表  （消费链表没有 消费类型 应当指定 为 -1）
		listListAccount.add(manager.getAccountByFilter(-1,-1,1,beginDate,endDate));
		
		
		
		//入账 入住 链表
		List<AccountAndCheckIn> listAccountAndCheckIn = new ArrayList<AccountAndCheckIn>();
		listListAccountAndCheckIn.add(listAccountAndCheckIn);
		for(Account account :listListAccount.get(0))
		{
			addBalanceSum += account.balance;
			listAccountAndCheckIn.add(new AccountAndCheckIn(account,manager.getCheckInById(account.cId)));
		}
		
		
		//消费 入住 链表
		listAccountAndCheckIn = new ArrayList<AccountAndCheckIn>();
		listListAccountAndCheckIn.add(listAccountAndCheckIn);
		for(Account account :listListAccount.get(1))
		{
			consumeSum += account.balance;
			listAccountAndCheckIn.add(new AccountAndCheckIn(account,manager.getCheckInById(account.cId)));
		}
		
		//房费链表
		listChargeAndCheckIn = new ArrayList<ChargeAndCheckIn>();
		listRoomCharge = manager.getChargeItemByFilter(-1,beginDate,endDate);
		for(RoomCharge charge :listRoomCharge)
		{
			roomChargeSum += charge.charge;
			listChargeAndCheckIn.add(new ChargeAndCheckIn(charge,manager.getCheckInById(charge.cId)));
		}
		
		return "requestqueryAccountSumSuccess";
	}

	
	/*----------------------------------------requestAddBalanceAction-----------------------------------------*/
	
	public double addBalance;
	public String requestResult;
	public boolean willCheckOut;
	
	
	public boolean isWillCheckOut()
	{
		return willCheckOut;
	}
	public void setWillCheckOut(boolean willCheckOut)
	{
		this.willCheckOut = willCheckOut;
	}
	public String getRequestResult()
	{
		return requestResult;
	}
	public void setRequestResult(String requestResult)
	{
		this.requestResult = requestResult;
	}
	public double getAddBalance()
	{
		return addBalance;
	}
	public void setAddBalance(double addBalance)
	{
		this.addBalance = addBalance;
	}
	public String requestAddBalance()
	{
		int cid = manager.getCheckIdByRoom(roomId);
		if(cid <= 0)
		{
			requestResult = "操作失败，检测到房间已经退房。请刷新！";
			return "requestAddBalanceFailed";
		}
		
		requestResult = manager.addBalanceAndConsume(new Account(roomId,cid,description,type,false,addBalance,null,0));
		
		if(willCheckOut)
			manager.checkOut(roomId);
		else
			refreshRoomCharge();
		return "requestAddBalanceSuccess";
	}
	
	/*----------------------------------------requestConsumeAction-----------------------------------------*/
	public double consumeBalance;
	public String description ;
	
	public String getDescription()
	{
		return description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
	public double getConsumeBalance()
	{
		return consumeBalance;
	}
	public void setConsumeBalance(double consumeBalance)
	{
		this.consumeBalance = consumeBalance;
	}
	
	public String requestConsume()
	{
		int cid = manager.getCheckIdByRoom(roomId);
		if(cid <= 0)
		{
			requestResult = "操作失败，检测到房间已经退房。请刷新！";
			return "requestConsumeFailed";
		}
		requestResult = manager.addBalanceAndConsume(new Account(roomId,cid,description,3,true,consumeBalance,null,0));
	
		refreshRoomCharge();
		return "requestConsumeSuccess";
	}
	
	/*-----------------------------------resetRoomChargeAction-----------------------------------------*/
	
	public String resetRoomCharge()
	{
		manager.delChargeItemByCheckInId(manager.getCheckIdByRoom(roomId));
		
		refreshRoomCharge();
		return "resetRoomChargeActionSuccess";
	}
}
