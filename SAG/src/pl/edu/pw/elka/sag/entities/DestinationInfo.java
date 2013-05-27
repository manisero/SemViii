package pl.edu.pw.elka.sag.entities;

import java.io.*;

public class DestinationInfo implements Serializable
{
	private static final long serialVersionUID = -3899690705742612607L;
	
	private Direction from;
	private Direction to;
	
	public DestinationInfo() { }
	
	public DestinationInfo(Direction from, Direction to)
	{
		this.from = from;
		this.to = to;
	}
	
	public Direction getFrom()
	{
		return from;
	}
	
	public void setFrom(Direction from)
	{
		this.from = from;
	}
	
	public Direction getTo()
	{
		return to;
	}
	
	public void setTo(Direction to)
	{
		this.to = to;
	}
}
