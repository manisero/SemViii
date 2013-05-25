package pl.edu.pw.elka.sag.gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import pl.edu.pw.elka.sag.gui.constants.PaintSettings;
import pl.edu.pw.elka.sag.gui.panels.CityMap;

public class TrafficSimulatorGUI extends JFrame
{
	private static final long serialVersionUID = 5554036514535482563L;
	
	public TrafficSimulatorGUI(int citySize)
	{
		initializeComponents(citySize);
		setShutdownApplicationOnClose();
	}
	
	/**
	 * Changes size of the window to the maximum possible square size.
	 */
	private int setWindowSize()
	{
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		int size = screenSize.height > screenSize.width ? screenSize.width : screenSize.height;
		
		size = (int) (size * PaintSettings.WINDOW_TO_SCREEN_SIZE_RATIO);
		
		setSize(size, size);
		
		return size;
	}
	
	/**
	 * Constructs the window by adding its components.
	 */
	private void initializeComponents(int citySize)
	{
		add(new CityMap(citySize, setWindowSize()));
	}
	
	/**
	 * Enforces shutting application down on window close.
	 */
	private void setShutdownApplicationOnClose()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
