package pl.edu.pw.elka.sag.agents.trafficlight;

import pl.edu.pw.elka.sag.agents.*;
import pl.edu.pw.elka.sag.constants.*;
import pl.edu.pw.elka.sag.entities.*;
import pl.edu.pw.elka.sag.exceptions.*;

public class TrafficLightAgent extends LocatableTrafficAgent
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
		
		int cyclePeriod = Integer.parseInt(arguments[2].toString());
		
		TrafficLight trafficLight = new TrafficLight(getLocation(), cyclePeriod, Direction.NORTH_SOUTH);
		
		addBehaviour(new TrafficLightCycleBehavior(this, trafficLight));
		addBehaviour(new ServeAllowedDirectionBehavior(this, trafficLight));
		addBehaviour(new ServeTrafficLightInfoBehaviour(this, trafficLight));
	}
	
	@Override
	protected String getServiceName()
	{
		return getTrafficLightServiceName(getLocation());
	}
}
