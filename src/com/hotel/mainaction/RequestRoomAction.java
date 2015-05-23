package com.hotel.mainaction;

import java.io.IOException;

import org.apache.struts2.json.annotations.JSON;

import com.hotel.datamanage.Manager;
import com.hotel.entity.Room;
import com.hotel.entity.RoomState;
import com.hotel.staticdata.StaticData;
import com.opensymphony.xwork2.ActionSupport;

public class RequestRoomAction extends ActionSupport
{
	private static final long serialVersionUID = 1L;
	
	int roomId;
	public int getRoomId()
	{
		return roomId;
	}
	public void setRoomId(int roomId)
	{
		this.roomId = roomId;
	}

	//ajax 异步 操作， jsonData 是将会被送回客服端的数据
	private String jsonForRoomState;
	@JSON(name="jsonData")
	public String getJsonForRoomState()
	{
		return jsonForRoomState;
	}
	public void setJsonForRoomState(String jsonForRoomState)
	{
		this.jsonForRoomState = jsonForRoomState;
	}
	
	public String execute() throws IOException
	{
		Manager manager = Manager.getManager();
		Room room  = manager.getRoomById(roomId);
		if(room != null && room.id != StaticData.NOT_VALIDATE)
		{
			RoomState rs = new RoomState(room,manager.getRoomPriceByType(room.type),manager.getCheckInById(room.cId));
			if(rs.checkInInfo != null && rs.checkInInfo.id != StaticData.NOT_VALIDATE)
				rs.checkInInfo.allAddBalance = manager.sumAccountByFilter(rs.checkInInfo.id,-1,0,null,null);
			setJsonForRoomState(rs.toJsonString());
			
		}
		else
			jsonForRoomState = null;
		return "info";
	}

	
}
