package pl.edu.pw.elka.sag.gui;

import java.awt.EventQueue;

import pl.edu.pw.elka.sag.entities.Location;
import pl.edu.pw.elka.sag.gui.objects.AnimatedCar;

public class TestApplication
{
	private static final int CITY_SIZE = 4;
	
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				AnimatedCar car = new AnimatedCar(new Location(0, 1), new Location(0, 0));
				
				TrafficSimulatorGUI gui = new TrafficSimulatorGUI(CITY_SIZE);
				
				gui.addDrawableObjectToCityMap(car);
				
				gui.setVisible(true);
			}
		});
	}
}
