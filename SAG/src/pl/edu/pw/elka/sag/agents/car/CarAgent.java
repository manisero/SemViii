package pl.edu.pw.elka.sag.agents.car;

import jade.core.*;
import jade.domain.*;
import jade.domain.FIPAAgentManagement.*;
import pl.edu.pw.elka.sag.agents.*;
import pl.edu.pw.elka.sag.constants.*;
import pl.edu.pw.elka.sag.entities.*;
import pl.edu.pw.elka.sag.exceptions.*;

public class CarAgent extends LocatableAgent
{
	private static final long serialVersionUID = 258671427576035083L;
	
	private Direction direction;
	
	@Override
	protected void setup()
	{
		super.setup();
		
		addBehaviour(new ChooseDirectionBehaviour(this, 5000, getCityAgentName()));
		
		System.out.println("CarAgent " + getName()  + " is ready!");
	}
	
	public Direction getDirection()
	{
		return direction;
	}
	
	public void move(Direction direction)
	{
		if (direction.equals(Direction.NORTH))
		{
			getLocation().setY(getLocation().getY() + 1);
		}
		else if (direction.equals(Direction.SOUTH))
		{
			getLocation().setY(getLocation().getY() - 1);
		}
		else if (direction.equals(Direction.EAST))
		{
			getLocation().setX(getLocation().getX() + 1);
		}
		else if (direction.equals(Direction.WEST))
		{
			getLocation().setX(getLocation().getX() - 1);
		}
		
		this.direction = direction;
		
		System.out.println("Current location: [x=" + getLocation().getX() + ",y=" + getLocation().getY() + "]");
	}
	
	private AID getCityAgentName()
	{
		try
		{
			ServiceDescription serviceDescription = new ServiceDescription();
			serviceDescription.setType(AgentTypes.CITY_AGENT_TYPE);
			
			DFAgentDescription template = new DFAgentDescription();
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
