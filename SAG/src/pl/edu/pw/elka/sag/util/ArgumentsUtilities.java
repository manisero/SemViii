package pl.edu.pw.elka.sag.util;

import pl.edu.pw.elka.sag.entities.*;

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
}
