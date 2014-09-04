package com.hotel.entity;

/**
 * @author christopher
 *	//和账单 的区别是 没有 isConsume 和 description 字段
 */
public class RoomCharge
{
	//和账单 的区别是 没有 isConsume 和 description 字段
	
	public static final int ROOM_CHARGE_ALLDAY = 1;
	public static final int ROOM_CHARGE_HALFDAY = 2;
	public static final int ROOM_CHARGE_HOUR = 3;
	
	
	public int id;
	public int cId;			//入住信息id
	public int rId;			//房间 id 
	public double charge;	//房费
	public int type;	  	//房费 类型。 全天房费，半天房费 ，和小时房费。
	public String date;		//房费产生日期。
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public int getcId()
	{
		return cId;
	}
	public void setcId(int cId)
	{
		this.cId = cId;
	}
	public int getrId()
	{
		return rId;
	}
	public void setrId(int rId)
	{
		this.rId = rId;
	}
	public double getCharge()
	{
		return charge;
	}
	public void setCharge(double charge)
	{
		this.charge = charge;
	}
	public int getType()
	{
		return type;
	}
	public void setType(int type)
	{
		this.type = type;
	}
	public String getDate()
	{
		return date;
	}
	public void setDate(String date)
	{
		this.date = date;
	}
	public RoomCharge(int id, int cId, int rId, double charge, int type,
			String date)
	{
		super();
		this.id = id;
		this.cId = cId;
		this.rId = rId;
		this.charge = charge;
		this.type = type;
		this.date = date;
	}
	public RoomCharge()
	{
	}
	
	
}
