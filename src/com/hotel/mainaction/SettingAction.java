package com.hotel.mainaction;

import java.util.ArrayList;
import java.util.List;

import com.hotel.entity.User;
import com.opensymphony.xwork2.ActionSupport;

public class SettingAction extends ActionSupport
{
	private static final long serialVersionUID = 1L;
	
	
	private String requestResult ;
	public String getRequestResult()
	{
		return requestResult;
	}

	public void setRequestResult(String requestResult)
	{
		this.requestResult = requestResult;
	}

	
	/*-----------------------------------modifyPswActionForSetting-----------------------------------------*/
	private String newPsw;
	public String getNewPsw()
	{
		return newPsw;
	}

	public void setNewPsw(String newPsw)
	{
		this.newPsw = newPsw;
	}
	
	public String modifyPsw()
	{
		
		System.out.println(newPsw);
		requestResult = "requestResult";
		
		return "requestModifyPswResult";
	}
	
	
	/*-----------------------------------queryAllAccountActionForSetting-----------------------------------------*/
	
	private List<User> users;
	
	public List<User> getUsers()
	{
		return users;
	}

	public void setUsers(List<User> users)
	{
		this.users = users;
	}

	public String queryAllAccount()
	{
		
		users = new ArrayList<User>();
		for(int i= 0 ;i <5 ;i++)
			users.add(new User("name","psw",2));
		return "requetQueryAllAccountResult";
	}
	

}
