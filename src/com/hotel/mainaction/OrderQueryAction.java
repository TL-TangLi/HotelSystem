package com.hotel.mainaction;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.hotel.datamanage.Manager;
import com.hotel.entity.Order;
import com.hotel.entity.RoomPrice;
import com.hotel.staticdata.StaticData;
import com.opensymphony.xwork2.ActionSupport;

public class OrderQueryAction extends ActionSupport
{

	private static final long serialVersionUID = 1L;

	Manager manager = Manager.getManager();
	
	//二维链表！！
	List<List<Order>> listListOrder ;
	
	public List<List<Order>> getListListOrder()
	{
		return listListOrder;
	}

	public void setListListOrder(List<List<Order>> listListOrder)
	{
		this.listListOrder = listListOrder;
	}
	//保存房间类型
	List<RoomPrice> listRoomPrice;
	public List<RoomPrice> getListRoomPrice()
	{
		return listRoomPrice;
	}

	public void setListRoomPrice(List<RoomPrice> listRoomPrice)
	{
		this.listRoomPrice = listRoomPrice;
	}
	
	///如果没提交订单 默认为 当天 入住订单查询
	int info = Order.ORDER_TYPE_IGNORE;
	String enterDate = StaticData.CalendarToString(Calendar.getInstance());
	String orderTime = null;
	
	public int getInfo()
	{
		return info;
	}

	public void setInfo(int info)
	{
		this.info = info;
	}

	public String getEnterDate()
	{
		return enterDate;
	}

	public void setEnterDate(String enterDate)
	{
		this.enterDate = enterDate;
	}

	public String getOrderTime()
	{
		return orderTime;
	}

	public void setOrderTime(String orderTime)
	{
		this.orderTime = orderTime;
	}

	public String execute()
	{
		
		listRoomPrice = manager.getAllRoomPrice();
		listListOrder = new ArrayList<List<Order>>();
		
		if(enterDate!= null &&enterDate.equals(""))
			enterDate =null;
		
		
		if(orderTime!=null&&orderTime.equals(""))
			orderTime =null;
		
		for(RoomPrice rp:listRoomPrice)
		{
			
			List<Order> listOrder = new ArrayList<Order>();
			
			//list 中至少要有一个元素 目的是 为了 迭代获取房间类型，第一个元素被隐藏了。
			Order firstOrder = new Order();
			firstOrder.roomType = rp.id;
			listOrder.add(firstOrder);
			
			listOrder.addAll(manager.getOrderByFilter(rp.id,info,enterDate,orderTime));
			
			listListOrder.add(listOrder);
		}
		
		
		return "success";
	}
	
	
	List<Integer> delIds;
	public List<Integer> getDelIds()
	{
		return delIds;
	}
	public void setDelIds(List<Integer> delIds)
	{
		this.delIds = delIds;
	}

	public String delOrder()
	{
//		System.out.println();
		for(Integer id:delIds)
		{
			manager.delOrder(id);
		}
		return "delOrderSuccess";
	}
	
	
}
