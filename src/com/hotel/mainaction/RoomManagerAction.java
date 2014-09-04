package com.hotel.mainaction;

import com.hotel.datamanage.Manager;
import com.hotel.entity.Room;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class RoomManagerAction extends ActionSupport implements ModelDriven<Room>
{
	private static final long serialVersionUID = 1L;

	
	/*-----------------------------------addRoomActionForRoom-----------------------------------------*/
	private Room room = new Room();
	private String requestResult;
	
	public Room getRoom()
	{
		return room;
	}

	public void setRoom(Room room)
	{
		this.room = room;
	}

	public String getRequestResult()
	{
		return requestResult;
	}

	public void setRequestResult(String requestResult)
	{
		this.requestResult = requestResult;
	}

	@Override
	public Room getModel()
	{
		return room;
	}
	
	
	public String addRoom()
	{
		room.isAvaliable  = true;
		room.isClean =  true;
		
		Manager manager = Manager.getManager();
		requestResult = manager.addRoom(room);
		return "addRoomResult";
	}
	
	
	
	/*----------------------------------------requestChangeRoomStateActionForRoom----andDeleteRoom-----------------------------------------*/
	Manager manager = Manager.getManager();
	public int roomId;
	public int getRoomId()
	{
		return roomId;
	}

	public void setRoomId(int roomId)
	{
		this.roomId = roomId;
	}


	private int changeState;
	public int getChangeState()
	{
		return changeState;
	}
	public void setChangeState(int changeState)
	{
		this.changeState = changeState;
	}
	public String requestChangeRoomState()
	{
		//删除房间
		if(changeState == 0)
		{
			requestResult = manager.deleteRoom(roomId);
		}
		//修改房间状态
		else
			requestResult = manager.changeRoomState(roomId,changeState);
		
		
		return "requestChangeRoomStateSuccess";
	}
	
	
	
	/*-----------------------------------changeRoomTypeActionForRoom-----------------------------------------*/
	
	public int changeRoomType;
	public String changeRoomDescription;
	public int oldType;
	
	
	public int getOldType()
	{
		return oldType;
	}

	public void setOldType(int oldType)
	{
		this.oldType = oldType;
	}

	public String getChangeRoomDescription()
	{
		return changeRoomDescription;
	}

	public void setChangeRoomDescription(String changeRoomDescription)
	{
		this.changeRoomDescription = changeRoomDescription;
	}

	public int getChangeRoomType()
	{
		return changeRoomType;
	}

	public void setChangeRoomType(int changeRoomType)
	{
		this.changeRoomType = changeRoomType;
	}

	public String changeRoomType()
	{
		requestResult  = manager.updatePartRoom(roomId,changeRoomDescription,changeRoomType,oldType);
		return "changeRoomTypeResult";
	}
	
	
	
	
}
