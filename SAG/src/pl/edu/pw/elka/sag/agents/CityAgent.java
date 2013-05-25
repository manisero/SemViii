package pl.edu.pw.elka.sag.agents;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import pl.edu.pw.elka.sag.behaviours.ServeDirectionBehaviour;
import pl.edu.pw.elka.sag.constants.AgentTypes;

public class CityAgent extends Agent
{
	private static final long serialVersionUID = 4471164341534623888L;
	
	private static int CITY_WIDTH = 4;
	private static int CITY_HEIGHT = 4;

	@Override
	protected void setup()
	{
		registerInDF();
		
		addBehaviour(new ServeDirectionBehaviour(CITY_WIDTH, CITY_HEIGHT));
		
		System.out.println("CityAgent is ready!");
	}
	
	@Override 
	protected void takeDown()
	{
		try
		{
			DFService.deregister(this);
		}
		catch (FIPAException e)
		{
			System.err.println("Failed to unregister agent: " + getAID());
			
			e.printStackTrace();
		}
	}
	
	private void registerInDF()
	{
		try
		{
			DFAgentDescription dfAgentDescription = new DFAgentDescription();
			
			dfAgentDescription.setName(getAID());
			
			ServiceDescription serviceDescription = new ServiceDescription();
			
			serviceDescription.setType(AgentTypes.CITY_AGENT_TYPE);
			serviceDescription.setName("manhattan");
			
			dfAgentDescription.addServices(serviceDescription);
			
			DFService.register(this, dfAgentDescription);
		}
		catch (FIPAException e)
		{
			System.err.println("Failed to register agent: " + getAID());
			
			e.printStackTrace();
		}
	}
}
