package pl.edu.pw.elka.sag.gui.logic;

import java.awt.Point;

import pl.edu.pw.elka.sag.entities.Location;

public interface IPaintablePropertyProvider
{
	public int getCarBoundingBoxSize();
	public Point getCarScreenPosition(Location currentLocation, Location previousLocation);
}
