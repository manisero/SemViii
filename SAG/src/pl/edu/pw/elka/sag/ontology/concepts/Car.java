package pl.edu.pw.elka.sag.ontology.concepts;

import java.awt.Color;

import jade.content.*;

public class Car implements Concept
{
	private static final long serialVersionUID = 8098439117766827737L;
	
	private int speed;
	private Location location;
	private Direction direction;
	private Direction previousDirection;
	private Direction nextDirection;
	private CarStatus status;
	private CarType type;
	private Color color;
	
	public Car() { }
	
	public Car(Location location, int speed, Direction nextDirection, CarType type, Color color)
	{
		this.location = location;
		this.speed = speed;
		this.nextDirection = nextDirection;
		this.type = type;
		this.color = color;
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
	
	public Direction getPreviousDirection()
	{
		return previousDirection;
	}

	public void setPreviousDirection(Direction previousDirection)
	{
		this.previousDirection = previousDirection;
	}

	public Direction getNextDirection()
	{
		return nextDirection;
	}

	public void setNextDirection(Direction nextDirection)
	{
		this.nextDirection = nextDirection;
	}

	public CarType getType()
	{
		return type;
	}

	public void setType(CarType type)
	{
		this.type = type;
	}

	public Color getColor()
	{
		return color;
	}

	public void setColor(Color color)
	{
		this.color = color;
	}
}
