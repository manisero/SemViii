package pl.edu.pw.elka.sag.gui;

import java.awt.EventQueue;

public class TestApplication
{
	private static final int CITY_SIZE = 4;
	
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				new TrafficSimulatorGUI(CITY_SIZE).setVisible(true);
			}
		});
	}
}
