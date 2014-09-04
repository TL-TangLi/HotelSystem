package com.hotel.entity;


/**
 * @author christopher
 * @info id ,cId ,type,description,isAvaliable,isClean
 */
public class Room
{
	public int id;
	public int cId;
	public int type;
	public String description;
	public boolean isAvaliable;
	public boolean isClean;
	

	public int getId()
	{
		return id;
	}


	public void setId(int id)
	{
		this.id = id;
	}


	public int getcId()
	{
		return cId;
	}


	public void setcId(int cId)
	{
		this.cId = cId;
	}


	public int getType()
	{
		return type;
	}


	public void setType(int type)
	{
		this.type = type;
	}


	public String getDescription()
	{
		return description;
	}


	public void setDescription(String description)
	{
		this.description = description;
	}


	public boolean isAvaliable()
	{
		return isAvaliable;
	}


	public void setAvaliable(boolean isAvaliable)
	{
		this.isAvaliable = isAvaliable;
	}


	public boolean isClean()
	{
		return isClean;
	}


	public void setClean(boolean isClean)
	{
		this.isClean = isClean;
	}


	public Room(int id, int cId, int type, String description,
			boolean isAvaliable, boolean isClean)
	{
		super();
		this.id = id;
		this.cId = cId;
		this.type = type;
		this.description = description;
		this.isAvaliable = isAvaliable;
		this.isClean = isClean;
	}


	public Room(){};
}
