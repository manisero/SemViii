package pl.edu.pw.elka.sag.logic.actions;

import java.util.Map.Entry;
import java.util.Map;

import pl.edu.pw.elka.sag.ontology.concepts.*;

public class SwitchTrafficLightStatusAction
{
	public void execute(TrafficLight trafficLight)
	{
		Map<Direction, TrafficLightStatus> trafficLightStatus = trafficLight.getStatus();
		
		for (Entry<Direction, TrafficLightStatus> entry : trafficLightStatus.entrySet())
		{
			trafficLightStatus.put(entry.getKey(), entry.getValue().getOpposite());
		}
	}
}
