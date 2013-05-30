package pl.edu.pw.elka.sag.gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.Timer;

import pl.edu.pw.elka.sag.gui.objects.DrawableCar;
import pl.edu.pw.elka.sag.gui.objects.DrawableTrafficLight;
import pl.edu.pw.elka.sag.ontology.concepts.*;

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

				final DrawableCar car = new DrawableCar(new Location(0, 10), Direction.NORTH);
				gui.addDrawableObjectToCityMap(car);
				
				LinkedHashMap<Direction, TrafficLightStatus> trafficLightStatus = new LinkedHashMap<Direction, TrafficLightStatus>();
				trafficLightStatus.put(Direction.NORTH, TrafficLightStatus.GREEN);
				trafficLightStatus.put(Direction.SOUTH, TrafficLightStatus.GREEN);
				trafficLightStatus.put(Direction.EAST, TrafficLightStatus.RED);
				trafficLightStatus.put(Direction.WEST, TrafficLightStatus.RED);
				
				final DrawableTrafficLight trafficLights = 
						new DrawableTrafficLight(new Location(0, 0), trafficLightStatus);
				gui.addDrawableObjectToCityMap(trafficLights);
				
				final DrawableTrafficLight otherTrafficLights = 
						new DrawableTrafficLight(new Location(20, 20), trafficLightStatus);
				gui.addDrawableObjectToCityMap(otherTrafficLights);
				
				gui.setVisible(true);
				
				Timer timer = new Timer(500, new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						Location location = car.getCarLocation();
						
						if (location.getY() >= 20)
						{
							location.setX(location.getX() + 1);
							
							car.setCarLocation(location);
							car.setCarDirection(Direction.EAST);
						}
						else
						{
							location.setY(location.getY() + 1);
							
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
