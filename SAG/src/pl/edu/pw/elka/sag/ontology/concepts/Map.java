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
	
	public void updateCarInfo(AID carId, MovementInfo info)
	{
		if (!cars.containsKey(carId))
		{
			 DrawableCar car = new DrawableCar(info.getLocation(), info.getDirection());
			 cars.put(carId, car);
			 gui.addDrawableObjectToCityMap(car);
		}
		else
		{
			DrawableCar car = cars.get(carId);
			car.setCarLocation(info.getLocation());
			car.setCarDirection(info.getDirection());
		}
	}
	
	public void updateTrafficLightInfo(AID trafficLightId, TrafficLight trafficLight)
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
