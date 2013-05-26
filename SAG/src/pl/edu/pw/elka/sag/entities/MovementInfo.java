package pl.edu.pw.elka.sag.entities;

import java.io.*;

public class MovementInfo implements Serializable
{
	private static final long serialVersionUID = 1911875956344622762L;

	private Location location;
	private Direction direction;
	
	public MovementInfo(Location location, Direction direction)
	{
		this.location = location;
		this.direction = direction;
	}
	
	public Location getLocation()
	{
		return location;
	}
	
	public void setLocation(Location location)
	{
		this.location = location;
	}
	
	public Direction getDirection()
	{
		return direction;
	}
	
	public void setDirection(Direction direction)
	{
		this.direction = direction;
	}
}
