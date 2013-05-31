package pl.edu.pw.elka.sag.util;

import java.awt.Color;

import pl.edu.pw.elka.sag.exceptions.InvalidAgentArgumentsException;
import pl.edu.pw.elka.sag.ontology.concepts.*;

public class ArgumentsUtilities
{
	public static int getInt(Object[] arguments, int index)
	{
		return Integer.parseInt(arguments[index].toString());
	}
	
	public static Direction getDirection(Object[] arguments, int index)
	{
		return Direction.valueOf(arguments[index].toString());
	}
	
	public static Location getLocation(Object[] arguments, int xIndex, int yIndex)
	{
		int locationX = Integer.parseInt(arguments[xIndex].toString()) * 10;
		int locationY = Integer.parseInt(arguments[yIndex].toString()) * 10;
		
		return new Location(locationX, locationY);
	}
	
	public static CarType getCarType(Object[] arguments, int index)
	{
		return CarType.valueOf(arguments[index].toString());
	}
	
	public static Color getColor(Object[] arguments, int index) throws InvalidAgentArgumentsException
	{
		try
		{
			return (Color) Class.forName("java.awt.Color").getField(arguments[index].toString()).get("");
		}
		catch (Exception e)
		{
			throw new InvalidAgentArgumentsException();
		}
	}
}
