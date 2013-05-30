package pl.edu.pw.elka.sag.util;

import jade.core.*;
import jade.domain.*;
import jade.domain.FIPAAgentManagement.*;

import java.util.*;

import pl.edu.pw.elka.sag.agents.car.*;
import pl.edu.pw.elka.sag.agents.city.*;
import pl.edu.pw.elka.sag.agents.highwaycode.*;
import pl.edu.pw.elka.sag.agents.map.*;
import pl.edu.pw.elka.sag.agents.trafficlight.*;
import pl.edu.pw.elka.sag.constants.*;
import pl.edu.pw.elka.sag.exceptions.*;

public class AgentRegistry
{
	private static AgentRegistry instance = new AgentRegistry();
	
	public static AgentRegistry getInstance()
	{
		return instance;
	}
	
	private Map<Class<?>, String> agentTypes = new LinkedHashMap<Class<?>, String>();
	
	private AgentRegistry()
	{
		agentTypes.put(CityAgent.class, AgentTypes.CITY_AGENT_TYPE);
		agentTypes.put(HighwayCodeAgent.class, AgentTypes.HIGHWAY_CODE_AGENT_TYPE);
		agentTypes.put(MapAgent.class, AgentTypes.MAP_AGENT_TYPE);
		agentTypes.put(CarAgent.class, AgentTypes.CAR_AGENT_TYPE);
		agentTypes.put(TrafficLightAgent.class, AgentTypes.TRAFFIC_LIGHT_AGENT_TYPE);
	}
	
	public void register(Agent agent)
	{
		register(agent, null);
	}
	
	public void register(Agent agent, String serviceName)
	{
		Class<?> agentClass = agent.getClass();
		
		if (!agentTypes.containsKey(agentClass))
		{
			throw new UnknownAgentTypeException();
		}
		
		try
		{
			ServiceDescription serviceDescription = new ServiceDescription();
			String type = agentTypes.get(agentClass);
			serviceDescription.setType(type);
			serviceDescription.setName(serviceName != null ? serviceName : type);
			
			DFAgentDescription dfAgentDescription = new DFAgentDescription();
			dfAgentDescription.setName(agent.getAID());
			dfAgentDescription.addServices(serviceDescription);
			
			DFService.register(agent, dfAgentDescription);
		}
		catch (FIPAException e)
		{
			System.err.println("Failed to register agent: " + agent.getAID());
			
			e.printStackTrace();
		}
	}
	
	public List<AID> getAgents(Agent agent, Class<?> searchedAgentClass)
	{
		return getAgents(agent, searchedAgentClass, null, true);
	}
	
	public List<AID> getAgents(Agent agent, Class<?> searchedAgentClass, String searchedServiceName, boolean ignoreAgent)
	{
		if (!agentTypes.containsKey(searchedAgentClass))
		{
			throw new UnknownAgentTypeException();
		}
		
		List<AID> result = new ArrayList<AID>();
		
		try
		{
			ServiceDescription serviceDescription = new ServiceDescription();
			serviceDescription.setType(agentTypes.get(searchedAgentClass));
			
			if (searchedServiceName != null)
			{
				serviceDescription.setName(searchedServiceName);
			}
			
			DFAgentDescription template = new DFAgentDescription();
			template.addServices(serviceDescription);
			
			DFAgentDescription[] agentDescriptions = DFService.search(agent, template);
				
			for (DFAgentDescription description : agentDescriptions)
			{
				if (!ignoreAgent || !description.getName().equals(agent.getName()))
				{
					result.add(description.getName());
				}
			}
		}
		catch (FIPAException e)
		{
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void unregister(Agent agent)
	{
		try
		{
			DFService.deregister(agent);
		} 
		catch (FIPAException e)
		{
			e.printStackTrace();
		}
	}
	
	public AID getCityAgentID(Agent agent)
	{
		List<AID> agents = getAgents(agent, CityAgent.class);
		
		if (agents.size() == 0)
		{
			throw new NoCityAgentFoundException();
		}
		
		return agents.get(0);
	}
	
	public AID getHighwayCodeAgentID(Agent agent)
	{
		List<AID> agents = getAgents(agent, HighwayCodeAgent.class);
		
		if (agents.size() == 0)
		{
			throw new NoHighwayCodeAgentFoundException();
		}
		
		return agents.get(0);
	}
}
