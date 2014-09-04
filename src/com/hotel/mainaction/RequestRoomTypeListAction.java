package com.hotel.mainaction;

import java.util.List;

import net.sf.json.JSONArray;

import org.apache.struts2.json.annotations.JSON;

import com.hotel.datamanage.Manager;
import com.hotel.entity.RoomPrice;
import com.opensymphony.xwork2.ActionSupport;

public class RequestRoomTypeListAction extends ActionSupport
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
	public String execute()
	{
		
		Manager manager =  Manager.getManager();
		List<RoomPrice>	roomPriceList  = manager.getAllRoomPrice();

		JSONArray ja = new JSONArray();
		
		for(RoomPrice rp:roomPriceList)
		{
			ja.add(rp);
		}
		roomPriceJsonData = ja.toString();
		return "requestRoomTypeListSuccess";
	}
}
