package pl.edu.pw.elka.sag.gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import pl.edu.pw.elka.sag.entities.Direction;
import pl.edu.pw.elka.sag.entities.Location;
import pl.edu.pw.elka.sag.gui.objects.DrawableCar;
import pl.edu.pw.elka.sag.gui.objects.DrawableTrafficLights;

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
				TrafficSimulatorGUI gui = new TrafficSimulatorGUI(CITY_SIZE);

				final DrawableCar car = new DrawableCar(new Location(0, 1), Direction.SOUTH);
				gui.addDrawableObjectToCityMap(car);
				
				final DrawableTrafficLights trafficLights = 
						new DrawableTrafficLights(new Location(0, 0), Direction.NORTH_SOUTH);
				gui.addDrawableObjectToCityMap(trafficLights);
				
				final DrawableTrafficLights otherTrafficLights = 
						new DrawableTrafficLights(new Location(2, 2), Direction.EAST_WEST);
				gui.addDrawableObjectToCityMap(otherTrafficLights);
				
				gui.setVisible(true);
				
				Timer timer = new Timer(500, new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						Location location = car.getCarLocation();
						
						if (location.getY() >= 2.0)
						{
							location.setX(location.getX() + 0.1);
							
							car.setCarLocation(location);
							car.setCarDirection(Direction.EAST);
						}
						else
						{
							location.setY(location.getY() + 0.1);
							
							car.setCarLocation(location);
						}
					}
				});
				
				timer.setRepeats(true);
				timer.start();
			}
		});
	}
}
