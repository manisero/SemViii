package pl.edu.pw.elka.sag.gui.objects;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import pl.edu.pw.elka.sag.gui.constants.PaintSettings;
import pl.edu.pw.elka.sag.gui.logic.IDrawablePropertyProvider;
import pl.edu.pw.elka.sag.gui.logic.IDrawablePropertyReceiver;
import pl.edu.pw.elka.sag.ontology.concepts.*;

public class DrawableTrafficLights extends Canvas implements IDrawablePropertyReceiver
{
	private static final long serialVersionUID = 3470287423237683611L;
	
	private Location trafficLightsLocation;
	private Direction allowedDirection;
	
	private IDrawablePropertyProvider propertyProvider;
	
	private Integer trafficLightsSize;

	public DrawableTrafficLights(Location trafficLightsLocation, Direction allowedDirection)
	{
		this.trafficLightsLocation = trafficLightsLocation;
		this.allowedDirection = allowedDirection;
	}
	
	/**
	 * Sets properties provider.
	 * 
	 * @param propertiesProvider {@link pl.edu.pw.elka.sag.gui.logic.IDrawablePropertyProvider} to set
	 */
	@Override
	public void setPropertyProvider(IDrawablePropertyProvider propertyProvider)
	{
		this.propertyProvider = propertyProvider;
	}
	
	/**
	 * Returns traffic lights location.
	 * 
	 * @return {@link pl.edu.pw.elka.sag.ontology.concepts.Location} of traffic lights
	 */
	public Location getTrafficLightsLocation()
	{
		return trafficLightsLocation;
	}

	/**
	 * Sets traffic lights location.
	 * 
	 * @param trafficLightsLocation {@link pl.edu.pw.elka.sag.ontology.concepts.Location} of traffic lights to set
	 */
	public void setTrafficLightsLocation(Location trafficLightsLocation)
	{
		this.trafficLightsLocation = trafficLightsLocation;
	}

	/**
	 * Returns allowed direction of traffic lights.
	 * 
	 * @return allowed {@link pl.edu.pw.elka.sag.ontology.concepts.Direction} of traffic lights
	 */
	public Direction getTrafficLightAllowedDirection()
	{
		return allowedDirection;
	}

	/**
	 * Sets allowed direction of traffic lights.
	 * 
	 * @param allowedDirection allowed {@link pl.edu.pw.elka.sag.ontology.concepts.Direction} of traffic lights to set
	 */
	public void setTrafficLightAllowedDirection(Direction allowedDirection)
	{
		this.allowedDirection = allowedDirection;
	}
	
	/**
	 * Paints traffic lights.
	 * 
	 * @param graphics {@link java.awt.Graphics} instance
	 */
	@Override
	public void paint(Graphics graphics)
	{
		super.paint(graphics);
		
		Graphics2D graphics2D = (Graphics2D) graphics;
		
		paintTrafficLights(graphics2D, Direction.NORTH);
		paintTrafficLights(graphics2D, Direction.SOUTH);
		paintTrafficLights(graphics2D, Direction.WEST);
		paintTrafficLights(graphics2D, Direction.EAST);
	}
	
	/**
	 * Lazy loading of traffic lights size.
	 * 
	 * @return traffic lights size
	 */
	private int getTrafficLightsSize()
	{
		if (trafficLightsSize == null)
		{
			trafficLightsSize = propertyProvider.getTrafficLightsBoundingBoxSize();
		}
		
		return trafficLightsSize;
	}
	
	/**
	 * Paints traffic light for given direction.
	 * 
	 * @param graphics2D {@link java.awt.Graphics2D} instance
	 * @param direction {@link pl.edu.pw.elka.sag.ontology.concepts.Direction} of traffic lights to paint
	 */
	private void paintTrafficLights(Graphics2D graphics2D, Direction direction)
	{
		Point currentPosition = propertyProvider.getTrafficLightsScreenPosition(trafficLightsLocation, direction);
		
		if (currentPosition == null)
		{
			return;
		}
		
		graphics2D.setColor(allowedDirection.hasPart(direction) ? 
				PaintSettings.LIGHTS_GREEN_COLOR : PaintSettings.LIGHTS_RED_COLOR);
		
		graphics2D.fillOval(currentPosition.x, currentPosition.y, getTrafficLightsSize(), getTrafficLightsSize());
	}
}
