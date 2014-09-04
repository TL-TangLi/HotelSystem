package com.hotel.entity;

/**
 * @author christopher
 *	rid (房间号) cid(入住号) description(说明) type(入账类型：如果为消费那么该字段将无意义，一般设置为 3！！) 
 *	isConsume(是否为消费账目) balance，genTime(生成时间，由数据库自动生成);
 */
public class Account
{
	public static final int  PAY_IGNORE = -1; 
	public static final int  PAY_BANK_CARD = 0;
	public static final int  PAY_CASH = 1;
	
	public int rId;						//房间id	
	public int cId;						//入住号
	public String description;			//说明
	public int type;					//入账类型， 银行 0，现金 1  
	public boolean isConsume;			//是否为 消费（不是消费 就是入账 如果是消费的话，type类型将 无意义！！）
	public double balance;				//金额
	public String genTime;				//生成时间
	public int id;						//id
	public Account(int rId, int cId, String description, int type, boolean isConsume,
			double balance, String genTime,int id)
	{
		super();
		this.rId = rId;
		this.cId = cId;
		this.description = description;
		this.type = type;
		this.isConsume = isConsume;
		this.balance = balance;
		this.genTime = genTime;
		this.id = id;
	}
	public Account()
	{
		super();
	}
	
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public int getrId()
	{
		return rId;
	}
	public void setrId(int rId)
	{
		this.rId = rId;
	}
	public int getcId()
	{
		return cId;
	}
	public void setcId(int cId)
	{
		this.cId = cId;
	}
	public String getDescription()
	{
		return description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
	public int getType()
	{
		return type;
	}
	public void setType(int type)
	{
		this.type = type;
	}
	public boolean isConsume()
	{
		return isConsume;
	}
	public void setConsume(boolean isConsume)
	{
		this.isConsume = isConsume;
	}
	public double getBalance()
	{
		return balance;
	}
	public void setBalance(double balance)
	{
		this.balance = balance;
	}
	public String getGenTime()
	{
		return genTime;
	}
	public void setGenTime(String genTime)
	{
		this.genTime = genTime;
	}
	
}
