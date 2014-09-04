package com.hotel.entity;

public class AccountAndCheckIn
{
	public Account account;
	public CheckInInfo cif;
	public AccountAndCheckIn(Account account, CheckInInfo cif)
	{
		super();
		this.account = account;
		this.cif = cif;
	}
	
}
