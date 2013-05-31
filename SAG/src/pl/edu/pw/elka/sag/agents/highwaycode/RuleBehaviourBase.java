package pl.edu.pw.elka.sag.agents.highwaycode;

import jade.core.behaviours.*;
import jade.lang.acl.*;

import java.io.*;

import pl.edu.pw.elka.sag.logic.highwaycode.*;

public abstract class RuleBehaviourBase extends CyclicBehaviour
{
	private static final long serialVersionUID = -1063042687412453068L;
	
	private final MessageTemplate messageTemplate;
	
	protected RuleBehaviourBase(HighwayCodeAgent agent, String conversationType)
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
	
	protected IHighwayCode getHighwayCode()
	{
		return ((HighwayCodeAgent)myAgent).getHighwayCode();
	}
	
	protected abstract void fillReply(ACLMessage message, ACLMessage reply) throws UnreadableException, IOException;
}
