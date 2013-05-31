package pl.edu.pw.elka.sag.ontology.concepts;

import java.awt.Color;

public enum CarType
{
	STANDARD(0),
	POLICE(1),
	AMBULANCE(2);
	
	private int value;
	
	private CarType(int value)
	{
		this.value = value;
	}
	
	public int getValue()
	{
		return value;
	}
	
	public Color getDefaultTypeColor()
	{
		switch (value)
		{
			case 1:
				return Color.BLUE;
				
			case 2:
				return Color.RED;
				
			default:
				return Color.WHITE;
		}
	}
}
