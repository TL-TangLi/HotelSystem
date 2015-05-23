package com.hotel.mainaction;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import com.hotel.datamanage.Manager;
import com.hotel.entity.Order;
import com.hotel.entity.RoomPrice;
import com.hotel.staticdata.StaticData;
import com.opensymphony.xwork2.ModelDriven;

public class OrderAction extends BaseAction implements ModelDriven<Order>
{
	private static final long serialVersionUID = 1L;

	String jsonForOrder;

	@JSON(name = "jsonOrder")
	public String getJsonForOrder()
	{
		return jsonForOrder;
	}

	public void setJsonForOrder(String jsonForOrder)
	{
		this.jsonForOrder = jsonForOrder;
	}
	
	Order od;
	@Override
	public Order getModel()
	{
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
	
	

	
	/*----------------------------------------redirectOrderAction 跳转到订单页面-----------------------------------------*/
	
	public String redirect()
	{
		return "redirect";
	}
	

	/*--------------------------------------info--------------------------------------*/
	public String info()
	{
		Manager manager = Manager.getManager();
		od = manager.getOrderById(od.getId());
		if (od == null || od.id == StaticData.NOT_VALIDATE)
			jsonForOrder = null;
		else
			jsonForOrder = od.toJsonString();

		return "info";
	}

	/*--------------------------------------add update--------------------------------------*/

	public String add()
	{
		Manager manager = Manager.getManager();
		if (addOrUpdate == 0)
		{
			requestResult = manager.addOrder(od);
		}
		else
			requestResult = manager.updateOrder(od);

		return "add";
	}

	/*--------------------------------------query--------------------------------------*/

	
	List<List<Order>> listListOrder;

	public List<List<Order>> getListListOrder()
	{
		return listListOrder;
	}

	public void setListListOrder(List<List<Order>> listListOrder)
	{
		this.listListOrder = listListOrder;
	}

	// /如果没提交订单 默认为 当天 入住订单查询
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

	// 保存房间类型
	List<RoomPrice> listRoomPrice;

	public List<RoomPrice> getListRoomPrice()
	{
		return listRoomPrice;
	}

	public void setListRoomPrice(List<RoomPrice> listRoomPrice)
	{
		this.listRoomPrice = listRoomPrice;
	}

	public String query()
	{

		listRoomPrice = manager.getAllRoomPrice();
		listListOrder = new ArrayList<List<Order>>();

		if (enterDate != null && enterDate.equals(""))
			enterDate = null;

		if (orderTime != null && orderTime.equals(""))
			orderTime = null;

		for (RoomPrice rp : listRoomPrice)
		{

			List<Order> listOrder = new ArrayList<Order>();

			// list 中至少要有一个元素 目的是 为了 迭代获取房间类型，第一个元素被隐藏了。
			Order firstOrder = new Order();
			firstOrder.roomType = rp.id;
			listOrder.add(firstOrder);

			listOrder.addAll(manager.getOrderByFilter(rp.id, info, enterDate, orderTime));

			listListOrder.add(listOrder);
		}

		return "query";
	}

	/*--------------------------------------del--------------------------------------*/
	
	List<Integer> delIds;

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
		if (delIds == null || delIds.size() == 0)
		{
			requestResult = manager.delOrder(od.getId());
			return "del";
		}
		else
		{
			for (Integer id : delIds)
				manager.delOrder(id);
			return "delMulti";
		}
	}
	
	
	

}
