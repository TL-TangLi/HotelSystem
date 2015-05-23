package com.hotel.mainaction;

import com.hotel.datamanage.Manager;
import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport
{
	private static final long serialVersionUID = 1L;

	Manager manager = Manager.getManager();

	// 如果为 0 代表 是添加订单 否者 被认为修改订单
	public int addOrUpdate;
	public String requestResult;
	
	
	
	
	

	public int getAddOrUpdate()
	{
		return addOrUpdate;
	}

	public void setAddOrUpdate(int addOrUpdate)
	{
		this.addOrUpdate = addOrUpdate;
	}


	public String getRequestResult()
	{
		return requestResult;
	}

	public void setRequestResult(String requestResult)
	{
		this.requestResult = requestResult;
	}
}
