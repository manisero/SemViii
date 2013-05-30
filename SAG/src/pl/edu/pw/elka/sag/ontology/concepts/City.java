package pl.edu.pw.elka.sag.ontology.concepts;

import jade.content.*;

public class City implements Concept
{
	private static final long serialVersionUID = 2996419799607017320L;
	private int blocksCount;
	private int size;
	
	public City() { }
	
	public City(int blocksCount)
	{
		setBlocksCount(blocksCount);
	}
	
	public int getBlocksCount()
	{
		return blocksCount;
	}

	public void setBlocksCount(int blocksCount)
	{
		this.blocksCount = blocksCount;
		this.size = blocksCount * 10;
	}
	
	public int getSize()
	{
		return size;
	}
}
