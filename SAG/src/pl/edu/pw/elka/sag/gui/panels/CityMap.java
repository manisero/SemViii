package pl.edu.pw.elka.sag.gui.panels;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import pl.edu.pw.elka.sag.gui.constants.PaintSettings;

public class CityMap extends JPanel
{
	private static final long serialVersionUID = 1468374317265426866L;

	private int citySize;
	private int screenSize;
	
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
	}
	
	/**
	 * Paints city's streets grid.
	 * 
	 * @param graphics {@link java.awt.Graphics2D} instance
	 */
	private void paintStreetGrid(Graphics2D graphics2D)
	{
		System.out.println("horizontal");
		paintHalfGrid(graphics2D, false);
		System.out.println("vertical");
		paintHalfGrid(graphics2D, true);
	}
	
	/**
	 * Wrapped {@link java.awt.Graphics2D}'s method allowing to easily swap x and y axis while drawing 
	 * {@link java.awt.Rectangle}.
	 * 
	 * @param graphics2D {@link java.awt.Graphics2D} instance
	 * @param x1 x-coordinate of line's beginning
	 * @param y1 y-coordinate of line's beginning
	 * @param x2 x-coordinate of line's ending
	 * @param y2 y-coordinate of line's ending
	 * @param swapAxis 
	 */
	private void drawFilledRectangle(Graphics2D graphics2D, int x, int y, int width, int height, boolean swapAxis)
	{
		graphics2D.fillRect(swapAxis ? y : x, swapAxis? x : y, swapAxis ? height : width, swapAxis ? width : height);
		
		System.out.println("drawing rect of x: " + (swapAxis ? y : x));
		System.out.println("drawing rect of y: " + (swapAxis? x : y));
		System.out.println("drawing rect of width: " + (swapAxis ? height : width));
		System.out.println("drawing rect of height: " + (swapAxis ? width : height));
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
		
		graphics.setColor(PaintSettings.STREET_COLOR);
		
		for (int i = 0; i < citySize; ++i)
		{
			drawFilledRectangle(graphics, currentWidth, currentHeight, baseLength, streetWidth, verticalGrid);
			
			currentHeight += streetLength;
		}
	}
}
