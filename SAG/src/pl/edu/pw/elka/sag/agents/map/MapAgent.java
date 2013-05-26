package pl.edu.pw.elka.sag.agents.map;

import jade.core.*;

import java.awt.*;
import java.util.*;

import pl.edu.pw.elka.sag.agents.*;
import pl.edu.pw.elka.sag.entities.*;
import pl.edu.pw.elka.sag.gui.*;
import pl.edu.pw.elka.sag.gui.objects.*;

public class MapAgent extends AgentBase
{
	private static final long serialVersionUID = 6834846218522498189L;

	private Map<AID, DrawableCar> cars = new LinkedHashMap<AID, DrawableCar>();
	private TrafficSimulatorGUI gui;
	
	@Override
	protected void setup()
	{
		super.setup();
		
		gui = new TrafficSimulatorGUI(4);
		
		EventQueue.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				gui.setVisible(true);
			}
		});
		
		addBehaviour(new RequestCarMovementInfoBehaviour(this, 100));
		addBehaviour(new ReceiveCarMovementInfoBehaviour(this));
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
}
