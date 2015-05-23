package com.hotel.mainaction;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.hotel.datamanage.Manager;
import com.hotel.entity.CheckInInfo;
import com.hotel.entity.Order;
import com.hotel.entity.Room;
import com.hotel.entity.RoomAndCheckIn;
import com.hotel.entity.RoomPrice;
import com.hotel.entity.RoomState;
import com.hotel.staticdata.StaticData;
import com.opensymphony.xwork2.ActionContext;

public class RequestAction extends BaseAction 
{
	private static final long serialVersionUID = 1L;
	
	
	/*----------------------------------------roomTrend-----------------------------------------*/
	private List<RoomState> list;
	public List<RoomState> getList()
	{
		return list;
	}
	public void setList(List<RoomState> list)
	{
		this.list = list;
	}

	int todayWaitToCheckInOrderCount;			//待住订单
	int todayNewOrderCount;						//新增订单
	int todayWaitToCheckOutRoomCount;			//待退房
	int todayNewCheckInRoomCount;				//新入住
	int todayCheckIngRoomCount;					//全部入住房
	int todayEmptyRoomCount;					//全部入住房
	
	int todayLeft;								//剩余数
	
	public int getTodayEmptyRoomCount()
	{
		return todayEmptyRoomCount;
	}
	public void setTodayEmptyRoomCount(int todayEmptyRoomCount)
	{
		this.todayEmptyRoomCount = todayEmptyRoomCount;
	}
	public int getTodayLeft()
	{
		return todayLeft;
	}
	public void setTodayLeft(int todayLeft)
	{
		this.todayLeft = todayLeft;
	}
	public int getTodayWaitToCheckInOrderCount()
	{
		return todayWaitToCheckInOrderCount;
	}
	public void setTodayWaitToCheckInOrderCount(int todayWaitToCheckInOrderCount)
	{
		this.todayWaitToCheckInOrderCount = todayWaitToCheckInOrderCount;
	}
	public int getTodayNewOrderCount()
	{
		return todayNewOrderCount;
	}
	public void setTodayNewOrderCount(int todayNewOrderCount)
	{
		this.todayNewOrderCount = todayNewOrderCount;
	}
	public int getTodayWaitToCheckOutRoomCount()
	{
		return todayWaitToCheckOutRoomCount;
	}
	public void setTodayWaitToCheckOutRoomCount(int todayWaitToCheckOutRoomCount)
	{
		this.todayWaitToCheckOutRoomCount = todayWaitToCheckOutRoomCount;
	}
	public int getTodayNewCheckInRoomCount()
	{
		return todayNewCheckInRoomCount;
	}
	public void setTodayNewCheckInRoomCount(int todayNewCheckInRoomCount)
	{
		this.todayNewCheckInRoomCount = todayNewCheckInRoomCount;
	}
	public int getTodayCheckIngRoomCount()
	{
		return todayCheckIngRoomCount;
	}
	public void setTodayCheckIngRoomCount(int todayCheckIngRoomCount)
	{
		this.todayCheckIngRoomCount = todayCheckIngRoomCount;
	}
	public String roomTrend() 
	{
		Manager manager =  Manager.getManager();
		try
		{
			///获取房态链表
			list = new ArrayList<RoomState>();
			
			List<Room> roomList = (List<Room>) manager.getAllRoom();
			for (Room room : roomList)
			{
				CheckInInfo ci = manager.getCheckInById(room.cId);
				list.add(new RoomState(room,manager.getRoomPriceByType(room.type) ,ci));
			}
			// 装入 request 属性中
			HttpServletRequest request = ServletActionContext.getRequest();
			
			request.setAttribute("roomStateList", list);
			
			todayWaitToCheckInOrderCount = manager.countOrderByfilter(StaticData.ROOM_TYPE_IGNORE,Order.ORDER_TYPE_AVALIDATE,"now",null);
			 request.setAttribute("todayWaitToCheckInOrderCount",todayWaitToCheckInOrderCount);
			
			todayNewOrderCount = manager.countOrderByfilter(StaticData.ROOM_TYPE_IGNORE,Order.ORDER_TYPE_IGNORE,null,"now");
			 request.setAttribute("todayNewOrderCount",todayNewOrderCount);
			
			todayWaitToCheckOutRoomCount = manager.countRoomByFilter(StaticData.ROOM_TYPE_IGNORE,null,"now");
			 request.setAttribute("todayWaitToCheckOutRoomCount",todayWaitToCheckOutRoomCount);
			
			todayNewCheckInRoomCount = manager.countRoomByFilter(StaticData.ROOM_TYPE_IGNORE,"now",null);
			 request.setAttribute("todayNewCheckInRoomCount",todayNewCheckInRoomCount);
			
			todayCheckIngRoomCount = manager.countRoomByFilter(StaticData.ROOM_TYPE_IGNORE,null,null);
			 request.setAttribute("todayCheckIngRoomCount",todayCheckIngRoomCount);
			 
			 todayEmptyRoomCount = manager.countEmptyRoom(StaticData.ROOM_TYPE_IGNORE,StaticData.ROOM_STATE_VALIABLE);
			 request.setAttribute("todayEmptyRoomCount",todayEmptyRoomCount);
			
			 todayLeft = manager.countLeftRoom(StaticData.ROOM_TYPE_IGNORE,"now");
			 request.setAttribute("todayLeft",todayLeft);
			 
			return "roomTrend";
		}
		catch (Exception e)
		{
			return "requestAllRoomException";
		}
		
	}
	
	
	
	
	
	/*----------------------------------------todayOrderSum-----------------------------------------*/
	
	int type = StaticData.ROOM_TYPE_IGNORE;
	String enterDate = null;
	
	public String getEnterDate()
	{
		return enterDate;
	}
	public void setEnterDate(String enterDate)
	{
		this.enterDate = enterDate;
	}
	public int getType()
	{
		return type;
	}
	public void setType(int type)
	{
		this.type = type;
	}

	List<RoomPrice> roomPriceList;
	public List<RoomPrice> getRoomPriceList()
	{
		return roomPriceList;
	}
	public void setRoomPriceList(List<RoomPrice> roomPriceList)
	{
		this.roomPriceList = roomPriceList;
	}

	String orderDate;
	//决定 action 执行完后 定位到 哪个资源 这是资源名！！！
	String orientaionName ;
	int info = Order.ORDER_TYPE_IGNORE;

	public int getInfo()
	{
		return info;
	}

	public void setInfo(int info)
	{
		this.info = info;
	}
	public String getOrientaionName()
	{
		return orientaionName;
	}
	public void setOrientaionName(String orientaionName)
	{
		this.orientaionName = orientaionName;
	}
	public String getOrderDate()
	{
		return orderDate;
	}
	public void setOrderDate(String orderDate)
	{
		this.orderDate = orderDate;
	}

	List<Order> listOrder;
	public List<Order> getListOrder()
	{
		return listOrder;
	}
	public void setListOrder(List<Order> listOrder)
	{
		this.listOrder = listOrder;
	}
	public String todayOrderSum()
	{
		
		//客户端 规定 如果不想用某个过滤器，如果该字段为int 指定为-1 如果为 String 指定为 ""
		if(enterDate.equals(""))
			enterDate = null;
		if(orderDate.equals(""))
			orderDate= null;
		if(type == -1)
			type = StaticData.ROOM_TYPE_IGNORE;
		if(info == -1)
			info = Order.ORDER_TYPE_IGNORE;
		
		listOrder = manager.getOrderByFilter(type,info,enterDate,orderDate);
		roomPriceList  = manager.getAllRoomPrice();
		return orientaionName;
	}
	
	
	/*-----------------------------------todayRoomEntered（今日已入住的房间）-----------------------------------------*/
	//和 以上的 type enterDate 组成 三个过滤器   
	String outDate;
	public String getOutDate()
	{
		return outDate;
	}
	public void setOutDate(String outDate)
	{
		this.outDate = outDate;
	}

	List<RoomAndCheckIn> roomAndCheckIn;
	public List<RoomAndCheckIn> getRoomAndCheckIn()
	{
		return roomAndCheckIn;
	}
	public void setRoomAndCheckIn(List<RoomAndCheckIn> roomAndCheckIn)
	{
		this.roomAndCheckIn = roomAndCheckIn;
	}
	public String todayRoomEntered()
	{
		//客户端 规定 如果不想用某个过滤器，如果该字段为int 指定为-1 如果为 String 指定为 ""
		if(type == -1)
			type =StaticData.ROOM_TYPE_IGNORE;
		if(enterDate.equals(""))
			enterDate = null;
		if(outDate.equals(""))
			outDate = null;
		
		roomAndCheckIn = manager.getRoomByFilter(type,enterDate,outDate);
		roomPriceList  = manager.getAllRoomPrice();
		
		return "todayRoomEntered";
	}
	
	/*-----------------------------------todayRoomEmpty（今日空房）-----------------------------------------*/
	List<Room> roomList;
	public List<Room> getRoomList()
	{
		return roomList;
	}
	public void setRoomList(List<Room> roomList)
	{
		this.roomList = roomList;
	}
	int state;
	public int getState()
	{
		return state;
	}
	public void setState(int state)
	{
		this.state = state;
	}
	public String todayRoomEmpty()
	{
		
		///客户端 如果 state type 指定 为 -1 代表 忽略过滤器
		if(type == -1)
			type =StaticData.ROOM_TYPE_IGNORE;
		
		roomList = manager.getEmptyRoom(type,state);
		return "todayRoomEmpty";
	}
	
	
	
	
	
	/*-----------------------------------requestTodayRoomStatisticsAction(今日房间统计--主要是增加了 ，过期房，以及待退房两项)-----------------------------------------*/

	List<RoomPrice> listRoomPrice;
	public List<RoomPrice> getListRoomPrice()
	{
		return listRoomPrice;
	}
	public void setListRoomPrice(List<RoomPrice> listRoomPrice)
	{
		this.listRoomPrice = listRoomPrice;
	}
	public String requestTodayRoomStatistics()
	{
		listRoomPrice = manager.getAllRoomPrice();
		if(listRoomPrice != null)
			for(RoomPrice rp:listRoomPrice)
			{
				rp.checkIngCount = manager.countContinueCheckInRoomMoreThanSomeDay(rp.id,"now");		//续住时间超过今天的房(正常入住)
				rp.waitCheckInCount = manager.countAvalidateOrderOutDateMoreThanSomeDay(rp.id,"now");	//将会在今天入住的订单（待住）
				
				rp.expireCount = manager.countExpireRoom(rp.id,"now");									//今天以前该退 却没退的房(过期房)
				rp.todayCheckOut = manager.countRoomByFilter(rp.id,null,"now");							//今天应该退的房		（待退）
				rp.allcheckIngCount = manager.countRoomByFilter(rp.id,null,null);
			}
			
		return "requestTodayRoomStatisticsSuccess";
	}
	
	/*-----------------------------------requestRoomStatisticsAction(房间统计，统计今天以后的房主要包括，某天将入驻的订单数，以及续住超过某天的房间)-----------------------------------------*/
	//最近几天的 房间统计
	public int days;
	//存放统计数据
	public List<List<RoomPrice>> listListRoomPrice;
	//存放 时间 链表
	public List<String> listDate;
	public List<String> getListDate()
	{
		return listDate;
	}
	public void setListDate(List<String> listDate)
	{
		this.listDate = listDate;
	}
	public List<List<RoomPrice>> getListListRoomPrice()
	{
		return listListRoomPrice;
	}
	public void setListListRoomPrice(List<List<RoomPrice>> listListRoomPrice)
	{
		this.listListRoomPrice = listListRoomPrice;
	}
	public int getDays()
	{
		return days;
	}
	public void setDays(int days)
	{
		this.days = days;
	}
	public String requestRoomStatistics()
	{
		listListRoomPrice = new ArrayList<List<RoomPrice>>();
		listDate = new ArrayList<String>();
		listRoomPrice = manager.getAllRoomPrice();
		
		
		Calendar cl = Calendar.getInstance();
		String 	today =StaticData.CalendarToString(cl);
		String date;
		
		List<RoomPrice> listRoomPriceTemp ;
		
		for(int i = 0;i <days ;i++)
		{
			date = StaticData.dateAdd(today,i+1);
			listRoomPriceTemp = new ArrayList<RoomPrice>();
			listDate.add(new String(date+"  "+StaticData.getDateWeek(cl,i+1)));
			
			
			for(RoomPrice rp:listRoomPrice)
			{
				rp.checkIngCount = manager.countContinueCheckInRoomMoreThanSomeDay(rp.id,date);			//续住时间超过date的房
				rp.waitCheckInCount = manager.countAvalidateOrderOutDateMoreThanSomeDay(rp.id,date);	//未入住 且入住时间小于等于date 且退房时间大于 date 的订单数
				try
				{
					RoomPrice rp2 = (RoomPrice) rp.clone();
					listRoomPriceTemp.add(rp2);
				}
				catch (CloneNotSupportedException e)
				{
					e.printStackTrace();
				}
			}
			
			listListRoomPrice.add(listRoomPriceTemp);
			
		}
		return "requestRoomStatisticsSuccess";
	}
	
	
	
	/*-----------------------------------requestLoginOffAction-----------------------------------------*/
	
	public String requestLoginOff()
	{
		ActionContext.getContext().getSession().put("user",null);
		return "requestLoginOffSuccess";
	}
	
}
