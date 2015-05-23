package com.hotel.mainaction;

import com.hotel.datamanage.Manager;
import com.hotel.entity.Room;
import com.opensymphony.xwork2.ModelDriven;

public class RoomAction extends BaseAction implements ModelDriven<Room>
{
	private static final long serialVersionUID = 1L;

	/*-----------------------------------addRoomActionForRoom-----------------------------------------*/
	private Room room = new Room();

	public Room getRoom()
	{
		return room;
	}

	public void setRoom(Room room)
	{
		this.room = room;
	}

	@Override
	public Room getModel()
	{
		return room;
	}

	public String add()
	{
		room.isAvaliable = true;
		room.isClean = true;

		Manager manager = Manager.getManager();
		requestResult = manager.addRoom(room);
		return "add";
	}

	/*----------------------------------------requestChangeRoomStateActionForRoom----andDeleteRoom-----------------------------------------*/
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

	public String changeState()
	{
		// 删除房间
		if (changeState == 0)
		{
			requestResult = manager.deleteRoom(roomId);
		}
		// 修改房间状态
		else
			requestResult = manager.changeRoomState(roomId, changeState);

		return "changeState";
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
		requestResult = manager.updatePartRoom(roomId, changeRoomDescription, changeRoomType, oldType);
		return "changeRoomType";
	}

	private int targetId;

	public int getTargetId()
	{
		return targetId;
	}

	public void setTargetId(int targetId)
	{
		this.targetId = targetId;
	}

	public String switchRoom()
	{
		requestResult = manager.switchRoom(roomId, targetId);
		return "switchRoom";
	}

}
