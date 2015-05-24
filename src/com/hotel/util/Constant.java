package com.hotel.util;

import java.util.ArrayList;
import java.util.List;

public class Constant
{
	
	static  int FAILED = 0 ;
	static  int SUCCESS = 1 ;
	static  boolean OPEN_ACCESS_INTERCEPTOR = true;
	static  List<String> EXCLUDE_INTERCEPTOR ;
	static{
		
		EXCLUDE_INTERCEPTOR = new ArrayList<String>();
		EXCLUDE_INTERCEPTOR.add("main/loginAction.action");
		EXCLUDE_INTERCEPTOR.add("main/requestLoginOffAction.action");
	}
}
