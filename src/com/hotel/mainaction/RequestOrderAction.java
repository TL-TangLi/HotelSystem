package com.hotel.mainaction;

import org.apache.struts2.json.annotations.JSON;

import com.hotel.datamanage.Manager;
import com.hotel.entity.Order;
import com.hotel.staticdata.StaticData;
import com.opensymphony.xwork2.ActionSupport;

public class RequestOrderAction extends ActionSupport
{
	private static final long serialVersionUID = 1L;
	
	Order od;

	int orderId;
	
	
	public int getOrderId()
	{
		return orderId;
	}
	public void setOrderId(int orderId)
	{
		this.orderId = orderId;
	}
	
	String jsonForOrder;
	
	@JSON(name="jsonOrder")
	public String getJsonForOrder()
	{
		return jsonForOrder;
	}
	public void setJsonForOrder(String jsonForOrder)
	{
		this.jsonForOrder = jsonForOrder;
	}
	public Order getOd()
	{
		return od;
	}
	
	public void setOd(Order od)
	{
		this.od = od;
	}
	public String execute()
	{
		Manager manager = Manager.getManager();
		od = manager.getOrderById(orderId);
		if(od == null || od.id == StaticData.NOT_VALIDATE)
			jsonForOrder = null;
		else
			jsonForOrder = od.toJsonString();
		
		return SUCCESS;
	}

}
