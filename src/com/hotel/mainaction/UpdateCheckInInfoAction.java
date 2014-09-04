package com.hotel.mainaction;

import com.hotel.datamanage.Manager;
import com.hotel.entity.CheckInInfo;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class UpdateCheckInInfoAction extends ActionSupport implements ModelDriven<CheckInInfo>
{
	private static final long serialVersionUID = 1L;
	
	private CheckInInfo cif = new CheckInInfo();
	@Override
	public CheckInInfo getModel()
	{
		return cif;
	}
	
	public CheckInInfo getCif()
	{
		return cif;
	}
	public void setCif(CheckInInfo cif)
	{
		this.cif = cif;
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
	
	/// 如果要修改 订单号，那么 modifyOrder 记录的是先前的 订单号 如果现在订单号和modifyOrder 不同说明已经修改订单号
	public int modifyOrder;
	public int getModifyOrder()
	{
		return modifyOrder;
	}

	public void setModifyOrder(int modifyOrder)
	{
		this.modifyOrder = modifyOrder;
	}

	public String execute()
	{
		Manager manager = Manager.getManager();
		requestResult =manager.updateCheckInInfoPart(cif,modifyOrder);
		return "info";
	}
	
	
}
