package pl.edu.pw.elka.sag.agents.car;

import jade.core.*;
import pl.edu.pw.elka.sag.agents.*;
import pl.edu.pw.elka.sag.entities.*;
import pl.edu.pw.elka.sag.exceptions.*;

public class CarAgent extends MovableTrafficAgent
{
	private static final long serialVersionUID = 258671427576035083L;
	
	private CarStatus status;
	private Direction nextDirection;
	private AID nextTrafficLight;
	private Direction nextTrafficLightAllowedDirection;
	
	@Override
	protected void setup()
	{
		super.setup();
		
		Object[] arguments = getArguments();
		
		if (arguments == null || arguments.length < 4)
		{
			throw new InvalidAgentArgumentsException();
		}
		
		setNextDirection(Direction.valueOf(arguments[2].toString()));
		
		int speed = Integer.parseInt(arguments[3].toString());
		
		addBehaviour(new MovementBehaviour(this, speed));
		addBehaviour(new ReceivePossibleDirectionsBehaviour(this));
		addBehaviour(new ReceiveAllowedDirectionBehaviour(this));
	}
	
	public CarStatus getStatus()
	{
		return status;
	}

	public void setStatus(CarStatus status)
	{
		this.status = status;
	}
	
	@Override
	public void setDirection(Direction direction)
	{
		super.setDirection(direction);
	}
	
	public Direction getNextDirection()
	{
		return nextDirection;
	}

	public void setNextDirection(Direction nextDirection)
	{
		this.nextDirection = nextDirection;
	}
	
	public AID getNextTrafficLight()
	{
		return nextTrafficLight;
	}

	public void setNextTrafficLight(AID nextTrafficLight)
	{
		this.nextTrafficLight = nextTrafficLight;
	}

	public Direction getNextTrafficLightAllowedDirection()
	{
		return nextTrafficLightAllowedDirection;
	}

	public void setNextTrafficLightAllowedDirection(Direction nextTrafficLightAllowedDirection)
	{
		this.nextTrafficLightAllowedDirection = nextTrafficLightAllowedDirection;
	}

	public void move()
	{
		if (getDirection().equals(Direction.NORTH))
		{
			getLocation().setY(getLocation().getY() + 1);
		}
		else if (getDirection().equals(Direction.SOUTH))
		{
			getLocation().setY(getLocation().getY() - 1);
		}
		else if (getDirection().equals(Direction.EAST))
		{
			getLocation().setX(getLocation().getX() + 1);
		}
		else if (getDirection().equals(Direction.WEST))
		{
			getLocation().setX(getLocation().getX() - 1);
		}
	}
}
