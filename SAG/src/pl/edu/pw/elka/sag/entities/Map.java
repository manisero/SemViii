package pl.edu.pw.elka.sag.entities;

import jade.core.*;

import java.io.*;
import java.util.*;

import pl.edu.pw.elka.sag.gui.*;
import pl.edu.pw.elka.sag.gui.objects.*;

public class Map implements Serializable 
{
	private static final long serialVersionUID = 4727355406265129148L;
	
	private TrafficSimulatorGUI gui;
	private java.util.Map<AID, DrawableCar> cars = new LinkedHashMap<AID, DrawableCar>();
	private java.util.Map<AID, DrawableTrafficLights> trafficLights = new LinkedHashMap<AID, DrawableTrafficLights>();
	
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

	public java.util.Map<AID, DrawableTrafficLights> getTrafficLights()
	{
		return trafficLights;
	}

	public void setTrafficLights(java.util.Map<AID, DrawableTrafficLights> trafficLights)
	{
		this.trafficLights = trafficLights;
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
	
	public void updateTrafficLightInfo(AID trafficLightId, TrafficLightInfo info)
	{
		if (!trafficLights.containsKey(trafficLightId))
		{
			DrawableTrafficLights trafficLight = new DrawableTrafficLights(info.getLocation(), info.getAllowedDirection());
			trafficLights.put(trafficLightId, trafficLight);
			gui.addDrawableObjectToCityMap(trafficLight);
		}
		else
		{
			DrawableTrafficLights trafficLight = trafficLights.get(trafficLightId);
			trafficLight.setTrafficLightsLocation(info.getLocation());
			trafficLight.setTrafficLightAllowedDirection(info.getAllowedDirection());
		}
	}
}
