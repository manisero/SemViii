package pl.edu.pw.elka.sag.agents.car;

import pl.edu.pw.elka.sag.agents.*;
import pl.edu.pw.elka.sag.entities.*;

public class CarAgent extends TrafficAgent
{
	private static final long serialVersionUID = 258671427576035083L;
	
	private Direction direction;
	
	@Override
	protected void setup()
	{
		super.setup();
		
		addBehaviour(new RequestDirectionBehaviour(this, 5000, getCityAgentID()));
		addBehaviour(new ReceiveDirectionBehaviour(this));
		
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
}
