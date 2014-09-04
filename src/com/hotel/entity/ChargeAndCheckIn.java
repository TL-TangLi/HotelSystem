package com.hotel.entity;

public class ChargeAndCheckIn
{
	public RoomCharge rc;
	public CheckInInfo cif;
	public ChargeAndCheckIn(RoomCharge rc, CheckInInfo cif)
	{
		super();
		this.rc = rc;
		this.cif = cif;
	}
	
}
