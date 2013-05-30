package pl.edu.pw.elka.sag.gui.objects;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.*;
import java.util.Map;

import pl.edu.pw.elka.sag.gui.constants.PaintSettings;
import pl.edu.pw.elka.sag.gui.logic.IDrawablePropertyProvider;
import pl.edu.pw.elka.sag.gui.logic.IDrawablePropertyReceiver;
import pl.edu.pw.elka.sag.ontology.concepts.*;

public class DrawableTrafficLight extends Canvas implements IDrawablePropertyReceiver
{
	private static final long serialVersionUID = 3470287423237683611L;
	
	private Location trafficLightLocation;
	private Map<Direction, TrafficLightStatus> trafficLightStatus = new LinkedHashMap<Direction, TrafficLightStatus>();
	
	private IDrawablePropertyProvider propertyProvider;
	
	private Integer trafficLightsSize;

	public DrawableTrafficLight(Location trafficLightLocation, Map<Direction, TrafficLightStatus> trafficLightStatus)
	{
		this.trafficLightLocation = trafficLightLocation;
		this.trafficLightStatus = trafficLightStatus;
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
	public Location getTrafficLightLocation()
	{
		return trafficLightLocation;
	}

	/**
	 * Sets traffic lights location.
	 * 
	 * @param trafficLightsLocation {@link pl.edu.pw.elka.sag.ontology.concepts.Location} of traffic lights to set
	 */
	public void setTrafficLightLocation(Location trafficLightsLocation)
	{
		this.trafficLightLocation = trafficLightsLocation;
	}

	/**
	 * Returns status of traffic lights.
	 * 
	 * @return status of traffic lights
	 */
	public Map<Direction, TrafficLightStatus> getTrafficLightStatus()
	{
		return trafficLightStatus;
	}

	/**
	 * Sets status of traffic lights.
	 * 
	 * @param status of traffic lights to set
	 */
	public void setTrafficLightStatus(Map<Direction, TrafficLightStatus> status)
	{
		this.trafficLightStatus = status;
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
		Point currentPosition = propertyProvider.getTrafficLightsScreenPosition(trafficLightLocation, direction);
		
		if (currentPosition == null)
		{
			return;
		}
		
		TrafficLightStatus status = trafficLightStatus.get(direction);
		graphics2D.setColor(status == TrafficLightStatus.GREEN
								? PaintSettings.LIGHTS_GREEN_COLOR
								: PaintSettings.LIGHTS_RED_COLOR);
		
		graphics2D.fillOval(currentPosition.x, currentPosition.y, getTrafficLightsSize(), getTrafficLightsSize());
	}
}
