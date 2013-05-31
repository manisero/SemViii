package pl.edu.pw.elka.sag.ontology.concepts;

import jade.core.*;

public class CarMovementInfo
{
	private Location nextCrossroadsLocation;
	private AID nextTrafficLight;
	private boolean nextTrafficLightRuleResult;
	private int otherCarsToCheck;
	private int otherCarsChecked;
	private boolean hasPriority;
	private boolean hasTypePriority;
	
	public Location getNextCrossroadsLocation()
	{
		return nextCrossroadsLocation;
	}

	public void setNextCrossroadsLocation(Location nextCrossroadsLocation)
	{
		this.nextCrossroadsLocation = nextCrossroadsLocation;
	}

	public AID getNextTrafficLight()
	{
		return nextTrafficLight;
	}

	public void setNextTrafficLight(AID nextTrafficLight)
	{
		this.nextTrafficLight = nextTrafficLight;
	}

	public boolean getNextTrafficLightRuleResult()
	{
		return nextTrafficLightRuleResult;
	}

	public void setNextTrafficLightRuleResult(boolean nextTrafficLightRuleResult)
	{
		this.nextTrafficLightRuleResult = nextTrafficLightRuleResult;
	}

	public int getOtherCarsToCheck()
	{
		return otherCarsToCheck;
	}

	public void setOtherCarsToCheck(int otherCarsToCheck)
	{
		this.otherCarsToCheck = otherCarsToCheck;
	}

	public int getOtherCarsChecked()
	{
		return otherCarsChecked;
	}

	public void setOtherCarsChecked(int otherCarsChecked)
	{
		this.otherCarsChecked = otherCarsChecked;
	}

	public boolean getHasPriority()
	{
		return hasPriority;
	}

	public void setHasPriority(boolean hasPriority)
	{
		this.hasPriority = hasPriority;
	}

	public boolean getHasTypePriority()
	{
		return hasTypePriority;
	}

	public void setHasTypePriority(boolean hasTypePriority)
	{
		this.hasTypePriority = hasTypePriority;
	}
}
