package com.hotel.mainaction;

import com.hotel.datamanage.Manager;
import com.hotel.entity.Order;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class AddOrderAction extends ActionSupport implements ModelDriven<Order>
{

	private static final long serialVersionUID = 1L;

	Order od = new Order();
	@Override
	public Order getModel()
	{
		// TODO Auto-generated method stub
		return od;
	}
	public Order getOd()
	{
		return od;
	}
	public void setOd(Order od)
	{
		this.od = od;
	}
	
	private String requestResult;
	public String getRequestResult()
	{
		return requestResult;
	}
	public void setRequestResult(String requestResult)
	{
		this.requestResult = requestResult;
	}
	
	//如果为 0 代表 是添加订单 否者 被认为修改订单
	public int addOrUpdate;
	
	public int getAddOrUpdate()
	{
		return addOrUpdate;
	}
	public void setAddOrUpdate(int addOrUpdate)
	{
		this.addOrUpdate = addOrUpdate;
	}
	public String execute()
	{
		
		Manager manager = Manager.getManager();
		if(addOrUpdate == 0)
		{
			requestResult = manager.addOrder(od);
		}
		else
			requestResult = manager.updateOrder(od);
		
		return "info";
		//以json 格式发送数据
//		return "updateOrderSuccess";
	}

	
	
	
}
