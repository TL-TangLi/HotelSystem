package com.hotel.data_access;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.hotel.entity.Account;
import com.hotel.entity.CheckInInfo;
import com.hotel.entity.Order;
import com.hotel.entity.Room;
import com.hotel.entity.RoomAndCheckIn;
import com.hotel.entity.RoomCharge;
import com.hotel.entity.RoomColor;
import com.hotel.entity.RoomPrice;
import com.hotel.entity.User;
import com.hotel.staticdata.StaticData;
import com.hotel.util.Common;
import com.sun.rowset.CachedRowSetImpl;


/**
 * @author christopher 唐力
 * 
 */
public class DataAccessFromSqlite extends DataAccess
{

	// /加载 sqlite 驱动
	static
	{
		try
		{
			Class.forName("org.sqlite.JDBC");
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	// /所有sql 语句执行 。返回 CachedRowSet 若没有数据返回 那么返回null
	public CachedRowSet execSQL(String sql, Object... objects)
			throws MyException
	{
		return execSQLArrayParameter(sql, objects);
	}

	/**
	 * @param sql
	 * @param objects
	 * @return
	 * @throws MyException
	 */
	private CachedRowSet execSQLArrayParameter(String sql, Object[] objects) throws MyException
	{
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		CachedRowSet crs = null;

		try
		{
			connection = DriverManager.getConnection("jdbc:sqlite:"+ StaticData.dataFilePath);
		}
		catch (SQLException e1)
		{
			e1.printStackTrace();
			throw new MyException(MyException.EXCEPTION_CONNECTE);
		}
		try
		{
			preparedStatement = connection.prepareStatement(sql);
			for (int i = 0; i < objects.length; i++)
			{
				preparedStatement.setObject(i + 1, objects[i]);
			}
			preparedStatement.execute();
		}
		catch (SQLException e3)
		{
			e3.printStackTrace();
			throw new MyException(MyException.EXCEPTION_EXECUTE);
		}
		try
		{
			resultSet = preparedStatement.getResultSet();
		}
		catch (SQLException e1)
		{
			// throw new MyException("获取数据集异常："+e1.getMessage());
			return null;
		}

		try
		{
			crs = new CachedRowSetImpl();
			crs.populate(resultSet);
		}
		catch (SQLException e1)
		{
			throw new MyException(MyException.EXCEPTION_RESULTSETTOROWSET);
		}
		finally
		{

			try
			{
				if (resultSet != null)
					resultSet.close();
				if (preparedStatement != null)
					preparedStatement.close();
				if (connection != null)
					connection.close();

			}
			catch (Exception e2)
			{
				throw new MyException(MyException.EXCEPTION_CONNECTECLOSE);
			}
		}
		return crs;
	}

	/*----------------------------------------room-----------------------------------------*/

	// ////////////////增///////////////////
	/**
	 * @param room
	 * @throws MyException
	 */
	public void addRoom(Room room) throws MyException
	{
		// id ,cId ,type,description,isAvaliable,isClean
		execSQL("insert into room(id,type,isAvaliable,isClean,description) "
				+ " values(?,?,?,?,?)", room.id, room.type,
				room.isAvaliable, room.isClean,room.description);
	}

	// ////////////////删///////////////////

	/**
	 * @param id
	 * @throws MyException
	 */
	public void deleteRoom(int id) throws MyException
	{
		execSQL("delete from room where room.id = ?", id);

	}

	// ////////////////改///////////////////

	/**
	 * @param room
	 * @throws MyException
	 */
	public void updatePartRoom(int id ,String description ,int type) throws MyException
	{
		// id ,cId ,type,description,isAvaliable,isClean
		execSQL("update room set  "
				+ "type = ?,description = ? where id = ? ",
				type,description,id);

	}
	// ////////////////查///////////////////

	/**
	 * @return 返回获取的list 不会是null
	 * @throws MyException
	 */
	public List<Room> getAllRoom() throws MyException
	{
		CachedRowSet crs = execSQL("select * from room");
		List<Room> list = new ArrayList<Room>();

		try
		{
			while (crs.next())
			{
				list.add(new Room(crs.getInt(1), // id
						crs.getInt(2), // cid
						crs.getInt(3), // type
						crs.getString(4), // description
						crs.getBoolean(5), // isAvaliable
						crs.getBoolean(6))); // isclean
			}
			return list;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new MyException(MyException.EXCEPTION_ROWSETOPERATION);
		}

	}

	/**
	 * @param id
	 * @return 如果 存在返回Room对象 否者返回null
	 * @throws MyException
	 */
	@Override
	public Room getRoomById(int id) throws MyException
	{
		CachedRowSet crs = execSQL("select * from room where id = ?", id);
		try
		{
			if (crs.next())
			{
				return new Room(crs.getInt(1), // id
						crs.getInt(2), // cid
						crs.getInt(3), // type
						crs.getString(4), // description
						crs.getBoolean(5), // isAvaliable
						crs.getBoolean(6)); // isclean
			}
			return new Room(StaticData.NOT_VALIDATE,0,0,null,false,false);
		}
		catch (SQLException e)
		{
			throw new MyException(MyException.EXCEPTION_ROWSETOPERATION);
		}
	}

	/*----------------------------------------order_data-----------------------------------------*/
	// ////////////////增///////////////////

	/**
	 * @param order
	 *            加入数据库，注意order的 id 以及 orderTime 两个字段由数据库自动生成！
	 * @throws MyException
	 */
	public void addOrder(Order order) throws MyException
	{
		// name,phoneNumber,description,roderTime(自动生成),roomType,extendHour,enterDate,firstId
		execSQL("insert into order_data"
				+ "(name,phoneNumber,description,roomType,extendHour,enterDate,days,rooms,info,outDate,orderTime) "
				+ "values (?,?,?,?,?,date(?,'localtime'),?,?,?,date(?,'localtime'),datetime('now','localtime'))", order.name, order.phoneNumber,
				order.description, order.roomType, order.extendHour,order.enterDate,order.days,order.rooms,order.info,order.outDate);

	}

	// ////////////////删///////////////////
	/**
	 * @param id
	 * @throws MyException
	 */
	public void deleteOrder(int id) throws MyException
	{
		execSQL("delete from order_data where id = ?", id);

	}

	// ////////////////改///////////////////

	/**
	 * @param order
	 *            可以修改除了 id 之外的全部字段
	 * @throws MyException
	 */
	public void updateOrder(Order order) throws MyException
	{
		execSQL("update order_data set "
				+ "name = ? ,phoneNumber = ?,description = ?," +
				" roomType = ?, extendHour = ? ,enterDate = ?,days = ? ,info = ? , outDate = ? where id = ?",
				order.name, order.phoneNumber, order.description,
				 order.roomType, order.extendHour,order.enterDate,order.days, order.info,order.outDate,order.id);
	}

	// ////////////////查///////////////////

	/**
	 * @return 返回 一个List 对象
	 * @throws MyException
	 */
	public List<Order> getAllOrder() throws MyException
	{
		CachedRowSet crs = execSQL("select * from order_data");
		List<Order> list = new ArrayList<Order>();
		try
		{
			while (crs.next())
			{
				list.add(new Order(
						crs.getInt(1), // id
						crs.getString(2), // name
						crs.getString(3), // phone_number
						crs.getString(4), // description
						crs.getString(5), // order_time
						crs.getInt(6), // room_type
						crs.getInt(7), // extend_hour
						crs.getString(8),//enterDate
						crs.getInt(9),	//days
						crs.getInt(10),	//rooms
						crs.getInt(11),	//info
						crs.getString(12)));//outDate
			}
			return list;
		}
		catch (Exception e)
		{
			throw new MyException(MyException.EXCEPTION_ROWSETOPERATION);
		}

	}

	/*----------------------------------------check_in-----------------------------------------*/
	// ////////////////增///////////////////

	/**
	 * @param ci
	 *            enterTime(自动生成),outTime(暂时为空),isCheckOut(默认为false)其他字段必须指定
	 * @throws MyException
	 */
	public void addCheckIn(CheckInInfo ci) throws MyException
	{
		// rId,orderId,name,phoneNumber,numberPeople,description,
		// enterTime(自动生成,为防止数据库失败，还是自己指定),outTime,isCheckOut(默认为false),balance,isHourRoom
		execSQL("insert into check_in"
				+ "(rId,orderId,name,phoneNumber,numberPeople,outTime,description,balance,isHourRoom,enterTime)"
				+ "values(?,?,?,?,?,?,?,?,?,datetime('now','localtime'))", ci.rId, ci.orderId, ci.name,
				ci.phoneNumber, ci.numberPeople,ci.outTime, ci.description, ci.balance,
				ci.isHourRoom);

	}

	// ////////////////删///////////////////

	/**
	 * @param id
	 * @throws MyException
	 */
	public void deleteCheckIn(int id) throws MyException
	{
		execSQL("delete from check_in where id = ?", id);
	}

	// ////////////////改///////////////////

	/**
	 * @param ci
	 *            应当指定全部字段
	 * @throws MyException
	 */
	public void updateCheckIn(CheckInInfo ci) throws MyException
	{
		execSQL("update check_in set "
				+ "rId = ?,orderId = ?,name = ? ,phoneNumber = ?,numberPeople = ?,description = ?,"
				+ "enterTime = ?,outTime =?,isCheckOut = ? ,balance = ?,isHourRoom = ? where id = ? ",
				ci.rId, ci.orderId, ci.name, ci.phoneNumber, ci.numberPeople,
				ci.description, ci.enterTime, ci.outTime, ci.isCheckOut,
				ci.balance, ci.isHourRoom, ci.id);

	}

	// ////////////////查///////////////////

	/**
	 * @return 返回值不会为 null
	 * @throws MyException
	 */
	public List<CheckInInfo> getAllCheckIn() throws MyException
	{
		CachedRowSet crs = execSQL("select * from check_in");
		List<CheckInInfo> list = new ArrayList<CheckInInfo>();
		try
		{
			while (crs.next())
			{
				// rId,orderId,name,phoneNumber,numberPeople,description,
				// enterTime(自动生成),outTime(暂时为空),isCheckOut(默认为false),balance,isHourRoom
				list.add(new CheckInInfo(crs.getInt(1), // id
						crs.getInt(2), // rid
						crs.getInt(3), // order_id
						crs.getString(4), // name
						crs.getString(5), // phone_number
						// crs.getBoolean(6), // have_order
						crs.getInt(6), // number_people
						crs.getString(7), // description
						crs.getString(8), // enter_time
						crs.getString(9), // out_time
						crs.getBoolean(10), // is_check_out
						crs.getDouble(11), // balance
						crs.getBoolean(12)));// isHourRoom
			}
			return list;
		}
		catch (SQLException e)
		{
			throw new MyException(MyException.EXCEPTION_ROWSETOPERATION);
		}

	}

	/**
	 * @param id
	 * @return 如果没有改对象返回null 否者返回CheckInInfo整个对象
	 * @throws MyException
	 */
	@Override
	public CheckInInfo getCheckInById(int id) throws MyException
	{
		CachedRowSet crs = execSQL("select * from check_in where id= ?", id);
		try
		{
			if (crs.next())
			{
				// rId,orderId,name,phoneNumber,numberPeople,description,
				// enterTime(自动生成),outTime(暂时为空),isCheckOut(默认为false),balance,isHourRoom
				return new CheckInInfo(crs.getInt(1), // id
						crs.getInt(2), // rid
						crs.getInt(3), // order_id
						crs.getString(4), // name
						crs.getString(5), // phone_number
						// crs.getBoolean(6), // have_order
						crs.getInt(6), // number_people
						crs.getString(7), // description
						crs.getString(8), // enter_time
						crs.getString(9), // out_time
						crs.getBoolean(10), // is_check_out
						crs.getDouble(11), // balance
						crs.getBoolean(12));// isHourRoom
			}
//			CheckInInfo cii = new CheckInInfo();
//			cii.id = StaticData.NOT_VALIDATE;
//			return cii;
			return null;
		}
		catch (SQLException e)
		{
			throw new MyException(MyException.EXCEPTION_ROWSETOPERATION);
		}

	}

	/*----------------------------------------color_roomstate-----------------------------------------*/
	/**
	 * @return 返回六种状态的颜色，以 房间状态号从小到大排序
	 * @throws MyException
	 */
	public List<RoomColor> getAllColor() throws MyException
	{

		List<RoomColor> list = new ArrayList<RoomColor>();
		CachedRowSet crs = execSQL("select * from color_roomstate");
		try
		{
			while (crs.next())
			{
				// // id colorValue description
				list.add(new RoomColor(crs.getInt(1), crs.getString(2), crs
						.getString(3)));
			}
			return list;
		}
		catch (SQLException e)
		{
			throw new MyException(MyException.EXCEPTION_ROWSETOPERATION);
		}

	}

	
	@Override
	public RoomColor getRoomColorByState(int state) throws MyException
	{
		CachedRowSet crs = execSQL("select * from color_roomstate where id = ?",state);
		try
		{
			if(crs.next())
			{
				// // id colorValue description
				return (new RoomColor(crs.getInt(1), crs.getString(2), crs
						.getString(3)));
			}
			return null;
		}
		catch (SQLException e)
		{
			throw new MyException(MyException.EXCEPTION_ROWSETOPERATION);
		}
	}
	/*----------------------------------------room_price-----------------------------------------*/
	/**
	 * @return
	 * @throws MyException
	 */
	public List<RoomPrice> getAllPrice() throws MyException
	{

		List<RoomPrice> list = new ArrayList<RoomPrice>();
		CachedRowSet crs = execSQL("select * from room_price");
		try
		{
			while (crs.next())
			{
				// // id description price num hourPrice
				list.add(new RoomPrice(crs.getInt(1), crs.getString(2), crs
						.getDouble(3),crs.getInt(4),crs.getDouble(5)));
			}
			return list;
		}
		catch (SQLException e)
		{
			throw new MyException(MyException.EXCEPTION_ROWSETOPERATION);
		}
	}

	/*----------------------------------------user_psw-----------------------------------------*/
	/**
	 * @param name
	 * @param psw
	 * @return 登录成功返回 0，密码错误返回1 ，用户不存在返回2
	 */
	public int login(User user)
	{

		CachedRowSet crs = null;
		try
		{
			crs = execSQL("select * from user_psw where name = ?", user.userName);
		}
		catch (MyException e)
		{
			e.printStackTrace();
			return 3;
		}
		try
		{
			if (!crs.next())
				return 2;
			if (crs.getString("psw").equals(user.psw))
			{
				user.level = crs.getInt("level");
				return 0;
			}
			return 1;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return 3;
		}
	}
	
	
	
	/*---------------------------------------------------------------------------------------------*/
	@Override
	public void updateRoomState(int rId, int state) throws MyException
	{
		boolean isClean = true;
		boolean isAvaliable = true;
		switch(state)
		{
		case StaticData.ROOM_STATE_DIRTY:
			isClean = false;
			break;
		case StaticData.ROOM_STATE_INVALIABLE:
			isAvaliable = false;
			break;
		default:
			break;
		}
		execSQL("update room set isClean = ? ,isAvaliable = ? ,cId = 0 where id = ?",isClean,isAvaliable,rId);
	}

	@Override
	public int getRoomCheckId(int rid) throws MyException
	{
		CachedRowSet crs = execSQL("select cid from room where id =?",rid);
		try
		{
			if(crs.next())
				return crs.getInt(1);
			return 0;
		}catch(Exception e)
		{
			
			throw new MyException(MyException.EXCEPTION_ROWSETOPERATION);
		}
	}

	@Override
	public void updateRoomCheckId(int rid,int cid) throws MyException
	{
		execSQL("update room set cId = ? where id =?",cid,rid);
	}

	@Override
	public int getMaxCheckId() throws MyException
	{
		CachedRowSet crs = null;
		crs = execSQL("select max(id) from check_in ");
		try
		{
			if (crs.next())
				return crs.getInt(1);
			return 0;
		}
		catch (Exception e)
		{
			throw new MyException(MyException.EXCEPTION_ROWSETOPERATION);
		}
	}

	@Override
	public void updateCheckOut(int cid) throws MyException
	{
//		updateBalance(cid,getBalance(cid)-leftBalance);
		execSQL("update check_in set isCheckOut = 1,outTime =datetime('now','localtime') where id =?",
				cid);

	}

	@Override
	public double getBalance(int cid) throws MyException
	{
		CachedRowSet crs = null;
		crs = execSQL("select balance from check_in where id = ?", cid);
		try
		{
			if (crs.next())
				return crs.getDouble(1);
			return 0;
		}
		catch (Exception e)
		{
			throw new MyException(MyException.EXCEPTION_ROWSETOPERATION);
		}
	}

	@Override
	public void updateBalance(int cid, double balance) throws MyException
	{
		execSQL("update check_in set balance=? where id=? ", balance, cid);

	}

	@Override
	public void updateCheckInPart(CheckInInfo ci) throws MyException
	{
		execSQL("update check_in set "
				+ "orderId = ?,name = ? ,phoneNumber = ?,numberPeople = ?,description = ?,isHourRoom = ? "
				+ "where id = ? ",
				ci.orderId, ci.name, ci.phoneNumber, ci.numberPeople,ci.description,ci.isHourRoom,				
				ci.id);
		
	}

	@Override
	public RoomPrice getPriceByType(int type) throws MyException
	{
		CachedRowSet crs = execSQL("select * from room_price where id = ?",type);
		try
		{
			if(crs.next())
			{
				// // id description price num hourPrice
				return  new RoomPrice(crs.getInt(1), crs.getString(2), crs
						.getDouble(3),crs.getInt(4),crs.getDouble(5));
			}
			return new RoomPrice(StaticData.NOT_VALIDATE,null,0,0,0);
		}
		catch (SQLException e)
		{
			throw new MyException(MyException.EXCEPTION_ROWSETOPERATION);
		}
	}

	@Override
	public int countFilteredOrder(int roomType,int info, String enterDate,String orderDate)
			throws MyException
	{
		
//		select count(*) from order_data where date(orderTime) = date('2013-03-30') and
//		enterDate = date('now') and roomType = 1 and info = 0
		
		ArrayList<Object> list = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("select count(*) from order_data where 1 = 1 ");
		if (info != Order.ORDER_TYPE_IGNORE)
		{
			list.add(info);
			sql.append(" and info = ? ");
		}
		if (roomType != StaticData.ROOM_TYPE_IGNORE)
		{
			sql.append(" and  roomType = ? ");
			list.add(roomType);
		}
		if (Common.notJustEmptyChar(enterDate))
		{
			sql.append(" and enterDate == date(?,'localtime') ");
			list.add(enterDate);
		}
		if (Common.notJustEmptyChar(orderDate))
		{

			sql.append(" and date(orderTime) = date(?,'localtime') ");
			list.add(orderDate);
		}
		
		CachedRowSet crs = execSQLArrayParameter(sql.toString(),list.toArray());
	
		try
		{
			if (crs.next())
				return crs.getInt(1);
			return 0;
		}
		catch (Exception e)
		{
			throw new MyException(MyException.EXCEPTION_ROWSETOPERATION);
		}
	}

	@Override
	public List<Order> getFilteredOrder(int roomType, int info,String enterDate ,String orderDate)
			throws MyException
	{
		ArrayList<Object> list = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("select * from order_data where 1 = 1 ");
		if (info != Order.ORDER_TYPE_IGNORE)
		{
			list.add(info);
			sql.append(" and info = ? ");
		}
		if (roomType != StaticData.ROOM_TYPE_IGNORE)
		{
			sql.append(" and  roomType = ? ");
			list.add(roomType);
		}
		if (Common.notJustEmptyChar(enterDate))
		{
			sql.append(" and enterDate == date(?,'localtime') ");
			list.add(enterDate);
		}
		if (Common.notJustEmptyChar(orderDate))
		{

			sql.append(" and date(orderTime) = date(?,'localtime') ");
			list.add(orderDate);
		}
		
		CachedRowSet crs = execSQLArrayParameter(sql.toString(),list.toArray());
		List<Order> result = new ArrayList<Order>();
		try
		{
			while (crs.next())
			{
				result.add(new Order(crs.getInt(1), // id
						crs.getString(2), // name
						crs.getString(3), // phone_number
						crs.getString(4), // description
						crs.getString(5), // order_time
						crs.getInt(6), // room_type
						crs.getInt(7), // extend_hour
						crs.getString(8),//enterDate
						crs.getInt(9),	//days
						crs.getInt(10),	//rooms
						crs.getInt(11),	//info
						crs.getString(12)));//outDate
			}
			return result;
		}
		catch (Exception e)
		{
			throw new MyException(MyException.EXCEPTION_ROWSETOPERATION);
		}
	}


	@Override
	public void updateCheckInOutDate(int cid, String outDate)
			throws MyException
	{
		
		execSQL("update check_in set outTime = ? where  id = ?",outDate,cid);
		
	}

	@Override
	public String getCheckInOutDate(int cid) throws MyException
	{
		CachedRowSet crs = null;
		crs = execSQL("select outTime from check_in where id = ?", cid);
		try
		{
			if (crs.next())
				return crs.getString(1);
			return null;
		}
		catch (Exception e)
		{
			throw new MyException(MyException.EXCEPTION_ROWSETOPERATION);
		}
	}

	@Override
	public void updateOrderInfo(int id, int info) throws MyException
	{
		execSQL("update order_data set info = ? where  id = ?",info,id);
	}

	@Override
	public boolean orderIsToDay(int id) throws MyException
	{
		CachedRowSet crs = execSQL("select id from  order_data where id = ? and enterDate == date('now','localtime')",id);
		try
		{
			if(crs.next())
			{
				return true;
			}
			return false;
		}
		catch (SQLException e)
		{
			throw new MyException(MyException.EXCEPTION_ROWSETOPERATION);
		}
	}

	@Override
	public Order getOrderById(int id) throws MyException
	{
		CachedRowSet crs = execSQL("select * from order_data where id =  ?",id);
		try
		{
			while (crs.next())
			{
				// name,phoneNumber,description,roderTime(自动生成),roomType,extendHour
				return(new Order(crs.getInt(1), // id
						crs.getString(2), // name
						crs.getString(3), // phone_number
						crs.getString(4), // description
						crs.getString(5), // order_time
						crs.getInt(6), // room_type
						crs.getInt(7), // extend_hour
						crs.getString(8),//enterDate
						crs.getInt(9),	//days
						crs.getInt(10),	//rooms
						crs.getInt(11),	//info
						crs.getString(12)));//outDate
			}
			Order o = new Order();
			o.id = StaticData.NOT_VALIDATE;
			return o;
		}
		catch (Exception e)
		{
			throw new MyException(MyException.EXCEPTION_ROWSETOPERATION);
		}
	}

	@Override
	public int countFilteredCheckedRoom(int type, String enterDate, String outDate)
			throws MyException
	{
		
		
		ArrayList<Object> list = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("select count(*) from room as r ,check_in as c where   r.cId = c.id  ");
		
//		crs = execSQL("select count(*) from room as r ,check_in as c where  r.type = ? and r.cId = c.id and "
//				+ "date(c.[enterTime]) = date(?,'localtime') and c.[outTime] = date(?,'localtime')",type,enterDate,outDate);
		
		if (Common.notJustEmptyChar(enterDate))
		{
			list.add(enterDate);
			sql.append(" and date(c.[enterTime]) = date(?,'localtime') ");

		}
		if (Common.notJustEmptyChar(outDate))
		{
			sql.append(" and c.[outTime] = date(?,'localtime') ");
			list.add(outDate);
		}
		if (type != StaticData.ROOM_TYPE_IGNORE)
		{

			sql.append(" and r.type = ? ");
			list.add(type);
		}
		
		CachedRowSet crs = execSQLArrayParameter(sql.toString(),list.toArray());
		
		try
		{
			crs.next();
			return crs.getInt(1);
		}
		catch (Exception e)
		{
			throw new MyException(MyException.EXCEPTION_ROWSETOPERATION);
		}
	}

	@Override
	public List<RoomAndCheckIn> getFilteredCheckedRoom(int type, String enterDate, String outDate)
			throws MyException
	{
		ArrayList<Object> list = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("select * from room as r ,check_in as c where   r.cId = c.id  ");
		
//		crs = execSQL("select count(*) from room as r ,check_in as c where  r.type = ? and r.cId = c.id and "
//				+ "date(c.[enterTime]) = date(?,'localtime') and c.[outTime] = date(?,'localtime')",type,enterDate,outDate);
		
		if (Common.notJustEmptyChar(enterDate))
		{
			list.add(enterDate);
			sql.append(" and date(c.[enterTime]) = date(?,'localtime') ");

		}
		if (Common.notJustEmptyChar(outDate))
		{
			sql.append(" and c.[outTime] = date(?,'localtime') ");
			list.add(outDate);
		}
		if (type != StaticData.ROOM_TYPE_IGNORE)
		{

			sql.append(" and r.type = ? ");
			list.add(type);
		}
		
		CachedRowSet crs = execSQLArrayParameter(sql.toString(),list.toArray());
		
		List<RoomAndCheckIn> result = new ArrayList<RoomAndCheckIn>();
		try
		{
			while (crs.next())
			{
				result.add(new RoomAndCheckIn(
						new Room(
								crs.getInt(1), // id
								crs.getInt(2), // cid
								crs.getInt(3), // type
								crs.getString(4), // description
								crs.getBoolean(5), // isAvaliable
								crs.getBoolean(6)), // isclean
						new CheckInInfo(
								crs.getInt(7), // id
								crs.getInt(8), // rid
								crs.getInt(9), // order_id
								crs.getString(10), // name
								crs.getString(11), // phone_number
								crs.getInt(12), 	// number_people
								crs.getString(13), // description
								crs.getString(14), // enter_time
								crs.getString(15), // out_time
								crs.getBoolean(16), // is_check_out
								crs.getDouble(17), // balance
								crs.getBoolean(18))
						));
			}	
			return result;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new MyException(MyException.EXCEPTION_ROWSETOPERATION);
		}
	}

	@Override
	public int countContinueCheckInRoomMoreThanSomeDay(int type, String date)
			throws MyException
	{
		CachedRowSet crs = null;
		if(type == StaticData.ROOM_TYPE_IGNORE)
			crs = execSQL("select count(*) from room as r ,check_in as c where r.cId = c.id and c.[outTime] > date(?,'localtime')",date);
		else
			crs = execSQL("select count(*) from room as r ,check_in as c where r.[type] = ? and r.cId = c.id and c.[outTime] > date(?,'localtime')",type,date);
		try
		{
			crs.next();
			return crs.getInt(1);
		}
		catch (Exception e)
		{
			throw new MyException(MyException.EXCEPTION_ROWSETOPERATION);
		}
	}

	@Override
	public int countAvalidateOrderOutDateMoreThanSomeDay(int type, String date)
			throws MyException
	{
		CachedRowSet crs = null;
		if(type == StaticData.ROOM_TYPE_IGNORE)
			crs = execSQL("select count(*) from order_data where info = ? and enterDate >= date('now','localtime') and enterDate<=date(?,'localtime') and outDate > date(?,'localtime') ",Order.ORDER_TYPE_AVALIDATE,date,date);
		else
			crs = execSQL("select count(*) from order_data where roomType = ? and info = ? and enterDate >= date('now','localtime') and enterDate<=date(?,'localtime') and outDate > date(?,'localtime')",type,Order.ORDER_TYPE_AVALIDATE,date,date);
		try
		{
			crs.next();
			return crs.getInt(1);
		}
		catch (Exception e)
		{
			throw new MyException(MyException.EXCEPTION_ROWSETOPERATION);
		}
	}

	@Override
	public int getRoomNumByType(int type) throws MyException
	{
		CachedRowSet crs = null;
		if(type == StaticData.ROOM_TYPE_IGNORE)
			crs = execSQL("select sum(num) from room_price ");
		else
			crs = execSQL("select num from room_price where id= ? ",type);
		try
		{
			if(crs.next())
				return crs.getInt(1);
			return 0;
		}
		catch (Exception e)
		{
			throw new MyException(MyException.EXCEPTION_ROWSETOPERATION);
		}
	}

	@Override
	public int countExpireRoom(int type,String date) throws MyException
	{
		CachedRowSet crs = null;
		if(type == StaticData.ROOM_TYPE_IGNORE)
			crs = execSQL("select count(*) from room as r, check_in as c where r.[cId] = c.[id]  and c.[outTime] < date(?,'localtime') ",date);
		else
			crs = execSQL("select count(*) from room as r, check_in as c where r.[type] = ? and r.[cId] = c.[id]  and c.[outTime] < date(?,'localtime') ",type,date);
		try
		{
			crs.next();
			return crs.getInt(1);
		}
		catch (Exception e)
		{
			throw new MyException(MyException.EXCEPTION_ROWSETOPERATION);
		}
	}

	@Override
	public void addRoomType(RoomPrice rp) throws MyException
	{
		execSQL("insert into room_price(description,price,hourPrice) "
				+ " values(?,?,?)", rp.description,rp.price,rp.hourPrice);
		
	}

	@Override
	public void delRoomType(int id) throws MyException
	{
		execSQL("delete from room_price where id = ?", id);
		
	}

	@Override
	public void updateRoomType(RoomPrice rp) throws MyException
	{
		execSQL("update room_price set  "
				+ " description = ?,price = ?,hourPrice = ? where id = ? ",
				rp.description,rp.price,rp.hourPrice,rp.id);
		
	}

	@Override
	public RoomPrice getRomTypeByName(String name) throws MyException
	{
		CachedRowSet crs = execSQL("select * from room_price where description = ?", name);
		try
		{
			if (crs.next())
			{
				return new RoomPrice(
						crs.getInt(1),
						crs.getString(2),
						crs.getDouble(3),
						crs.getInt(4),
						crs.getDouble(5));
			}
			return new RoomPrice(StaticData.NOT_VALIDATE,null,0,0,0);
		}
		catch (SQLException e)
		{
			throw new MyException(MyException.EXCEPTION_ROWSETOPERATION);
		}
	}

	@Override
	public List<Room> getEmptyRoom(int type, int state) throws MyException
	{
		
		
		CachedRowSet crs = null;
		
		if(type == StaticData.ROOM_TYPE_IGNORE)
		{
			if(state == StaticData.ROOM_STATE_CLEAN)
				crs = execSQL("select * from room where isAvaliable = 1  and cId = 0 and isClean = 1");
			else if(state == StaticData.ROOM_STATE_DIRTY)
				crs = execSQL("select * from room where isAvaliable = 1  and cId = 0 and isClean = 0");
			else if(state == StaticData.ROOM_STATE_INVALIABLE)
				crs = execSQL("select * from room where isAvaliable = 0 ");
			else if(state == StaticData.ROOM_STATE_VALIABLE)
				crs = execSQL("select * from room where isAvaliable = 1 and cId = 0");
			else 
				crs = execSQL("select * from room where cId = 0");
			
			
		}
		else
		{
			if(state == StaticData.ROOM_STATE_CLEAN)
				crs = execSQL("select * from room where isAvaliable = 1  and cId = 0 and isClean = 1 and type = ?",type);
			else if(state == StaticData.ROOM_STATE_DIRTY)
				crs = execSQL("select * from room where isAvaliable = 1  and cId = 0 and isClean = 0 and type = ?",type);
			else if(state == StaticData.ROOM_STATE_INVALIABLE)
				crs = execSQL("select * from room where isAvaliable = 0 and type = ?",type);
			else if(state == StaticData.ROOM_STATE_VALIABLE)
				crs = execSQL("select * from room where isAvaliable = 1 and cId = 0 and type = ?",type);
			else 
				crs = execSQL("select * from room where cId = 0 and type = ?",type);
			
		}
		
		
		
		List<Room> list = new ArrayList<Room>();

		try
		{
			while (crs.next())
			{
				list.add(new Room(crs.getInt(1), // id
						crs.getInt(2), // cid
						crs.getInt(3), // type
						crs.getString(4), // description
						crs.getBoolean(5), // isAvaliable
						crs.getBoolean(6))); // isclean
			}
			return list;
		}
		catch (SQLException e)
		{
			throw new MyException(MyException.EXCEPTION_ROWSETOPERATION);
		}
	}

	@Override
	public int countEmptyRoom(int type, int state) throws MyException
	{
		CachedRowSet crs = null;
		
		if(type == StaticData.ROOM_TYPE_IGNORE)
		{
			if(state == StaticData.ROOM_STATE_CLEAN)
				crs = execSQL("select count(*) from room where isAvaliable = 1  and cId = 0 and isClean = 1");
			else if(state == StaticData.ROOM_STATE_DIRTY)
				crs = execSQL("select count(*) from room where isAvaliable = 1  and cId = 0 and isClean = 0");
			else if(state == StaticData.ROOM_STATE_INVALIABLE)
				crs = execSQL("select count(*) from room where isAvaliable = 0 ");
			else if(state == StaticData.ROOM_STATE_VALIABLE)
				crs = execSQL("select count(*) from room where isAvaliable = 1 and cId = 0");
			else 
				crs = execSQL("select count(*) from room where cId = 0");
			
			
		}
		else
		{
			if(state == StaticData.ROOM_STATE_CLEAN)
				crs = execSQL("select count(*) from room where isAvaliable = 1  and cId = 0 and isClean = 1 and type = ?",type);
			else if(state == StaticData.ROOM_STATE_DIRTY)
				crs = execSQL("select count(*) from room where isAvaliable = 1  and cId = 0 and isClean = 0 and type = ?",type);
			else if(state == StaticData.ROOM_STATE_INVALIABLE)
				crs = execSQL("select count(*) from room where isAvaliable = 0 and type = ?",type);
			else if(state == StaticData.ROOM_STATE_VALIABLE)
				crs = execSQL("select count(*) from room where isAvaliable = 1 and cId = 0 and type = ?",type);
			else 
				crs = execSQL("select count(*) from room where cId = 0 and type = ?",type);
			
		}
		try
		{
			crs.next();
			return crs.getInt(1);
		}
		catch (SQLException e)
		{
			throw new MyException(MyException.EXCEPTION_ROWSETOPERATION);
		}
	}

	@Override
	public void delOrderByRoomType(int roomType) throws MyException
	{
		execSQL("delete from order_data where roomType = ?",roomType);
		
	}

	@Override
	public void addAccount(Account account) throws MyException
	{
		execSQL("insert into account(rId,cId,description,type,isConsume,balance,genTime) "
				+ " values(?,?,?,?,?,?,datetime('now','localtime'))",
				account.rId,account.cId,account.description,
				account.type,account.isConsume,account.balance);
		
	}

	@Override
	public List<Account> getAccountByFilter(int cId, int type, int isConsume,
			String beginDate,String endDate) throws MyException
	{
		
//		select * from account where cId = 87 and type = 1 and isConsume = 0 
//		and date(genTime) >= date('2013-04-12')  and date(genTime) <= date('2013-04-13')
		
		ArrayList<Object> list = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("select * from account where 1 = 1 ");
		if (cId != -1)
		{
			list.add(cId);
			sql.append(" and cId = ? ");
		}
		if (type != Account.PAY_IGNORE)
		{
			sql.append(" and type = ? ");
			list.add(type);
		}
		if (isConsume != -1)
		{
			sql.append(" and isConsume = ? ");
			list.add(isConsume);
		}
		if (Common.notJustEmptyChar(beginDate))
		{

			sql.append(" and date(genTime) >= date(?,'localtime') ");
			list.add(beginDate);
		}
		if (Common.notJustEmptyChar(endDate))
		{

			sql.append(" and date(genTime) <= date(?,'localtime') ");
			list.add(endDate);
		}
		
		CachedRowSet crs = execSQLArrayParameter(sql.toString(),list.toArray());
		
		List<Account> result = new ArrayList<Account>();
		try
		{
			while (crs.next())
			{
				result.add(
						new Account(
								crs.getInt(1),		//rid
								crs.getInt(2),		//cid
								crs.getString(3),	//description
								crs.getInt(4),		//type
								crs.getBoolean(5),	//isConsume
								crs.getDouble(6),	//balance
								crs.getString(7),	//genTime
								crs.getInt(8)		//id
								));
			}
			return result;
		}
		catch (Exception e)
		{
			throw new MyException(MyException.EXCEPTION_ROWSETOPERATION);
		}
	}

	@Override
	public double sumAccountByFilter(int cId, int type, int isConsume,
			String beginDate, String endDate) throws MyException
	{
		
//		select * from account where cId = 87 and type = 1 and isConsume = 0 
//		and date(genTime) >= date('2013-04-12')  and date(genTime) <= date('2013-04-13')
		
		ArrayList<Object> list = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("select sum(account.[balance]) from account where 1 = 1 ");
		if (cId != -1)
		{
			list.add(cId);
			sql.append(" and cId = ? ");
		}
		if (type != Account.PAY_IGNORE)
		{
			sql.append(" and type = ? ");
			list.add(type);
		}
		if (isConsume != -1)
		{
			sql.append(" and isConsume = ? ");
			list.add(isConsume);
		}
		if (Common.notJustEmptyChar(beginDate))
		{

			sql.append(" and date(genTime) >= date(?,'localtime') ");
			list.add(beginDate);
		}
		if (Common.notJustEmptyChar(endDate))
		{

			sql.append(" and date(genTime) <= date(?,'localtime') ");
			list.add(endDate);
		}
		
		CachedRowSet crs = execSQLArrayParameter(sql.toString(),list.toArray());
		
		try
		{
				crs.next();
				return crs.getDouble(1);
		}
		catch (Exception e)
		{
			throw new MyException(MyException.EXCEPTION_ROWSETOPERATION);
		}
	}

	@Override
	public void delAccount(int id) throws MyException
	{
		execSQL("delete from account where id = ?", id);
	}

	@Override
	public Account getAccountById(int id) throws MyException
	{
		CachedRowSet crs = execSQL("select * from account where id =  ?",id);
		try
		{
			if (crs.next())
			{
				return new  Account(
						crs.getInt(1),		//rid
						crs.getInt(2),		//cid
						crs.getString(3),	//description
						crs.getInt(4),		//type
						crs.getBoolean(5),	//isConsume
						crs.getDouble(6),	//balance
						crs.getString(7),	//genTime
						crs.getInt(8)		//id
						);
			}
			return new Account(StaticData.NOT_VALIDATE,0,null,0,false,0,null,0);
		}
		catch (Exception e)
		{
			throw new MyException(MyException.EXCEPTION_ROWSETOPERATION);
		}
	}

	@Override
	public List<CheckInInfo> getCheckInByFilter(String enterDateBegin,
			String enterDateEnd, String outDateBegin, String outDateEnd, String name, String remark, String roomId, String phone)
			throws MyException
	{
		
//		select * from check_in where enterTime >= date('2013-04-12') and enterTime <= date('2013-05-05') and
//		outTime >= date('2013-04-12') and outTime <= date('2013-05-05')
		
		ArrayList<Object> list = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("select * from check_in where 1 = 1 ");
		if (Common.notJustEmptyChar(enterDateBegin))
		{
			list.add(enterDateBegin);
			sql.append(" and date(enterTime) >= date(?,'localtime') ");

		}
		if (Common.notJustEmptyChar(enterDateEnd))
		{
			sql.append(" and date(enterTime) <= date(?,'localtime') ");
			list.add(enterDateEnd);
		}
		if (Common.notJustEmptyChar(outDateBegin))
		{

			sql.append(" and date(outTime) >= date(?,'localtime') ");
			list.add(outDateBegin);
		}
		if (Common.notJustEmptyChar(outDateEnd))
		{

			sql.append(" and date(outTime) <= date(?,'localtime') ");
			list.add(outDateEnd);
		}

		if (Common.notJustEmptyChar(name))
		{
			sql.append(" and name like ? ");
			list.add("%"+name+"%");

		}
		if (Common.notJustEmptyChar(remark))
		{
			sql.append(" and description like ? ");
			list.add("%"+remark+"%");

		}
		if (Common.notJustEmptyChar(roomId))
		{

			sql.append(" and rId like ? ");
			list.add("%"+roomId+"%");
		}
		if (Common.notJustEmptyChar(phone))
		{
			sql.append(" and phoneNumber like ? ");
			list.add("%"+phone+"%");

		}
		
		CachedRowSet crs = execSQLArrayParameter(sql.toString(),list.toArray());
		List<CheckInInfo> result = new ArrayList<CheckInInfo>();
		try
		{
			while (crs.next())
			{
				result.add(new CheckInInfo(crs.getInt(1), // id
						crs.getInt(2), // rid
						crs.getInt(3), // order_id
						crs.getString(4), // name
						crs.getString(5), // phone_number
						crs.getInt(6), // number_people
						crs.getString(7), // description
						crs.getString(8), // enter_time
						crs.getString(9), // out_time
						crs.getBoolean(10), // is_check_out
						crs.getDouble(11), // balance
						crs.getBoolean(12)));// isHourRoom
			}
			return result;
		}
		catch (SQLException e)
		{
			throw new MyException(MyException.EXCEPTION_ROWSETOPERATION);
		}
	}

	@Override
	public String getSuperManagerPsw()
	{
		CachedRowSet crs = null;
		try
		{
			crs = execSQL("select psw from user_psw where name = 'sa'");
			if (crs.next())
				return crs.getString(1);
			return null;
		}
		catch (Exception e)
		{
			return null;
		}
	}
	
	@Override
	public void addChargeItem(RoomCharge charge) throws MyException
	{
		//insert into room_charge(cId,rId,charge,type,date) values(1,1,23,1,'2012-01-01')
		
		execSQL("insert into room_charge(cId,rId,charge,type,date) values(?,?,?,?,date(?,'localtime'))"
				,charge.cId,charge.rId,charge.charge,charge.type,charge.date);
		
		
	}

	@Override
	public void updateChargeItem(int rcId, int type, double balance)
			throws MyException
	{
		
		//update room_charge set type = 1 ,charge = 10000 where id = 1
		execSQL("update room_charge set type = ? ,charge = ? where id = ?"
				,type,balance,rcId);
		
	}

	
	@Override
	public List<RoomCharge> getChargeItemByDate(int cid, String beginDate,
			String endDate) throws MyException
	{
		//select * from room_charge where cId= 115 and room_charge.[date] >= date('2013-05-07') and room_charge.[date] <= date('2013-05-07')
		List<RoomCharge> list = new ArrayList<RoomCharge>();
		CachedRowSet crs = null;
		if(cid == -1)
			if(beginDate == null)
				if(endDate == null)
					crs = execSQL("select * from room_charge order by julianday(date) asc");
				else
					crs = execSQL("select * from room_charge where room_charge.[date] <= date(?,'localtime') order by julianday(date) asc",endDate);
			else
				if(endDate == null)
					crs = execSQL("select * from room_charge where room_charge.[date] >= date(?,'localtime') order by julianday(date) asc",beginDate);
				else
					crs = execSQL("select * from room_charge where room_charge.[date] >= date(?,'localtime') and room_charge.[date] <= date(?,'localtime') order by julianday(date) asc",beginDate,endDate);
		else
			if(beginDate == null)
				if(endDate == null)
					crs = execSQL("select * from room_charge where cId = ? order by julianday(date) asc",cid);
				else
					crs = execSQL("select * from room_charge where cId = ? and room_charge.[date] <= date(?,'localtime') order by julianday(date) asc",cid,endDate);
			else
				if(endDate == null)
					crs = execSQL("select * from room_charge where cId = ? and room_charge.[date] >= date(?,'localtime') order by julianday(date) asc",cid,beginDate);
				else
					crs = execSQL("select * from room_charge where cId = ? and room_charge.[date] >= date(?,'localtime') and room_charge.[date] <= date(?,'localtime') order by julianday(date) asc",cid,beginDate,endDate);

		try
		{
			while(crs.next())
			{
				list.add(new RoomCharge(
						crs.getInt(1),		//id
						crs.getInt(2),		//cId
						crs.getInt(3),		//rId
						crs.getDouble(4),	//charge
						crs.getInt(5),		//type
						crs.getString(6)	//date
						));
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new MyException(MyException.EXCEPTION_ROWSETOPERATION);
		}
		return list;
	}

	@Override
	public void delChargeItem(int rcId) throws MyException
	{
		execSQL("delete from room_charge where id = ?",rcId);
	}

	@Override
	public void delChargeItemByCheckId(int cid) throws MyException
	{
		execSQL("delete from room_charge where cId = ?",cid);
	}

	@Override
	public void updateRoomTypeNum(int type) throws MyException
	{
		int num = countRoomNumByType(type,false);
		execSQL("update room_price set num = ? where id = ?",num,type);
		
	}

	@Override
	public int countRoomNumByType(int type,boolean includeNotAvaliable) throws MyException
	{
		CachedRowSet crs = null;
		if(includeNotAvaliable)
			if(type == StaticData.ROOM_TYPE_IGNORE)
				crs = execSQL("select count(*) from room ");
			else
				crs = execSQL("select count(*) from room where type = ? ",type);
		else
			if(type == StaticData.ROOM_TYPE_IGNORE)
				crs = execSQL("select count(*) from room where isAvaliable = ? ",true);
			else
				crs = execSQL("select count(*) from room where type = ? and isAvaliable = ?",type,true);
		try
		{
			crs.next();
			return crs.getInt(1);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new MyException(MyException.EXCEPTION_ROWSETOPERATION);
		}
		
	}

	
	@Override
	public RoomPrice getFirstRoomPrice() throws MyException
	{
		CachedRowSet crs = execSQL("select * from room_price limit 1");
		try
		{
			if (crs.next())
			{
				// // id description price num hourPrice
				return (new RoomPrice(crs.getInt(1), crs.getString(2), crs
						.getDouble(3),crs.getInt(4),crs.getDouble(5)));
			}
			return new RoomPrice(StaticData.NOT_VALIDATE,null,0,0,0);
		}
		catch (SQLException e)
		{
			throw new MyException(MyException.EXCEPTION_ROWSETOPERATION);
		}
	}

	@Override
	public double sumChargeItemByDate(int cid, String beginDate, String endDate)
			throws MyException
	{
				CachedRowSet crs = null;
				if(cid == -1)
					if(beginDate == null)
						if(endDate == null)
							crs = execSQL("select sum(charge) from room_charge order by julianday(date) asc");
						else
							crs = execSQL("select sum(charge) from room_charge where room_charge.[date] <= date(?,'localtime') order by julianday(date) asc",endDate);
					else
						if(endDate == null)
							crs = execSQL("select sum(charge) from room_charge where room_charge.[date] >= date(?,'localtime') order by julianday(date) asc",beginDate);
						else
							crs = execSQL("select sum(charge) from room_charge where room_charge.[date] >= date(?,'localtime') and room_charge.[date] <= date(?,'localtime') order by julianday(date) asc",beginDate,endDate);
				else
					if(beginDate == null)
						if(endDate == null)
							crs = execSQL("select sum(charge) from room_charge where cId = ? order by julianday(date) asc",cid);
						else
							crs = execSQL("select sum(charge) from room_charge where cId = ? and room_charge.[date] <= date(?,'localtime') order by julianday(date) asc",cid,endDate);
					else
						if(endDate == null)
							crs = execSQL("select sum(charge) from room_charge where cId = ? and room_charge.[date] >= date(?,'localtime') order by julianday(date) asc",cid,beginDate);
						else
							crs = execSQL("select sum(charge) from room_charge where cId = ? and room_charge.[date] >= date(?,'localtime') and room_charge.[date] <= date(?,'localtime') order by julianday(date) asc",cid,beginDate,endDate);

				try
				{
					crs.next();
					return crs.getDouble(1);
				}
				catch (SQLException e)
				{
					e.printStackTrace();
					throw new MyException(MyException.EXCEPTION_ROWSETOPERATION);
				}
	}

	@Override
	public boolean transactionForSwitchRoom(int rid, int tagetId) throws Exception
	{
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		CheckInInfo cif = getCheckInById(getRoomCheckId(rid));
		if(cif == null || cif.id <=0)
			return false;
		
		try
		{
			con = DriverManager.getConnection("jdbc:sqlite:"+ StaticData.dataFilePath);
			con.setAutoCommit(false);
			
			///新房间关联 入住信息
			pst = con.prepareStatement("update room set cId = ? where id = ?");
			pst.setInt(1,cif.id);
			pst.setInt(2,tagetId);
			pst.execute();
			pst.close();
			
			
			///入住信息关联新 房间
			pst = con.prepareStatement("update check_in set rId = ? where id = ?");
			pst.setInt(1,tagetId);
			pst.setInt(2,cif.id);
			pst.execute();
			pst.close();
			
			///原房间 设置为 脏房
			pst = con.prepareStatement("update room set isClean = ? ,cId = 0 where id = ?");
			pst.setBoolean(1,false);
			pst.setInt(2,rid);
			pst.execute();
			
			con.commit();
			
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			con.rollback();
			throw e;
		}
		finally
		{
			try
			{
				if(rs != null)
					rs.close();
				if(pst != null)
					pst.close();
				if(con != null)
					con.close();
			}
			catch(Exception e)
			{
				return false;
			}
		}
	}

	@Override
	public boolean transactionForCheckIn(CheckInInfo ci,int balanceType) throws Exception
	{
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		
		try
		{
			con = DriverManager.getConnection("jdbc:sqlite:"+ StaticData.dataFilePath);
			con.setAutoCommit(false);
			
			///添加 checkIn
			pst = con.prepareStatement("insert into check_in"
					+ "(rId,orderId,name,phoneNumber,numberPeople,outTime,description,balance,isHourRoom,enterTime)"
					+ "values(?,?,?,?,?,?,?,?,?,datetime('now','localtime'))",Statement.RETURN_GENERATED_KEYS);
			pst.setInt(1,ci.rId);
			pst.setInt(2,ci.orderId);
			pst.setString(3,ci.name);
			pst.setString(4,ci.phoneNumber);
			pst.setInt(5,ci.numberPeople);
			pst.setString(6,ci.outTime);
			pst.setString(7,ci.description);
			pst.setDouble(8,ci.balance);
			pst.setBoolean(9,ci.isHourRoom);
			
			pst.executeUpdate();
			rs = pst.getGeneratedKeys();
			rs.next();
			int cId = rs.getInt(1); 
			
			
			///房间关联checkIn
			pst.close();
			pst = con.prepareStatement("update room set cId = ? where id =?");
			pst.setInt(1,cId);
			pst.setInt(2,ci.rId);
			pst.execute();
			
			//更新 订单
			if(ci.orderId > 0)
			{
				pst = con.prepareStatement("update order_data set info = ? where  id = ?");
				pst.setInt(1,Order.ORDER_TYPE_CHECKED);
				pst.setInt(2,ci.orderId);
				pst.execute();
			}
			
			
			///添加 账目 （首次入账）
			Account ac = new Account(ci.rId,cId,"入住时首次入账",balanceType,false,ci.balance,null,0);
			pst = con.prepareStatement("insert into account(rId,cId,description,type,isConsume,balance,genTime) "
					+ " values(?,?,?,?,?,?,datetime('now','localtime'))");
			pst.setInt(1,ac.rId);
			pst.setInt(2,ac.cId);
			pst.setString(3,ac.description);
			pst.setInt(4,ac.type);
			pst.setBoolean(5,ac.isConsume);
			pst.setDouble(6,ac.balance);
			pst.execute();
			
			
			con.commit();
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			con.rollback();
			throw e;
		}
		finally
		{
			try
			{
				if(rs != null)
					rs.close();
				if(pst != null)
					pst.close();
				if(con != null)
					con.close();
			}
			catch(Exception e)
			{
				return false;
			}
		}
	}

	
	@Override
	public boolean transactionForCheckOut(int roomId)
			throws Exception
	{
		
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try
		{
			
			int cid = getRoomCheckId(roomId);	
			if(cid == 0)
				return false;
			
			double balance = sumChargeItemByDate(cid,null,null);	//计算房费
			
			
			con = DriverManager.getConnection("jdbc:sqlite:"+ StaticData.dataFilePath);
			con.setAutoCommit(false);
			
			
			///更新入住信息
			pst = con.prepareStatement("update check_in set balance = ?, isCheckOut = 1,outTime =datetime('now','localtime') where id =?");
			pst.setDouble(1,balance);
			pst.setInt(2,cid);
			pst.execute();
			pst.close();
			
			///更新 房间为脏房
			pst = con.prepareStatement("update room set isClean = ? ,isAvaliable = ? ,cId = 0 where id = ?");
			pst.setBoolean(1,false);
			pst.setBoolean(2,true);
			pst.setInt(3,roomId);
			pst.execute();
			
			con.commit();
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			con.rollback();
			throw e;
		}
		finally
		{
			try
			{
				if(rs != null)
					rs.close();
				if(pst != null)
					pst.close();
				if(con != null)
					con.close();
			}
			catch(Exception e)
			{
				return false;
			}
		}
		
		
	}


	@Override
	public boolean transactionForUpdateCheckInInfoOrder(CheckInInfo cif,int modifyOrederId,String outDate) throws Exception
	{
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		
		try
		{
			con = DriverManager.getConnection("jdbc:sqlite:"+ StaticData.dataFilePath);
			con.setAutoCommit(false);
			
			///将当前订单 改为已入住
			pst = con.prepareStatement("update order_data set info = ? where  id = ?");
			pst.setInt(1,Order.ORDER_TYPE_CHECKED);
			pst.setInt(2,cif.orderId);
			pst.execute();
			pst.close();
			
			///将以前的订单设置为 可用
			if(modifyOrederId != 0)
			{
				pst = con.prepareStatement("update order_data set info = ? where  id = ?");
				pst.setInt(1,Order.ORDER_TYPE_AVALIDATE);
				pst.setInt(2,modifyOrederId);
				pst.execute();
				pst.close();
			}
			
			
			///更新checkIninfo 对象
			pst = con.prepareStatement("update check_in set "
					+ "orderId = ?,name = ? ,phoneNumber = ?,numberPeople = ?,description = ?,isHourRoom = ? ,outTime = ? "
					+ "where id = ? ");
			pst.setInt(1,cif.orderId);
			pst.setString(2,cif.name);
			pst.setString(3,cif.phoneNumber);
			pst.setInt(4,cif.numberPeople);
			pst.setString(5,cif.description);
			pst.setBoolean(6,cif.isHourRoom);
			pst.setString(7,outDate);
			pst.setInt(8,cif.id);
			pst.execute();
			
			
			con.commit();
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			con.rollback();
			throw e;
		}
		finally
		{
			try
			{
				if(rs != null)
					rs.close();
				if(pst != null)
					pst.close();
				if(con != null)
					con.close();
			}
			catch(Exception e)
			{
				return false;
			}
		}
	}

	
	
	

}
