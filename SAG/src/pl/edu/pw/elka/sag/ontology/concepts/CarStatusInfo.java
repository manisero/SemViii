package pl.edu.pw.elka.sag.ontology.concepts;

import jade.content.*;

import java.io.*;

public class CarStatusInfo implements Serializable, Concept
{
	private static final long serialVersionUID = -3899690705742612607L;
	
	private CarStatus status;
	private Direction from;
	private Direction to;
	
	public CarStatusInfo() { }
	
	public CarStatusInfo(CarStatus staus, Direction from, Direction to)
	{
		this.status = staus;
		this.from = from;
		this.to = to;
	}
	
	public CarStatus getStatus()
	{
		return status;
	}

	public void setStatus(CarStatus status)
	{
		this.status = status;
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
