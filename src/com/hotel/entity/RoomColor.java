package com.hotel.entity;

/**
 * @author christopher
 * @info id colorValue description
 */
public class RoomColor
{
	public int id;
	public String colorValue;
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getColorValue()
	{
		return colorValue;
	}
	public void setColorValue(String colorValue)
	{
		this.colorValue = colorValue;
	}
	public String getDescription()
	{
		return description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
	public String description;
	public RoomColor(int id, String colorValue, String description)
	{
		super();
		this.id = id;
		this.colorValue = colorValue;
		this.description = description;
	}
	public RoomColor(){};
	
}
