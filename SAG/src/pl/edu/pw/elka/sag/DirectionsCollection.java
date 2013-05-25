package pl.edu.pw.elka.sag;

import java.io.Serializable;
import java.util.List;

public class DirectionsCollection implements Serializable
{
	private static final long serialVersionUID = 278883752368469703L;
	
	private List<Direction> directions;
	
	public DirectionsCollection() { }
	
	public DirectionsCollection(List<Direction> directions)
	{
		this.directions = directions;
	}

	public List<Direction> getDirections()
	{
		return directions;
	}

	public void setDirections(List<Direction> directions)
	{
		this.directions = directions;
	}
}
