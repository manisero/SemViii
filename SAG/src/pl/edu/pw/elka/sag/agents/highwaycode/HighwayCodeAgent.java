package pl.edu.pw.elka.sag.agents.highwaycode;

import java.awt.EventQueue;

import pl.edu.pw.elka.sag.agents.AgentBase;
import pl.edu.pw.elka.sag.constants.HighwayCodeTypes;
import pl.edu.pw.elka.sag.gui.HighwayCodeAgentGUI;
import pl.edu.pw.elka.sag.logic.highwaycode.HighwayCodeFactory;
import pl.edu.pw.elka.sag.logic.highwaycode.IHighwayCode;

public class HighwayCodeAgent extends AgentBase
{
	private static final long serialVersionUID = 1567080874884137549L;

	@Override
	protected void setup()
	{
		super.setup();
		register();
		
		Object[] arguments = getArguments();
		String highwayCodeType;
		
		if (arguments == null || arguments.length < 1)
		{
			 highwayCodeType = HighwayCodeTypes.POLISH_HIGHWAY_CODE_TYPE;
		}
		else
		{
			highwayCodeType = arguments[0].toString();
		}
		
		System.out.println("Highway Code used: " + highwayCodeType);
		
		HighwayCodeFactory highwayCodeFactory = new HighwayCodeFactory();
		
		final HighwayCodeAgentGUI gui = new HighwayCodeAgentGUI(highwayCodeFactory);
		
		EventQueue.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				gui.setVisible(true);
			}
		});
		
		IHighwayCode highwayCode = highwayCodeFactory.createHighwayCode(highwayCodeType);
		
		ServeRoadSideRuleBehaviour serveRoadSideRuleBehaviour = new ServeRoadSideRuleBehaviour(this, highwayCode);
		addBehaviour(serveRoadSideRuleBehaviour);
		gui.addListener(serveRoadSideRuleBehaviour);
		
		ServeTrafficLightRuleBehaviour serveTrafficLightRuleBehaviour = new ServeTrafficLightRuleBehaviour(this, highwayCode);
		addBehaviour(serveTrafficLightRuleBehaviour);
		gui.addListener(serveTrafficLightRuleBehaviour);
	}
}
