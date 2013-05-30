package pl.edu.pw.elka.sag.agents.highwaycode;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

import java.io.IOException;

public abstract class RuleBehaviourBase extends CyclicBehaviour
{
	private static final long serialVersionUID = -1063042687412453068L;
	
	private final MessageTemplate messageTemplate;
	
	protected RuleBehaviourBase(Agent agent, String conversationType)
	{
		super(agent);
		messageTemplate = MessageTemplate.MatchConversationId(conversationType);
	}
	
	@Override
	public void action()
	{
		ACLMessage message = myAgent.receive(messageTemplate);
		
		if (message != null)
		{
			try
			{
				ACLMessage reply = message.createReply();
				fillReply(message, reply);
				myAgent.send(reply);
			}
			catch (UnreadableException e)
			{
				e.printStackTrace();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			block();
		}
	}
	
	protected HighwayCodeAgent getHighwayCodeAgent()
	{
		return (HighwayCodeAgent) myAgent;
	}
	
	protected abstract void fillReply(ACLMessage message, ACLMessage reply) throws UnreadableException, IOException;
}
