package pl.edu.pw.elka.sag.ontology.concepts;

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
}
