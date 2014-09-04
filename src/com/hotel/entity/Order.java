package com.hotel.entity;

import net.sf.json.JSONArray;

/**
 * @author christopher
 *@info(name,phone_number,description,order_time,room_type,extend_hour,enterDate,days,rooms,info) 
 */
public class Order
{
	
	/*----------------------------------------订单 info 字段-----------------------------------------*/
	public static final int ORDER_TYPE_AVALIDATE = 0;		//有效未入住 订单  如果过了当天19点还未入住，也无效了
	public static final int ORDER_TYPE_CHECKED = 1;		//已经入住的订单 (不管有没有退房)
	public static final int ORDER_TYPE_GIVEUP = 2; 		//已说明放弃的订单 (作废的订单)
	public static final int ORDER_TYPE_IGNORE = 3;			//忽略 info 	
	
	
//	(name,phone_number,description,order_time,room_type,extend_hour,enterDate,days,rooms,info) 
	
	public int id;
	public String name;
	public String phoneNumber;
	public String description;
//	2007-08-01 23:34:22.
	public String orderTime;
	public int roomType;
	public int extendHour;
	public String enterDate;
	public int days ;
	public int rooms ;
	public int info;
	public String outDate;
	
	
	public String getOutDate()
	{
		return outDate;
	}
	public void setOutDate(String outDate)
	{
		this.outDate = outDate;
	}
	public Order(int id, String name, String phoneNumber, String description,
			String orderTime, int roomType, int extendHour, String enterDate,
			int days, int rooms, int info, String outDate)
	{
		super();
		this.id = id;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.description = description;
		this.orderTime = orderTime;
		this.roomType = roomType;
		this.extendHour = extendHour;
		this.enterDate = enterDate;
		this.days = days;
		this.rooms = rooms;
		this.info = info;
		this.outDate = outDate;
	}
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
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
	public String getDescription()
	{
		return description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
	public String getOrderTime()
	{
		return orderTime;
	}
	public void setOrderTime(String orderTime)
	{
		this.orderTime = orderTime;
	}
	public int getRoomType()
	{
		return roomType;
	}
	public void setRoomType(int roomType)
	{
		this.roomType = roomType;
	}
	public int getExtendHour()
	{
		return extendHour;
	}
	public void setExtendHour(int extendHour)
	{
		this.extendHour = extendHour;
	}
	public String getEnterDate()
	{
		return enterDate;
	}
	public void setEnterDate(String enterDate)
	{
		this.enterDate = enterDate;
	}
	public int getDays()
	{
		return days;
	}
	public void setDays(int days)
	{
		this.days = days;
	}
	public int getRooms()
	{
		return rooms;
	}
	public void setRooms(int rooms)
	{
		this.rooms = rooms;
	}
	public int getInfo()
	{
		return info;
	}
	public void setInfo(int info)
	{
		this.info = info;
	}
	public Order()
	{
	}
	
	
	public String toJsonString()
	{
		JSONArray ja = new JSONArray();
		ja.add(this);
		return ja.toString();
	}
	
	
}
