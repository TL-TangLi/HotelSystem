package com.hotel.mainaction;

import java.util.ArrayList;
import java.util.List;

import com.hotel.datamanage.Manager;
import com.hotel.entity.CheckInInfo;
import com.opensymphony.xwork2.ActionSupport;

public class CheckInInfoManagerAction extends ActionSupport
{
	private static final long serialVersionUID = -7549542292224011903L;
	
	/*-----------------------------------requestqueryCheckInAction-----------------------------------------*/
	Manager manager = Manager.getManager();
	
	
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
	public String execute()
	{
		
		if(enterDateBegin.equals(""))
			enterDateBegin =null;
		if(enterDateEnd.equals(""))
			enterDateEnd =null;
		if(outDateBegin.equals(""))
			outDateBegin =null;
		if(outDateEnd.equals(""))
			outDateEnd =null;
		
		listCheckInInfo = manager.getCheckInInfoByFilter(enterDateBegin,enterDateEnd,outDateBegin,outDateEnd);
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
		return "queryCheckInInfoSuccess";
	}
	
	/*-----------------------------------requestDelCheckInAction-----------------------------------------*/
	
	List<Integer> delIds = new ArrayList<Integer>();;
	
	public List<Integer> getDelIds()
	{
		return delIds;
	}
	public void setDelIds(List<Integer> delIds)
	{
		this.delIds = delIds;
	}
	public String requestDelCheckIn()
	{
		for(Integer id:delIds)
		{
			manager.delCheckIn(id);
		}
		return "delCheckInInfoSuccess";
	}
}
