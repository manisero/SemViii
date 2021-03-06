package pl.edu.pw.elka.sag.ontology.concepts;

import jade.content.*;
import jade.core.*;

import java.util.*;

import pl.edu.pw.elka.sag.gui.*;
import pl.edu.pw.elka.sag.gui.objects.*;

public class Map implements Concept
{
	private static final long serialVersionUID = 4727355406265129148L;
	
	private TrafficSimulatorGUI gui;
	private java.util.Map<AID, DrawableCar> cars = new LinkedHashMap<AID, DrawableCar>();
	private java.util.Map<AID, DrawableTrafficLight> trafficLights = new LinkedHashMap<AID, DrawableTrafficLight>();
	
	public Map() { }
	
	public Map(TrafficSimulatorGUI gui)
	{
		this.setGui(gui);
	}

	public TrafficSimulatorGUI getGui()
	{
		return gui;
	}

	public void setGui(TrafficSimulatorGUI gui)
	{
		this.gui = gui;
	}

	public java.util.Map<AID, DrawableCar> getCars()
	{
		return cars;
	}

	public void setCars(java.util.Map<AID, DrawableCar> cars)
	{
		this.cars = cars;
	}

	public java.util.Map<AID, DrawableTrafficLight> getTrafficLights()
	{
		return trafficLights;
	}

	public void setTrafficLights(java.util.Map<AID, DrawableTrafficLight> trafficLights)
	{
		this.trafficLights = trafficLights;
	}
	
	public void updateRoadSide(RoadSide roadSide)
	{
		gui.setCityMapRoadSide(roadSide);
	}
	
	public void updateCar(AID carId, Car car)
	{
		if (!cars.containsKey(carId))
		{
			 DrawableCar drawable = new DrawableCar(car.getLocation(), car.getDirection(), car.getColor());
			 cars.put(carId, drawable);
			 gui.addDrawableObjectToCityMap(drawable);
		}
		else
		{
			DrawableCar drawable = cars.get(carId);
			drawable.setCarLocation(car.getLocation());
			drawable.setCarDirection(car.getDirection());
		}
	}
	
	public void updateTrafficLight(AID trafficLightId, TrafficLight trafficLight)
	{
		if (!trafficLights.containsKey(trafficLightId))
		{
			DrawableTrafficLight drawableTrafficLight = new DrawableTrafficLight(trafficLight.getLocation(), trafficLight.getStatus());
			trafficLights.put(trafficLightId, drawableTrafficLight);
			gui.addDrawableObjectToCityMap(drawableTrafficLight);
		}
		else
		{
			DrawableTrafficLight drawableTrafficLight = trafficLights.get(trafficLightId);
			drawableTrafficLight.setTrafficLightLocation(trafficLight.getLocation());
			drawableTrafficLight.setTrafficLightStatus(trafficLight.getStatus());
		}
	}
}
