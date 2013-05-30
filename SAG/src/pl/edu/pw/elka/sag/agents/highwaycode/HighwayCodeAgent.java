package pl.edu.pw.elka.sag.agents.highwaycode;

import pl.edu.pw.elka.sag.agents.*;
import pl.edu.pw.elka.sag.constants.*;
import pl.edu.pw.elka.sag.logic.highwaycode.*;

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
		
		IHighwayCode highwayCode = new HighwayCodeFactory().createHighwayCode(highwayCodeType);
		
		addBehaviour(new ServeRoadSideRuleBehaviour(this, highwayCode));
		addBehaviour(new ServeTrafficLightRuleBehaviour(this, highwayCode));
	}
}
