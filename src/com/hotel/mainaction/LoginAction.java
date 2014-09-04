package com.hotel.mainaction;

import com.hotel.datamanage.Manager;
import com.hotel.entity.User;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class LoginAction extends ActionSupport implements ModelDriven<User>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private User user = new User();
	@Override
	public User getModel()
	{
		return user;
	}
	

	public String execute() throws Exception
	{

		Manager manager = Manager.getManager();
		int loginResult = manager.login(user);
		switch (loginResult)
		{
		case 1:
			this.addActionError("密码错误！");
			break;
		case 2:
			this.addActionError("用户名不存在！");
			break;
		case 3:
			this.addActionError("处理异常，请重试！");
			break;
		}
		if (loginResult == 0)
		{
			ActionContext.getContext().getSession().put("user",user);
			
			return SUCCESS;
		}
		else
			return INPUT;
		
	}


	@Override
	public void validate()
	{
		if(user.getUserName()==null ||user.getUserName().equals(""))
			addFieldError("userName","用户名不能为空");
		
		if(user.getPsw()==null||user.getPsw().equals(""))
			addFieldError("psw","密码不能为空");
	}

	
}
