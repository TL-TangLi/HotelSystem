package com.hotel.entity;

import java.util.Calendar;
import java.util.List;

import net.sf.json.JSONArray;

import com.hotel.datamanage.Manager;
import com.hotel.staticdata.StaticData;

public class RoomState
{

	public Room room;
	public RoomPrice roomPrice;
	public CheckInInfo checkInInfo;
	public RoomColor roomColor;

	
	public  double leftMoney;			//余额
	
	private boolean countByHour;		//当前是否以小时计算房费
	private double enterCount; 		// 入住天数 或者小时数
	
	
	public boolean isCountByHour()
	{
		return countByHour;
	}

	public void setCountByHour(boolean countByHour)
	{
		this.countByHour = countByHour;
	}

	public double getEnterCount()
	{
		return enterCount;
	}

	public void setEnterCount(double enterCount)
	{
		this.enterCount = enterCount;
	}

	public double getLeftMoney()
	{
		return leftMoney;
	}

	public void setLeftMoney(double leftMoney)
	{
		this.leftMoney = leftMoney;
	}

	
	Manager manager;
	
	public RoomState(Room room, RoomPrice rp,CheckInInfo checkInInfo)
	{
		
		manager = Manager.getManager();
		this.room = room;
		this.roomPrice = rp;
		this.checkInInfo = checkInInfo;
		
		
		/*-----------------------------------roomPrice 可以为null（数据库异常） 如果 其id 无效 说明该房间类型被非法删除，应当重新关联 -----------------------------------------*/
		if(roomPrice.id == StaticData.NOT_VALIDATE)
		{
			roomPrice = manager.getFirstRoomPrice();
			//  如果依然非法那么 数据库中 一个 房间类型都没有，应当向其中加入一个
			if(roomPrice.id == StaticData.NOT_VALIDATE)
			{
				manager.addRoomType(new RoomPrice(0,"XXX未知XXX",0,0,0));
				roomPrice = new RoomPrice(0,"XXX未知XXX",0,0,0);
			}
			manager.updatePartRoom(room.id,"XXXX",roomPrice.id,room.type);
			room.type = roomPrice.id;
		}
		
		/*-----------------------------------房费问题-----------------------------------------*/
		if(checkInInfo != null )
		{
			Calendar nowCalendar = Calendar.getInstance();	//现在日期时间
			Calendar enterCalendar = Calendar.getInstance(); //中国习惯，东八时区
			
			String dateTime = checkInInfo.enterTime.split("\\.")[0];	//去掉秒后面的毫秒		
			String[] date_time =dateTime.split(" ");					//分离日期 和时间
			String[] date = date_time[0].split("-");					//获取日期
			String[] time = date_time[1].split(":");					//获取时间
			
			
			int year = Integer.parseInt(date[0]);
			int day = Integer.parseInt(date[2]);
			enterCalendar.set(year,Integer.parseInt(date[1])-1,day);	//月份从0开始必须减一
			
			int hour = Integer.parseInt(time[0]);						//入住的时间点
			enterCalendar.set(Calendar.HOUR_OF_DAY,hour);				//千万要注意这时hour_OF_DAY 不是hour！！
			enterCalendar.set(Calendar.MINUTE,Integer.parseInt(time[1]));
			enterCalendar.set(Calendar.SECOND,Integer.parseInt(time[2]));
			
			
			
			////////////////房费延迟产生--未产生房费
			/*----------------------------------------------------------------------------*/
			long intervalSecond = ((nowCalendar.getTime()).getTime() - (enterCalendar.getTime()).getTime())/1000;
			//如果入住还不到 房费延迟生成时间 那么 不要产生房费
			if((intervalSecond/60) <= StaticData.CHARGE_GEN_DELAY)
			{
				enterCount  = 0;
				countByHour = true;
				leftMoney = manager.sumAccountByFilter(checkInInfo.id,-1,0,null,null)		//入账
						-manager.sumAccountByFilter(checkInInfo.id,-1,1,null,null);			//消费
			}
			
			//////////////开始计算房费
			/*----------------------------------------------------------------------------*/
			else
			{
				//如果是当天入住的 ,且在6点之后，20点之前的,可以算作小时房
				if(nowCalendar.get(Calendar.DAY_OF_YEAR) - enterCalendar.get(Calendar.DAY_OF_YEAR) == 0
						&& hour>= StaticData.HOUR_ROOM_BEGIN&& hour<StaticData.HOUR_ROOM_END)
					countByHour(nowCalendar,enterCalendar);	
				
				
				////说明已经不是小时房了。。应当，以天数来计算！
				if(countByHour == false)
				{
					countByDay(nowCalendar,enterCalendar,year,hour);
				}
			}
			
		}
		
		/*-----------------------------------入住状态检测 并更新balance 字段-----------------------------------------*/
		///入住状态检测
		int state;
		//不可入住
		if(room.isAvaliable == false)
			state = StaticData.ROOM_STATE_INVALIABLE;
		else if(checkInInfo == null)
		{
			//干净房
			if(room.isClean)
				state = StaticData.ROOM_STATE_CLEAN;
			//脏房
			else
				state = StaticData.ROOM_STATE_DIRTY;
		}
		else
		{
			
//			//超过 退房日期的 一点 算是过期
//			if(StaticData.beforeNow(checkInInfo.outTime))
//				state = StaticData.ROOM_STATE_EXPIRED;
			
			//小时房
			if(checkInInfo.isHourRoom)
				state = StaticData.ROOM_STATE_HOURROOM;
			//欠费房
			else if(leftMoney<0)
				state = StaticData.ROOM_STATE_OWEROOM;
			//正常入住房
			else
				state = StaticData.ROOM_STATE_NORMALENTER;
		}
		
		roomColor = manager.getRoomColorByState(state);
		
	}
	
	//按 天数计算房费。
	private void countByDay(Calendar nowCalendar,Calendar enterCalendar,int year,int hour)
	{
		double dayDistance = 0;										//存放入住日期和现在日期的相隔天数 可能是小数 （半天！）
		int yearDistance = nowCalendar.get(Calendar.YEAR) - year;	//相隔年数
		//客户跨年入住 情况下获取 间隔天数！
		if(yearDistance != 0 )							
		{
			//获取入住当年总共入住天数
			dayDistance += enterCalendar.getActualMaximum(Calendar.DAY_OF_YEAR)-enterCalendar.get(Calendar.DAY_OF_YEAR);
			//获取以后几年的全年天数
			for(int i = 1;i<yearDistance;i++)
			{
				enterCalendar.set(Calendar.YEAR,year+i);
				dayDistance += enterCalendar.getActualMaximum(Calendar.DAY_OF_YEAR);
			}
			//获取今年的入住天数
			dayDistance += nowCalendar.get(Calendar.DAY_OF_YEAR); 
		}
		//未跨年住获取间隔天数
		else									
		{
			dayDistance = nowCalendar.get(Calendar.DAY_OF_YEAR) - enterCalendar.get(Calendar.DAY_OF_YEAR);
		}
		
		//入住时为 6点之前，那么要算一天
		if(hour<StaticData.HOUR_ROOM_BEGIN)			
		{
			dayDistance += 1;
		}
		
		//如果是当天，且入住时间在 6点 之后  且超过 四个小时（未超过，就在该函数调用之前已经以小时房计算了）  那么算一天
		if(dayDistance == 0)
			dayDistance = 1;
		
		//如果不是当天入住，且一点过后，半天房费，过了六点 全天房费
		else if(nowCalendar.get(Calendar.HOUR_OF_DAY)>=StaticData.CHECK_OUT_DEADLINE_HALFDAY)
		{
			dayDistance += 0.5;
			if(nowCalendar.get(Calendar.HOUR_OF_DAY)>=StaticData.CHECK_OUT_DEADLINE_ALLDAY)
				dayDistance += 0.5;
		}
		
		
		//记录入住天数
		enterCount = dayDistance;
		
		//入账总额，减去消费，减去房费，算出余额。
//		leftMoney = checkInInfo.balance - dayDistance*(roomPrice.price);
		leftMoney = manager.sumAccountByFilter(checkInInfo.id,-1,0,null,null)
				-manager.sumAccountByFilter(checkInInfo.id,-1,1,null,null)
//				-roomPrice.price*enterCount;
				- updateRoomChargeItemAndGetRoomCharge(nowCalendar,enterCalendar);
	}
	
	
	//按小时 计算房费，如果计算出了 返回 true 否者返回false(说明已经超过了四个小时,数据库中将小时房改为false)
	private boolean countByHour(Calendar nowCalendar,Calendar enterCalendar)
	{
		int hours = 0;
		//如果是当天入住的小时房
		hours = nowCalendar.get(Calendar.HOUR_OF_DAY) - enterCalendar.get(Calendar.HOUR_OF_DAY);
		if(nowCalendar.get(Calendar.MINUTE) >=enterCalendar.get(Calendar.MINUTE))
			hours+= 1;
		
		//说明还是小时房，
		if(hours< StaticData.HOUR_ROOM_HOURS + 1)
		{
			//记录入住小时数
			enterCount = hours;
			countByHour = true;
			
			//入账总额减去花费，算出余额。  这时的房价不是按照房型算的 ，而是小时房的价格
//			leftMoney = checkInInfo.balance - hours*(roomPrice.hourPrice);
//			leftMoney = checkInInfo.balance - (roomPrice.hourPrice);
			leftMoney = manager.sumAccountByFilter(checkInInfo.id,-1,0,null,null) 
					-manager.sumAccountByFilter(checkInInfo.id,-1,1,null,null) 
//					-roomPrice.hourPrice*enterCount;
					- updateRoomChargeItemAndGetRoomCharge(nowCalendar,enterCalendar);
			return true;
			
		}
		countByHour = false;
		return false;
	}
	
	
	//更新 房费 条目  并且返回 房费！
	private double updateRoomChargeItemAndGetRoomCharge(Calendar nowCalendar,Calendar enterCalendar)
	{
		try
		{
			List<RoomCharge> listRoomCharge =manager.getChargeItemByFilter(checkInInfo.id,null,null);
			
			//如果 目前还是小时房，（肯定只有一条房费记录）
			if(countByHour)
			{
				//如果数据库异常，临时返回  小时房费
				if(listRoomCharge ==  null)
					return (roomPrice.hourPrice);
				
				//如果存在  返回对应房费
				if(listRoomCharge.size() != 0)
					return listRoomCharge.get(0).charge;
				//如果没有记录   那么加入 并返回 房费
				else
				{
					manager.addChargeItem(new RoomCharge(0,checkInInfo.id,room.id,(roomPrice.hourPrice),RoomCharge.ROOM_CHARGE_HOUR,StaticData.CalendarToString(nowCalendar)));
					return (roomPrice.hourPrice);
				}
					
			}
			//非小时费 房费  假设有 n 天 或者 n-1天半了。那么应该有 n条 记录
			else
			{
				//如果数据库异常
				if(listRoomCharge == null)
					return roomPrice.price*enterCount;
				
				double sum = 0;
				boolean haveHalfDay = false;
				int n = (int) enterCount;
				//如果有个半天，那么 n +1
				if(enterCount > n)
				{
					haveHalfDay = true;
					n++;
				}
				
				//如果记录数 刚好
				if(listRoomCharge.size() >= n)
				{
					//对 最后一天以前的 房费作检查  如果不为 全天房费 那么必须 调整为全天房费！ 注意是最后一天 以前 
					for(int i = 0 ;i<listRoomCharge.size()-1;i++)
					{
						RoomCharge charge = listRoomCharge.get(i);
						if(charge.type != RoomCharge.ROOM_CHARGE_ALLDAY)
						{
							manager.updateChargeItem(charge.id,RoomCharge.ROOM_CHARGE_ALLDAY,roomPrice.price);
							sum += roomPrice.price;
						}
						else
							sum += charge.charge;
					}
					
					RoomCharge charge = listRoomCharge.get(n-1);
					//如果最后一天算作半天，
					if(haveHalfDay)
					{
						if(charge.type != RoomCharge.ROOM_CHARGE_HALFDAY)
							manager.updateChargeItem(charge.id,RoomCharge.ROOM_CHARGE_HALFDAY,roomPrice.price/2);
						sum += roomPrice.price/2;
					}
					//如果 最后一天 按全天算
					else
					{
						if(charge.type != RoomCharge.ROOM_CHARGE_ALLDAY)
							manager.updateChargeItem(charge.id,RoomCharge.ROOM_CHARGE_ALLDAY,roomPrice.price);
						sum += roomPrice.price;
					}
					
				}
				
				
				//如果记录数 不够，那么少 几条 ，从 今天开始 依次向以前 添加 房费记录
				else
				{
					//所有已入 房费作检查  如果不为 全天房费 那么必须 调整为全天房费！
					for(int i = 0 ;i<listRoomCharge.size();i++)
					{
						RoomCharge charge = listRoomCharge.get(i);
						if(charge.type != RoomCharge.ROOM_CHARGE_ALLDAY)
						{
							manager.updateChargeItem(charge.id,RoomCharge.ROOM_CHARGE_ALLDAY,roomPrice.price);
							sum += roomPrice.price; 
						}
						else
							sum += charge.charge;
					}
					
					int addNum =  n - listRoomCharge.size();
					
					//添加最后一天 房费记录
					if(haveHalfDay)
					{
						//因为有半天 房费了 。说明，最后一天房费记录 是今天的。
						manager.addChargeItem(new RoomCharge(0,checkInInfo.id,room.id,(roomPrice.price)/2,RoomCharge.ROOM_CHARGE_HALFDAY,StaticData.CalendarToString(nowCalendar)));
						sum += roomPrice.price/2;
					}
					else
					{
						
						//如果 入住 天数 大于 1天，并且 现在时间 小于 下午一点，那么，最后一条房费 不是今天 ，而是 昨天！！！！因此要向后退一天
						//如果 入住 天数 等于 1天，并且 是在 今天0点 到六点 入住的，或者昨天 入住的，那么，最后一天的房费记录 也是算作 昨天的。
						if((enterCount>1 && nowCalendar.get(Calendar.HOUR_OF_DAY) < StaticData.CHECK_OUT_DEADLINE_HALFDAY)
								||(enterCount == 1 &&(enterCalendar.get(Calendar.HOUR_OF_DAY)<StaticData.HOUR_ROOM_BEGIN ||
										enterCalendar.get(Calendar.DAY_OF_MONTH) < nowCalendar.get(Calendar.DAY_OF_MONTH))))
							nowCalendar.add(Calendar.DAY_OF_MONTH,-1);
						
						manager.addChargeItem(new RoomCharge(0,checkInInfo.id,room.id,(roomPrice.price),RoomCharge.ROOM_CHARGE_ALLDAY,StaticData.CalendarToString(nowCalendar)));
						sum += roomPrice.price;
					}
					//添加最后一天 以前的记录
					for(int i= 1;i<addNum;i++)
					{
						nowCalendar.add(Calendar.DAY_OF_MONTH,-1);
						manager.addChargeItem(new RoomCharge(0,checkInInfo.id,room.id,(roomPrice.price),RoomCharge.ROOM_CHARGE_ALLDAY,StaticData.CalendarToString(nowCalendar)));
						sum += roomPrice.price;
					}
				}
				//返回记录的房费总数
				return sum;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return 0;
		}
		
		
		
	}
	
	///将roomState 转为 json数组 ，用于jsp的请求应答（requestRoomAction）  其实 完全没有必要，struts json plugin 会做的工作！引以为鉴
	public String toJsonString()
	{
		JSONArray ja = new JSONArray();
		ja.add(this);
		ja.add(this.room);
		ja.add(this.roomPrice);
		ja.add(this.roomColor);
		if(this.checkInInfo != null)
			ja.add(this.checkInInfo);
		return ja.toString();
	}
	
	
	
}
