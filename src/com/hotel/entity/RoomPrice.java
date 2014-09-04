package com.hotel.entity;


/**
 * @author christopher
 * @info id decription price RoomPrice 不只是 房价 那么简单，它包含了房间 剩余 统计的详情！！
 */
public class RoomPrice implements Cloneable
{
	@Override
	public Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}
	public int id;
	public String description;
	public double price;
	public int num ;
	public double hourPrice;
	public RoomPrice(int id, String description, double price, int num,
			double hourPrice)
	{
		super();
		this.id = id;
		this.description = description;
		this.price = price;
		this.num = num;
		this.hourPrice = hourPrice;
	}
	public double getHourPrice()
	{
		return hourPrice;
	}
	public void setHourPrice(double hourPrice)
	{
		this.hourPrice = hourPrice;
	}
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getDescription()
	{
		return description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
	public double getPrice()
	{
		return price;
	}
	public void setPrice(double price)
	{
		this.price = price;
	}
	public int getNum()
	{
		return num;
	}
	public void setNum(int num)
	{
		this.num = num;
	}
	public RoomPrice(){}
	
	
	
	//正入住的房间数（续住）
	public int checkIngCount;
	//待入住的房间数
	public int waitCheckInCount;
	
	
	
	//////用于今天房态
	//过期房
	public int expireCount;
	
	//待退房
	public int todayCheckOut;
	
	//所有在住
	public int allcheckIngCount;
	
}
