package pl.edu.pw.elka.sag.gui.objects;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import pl.edu.pw.elka.sag.gui.constants.MapPaintSettings;
import pl.edu.pw.elka.sag.gui.logic.IDrawablePropertyProvider;
import pl.edu.pw.elka.sag.gui.logic.IDrawablePropertyReceiver;
import pl.edu.pw.elka.sag.ontology.concepts.*;

public class DrawableCar extends Canvas implements IDrawablePropertyReceiver
{
	private static final long serialVersionUID = 3556163878139145448L;
	
	private Location carLocation;
	private Direction carDirection;

	private IDrawablePropertyProvider propertiesProvider;
	
	private Integer carRadius;
	
	/**
	 * Constructs animated car.
	 * 
	 * @param carLocation heading {@link pl.edu.pw.elka.sag.ontology.concepts.Location}
	 * @param previousLocation starting {@link pl.edu.pw.elka.sag.ontology.concepts.Location}
	 */
	public DrawableCar(Location carLocation, Direction carDirection)
	{
		this.carLocation = carLocation;
		this.carDirection = carDirection;
	}

	/**
	 * Sets properties provider.
	 * 
	 * @param propertiesProvider {@link pl.edu.pw.elka.sag.gui.logic.IDrawablePropertyProvider} to set
	 */
	@Override
	public void setPropertyProvider(IDrawablePropertyProvider propertiesProvider)
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
	 * Returns current car location.
	 * 
	 * @return current {@link pl.edu.pw.elka.sag.ontology.concepts.Location}
	 */
	public Location getCarLocation()
	{
		return carLocation;
	}
	
	/**
	 * Sets current car location.
	 * 
	 * @param carLocation {@link pl.edu.pw.elka.sag.ontology.concepts.Location} to set
	 */
	public void setCarLocation(Location carLocation)
	{
		this.carLocation = carLocation;
	}
	
	/**
	 * Returns current car direction.
	 * 
	 * @return current {@link pl.edu.pw.elka.sag.ontology.concepts.Direction}
	 */
	public Direction getCarDirection()
	{
		return carDirection;
	}
	
	/**
	 * Sets current car direction.
	 * 
	 * @param carDirection {@link pl.edu.pw.elka.sag.ontology.concepts.Direction} to set
	 */
	public void setCarDirection(Direction carDirection)
	{
		this.carDirection = carDirection;
	}
	
	/**
	 * Paints car.
	 * 
	 * @param graphics {@link java.awt.Graphics} instance
	 */
	@Override
	public void paint(Graphics graphics)
	{
		super.paint(graphics);
		
		Point currentPosition = propertiesProvider.getCarScreenPosition(carLocation, carDirection);
		
		Graphics2D graphics2D = (Graphics2D) graphics;
		graphics2D.setColor(MapPaintSettings.CAR_COLOR);
		graphics2D.fillOval(currentPosition.x, currentPosition.y, getCarRadius(), getCarRadius());
	}
}
