package pl.edu.pw.elka.sag.agents.car;

import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.*;
import pl.edu.pw.elka.sag.constants.*;
import pl.edu.pw.elka.sag.ontology.concepts.*;

public class ReceiveTrafficLightStatusBehaviour extends CyclicBehaviour
{
	private static final long serialVersionUID = -5012994635276751890L;
	private static final MessageTemplate messageTemplate = MessageTemplate.MatchConversationId(ConversationTypes.TRAFFIC_LIGHT_STATUS_CONVERSATION_TYPE);
	
	private final Car car;
	
	public ReceiveTrafficLightStatusBehaviour(Agent agent, Car car)
	{
		super(agent);
		this.car = car;
	}
	
	@Override
	public void action()
	{
		ACLMessage message = myAgent.receive(messageTemplate);
		
		if (message != null)
		{
			try
			{
				TrafficLightStatus status = (TrafficLightStatus) message.getContentObject();
				car.setNextTrafficLightStatus(status);
			}
			catch (UnreadableException e)
			{
				e.printStackTrace();
			}				
		}
		else
		{
			block();
		}
	}
}
