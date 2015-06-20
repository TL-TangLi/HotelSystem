package com.hotel.util;

public class Common
{
	public static boolean notJustEmptyChar(String src)
	{
		return !(src==null || src.trim().equals(""));
	}
}
