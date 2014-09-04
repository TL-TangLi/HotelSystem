package com.hotel.data_access;

public class MyException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	
	public static final int EXCEPTION_CONNECTE = 0;			//数据库连接异常
	public static final int EXCEPTION_EXECUTE = 1;				//sql 执行异常
	public static final int EXCEPTION_RESULTSETTOROWSET = 2;	//rs 转换 rowset 异常
	public static final int EXCEPTION_CONNECTECLOSE = 3;		//数据库 连接关闭异常
	public static final int EXCEPTION_ROWSETOPERATION = 4;		//cachedrowset 操作异常
	
	public int msgType ;
	public MyException(int type)
	{
		this.msgType = type;
	}
	
	
}
