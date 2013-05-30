package pl.edu.pw.elka.sag.ontology.concepts;

import jade.content.*;

import java.io.*;

public class TrafficLightInfo implements Serializable, Concept
{
	private static final long serialVersionUID = -3408982378321530210L;
	
	private Location location;
	private Direction allowedDirection;
	
	public TrafficLightInfo(Location location, Direction allowedDirection)
	{
		this.location = location;
		this.allowedDirection = allowedDirection;
	}
	
	public Location getLocation()
	{
		return location;
	}
	
	public void setLocation(Location location)
	{
		this.location = location;
	}
	
	public Direction getAllowedDirection()
	{
		return allowedDirection;
	}
	
	public void setAllowedDirection(Direction allowedDirection)
	{
		this.allowedDirection = allowedDirection;
	}
}
