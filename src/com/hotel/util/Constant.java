package com.hotel.util;

import java.util.ArrayList;
import java.util.List;

public class Constant
{

	public static	String PROJECT_NAME = "HotelSystem";
	public static  int FAILED = 0 ;
	public static  int SUCCESS = 1 ;
	public static  boolean OPEN_ACCESS_INTERCEPTOR = true;
	public static  List<String> EXCLUDE_INTERCEPTOR ;
	static{
		
		EXCLUDE_INTERCEPTOR = new ArrayList<String>();
		EXCLUDE_INTERCEPTOR.add("main/loginAction.action");
		EXCLUDE_INTERCEPTOR.add("main/requestLoginOffAction.action");
	}
}
