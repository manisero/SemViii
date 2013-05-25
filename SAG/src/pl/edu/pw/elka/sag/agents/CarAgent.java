package pl.edu.pw.elka.sag.agents;

import jade.core.Agent;
import pl.edu.pw.elka.sag.Direction;
import pl.edu.pw.elka.sag.Location;
import pl.edu.pw.elka.sag.behaviours.ChooseDirectionBehaviour;

public class CarAgent extends Agent
{
	private static final long serialVersionUID = 258671427576035083L;

	private Location location = new Location();
	
	@Override
	protected void setup()
	{
		addBehaviour(new ChooseDirectionBehaviour(this, 5000));
		
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
		
		System.out.println("Current location: [x=" + location.getX() + ",y=" + location.getY() + "]");
	}
}
