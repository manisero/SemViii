package pl.edu.pw.elka.sag.gui.panels;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.LinkedHashSet;

import javax.swing.JPanel;
import javax.swing.Timer;

import pl.edu.pw.elka.sag.entities.Direction;
import pl.edu.pw.elka.sag.entities.Location;
import pl.edu.pw.elka.sag.gui.constants.PaintSettings;
import pl.edu.pw.elka.sag.gui.logic.IDrawablePropertyProvider;
import pl.edu.pw.elka.sag.gui.logic.IDrawablePropertyReceiver;

public class CityMap extends JPanel implements IDrawablePropertyProvider
{
	private static final long serialVersionUID = 1468374317265426866L;

	private int citySize;
	private int screenSize;
	
	private Collection<Canvas> drawableObjects = new LinkedHashSet<Canvas>();
	
	/**
	 * Constructs city map view.
	 * 
	 * @param citySize number of street crossings at one level
	 * @param screenSize size of the viewport box
	 */
	public CityMap(int citySize, int screenSize)
	{
		this.citySize = citySize;
		this.screenSize = screenSize;
		
		animate();
	}
	
	/**
	 * Returns car's radius.
	 * 
	 * @return int car object's radius
	 */
	@Override
	public int getCarBoundingBoxSize()
	{
		return getLaneOffset();
	}

	/**
	 * Returns car's position on the screen.
	 * 
	 * @return {@link java.awt.Point} car's position
	 */
	@Override
	public Point getCarScreenPosition(Location location, Direction direction)
	{
		int xCar = (int) (getMarginSize() + location.getX() * getStreetLength() + (int) (getStreetWidth() / 2.0) + 
				(int) (1.5 * getCarBoundingBoxSize()));
	
		int yCar = (int) (getMarginSize() + location.getY() * getStreetLength() + (int) (getStreetWidth() / 2.0) +
				(int) (1.5 * getCarBoundingBoxSize()));
		
		int xCorrection = 0;
		int yCorrection = 0; 
		
		if (direction.equals(Direction.EAST))
		{
			xCorrection -= ((int) (3 * getCarBoundingBoxSize() / 2.0));
			yCorrection += (int) (getCarBoundingBoxSize() / 2.0); 
		}
		else if (direction.equals(Direction.WEST))
		{
			xCorrection += ((int) (3 * getCarBoundingBoxSize() / 2.0));
			yCorrection -= (int) (getCarBoundingBoxSize() / 2.0);
		}
		else if (direction.equals(Direction.NORTH))
		{
			xCorrection += (int) (getCarBoundingBoxSize() / 2.0);
			yCorrection += ((int) (3 * getCarBoundingBoxSize() / 2.0));
		}
		else if (direction.equals(Direction.SOUTH))
		{
			xCorrection -= (int) (getCarBoundingBoxSize() / 2.0);
			yCorrection -= ((int) (3 * getCarBoundingBoxSize() / 2.0));
		}
		
		return new Point(xCar + xCorrection, yCar + yCorrection);
	}
	
	private void animate()
	{
		new Timer(1000 / PaintSettings.FPS, new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				repaint();
			}
		}).start();
	}
	
	/**
	 * Adds drawable object. 
	 * 
	 * @param object {@link java.awt.Canvas} to draw
	 */
	public void addDrawableObject(Canvas object)
	{
		if (drawableObjects.add(object) && object instanceof IDrawablePropertyReceiver)
		{
			((IDrawablePropertyReceiver) object).setPropertyProvider(this);
		}
	}
	
	/**
	 * Removes drawable object.
	 * 
	 * @param object {@link java.awt.Canvas} to draw
	 */
	public void removeDrawableObject(Canvas object)
	{
		drawableObjects.remove(object);
	}
	
	/**
	 * Returns viewport's size with margins excluded.
	 * 
	 * @return viewport size
	 */
	private int getViewportSize()
	{
		return (int) (screenSize * PaintSettings.PANEL_TO_WINDOW_SIZE_RATIO);
	}
	
	/**
	 * Returns single margin's size.
	 */
	private int getMarginSize()
	{
		return (int) (screenSize * ((1.0 - PaintSettings.PANEL_TO_WINDOW_SIZE_RATIO) / 2.0));
	}
	
	/**
	 * Returns single street's length.
	 * 
	 * @return int street's length
	 */
	private int getStreetLength()
	{
		double streetLength = getViewportSize() / ((PaintSettings.STREET_WIDTH_TO_LENGTH_RATIO + 1) * citySize - 1);
		
		return (int) Math.floor(streetLength);
	}
	
	/**
	 * Returns single street's width.
	 * 
	 * @return int street's width
	 */
	private int getStreetWidth()
	{
		double streetWidth = getStreetLength() * PaintSettings.STREET_WIDTH_TO_LENGTH_RATIO; 
		
		return (int) Math.floor(streetWidth);
	}
	
	/**
	 * Returns single lane's length.
	 * 
	 * @return int lane's length
	 */
	private int getLaneLength()
	{
		int streetLength = getStreetLength() - getStreetWidth();
		
		double laneLength = streetLength / (PaintSettings.LANES_PER_STREET * 
				(1 + PaintSettings.LANE_TO_SPACE_LENGTH_RATIO) + PaintSettings.LANE_TO_SPACE_LENGTH_RATIO);
		
		return (int) laneLength;
	}
	
	/**
	 * Returns single lane's space length.
	 * 
	 * @return int lane's space length
	 */
	private int getLaneSpaceLength()
	{
		double laneSpaceLength = getLaneLength() * PaintSettings.LANE_TO_SPACE_LENGTH_RATIO;
		
		return (int) laneSpaceLength;
	}
	
	/**
	 * Returns single lane's thickness.
	 * 
	 * @return int lane's thickness
	 */
	private int getLaneThickness()
	{
		boolean streetWidthEven = getStreetWidth() % 2 == 0;
		
		return streetWidthEven ? 2 : 1;
	}
	
	/**
	 * Returns single lane's offset.
	 * 
	 * @return 
	 */
	private int getLaneOffset()
	{
		boolean streetWidthEven = getStreetWidth() % 2 == 0;
		
		return (int) (getStreetWidth() / 2.0) + (streetWidthEven ? 0 : 1);
	}
	
	/**
	 * Paints city map.
	 *  
	 *  @param graphics {@link java.awt.Graphics2D} instance
	 */
	@Override 
	public void paint(Graphics graphics)
	{
		super.paint(graphics);
		
		Graphics2D graphics2D = (Graphics2D) graphics;
		
		paintStreetGrid(graphics2D);
		paintNestedObjects(graphics2D);
	}
	
	/**
	 * Paints city's streets grid.
	 * 
	 * @param graphics2D {@link java.awt.Graphics2D} instance
	 */
	private void paintStreetGrid(Graphics2D graphics2D)
	{
		paintHalfGrid(graphics2D, false);
		paintHalfGrid(graphics2D, true);
	}
	
	/**
	 * Paints nested objects.
	 * 
	 * @param graphics2D {@link java.awt.Graphics2D} instance
	 */
	private void paintNestedObjects(Graphics2D graphics2D)
	{
		for (Canvas canvas : drawableObjects)
		{
			canvas.paint(graphics2D);
		}
	}
	
	/**
	 * Wrapped {@link java.awt.Graphics2D}'s method allowing to easily swap x and y axis while drawing 
	 * {@link java.awt.Rectangle}.
	 * 
	 * @param graphics2D {@link java.awt.Graphics2D} instance
	 * @param color {@link java.awt.Color} of the rectangle
	 * @param x1 x-coordinate of line's beginning
	 * @param y1 y-coordinate of line's beginning
	 * @param x2 x-coordinate of line's ending
	 * @param y2 y-coordinate of line's ending
	 * @param swapAxis 
	 */
	private void drawFilledRectangle(Graphics2D graphics2D, Color color, int x, int y, int width, int height, 
			boolean swapAxis)
	{
		graphics2D.setColor(color);
		graphics2D.fillRect(swapAxis ? y : x, swapAxis? x : y, swapAxis ? height : width, swapAxis ? width : height);
	}
	
	/**
	 * Paints city's horizontal or vertical part of the grid.
	 * 
	 * @param graphics {@link java.awt.Graphics2D} instance
	 * @param verticalGrid specifies whether grid should be horizontal (false) or vertical (true)
	 */
	private void paintHalfGrid(Graphics2D graphics, boolean verticalGrid)
	{
		int streetWidth = getStreetWidth();
		int streetLength = getStreetLength();
		
		int baseLength = streetLength * (citySize - 1) + streetWidth;
		
		int currentHeight = getMarginSize() + streetWidth;
		int currentWidth = getMarginSize() + streetWidth;
		
		for (int i = 0; i < citySize; ++i)
		{
			drawFilledRectangle(graphics, PaintSettings.STREET_COLOR, currentWidth, currentHeight, baseLength, 
					streetWidth, verticalGrid);
			
			int laneOffset = getLaneOffset();
			int laneThickness = getLaneThickness();
			
			for (int j = 0; j < citySize - 1; ++j)
			{
				int xLane = currentWidth + streetWidth + getLaneSpaceLength() + j * streetLength; 
				int yLane = currentHeight + laneOffset;
				
				for (int k = 0; k < PaintSettings.LANES_PER_STREET; ++k)
				{
					drawFilledRectangle(graphics, PaintSettings.LANE_COLOR, xLane, yLane, getLaneLength(),
							laneThickness, verticalGrid);
					
					xLane += getLaneLength() + getLaneSpaceLength();
				}
			}
			
			currentHeight += streetLength;
		}
	}
}
