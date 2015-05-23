package com.hotel.mainaction;

import java.util.List;

import net.sf.json.JSONArray;

import org.apache.struts2.json.annotations.JSON;

import com.hotel.datamanage.Manager;
import com.hotel.entity.RoomPrice;
import com.hotel.staticdata.StaticData;

public class RoomTypeAction extends BaseAction
{

	private static final long serialVersionUID = 1L;

	
	String roomPriceJsonData;
	@JSON(name="jsonData")
	public String getRoomPriceJsonData()
	{
		return roomPriceJsonData;
	}
	public void setRoomPriceJsonData(String roomPriceJsonData)
	{
		this.roomPriceJsonData = roomPriceJsonData;
	}
	public String query()
	{
		
		Manager manager =  Manager.getManager();
		List<RoomPrice>	roomPriceList  = manager.getAllRoomPrice();

		JSONArray ja = new JSONArray();
		
		for(RoomPrice rp:roomPriceList)
		{
			ja.add(rp);
		}
		roomPriceJsonData = ja.toString();
		return "query";
	}
	
	public String add()
	{
		requestResult = manager.addRoomType(new RoomPrice(0,description,price,0,hourPrice));
		return "add";
	}
	
	/*-----------------------------------requestUpdateRoomTypeAction-----------------------------------------*/
	
	public String update()
	{
		requestResult = manager.updateRoomType(new RoomPrice(type,description,price,num,hourPrice));
		return "update";
	}
	
	/*-----------------------------------requestDelRoomTypeAction-----------------------------------------*/
	
	public String del()
	{
		requestResult = manager.delRoomType(type);
		return "del";
	}
	
	
	int type = StaticData.ROOM_TYPE_IGNORE;
	String description;
	int hourPrice;
	int price;
	int num;
	
	public int getType()
	{
		return type;
	}
	public void setType(int type)
	{
		this.type = type;
	}
	public int getNum()
	{
		return num;
	}
	public void setNum(int num)
	{
		this.num = num;
	}
	public String getDescription()
	{
		return description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
	public int getHourPrice()
	{
		return hourPrice;
	}
	public void setHourPrice(int hourPrice)
	{
		this.hourPrice = hourPrice;
	}
	public int getPrice()
	{
		return price;
	}
	public void setPrice(int price)
	{
		this.price = price;
	}

}
