package pl.edu.pw.elka.sag.ontology.concepts;

import java.util.*;
import java.util.Map;

import jade.content.*;

public class TrafficLight implements Concept
{
	private static final long serialVersionUID = -9183209183508669967L;

	private final Map<Direction, TrafficLightStatus> status = new LinkedHashMap<Direction, TrafficLightStatus>();
	
	private Location location;
	private int cyclePeriod;

	public TrafficLight() { }
	
	public TrafficLight(Location location, int cyclePeriod,
						TrafficLightStatus northStatus, TrafficLightStatus southStatus,
						TrafficLightStatus eastStatus, TrafficLightStatus westStatus)
	{
		this.location = location;
		this.cyclePeriod = cyclePeriod;
		
		status.put(Direction.NORTH, northStatus);
		status.put(Direction.SOUTH, southStatus);
		status.put(Direction.EAST, eastStatus);
		status.put(Direction.WEST, westStatus);
	}
	
	public Map<Direction, TrafficLightStatus> getStatus()
	{
		return status;
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
}
