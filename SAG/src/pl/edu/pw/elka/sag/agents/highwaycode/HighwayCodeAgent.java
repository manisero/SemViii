package pl.edu.pw.elka.sag.agents.highwaycode;

import java.awt.EventQueue;

import pl.edu.pw.elka.sag.agents.AgentBase;
import pl.edu.pw.elka.sag.constants.HighwayCodeTypes;
import pl.edu.pw.elka.sag.gui.HighwayCodeAgentGUI;
import pl.edu.pw.elka.sag.logic.highwaycode.HighwayCodeFactory;
import pl.edu.pw.elka.sag.logic.highwaycode.IHighwayCode;

public class HighwayCodeAgent extends AgentBase implements IHighwayCodeChangeListener
{
	private static final long serialVersionUID = 1567080874884137549L;
	
	private HighwayCodeFactory highwayCodeFactory = new HighwayCodeFactory();
	private IHighwayCode highwayCode;

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
		
		highwayCode = highwayCodeFactory.createHighwayCode(highwayCodeType);
		
		final HighwayCodeAgentGUI gui = new HighwayCodeAgentGUI(highwayCodeFactory.getHighwayCodes());
		gui.addListener(this);
		
		EventQueue.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				gui.setVisible(true);
			}
		});
		
		addBehaviour(new ServeRoadSideRuleBehaviour(this));
		addBehaviour(new ServeTrafficLightRuleBehaviour(this));
		addBehaviour(new ServePriorityRuleBehaviour(this));
		addBehaviour(new ServeTypePriorityRuleBehaviour(this));
	}

	@Override
	public void onHighwayCodeChanged(String highwayCodeType)
	{
		IHighwayCode highwayCode = highwayCodeFactory.createHighwayCode(highwayCodeType);
		
		if (highwayCode != null)
		{
			this.highwayCode = highwayCode;
		}
	}
	
	public IHighwayCode getHighwayCode()
	{
		return highwayCode;
	}
}
