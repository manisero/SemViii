package pl.edu.pw.elka.sag.ontology.concepts;

import jade.content.*;

import java.io.*;

public class TrafficLight implements Serializable, Concept, ILocatable
{
	private static final long serialVersionUID = -9183209183508669967L;

	private Location location;
	private int cyclePeriod;
	private Direction allowedDirection;

	public TrafficLight() { }
	
	public TrafficLight(Location location, int cyclePeriod, Direction allowedDirection)
	{
		this.location = location;
		this.cyclePeriod = cyclePeriod;
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

	public int getCyclePeriod()
	{
		return cyclePeriod;
	}

	public void setCyclePeriod(int cyclePeriod)
	{
		this.cyclePeriod = cyclePeriod;
	}

	public Direction getAllowedDirection()
	{
		return allowedDirection;
	}

	public void setAllowedDirection(Direction allowedDirection)
	{
		this.allowedDirection = allowedDirection;
	}
	
	public void switchAllowedDirection()
	{
		allowedDirection = allowedDirection.getOpposite();
	}
}
