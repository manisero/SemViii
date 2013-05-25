package pl.edu.pw.elka.sag.gui.objects;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

import pl.edu.pw.elka.sag.entities.Location;
import pl.edu.pw.elka.sag.gui.logic.IPaintablePropertyProvider;
import pl.edu.pw.elka.sag.gui.logic.IPaintablePropertyReceiver;

public class AnimatedCar extends Canvas implements IPaintablePropertyReceiver
{
	private static final long serialVersionUID = 3556163878139145448L;
	
	private Location currentLocation;
	private Location previousLocation;

	private IPaintablePropertyProvider propertiesProvider;
	
	private Integer carRadius;
	
	private List<Location> destinies = new LinkedList<Location>();

	/**
	 * Constructs animated car.
	 * 
	 * @param currentLocation heading {@link pl.edu.pw.elka.sag.entities.Location}
	 * @param previousLocation starting {@link pl.edu.pw.elka.sag.entities.Location}
	 */
	public AnimatedCar(Location currentLocation, Location previousLocation)
	{
		this.currentLocation = currentLocation;
		this.previousLocation = previousLocation;
	}
	
	/**
	 * Sets properties provider.
	 * 
	 * @param propertiesProvider {@link pl.edu.pw.elka.sag.gui.logic.IPaintablePropertyProvider} to set
	 */
	@Override
	public void setPropertyProvider(IPaintablePropertyProvider propertiesProvider)
	{
		this.propertiesProvider = propertiesProvider;
	}
	
	/**
	 * Lazy loading of car radius.
	 * 
	 * @return int car object's radius
	 */
	private int getCarRadius()
	{
		if (carRadius == null)
		{
			carRadius = propertiesProvider.getCarBoundingBoxSize();
		}
		
		return carRadius;
	}
	
	/**
	 * Returns current car's location.
	 * 
	 * @return current {@link pl.edu.pw.elka.sag.entities.Location}
	 */
	public Location getCarLocation()
	{
		return currentLocation;
	}
	
	/**
	 * Adds new car's destination.
	 * 
	 * @param location car's destination {@link pl.edu.pw.elka.sag.entities.Location}
	 */
	public void addDestination(Location location)
	{
		destinies.add(location);
	}
	
	/**
	 * Paints animated car.
	 * 
	 * @param graphics {@link java.awt.Graphics} instance
	 */
	@Override
	public void paint(Graphics graphics)
	{
		super.paint(graphics);
		
		Graphics2D graphics2D = (Graphics2D) graphics;
		
		paintCar(graphics2D);
	}
	
	/**
	 * Paints car's oval.
	 * 
	 * @param graphics2D {@link java.awt.Graphics2D} instance
	 */
	private void paintCar(Graphics2D graphics2D)
	{
		Point carPosition = propertiesProvider.getCarScreenPosition(currentLocation, previousLocation);
		
		graphics2D.setColor(Color.WHITE);
		
		graphics2D.fillOval(carPosition.x, carPosition.y, getCarRadius(), getCarRadius());		
	}
}
