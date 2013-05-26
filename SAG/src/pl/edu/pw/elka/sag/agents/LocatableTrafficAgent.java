package pl.edu.pw.elka.sag.agents;

import jade.core.*;

import java.util.*;

import pl.edu.pw.elka.sag.*;
import pl.edu.pw.elka.sag.agents.city.*;
import pl.edu.pw.elka.sag.entities.Location;
import pl.edu.pw.elka.sag.exceptions.*;
import pl.edu.pw.elka.sag.util.*;

public abstract class LocatableTrafficAgent extends AgentBase implements ILocatable
{
	private static final long serialVersionUID = -3136958477961496734L;
	
	private AID cityId;
	private Location location;
	
	@Override
	protected void setup()
	{
		super.setup();
		
		Object[] arguments = getArguments();
		
		if (arguments == null || arguments.length < 2)
		{
			throw new InvalidAgentArgumentsException();
		}
		
		addBehaviour(new ServeLocationBehaviour(this));
	}

	@Override
	public Location getLocation()
	{
		if (location == null)
		{
			Object[] arguments = getArguments();
			
			int locationX = Integer.parseInt(arguments[0].toString());
			int locationY = Integer.parseInt(arguments[1].toString());
			
			location = new Location(locationX, locationY);
		}
		
		return location;
	}

	protected void setLocation(Location location)
	{
		this.location = location;
	}
	
	public AID getCityAgentID()
	{
		if (cityId == null)
		{
			List<AID> agents = AgentRegistrar.getInstance().getAgents(this, CityAgent.class);
			
			if (agents.size() != 1)
			{
				throw new NoCityAgentFoundException();
			}
			
			cityId = agents.get(0);
		}
		
		return cityId;
	}
}
