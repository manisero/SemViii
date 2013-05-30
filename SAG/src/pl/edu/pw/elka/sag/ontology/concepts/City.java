package pl.edu.pw.elka.sag.ontology.concepts;

import jade.content.*;

import java.io.*;
import java.util.*;

public class City implements Serializable, Concept
{
	private static final long serialVersionUID = 2996419799607017320L;
	private int blocksCount;
	private int size;
	
	public City() { }
	
	public City(int blocksCount)
	{
		setBlocksCount(blocksCount);
	}
	
	public int getBlocksCount()
	{
		return blocksCount;
	}

	public void setBlocksCount(int blocksCount)
	{
		this.blocksCount = blocksCount;
		this.size = blocksCount * 10;
	}
	
	public int getSize()
	{
		return size;
	}

	public List<Direction> getPossibleDirections(Location location)
	{
		List<Direction> directions = new LinkedList<Direction>();
		
		if (location.getX() > 0)
		{
			directions.add(Direction.WEST);
		}
		
		if (location.getX() < size - 1)
		{
			directions.add(Direction.EAST);
		}
		
		if (location.getY() > 0)
		{
			directions.add(Direction.SOUTH);
		}
		
		if (location.getY() < size - 1)
		{
			directions.add(Direction.NORTH);
		}
		
		return directions;
	}
}
