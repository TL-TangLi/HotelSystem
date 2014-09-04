package com.hotel.data_access;

import com.hotel.entity.Order;
import com.hotel.staticdata.StaticData;



public class DataTest
{
	/**
	 * @param args
	 */
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		
		DataAccess da = DataAccess.getDataAccess();
		
		try{
			
			
//			List<Room>list = da.getAllRoom();
//			id,cid,type,description,is_avaliable,is_check_in,left_time
//			da.updateRoom(new Room(301,122323,12,"stirngsdf",true,false));
//			da.addRoom(new Room(111,12,12,"stirng",true,false));
//			da.deleteRoom(2);
//			for(Room room :list)
//			{
//				System.out.println(room.cId);
//			
//			}
		
		
//			List<Order> list1= da.getAllOrder();
//			(name,phone_number,description,order_time,room_type,extend_hour)
//			da.updateOrder(new Order(9,"nvshi","123123","wwwwww","2008-01-01 23:23:12",21,12,"2001-02-1",3));
//			da.addOrder(new  Order(9,"xiansheng","123123","ssomenasd","2008-1-1 23:23:12",1,12,"now",2,5,0));
//			da.deleteOrder(8);
//			list1 = da.getFilteredOrder(1,Order.ORDER_TYPE_AVALIDATE,null,null);
//			for(Order order :list1)
//			{
//				System.out.println(order.rooms);
//			
//			}
//			System.out.println(da.countFilteredOrder(1,Order.ORDER_TYPE_IGNORE,null,null));
//		
		
//			List<CheckInInfo> l 0ist= da.getAllCheckIn();
//			(name,phone_number,description,order_time,room_type,extend_hour)
//			da.updateCheckIn(new CheckInInfo(8,305,3,"huasdfsaasd","1331",2,"sometext","2005-01-21 23:12:12",null,false,100,true));
//			da.addCheckIn(new CheckInInfo(0,11,0,"hu苏打水 a","12312331",2,"sometext",null,null,false,100,true));
//			da.deleteCheckIn(6);
	
//			for(CheckInInfo ci:list)
//			{
//				System.out.println(ci.orderId);
//			}
		
//			System.out.println(da.getCheckInById(2).id);
		
		
//			List<RoomColor> list = da.getAllColor();
//			System.out.println(list.get(0).id);
//			for(RoomColor c :list)
//			{
//				System.out.println(c.id);
//			}
		
		
//			List<RoomPrice> list = da.getAllPrice();
//			for(RoomPrice c :list)
//			{
//				System.out.println(c.description);
//			}	
		
		
//			if(da.login("name1","psw1") ==0)
//			{
//				System.out.println("success");
//			
//			}
//			else
//				System.out.println("falied");
		
		
//			System.out.println(da.getMaxCheckId());
//			da.updateCheckOut(20);
			
//			da.updateBalance(22,da.getBalance(22)+22.5);
			
//			da.updateRoomState(11,StaticData.ROOM_STATE_DIRTY);
//			System.out.println(da.getRoomCheckId(111));
//			da.updateRoomCheckId(111,34);
			
			
//			Manager manager = Manager.getManager();
//			System.out.println(manager.checkIn(11,new CheckInInfo(0,0,0,"name","phoneNumber",2,"description",null,null,false,100,false)));
		
//			System.out.println(manager.checkOut(11));
		
//			System.out.println(manager.addBalance(11,100));
//			System.out.println(manager.changeRoomState(11,StaticData.ROOM_STATE_CLEAN));
			
//			da.increaseOrReduceNumOfRoomType(1,true);
			
			
//			System.out.println(StaticData.formatDate(2014,1,3));
			
			
//			System.out.println(da.countAvaliableOrderByRoomTypeAndDate(1,0,"2013-03-27"));
//			System.out.println(da.getCheckInOutDate(54));
//			da.updateCheckInOutDate(54,"2013-04-04");
			
//			System.out.println(da.countCheckedByDateAndType(1,"2013-03-27"));
			
//			System.out.println(StaticData.dateAdd("2013-03-26",365));
			
//			System.out.println(da.orderIsToDay(100));
			
//			da.updateOrderInfo(18,0);
			
//			System.out.println(da.countFilteredRoom(-1,"2013-03-30",null));
//			List<RoomAndCheckIn> list = new ArrayList<RoomAndCheckIn>();
//			list = da.getFilteredRoom(-1,"now",null);
//			for(RoomAndCheckIn room :list)
//			{
//				System.out.println(room.room.id);
//			}
			
			
//			System.out.println(da.getRoomNumByType(323));
			
			
//			da.addRoomType(new RoomPrice(1,"fangjia",100,0,10));
			
//			System.out.println(da.getEmptyRoom(StaticData.ROOM_TYPE_IGNORE,-1).size());
			
//			System.out.println(StaticData.beforeNow("2013-04-04"));
			
			
//			da.addAccount(new Account(1,1,"s",1,false,1,null));
			
//			System.out.println(da.getAccountByFilter(-1,1,-1,"2013-04-12").size());
			
//			da.getRoomCheckId(100);
			
			
//			da.addChargeItem(new RoomCharge(0,5,5,200,1,"now"));
//			List<RoomCharge> list = da.getChargeItemByDate(115,"2013-05-06","2013-05-07");
//			for(RoomCharge charge:list)
//			{
//				System.out.println(charge.charge);
//			}
//			
//			da.updateChargeItem(5,2,5000);
			
			System.out.println(da.countFilteredOrder(StaticData.ROOM_TYPE_IGNORE,Order.ORDER_TYPE_AVALIDATE,StaticData.todayToString(),null));
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			
		}
	}

}
