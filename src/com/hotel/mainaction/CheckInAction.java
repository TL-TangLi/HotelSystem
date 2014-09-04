package com.hotel.mainaction;

import com.hotel.datamanage.Manager;
import com.hotel.entity.CheckInInfo;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class CheckInAction extends ActionSupport implements ModelDriven<CheckInInfo>
{
	private static final long serialVersionUID = 1L;
	
	
	private int days ;
	private int balanceType;
	public int getBalanceType()
	{
		return balanceType;
	}

	public void setBalanceType(int balanceType)
	{
		this.balanceType = balanceType;
	}

	public int getDays()
	{
		return days;
	}

	public void setDays(int days)
	{
		this.days = days;
	}

	private CheckInInfo cif = new CheckInInfo();
	@Override
	public CheckInInfo getModel()
	{
		return cif;
	}
	
	private String requestResult;
	

	public String getRequestResult()
	{
		return requestResult;
	}


	public void setRequestResult(String requestResult)
	{
		this.requestResult = requestResult;
	}


	public CheckInInfo getCif()
	{
		return cif;
	}


	public void setCif(CheckInInfo cif)
	{
		this.cif = cif;
	}
	

	public String execute()
	{
		
		Manager manager  = Manager.getManager();
		requestResult = manager.checkIn(cif,days,balanceType);
		
		return "info";
	}




	
}
