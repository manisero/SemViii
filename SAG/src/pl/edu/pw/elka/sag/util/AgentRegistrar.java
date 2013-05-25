package pl.edu.pw.elka.sag.util;

import jade.core.*;
import jade.domain.*;
import jade.domain.FIPAAgentManagement.*;

import java.util.*;

import pl.edu.pw.elka.sag.agents.car.*;
import pl.edu.pw.elka.sag.agents.city.*;
import pl.edu.pw.elka.sag.agents.trafficlight.*;
import pl.edu.pw.elka.sag.constants.*;
import pl.edu.pw.elka.sag.exceptions.*;

public class AgentRegistrar
{
	private static AgentRegistrar instance = new AgentRegistrar();
	
	public static AgentRegistrar getInstance()
	{
		return instance;
	}
	
	private Map<Class<?>, String> agentTypes = new LinkedHashMap<Class<?>, String>();
	
	private AgentRegistrar()
	{
		agentTypes.put(CityAgent.class, AgentTypes.CITY_AGENT_TYPE);
		agentTypes.put(CarAgent.class, AgentTypes.CAR_AGENT_TYPE);
		agentTypes.put(TrafficLightAgent.class, AgentTypes.TRAFFIC_LIGHT_AGENT_TYPE);
	}
	
	public void register(Agent agent)
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
			serviceDescription.setName(type);
			
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
		if (!agentTypes.containsKey(searchedAgentClass))
		{
			throw new UnknownAgentTypeException();
		}
		
		List<AID> result = new ArrayList<AID>();
		
		try
		{
			ServiceDescription serviceDescription = new ServiceDescription();
			serviceDescription.setType(agentTypes.get(searchedAgentClass));
			
			DFAgentDescription template = new DFAgentDescription();
			template.addServices(serviceDescription);
			
			DFAgentDescription[] agentDescriptions = DFService.search(agent, template);
				
			for (DFAgentDescription description : agentDescriptions)
			{
				result.add(description.getName());
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
}
