package pl.edu.pw.elka.sag.entities;

import java.io.*;

import jade.core.*;

public class Car implements Serializable
{
	private static final long serialVersionUID = 8098439117766827737L;
	
	private int speed;
	private Location location;
	private Direction direction;
	private CarStatus status;
	private Direction nextDirection;
	private Location nextCrossroadsLocation;
	private AID nextTrafficLight;
	private Direction nextTrafficLightAllowedDirection;
	private int otherCarsToCheck;
	private int otherCarsChecked;
	private boolean hasPriority;
	
	public Car() { }
	
	public Car(Location location, int speed, Direction nextDirection)
	{
		this.location = location;
		this.speed = speed;
		this.nextDirection = nextDirection;
	}
	
	public int getSpeed()
	{
		return speed;
	}

	public void setSpeed(int speed)
	{
		this.speed = speed;
	}

	public Location getLocation()
	{
		return location;
	}

	public void setLocation(Location location)
	{
		this.location = location;
	}

	public CarStatus getStatus()
	{
		return status;
	}

	public void setStatus(CarStatus status)
	{
		this.status = status;
	}
	
	public Direction getDirection()
	{
		return direction;
	}
	
	public void setDirection(Direction direction)
	{
		this.direction = direction;;
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
