package pl.edu.pw.elka.sag;

public enum Direction
{
	NORTH,
	SOUTH,
	EAST,
	WEST;
	
	public Direction getOppositeDirection()
	{
		if (equals(Direction.NORTH))
		{
			return Direction.SOUTH;
		}
		else if (equals(Direction.SOUTH))
		{
			return Direction.NORTH;
		}
		else if (equals(Direction.WEST))
		{
			return Direction.EAST;
		}
		else
		{
			return Direction.WEST;
		}
	}
}
