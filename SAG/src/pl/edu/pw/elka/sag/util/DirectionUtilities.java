package pl.edu.pw.elka.sag.util;

import pl.edu.pw.elka.sag.ontology.concepts.*;

public class DirectionUtilities
{
	public static boolean isStraight(Direction direction, Direction nextDirection)
	{
		return nextDirection.equals(direction);
	}
	
	public static boolean isRightTurn(Direction direction, Direction nextDirection)
	{
		return 	direction.equals(Direction.NORTH) && nextDirection.equals(Direction.EAST) ||
				direction.equals(Direction.EAST) && nextDirection.equals(Direction.SOUTH) ||
				direction.equals(Direction.SOUTH) && nextDirection.equals(Direction.WEST) ||
				direction.equals(Direction.WEST) && nextDirection.equals(Direction.NORTH);
	}
	
	public static boolean isLeftTurn(Direction direction, Direction nextDirection)
	{
		return  direction.equals(Direction.NORTH) && nextDirection.equals(Direction.WEST) ||
				direction.equals(Direction.WEST) && nextDirection.equals(Direction.SOUTH) ||
				direction.equals(Direction.SOUTH) && nextDirection.equals(Direction.EAST) ||
				direction.equals(Direction.EAST) && nextDirection.equals(Direction.NORTH);
	}
	
	public static boolean isAcross(Direction direction, Direction otherDirection)
	{
		return direction.getOpposite().equals(otherDirection);
	}
	
	public static boolean isOnTheRight(Direction direction, Direction otherDirection)
	{
		return	direction.equals(Direction.NORTH) && otherDirection.equals(Direction.WEST) ||
				direction.equals(Direction.WEST) && otherDirection.equals(Direction.SOUTH) ||
				direction.equals(Direction.SOUTH) && otherDirection.equals(Direction.EAST) ||
				direction.equals(Direction.EAST) && otherDirection.equals(Direction.NORTH);
	}
	
	public static boolean isOnTheLeft(Direction direction, Direction otherDirection)
	{
		return	direction.equals(Direction.NORTH) && otherDirection.equals(Direction.EAST) ||
				direction.equals(Direction.EAST) && otherDirection.equals(Direction.SOUTH) ||
				direction.equals(Direction.SOUTH) && otherDirection.equals(Direction.WEST) ||
				direction.equals(Direction.WEST) && otherDirection.equals(Direction.NORTH);
	}
}
