package pl.edu.pw.elka.sag.agents.car;

import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.*;
import pl.edu.pw.elka.sag.constants.*;
import pl.edu.pw.elka.sag.entities.*;

public class ReceiveAllowedDirectionBehaviour extends CyclicBehaviour
{
	private static final long serialVersionUID = -5012994635276751890L;
	private static final MessageTemplate messageTemplate = MessageTemplate.MatchConversationId(ConversationTypes.TRAFFIC_LIGHTS_CONVERSATION_TYPE);
	
	private final Car car;
	
	public ReceiveAllowedDirectionBehaviour(Agent agent, Car car)
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
				Direction allowedDirection = (Direction) message.getContentObject();
				car.setNextTrafficLightAllowedDirection(allowedDirection);
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
