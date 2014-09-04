package com.hotel.entity;

/**
 * @author christopher
 * @info name psw
 */
public class User
{
	public  String userName ;
	public  String psw;
	public int level;
	
	public int getLevel()
	{
		return level;
	}
	public void setLevel(int level)
	{
		this.level = level;
	}
	public String getUserName()
	{
		return userName;
	}
	public void setUserName(String userName)
	{
		this.userName = userName;
	}
	public String getPsw()
	{
		return psw;
	}
	public void setPsw(String psw)
	{
		this.psw = psw;
	}
	public User(String userName, String psw,int level)
	{
		super();
		this.userName = userName;
		this.psw = psw;
		this.level = level;
	}
	public User(){}
}
