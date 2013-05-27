package pl.edu.pw.elka.sag.agents.car;

import jade.core.*;
import pl.edu.pw.elka.sag.agents.*;
import pl.edu.pw.elka.sag.entities.*;
import pl.edu.pw.elka.sag.entities.Location;
import pl.edu.pw.elka.sag.exceptions.*;

public class CarAgent extends MovableTrafficAgent
{
	private static final long serialVersionUID = 258671427576035083L;
	
	private CarStatus status;
	private Direction nextDirection;
	private Location nextCrossroadsLocation;
	private AID nextTrafficLight;
	private Direction nextTrafficLightAllowedDirection;
	private int otherCarsToCheck;
	private int otherCarsChecked;
	private boolean hasPriority;
	
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
		addBehaviour(new ServeCarStatusInfoBehaviour(this));
		addBehaviour(new ReceivePossibleDirectionsBehaviour(this));
		addBehaviour(new ReceiveAllowedDirectionBehaviour(this));
		addBehaviour(new ReceiveCarStatusInfoBehaviour(this));
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
	
	public Location getNextCrossroadsLocation()
	{
		return nextCrossroadsLocation;
	}

	public void setNextCrossroadsLocation(Location nextCrossroadsLocation)
	{
		this.nextCrossroadsLocation = nextCrossroadsLocation;
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

	public int getOtherCarsToCheck()
	{
		return otherCarsToCheck;
	}

	public void setOtherCarsToCheck(int otherCarsToCheck)
	{
		this.otherCarsToCheck = otherCarsToCheck;
	}

	public int getOtherCarsChecked()
	{
		return otherCarsChecked;
	}

	public void setOtherCarsChecked(int otherCarsChecked)
	{
		this.otherCarsChecked = otherCarsChecked;
	}

	public boolean getHasPriority()
	{
		return hasPriority;
	}

	public void setHasPriority(boolean hasPriority)
	{
		this.hasPriority = hasPriority;
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
