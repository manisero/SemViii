package pl.edu.pw.elka.sag.ontology.concepts;

public enum Direction
{
	UNKNOWN	(Integer.parseInt("0000", 2)),
	NORTH	(Integer.parseInt("0001", 2)),
	SOUTH	(Integer.parseInt("0010", 2)),
	EAST	(Integer.parseInt("0100", 2)),
	WEST	(Integer.parseInt("1000", 2)),
	
	NORTH_SOUTH	(Integer.parseInt("0011", 2)),
	EAST_WEST	(Integer.parseInt("1100", 2));
	
	private final int _id;
	
	Direction(int id)
	{
		_id = id;
	}
	
	public int getValue()
	{
		return _id;
	}
	
	public static Direction valueOf(Integer value)
	{
		for (Direction direction : Direction.values())
		{
			if (direction.getValue() == value)
			{
				return direction;
			}
		}
		
		return Direction.UNKNOWN;
	}
	
	public boolean hasPart(Direction part)
	{
		return (getValue() & part.getValue()) != Direction.UNKNOWN.getValue();
	}
	
	public boolean hasAnyOfParts(Direction... parts)
	{
		for (Direction part : parts)
		{
			if (hasPart(part))
			{
				return true;
			}
		}
		
		return false;
	}
	
	public Direction getOpposite()
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
		else if (equals(Direction.EAST))
		{
			return Direction.WEST;
		}
		else if (equals(Direction.NORTH_SOUTH))
		{
			return Direction.EAST_WEST;
		}
		else if (equals(Direction.EAST_WEST))
		{
			return Direction.NORTH_SOUTH;
		}
		
		return Direction.UNKNOWN;
	}
}
