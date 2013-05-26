package pl.edu.pw.elka.sag.entities;

import java.io.Serializable;

public class Location implements Serializable
{
	private static final long serialVersionUID = -3851200930745256723L;
	
	private double x;
	private double y;
	
	public Location() { }
	
	public Location(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public double getX()
	{
		return x;
	}

	public void setX(double x)
	{
		this.x = x;
	}

	public double getY()
	{
		return y;
	}

	public void setY(double y)
	{
		this.y = y;
	}
}
