package pl.edu.pw.elka.sag.ontology.concepts;

import jade.content.*;

public class Location implements Concept
{
	private static final long serialVersionUID = -3851200930745256723L;
	
	private int x;
	private int y;
	
	public Location() { }
	
	public Location(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}
	
	@Override
	public String toString()
	{
		return "[x=" + getX() + ",y=" + getY() + "]";
	}
}
