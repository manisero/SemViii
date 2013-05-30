package pl.edu.pw.elka.sag.gui;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import pl.edu.pw.elka.sag.gui.constants.MapPaintSettings;
import pl.edu.pw.elka.sag.gui.panels.CityMap;
import pl.edu.pw.elka.sag.ontology.concepts.*;

public class TrafficSimulatorGUI extends JFrame
{
	private static final long serialVersionUID = 5554036514535482563L;
	
	private CityMap cityMap;
	
	public TrafficSimulatorGUI(int citySize)
	{
		initializeComponents(citySize);
		setShutdownApplicationOnClose();
	}
	
	public void setCityMapRoadSide(RoadSide roadSide)
	{
		cityMap.setRoadSide(roadSide);
	}
	
	/**
	 * Adds drawable object to nested {@link pl.edu.pw.elka.sag.gui.panels.CityMap}.
	 * 
	 * @param drawable object to add
	 */
	public void addDrawableObjectToCityMap(Canvas drawable)
	{
		cityMap.addDrawableObject(drawable);
	}
	
	/**
	 * Changes size of the window to the maximum possible square size.
	 */
	private int setWindowSize()
	{
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		int size = screenSize.height > screenSize.width ? screenSize.width : screenSize.height;
		
		size = (int) (size * MapPaintSettings.WINDOW_TO_SCREEN_SIZE_RATIO);
		
		setSize(size, size);
		
		return size;
	}
	
	/**
	 * Constructs the window by adding its components.
	 */
	private void initializeComponents(int citySize)
	{
		cityMap = new CityMap(citySize, setWindowSize());
		
		add(cityMap);
	}
	
	/**
	 * Enforces shutting application down on window close.
	 */
	private void setShutdownApplicationOnClose()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
