package pl.edu.pw.elka.sag.agents.trafficlight;

import pl.edu.pw.elka.sag.agents.*;
import pl.edu.pw.elka.sag.constants.*;
import pl.edu.pw.elka.sag.exceptions.*;
import pl.edu.pw.elka.sag.ontology.concepts.*;
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
		TrafficLight trafficLight = new TrafficLight(trafficLightLocation, trafficLightCyclePeriod,
													 TrafficLightStatus.GREEN, TrafficLightStatus.GREEN,
													 TrafficLightStatus.RED, TrafficLightStatus.RED);
		
		register(getTrafficLightServiceName(trafficLightLocation));
		
		addBehaviour(new TrafficLightCycleBehavior(this, trafficLight));
		addBehaviour(new ServeTrafficLightStatusDirectionBehavior(this, trafficLight));
		addBehaviour(new ServeTrafficLightBehaviour(this, trafficLight));
	}
}
