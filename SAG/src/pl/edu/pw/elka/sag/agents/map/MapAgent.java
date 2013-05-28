package pl.edu.pw.elka.sag.agents.map;

import java.awt.*;

import pl.edu.pw.elka.sag.agents.*;
import pl.edu.pw.elka.sag.entities.*;
import pl.edu.pw.elka.sag.gui.*;

public class MapAgent extends AgentBase
{
	private static final long serialVersionUID = 6834846218522498189L;
	
	@Override
	protected void setup()
	{
		super.setup();
		register();
		
		final TrafficSimulatorGUI gui = new TrafficSimulatorGUI(4);
		Map map = new Map(gui);
		
		EventQueue.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				gui.setVisible(true);
			}
		});
		
		addBehaviour(new RequestCarMovementInfoBehaviour(this, 100));
		addBehaviour(new ReceiveCarMovementInfoBehaviour(this, map));
		addBehaviour(new RequestTrafficLightInfoBehaviour(this, 100));
		addBehaviour(new ReceiveTrafficLightInfoBehaviour(this, map));
	}
}
