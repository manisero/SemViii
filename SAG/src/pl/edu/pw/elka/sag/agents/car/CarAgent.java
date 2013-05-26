package pl.edu.pw.elka.sag.agents.car;

import pl.edu.pw.elka.sag.agents.*;
import pl.edu.pw.elka.sag.entities.*;

public class CarAgent extends MovableTrafficAgent
{
	private static final long serialVersionUID = 258671427576035083L;
	
	@Override
	protected void setup()
	{
		super.setup();
		addBehaviour(new RequestDirectionBehaviour(this, 5000, getCityAgentID()));
		addBehaviour(new ReceiveDirectionBehaviour(this));
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
		
		setDirection(direction);
		
		System.out.println("Current location: [x=" + getLocation().getX() + ",y=" + getLocation().getY() + "]");
	}
}
