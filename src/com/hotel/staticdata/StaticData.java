package com.hotel.staticdata;

import java.io.File;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author christopher
 * @info 本类的数据，应该和js 中数据一一对应，后者是客服端的全局数据
 */
public class StaticData
{
	
	
	public static String dataFilePath ;
	static
	{
		dataFilePath = getWebRootPath()+"\\WEB-INF\\HotelSysData.db";
//		dataFilePath = "C:\\Users\\christopher\\Desktop\\HotelSysData.db";
		
	}
	
	public static String todayToString()
	{
		return CalendarToString(Calendar.getInstance());
	}
	
	
	public static String CalendarToString(Calendar cl)
	{
		int year = cl.get(Calendar.YEAR);
		int month = cl.get(Calendar.MONTH)+1;
		int day = cl.get(Calendar.DAY_OF_MONTH);
		return formatDate(year, month, day);
	}
	
	/**
	 * @param year
	 * @param month
	 * @param day
	 * @return   返回xxxx-xx-xx 形式字符串
	 */
	public static String formatDate(int year,int month,int day)
	{
		String temp = year +"-";
		if(month <10)
			temp += "0"+month+"-";
		else
			temp += month+"-";
		
		if(day<10)
			temp += "0"+day;
		else
			temp += day;
			
		return temp;
		
	}
	
	
	/**
	 * @param date xxxx-xx-xx 形式的数据
	 * @return 如果格式不正确返回 null
	 */
	public static int[] dateConvertToInt(String date)
	{
		int[] temp = new int[3];
		
		String str[] = date.split("-");
		if(str == null ||str.length != 3)
			return null;
		
		temp[0] = Integer.parseInt(str[0]);
		temp[1] = Integer.parseInt(str[1]);
		temp[2] = Integer.parseInt(str[2]);
		
		return temp;
	}
	
	
	/**
	 * @param date
	 * @param days
	 * @return 如果格式不正确 返回 null
	 */
	public static String dateAdd(String date,int days)
	{
		int[] temp = StaticData.dateConvertToInt(date);
		
		if(temp == null)
			return null;
		
		Calendar cl = Calendar.getInstance();
		cl.set(temp[0],temp[1]-1,temp[2]);
		cl.add(Calendar.DAY_OF_YEAR,days);
		temp[0] = cl.get(Calendar.YEAR);
		temp[1] = cl.get(Calendar.MONTH) +1;
		temp[2] = cl.get(Calendar.DAY_OF_MONTH);
		
		return StaticData.formatDate(temp[0],temp[1],temp[2]);
	}
	
	
	/**
	 * @param cl
	 * @param addDays
	 * @return  获取 cl 之后addDays 天 ，的星期
	 */
	public static String getDateWeek(Calendar cl,int addDays)
	{
		Calendar newDate = (Calendar) cl.clone();
		newDate.add(Calendar.DAY_OF_YEAR,addDays);
		int week = newDate.get(Calendar.DAY_OF_WEEK);
		
		switch(week)
		{	
		case Calendar.SUNDAY:
			return "星期日";
		case Calendar.MONDAY:
			return "星期一";
		case Calendar.TUESDAY:
			return "星期二";
		case Calendar.WEDNESDAY:
			return "星期三";
		case Calendar.THURSDAY:
			return "星期四";
		case Calendar.FRIDAY:
			return "星期五";
		case Calendar.SATURDAY:
			return "星期六";
		}
		return null;
	}
	
	
	/**
	 * @param date
	 * @return  判断一个 字符串日期是否 大于现在， 如果 日期小于 或者 等于现在返回false  字符串形式必须是 xxxx-xx-xx 否者转换异常返回 false
	 */
	public static boolean beforeNow(String date)
	{
		java.util.Date nowdate=new java.util.Date(); 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date d;
		try
		{
			d = sdf.parse(date);
			return d.before(nowdate);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
			return false;
		}

		

	}
	
	
	
	
	//获取自己某个类 .class 文件所在路径
	public static String getCurrentClassPath(@SuppressWarnings("rawtypes") Class clazz)
	{
	        String path = "";
	        try
	        {
	            File file = new File(clazz.getResource("").toURI());
	            path = file.getAbsolutePath();
	        }catch (URISyntaxException e){
	            e.printStackTrace();
	        }
	        return path;
	  }
	    
	    /*此方法只能对web项目在服务器开启的情况下，在页面上调用时才有用。
	     *因为只有这样getClassesPath2才能得到工程发布目录下的类编译路径（在WEB-INF\classes下）
	     *如果是普通的java工程，则只会返回类文件的编译路径*/
	public static String getWebRootPath(){
        String path = "";
        path = getCurrentClassPath(StaticData.class).split("WEB-INF")[0];
        if(path.endsWith("/") || path.endsWith("\\")){
            //使返回的路径不包含最后的"/"或"\"
            path = path.substring(0, path.length()-1);
        }
        return path;
    }
	
	
	///在订单过滤式 如果房型指定为 -1 ，那么将忽略房型过滤
	public static final int ROOM_TYPE_IGNORE = -1;
	
	///数据库 查询 某个对象，如果不存在，那么返回的对象的id 为 NOT_AVALIDATE;
	public static final int NOT_VALIDATE = -1;
	
	///房间状态号 ，与数据库对应，从1--6
	public static final int ROOM_STATE_CLEAN = 1;
	public static final int ROOM_STATE_DIRTY = 2;
	public static final int ROOM_STATE_NORMALENTER = 3;
	public static final int ROOM_STATE_HOURROOM = 4;
	public static final int ROOM_STATE_OWEROOM = 5;
	
	public static final int ROOM_STATE_INVALIABLE = 6;
	
	public static final int ROOM_STATE_VALIABLE = 8; 
	
	public static final int ROOM_STATE_EXPIRED = 7;
	
	
	
	//房费产生 时间 协议
	public static final int HOUR_ROOM_END = 20;	//小时房在该时间结束，超过该时间 哪怕 一分钟入住的，都不能算作小时房了，直接产生全天房费  8：01 入住不能算作小时房
	public static final int HOUR_ROOM_BEGIN = 6;	//小时房开始时间，在当日 该时间以下入住的。 都要多算一天房费。！ 比如 6：01分 可以入住，5：59 要多算一天
	public static final int CHECK_OUT_DEADLINE_HALFDAY = 13 ; //超过改时间为退房的产生半天房费。 13：00 之前应当退房
	public static final  int CHECK_OUT_DEADLINE_ALLDAY = 18; //超过改时间未退房的产生全天房费。 18：00 之前应当退房
	
	public static final int HOUR_ROOM_HOURS = 4;			//4*60 min 内算作小时房，操作该时长，算全天
	
	public static final int CHARGE_GEN_DELAY = 15 ;  		//房费延迟产生 的分钟数
	
}
