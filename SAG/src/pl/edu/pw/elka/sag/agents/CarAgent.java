package pl.edu.pw.elka.sag.agents;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import pl.edu.pw.elka.sag.Direction;
import pl.edu.pw.elka.sag.Location;
import pl.edu.pw.elka.sag.behaviours.ChooseDirectionBehaviour;
import pl.edu.pw.elka.sag.constants.AgentTypes;
import pl.edu.pw.elka.sag.exception.NoCityAgentRegisteredException;

public class CarAgent extends Agent
{
	private static final long serialVersionUID = 258671427576035083L;
	
	private Location location = new Location();
	private Direction direction;
	
	@Override
	protected void setup()
	{
		addBehaviour(new ChooseDirectionBehaviour(this, 5000, getCityAgentName()));
		
		System.out.println("CarAgent is ready!");
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
	
	public void move(Direction direction)
	{
		if (direction.equals(Direction.NORTH))
		{
			location.setY(location.getY() + 1);
		}
		else if (direction.equals(Direction.SOUTH))
		{
			location.setY(location.getY() - 1);
		}
		else if (direction.equals(Direction.EAST))
		{
			location.setX(location.getX() + 1);
		}
		else if (direction.equals(Direction.WEST))
		{
			location.setX(location.getX() - 1);
		}
		
		this.direction = direction;
		
		System.out.println("Current location: [x=" + location.getX() + ",y=" + location.getY() + "]");
	}
	
	private AID getCityAgentName()
	{
		try
		{
			DFAgentDescription template = new DFAgentDescription();
			
			ServiceDescription serviceDescription = new ServiceDescription();
			
			serviceDescription.setType(AgentTypes.CITY_AGENT_TYPE);
			
			template.addServices(serviceDescription);
			
			DFAgentDescription[] agentDescriptions = DFService.search(this, template);
			
			if (agentDescriptions.length > 0)
			{
				return agentDescriptions[0].getName();
			}
		}
		catch (FIPAException e)
		{
			e.printStackTrace();
		}
		
		throw new NoCityAgentRegisteredException();
	}
}
