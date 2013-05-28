package pl.edu.pw.elka.sag.agents.trafficlight;

import pl.edu.pw.elka.sag.agents.*;
import pl.edu.pw.elka.sag.constants.*;
import pl.edu.pw.elka.sag.entities.*;
import pl.edu.pw.elka.sag.exceptions.*;
import pl.edu.pw.elka.sag.util.*;

public class TrafficLightAgent extends AgentBase
{
	private static final long serialVersionUID = 8004867227278039286L;
	
	public static String getTrafficLightServiceName(Location location)
	{
		return AgentTypes.TRAFFIC_LIGHT_AGENT_TYPE + location;
	}
	
	@Override
	protected void setup()
	{
		super.setup();
		
		Object[] arguments = getArguments();
		
		if (arguments == null || arguments.length < 3)
		{
			throw new InvalidAgentArgumentsException();
		}
		
		Location trafficLightLocation = ArgumentsUtilities.getLocation(arguments, 0, 1);
		int trafficLightCyclePeriod = ArgumentsUtilities.getInt(arguments, 2);
		Direction trafficLightAllowedDirection = Direction.NORTH_SOUTH;
		TrafficLight trafficLight = new TrafficLight(trafficLightLocation, trafficLightCyclePeriod, trafficLightAllowedDirection);
		
		register(getTrafficLightServiceName(trafficLightLocation));
		
		addBehaviour(new TrafficLightCycleBehavior(this, trafficLight));
		addBehaviour(new ServeLocationBehaviour(this, trafficLight));
		addBehaviour(new ServeAllowedDirectionBehavior(this, trafficLight));
		addBehaviour(new ServeTrafficLightInfoBehaviour(this, trafficLight));
	}
}
