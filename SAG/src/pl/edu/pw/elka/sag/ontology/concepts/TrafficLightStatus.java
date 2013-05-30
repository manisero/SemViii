package pl.edu.pw.elka.sag.ontology.concepts;

public enum TrafficLightStatus
{
	GREEN,
	RED;
	
	public TrafficLightStatus getOpposite()
	{
		if (equals(TrafficLightStatus.GREEN))
		{
			return TrafficLightStatus.RED;
		}
		else
		{
			return TrafficLightStatus.GREEN;
		}
	}
}
