package com.hotel.entity;


/**
 * @author christopher
 * @info //rId,orderId,name,phoneNumber,numberPeople,description,
 * // enterTime(自动生成),outTime(暂时为空),isCheckOut(默认为false),balance,isHourRoom
 * 
 * balance 字段一直记录 房间 的房费总数  （仅仅当查看房间详细账单的时候更新到数据库，或者在最后退房的时候，平时balance虽然统计了最新房费（入住信息统计中），但不代表它已经写入数据库）
 */
public class CheckInInfo
{
	//总消费
	public double allConsume;
	//总入账
	public double allAddBalance;

	public double getAllConsume()
	{
		return allConsume;
	}
	public void setAllConsume(double allConsume)
	{
		this.allConsume = allConsume;
	}
	public double getAllAddBalance()
	{
		return allAddBalance;
	}
	public void setAllAddBalance(double allAddBalance)
	{
		this.allAddBalance = allAddBalance;
	}

	
	
	public int id ;
	public int rId;
	public int orderId;
	public String name;
	public String phoneNumber;
	public int numberPeople;
	public String description;
	public String enterTime;
	public String outTime;
	public boolean isCheckOut;
	public double balance;
	public boolean isHourRoom;
	
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public int getrId()
	{
		return rId;
	}
	public void setrId(int rId)
	{
		this.rId = rId;
	}
	public int getOrderId()
	{
		return orderId;
	}
	public void setOrderId(int orderId)
	{
		this.orderId = orderId;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getPhoneNumber()
	{
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}
	public int getNumberPeople()
	{
		return numberPeople;
	}
	public void setNumberPeople(int numberPeople)
	{
		this.numberPeople = numberPeople;
	}
	public String getDescription()
	{
		return description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
	public String getEnterTime()
	{
		return enterTime;
	}
	public void setEnterTime(String enterTime)
	{
		this.enterTime = enterTime;
	}
	public String getOutTime()
	{
		return outTime;
	}
	public void setOutTime(String outTime)
	{
		this.outTime = outTime;
	}
	public boolean isCheckOut()
	{
		return isCheckOut;
	}
	public void setCheckOut(boolean isCheckOut)
	{
		this.isCheckOut = isCheckOut;
	}
	public double getBalance()
	{
		return balance;
	}
	public void setBalance(double balance)
	{
		this.balance = balance;
	}
	public boolean getHourRoom()
	{
		return isHourRoom;
	}
	public void setHourRoom(boolean isHourRoom)
	{
		this.isHourRoom = isHourRoom;
	}

	public CheckInInfo(int id, int rId, int orderId, String name,
			String phoneNumber, int numberPeople, String description,
			String enterTime, String outTime, boolean isCheckOut,
			double balance, boolean isHourRoom)
	{
		super();
		this.id = id;
		this.rId = rId;
		this.orderId = orderId;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.numberPeople = numberPeople;
		this.description = description;
		this.enterTime = enterTime;
		this.outTime = outTime;
		this.isCheckOut = isCheckOut;
		this.balance = balance;
		this.isHourRoom = isHourRoom;
	}
	
	public CheckInInfo(){}
	
	

	
	
}
