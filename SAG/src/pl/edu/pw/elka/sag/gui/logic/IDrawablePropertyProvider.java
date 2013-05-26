package pl.edu.pw.elka.sag.gui.logic;

import java.awt.Point;

import pl.edu.pw.elka.sag.entities.Direction;
import pl.edu.pw.elka.sag.entities.Location;

public interface IDrawablePropertyProvider
{
	public int getCarBoundingBoxSize();
	public Point getCarScreenPosition(Location location, Direction direction);
	
	public int getTrafficLightsBoundingBoxSize(); 
	public Point getTrafficLightsScreenPosition(Location location, Direction direction);
}
