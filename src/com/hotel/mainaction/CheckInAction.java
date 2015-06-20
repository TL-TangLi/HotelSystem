package com.hotel.mainaction;

import java.util.ArrayList;
import java.util.List;

import com.hotel.datamanage.Manager;
import com.hotel.entity.CheckInInfo;
import com.opensymphony.xwork2.ModelDriven;

public class CheckInAction extends BaseAction implements ModelDriven<CheckInInfo>
{
	private static final long serialVersionUID = 1L;

	private int days;
	private int balanceType;

	public int getBalanceType()
	{
		return balanceType;
	}

	public void setBalanceType(int balanceType)
	{
		this.balanceType = balanceType;
	}

	public int getDays()
	{
		return days;
	}

	public void setDays(int days)
	{
		this.days = days;
	}

	private CheckInInfo cif = new CheckInInfo();

	@Override
	public CheckInInfo getModel()
	{
		return cif;
	}

	public CheckInInfo getCif()
	{
		return cif;
	}

	public void setCif(CheckInInfo cif)
	{
		this.cif = cif;
	}

	public String info()
	{

		Manager manager = Manager.getManager();
		requestResult = manager.checkIn(cif, days, balanceType);

		return "info";
	}

	// / 如果要修改 订单号，那么 modifyOrder 记录的是先前的 订单号 如果现在订单号和modifyOrder 不同说明已经修改订单号
	public int modifyOrder;

	public int getModifyOrder()
	{
		return modifyOrder;
	}

	public void setModifyOrder(int modifyOrder)
	{
		this.modifyOrder = modifyOrder;
	}

	public String update()
	{
		Manager manager = Manager.getManager();
		requestResult = manager.updateCheckInInfoPart(cif, modifyOrder);
		return "update";
	}
	
	
	
	/*----------------------------------------------------------------------------*/
	
	List<Integer> delIds = new ArrayList<Integer>();;
	
	public List<Integer> getDelIds()
	{
		return delIds;
	}
	public void setDelIds(List<Integer> delIds)
	{
		this.delIds = delIds;
	}
	public String del()
	{
		for(Integer id:delIds)
		{
			manager.delCheckIn(id);
		}
		return "del";
	}
	
	
	List<CheckInInfo> listCheckInInfo;
	public List<CheckInInfo> getListCheckInInfo()
	{
		return listCheckInInfo;
	}
	public void setListCheckInInfo(List<CheckInInfo> listCheckInInfo)
	{
		this.listCheckInInfo = listCheckInInfo;
	}
	public String enterDateBegin;
	public String enterDateEnd;
	public String outDateBegin;
	public String outDateEnd;
	public String guestName;
	public String remark;
	public String phone;
	public String roomName;
	 
	
	public String getGuestName()
	{
		return guestName;
	}
	public void setGuestName(String guestName)
	{
		this.guestName = guestName;
	}
	public String getRoomName()
	{
		return roomName;
	}
	public void setRoomName(String roomName)
	{
		this.roomName = roomName;
	}
	public String getRemark()
	{
		return remark;
	}
	public void setRemark(String remark)
	{
		this.remark = remark;
	}
	public String getPhone()
	{
		return phone;
	}
	public void setPhone(String phone)
	{
		this.phone = phone;
	}
	public String getEnterDateBegin()
	{
		return enterDateBegin;
	}
	public void setEnterDateBegin(String enterDateBegin)
	{
		this.enterDateBegin = enterDateBegin;
	}
	public String getEnterDateEnd()
	{
		return enterDateEnd;
	}
	public void setEnterDateEnd(String enterDateEnd)
	{
		this.enterDateEnd = enterDateEnd;
	}
	public String getOutDateBegin()
	{
		return outDateBegin;
	}
	public void setOutDateBegin(String outDateBegin)
	{
		this.outDateBegin = outDateBegin;
	}
	public String getOutDateEnd()
	{
		return outDateEnd;
	}
	public void setOutDateEnd(String outDateEnd)
	{
		this.outDateEnd = outDateEnd;
	}

	public String query()
	{
		
		if(enterDateBegin.equals(""))
			enterDateBegin =null;
		if(enterDateEnd.equals(""))
			enterDateEnd =null;
		if(outDateBegin.equals(""))
			outDateBegin =null;
		if(outDateEnd.equals(""))
			outDateEnd =null;
		
		listCheckInInfo = manager.getCheckInInfoByFilter(enterDateBegin,enterDateEnd,outDateBegin,outDateEnd, guestName, remark, roomName, phone);
		for(CheckInInfo info:listCheckInInfo)
		{
			info.allAddBalance = manager.sumAccountByFilter(info.id,-1,0,null,null);
			info.allConsume = manager.sumAccountByFilter(info.id,-1,1,null,null);
			
			//如果房间没退，那么生成最新的房费 （只是获取最新房费  balance 字段不做改变）
			if(info.isCheckOut)
				info.balance = manager.sumChargeItemByFilter(info.id,null,null);
			
			///balance 字段重新生成
//			manager.updateCheckInBalance(info.id);
			
		}
		return "query";
	}
	
	
	private int roomId;
	public int getRoomId()
	{
		return roomId;
	}
	public void setRoomId(int roomId)
	{
		this.roomId = roomId;
	}
	int continueDays;
	public int getContinueDays()
	{
		return continueDays;
	}
	public void setContinueDays(int continueDays)
	{
		this.continueDays = continueDays;
	}

	public String requestContinue()
	{
		requestResult =  manager.continueCheckIn(roomId,continueDays);
		
		return "requestContinue";
	}
	
	
	
	
	

}
