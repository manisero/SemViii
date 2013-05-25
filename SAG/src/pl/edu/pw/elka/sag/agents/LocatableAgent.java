package pl.edu.pw.elka.sag.agents;

import pl.edu.pw.elka.sag.*;
import pl.edu.pw.elka.sag.entities.*;
import pl.edu.pw.elka.sag.exceptions.*;

public class LocatableAgent extends AgentBase implements ILocatable
{
	private static final long serialVersionUID = -3136958477961496734L;
	
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
		
		int locationX = Integer.parseInt(arguments[0].toString());
		int locationY = Integer.parseInt(arguments[1].toString());
		
		location = new Location(locationX, locationY);
	}

	@Override
	public Location getLocation()
	{
		return location;
	}

	protected void setLocation(Location location)
	{
		this.location = location;
	}
}
