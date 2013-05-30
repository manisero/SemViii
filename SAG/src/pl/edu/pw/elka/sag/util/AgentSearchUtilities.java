package pl.edu.pw.elka.sag.util;

import jade.core.*;

import java.util.*;

import pl.edu.pw.elka.sag.agents.city.*;
import pl.edu.pw.elka.sag.agents.highwaycode.*;
import pl.edu.pw.elka.sag.agents.trafficlight.*;
import pl.edu.pw.elka.sag.exceptions.*;
import pl.edu.pw.elka.sag.ontology.concepts.Location;

public class AgentSearchUtilities
{
	public static AID findCityAgent(Agent agent)
	{
		List<AID> agents = AgentRegistry.getInstance().getAgents(agent, CityAgent.class);
		
		if (agents.size() == 0)
		{
			throw new NoCityAgentFoundException();
		}
		
		return agents.get(0);
	}
	
	public static AID findHighwayCodeAgent(Agent agent)
	{
		List<AID> agents = AgentRegistry.getInstance().getAgents(agent, HighwayCodeAgent.class);
		
		if (agents.size() == 0)
		{
			throw new NoHighwayCodeAgentFoundException();
		}
		
		return agents.get(0);
	}
	
	public static AID findTrafficLight(Agent agent, Location location)
	{
		String serviceName = TrafficLightAgent.getTrafficLightServiceName(location);
		List<AID> trafficLights = AgentRegistry.getInstance().getAgents(agent, TrafficLightAgent.class, serviceName, true);
		
		if (trafficLights.size() > 0)
		{
			return trafficLights.get(0);
		}
		
		return null;
	}
}
