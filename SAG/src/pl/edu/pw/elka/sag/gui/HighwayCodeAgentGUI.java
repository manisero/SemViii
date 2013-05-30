package pl.edu.pw.elka.sag.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;

import pl.edu.pw.elka.sag.agents.highwaycode.IHighwayCodeChangeListener;
import pl.edu.pw.elka.sag.gui.constants.HighwayCodeGUIPaintSettings;
import pl.edu.pw.elka.sag.gui.panels.HighwayCodeSelector;
import pl.edu.pw.elka.sag.logic.highwaycode.HighwayCodeFactory;
import pl.edu.pw.elka.sag.logic.highwaycode.IHighwayCode;

public class HighwayCodeAgentGUI extends JFrame
{
	private static final long serialVersionUID = -4799963876349719507L;
	
	private Set<IHighwayCodeChangeListener> listeners = new LinkedHashSet<IHighwayCodeChangeListener>();
	private HighwayCodeFactory highwayCodeFactory;
	private HighwayCodeSelector highwayCodeSelector;
	
	public HighwayCodeAgentGUI(HighwayCodeFactory highwayCodeFactory)
	{
		this.highwayCodeFactory = highwayCodeFactory;
		
		setupFrame();
		setLayoutManager();
		addHighwayCodeSelector();
		addAcceptButton();
	}
	
	public void addListener(IHighwayCodeChangeListener listener)
	{
		listeners.add(listener);
	}
	
	public void removeListener(IHighwayCodeChangeListener listener)
	{
		listeners.remove(listener);
	}
	
	private void setupFrame()
	{
		setSize(HighwayCodeGUIPaintSettings.PREFERRED_WIDTH, HighwayCodeGUIPaintSettings.PREFERRED_HEIGHT);
	}
	
	private void setLayoutManager()
	{
		setLayout(new BorderLayout());
	}
	
	private void addHighwayCodeSelector()
	{
		add(highwayCodeSelector = new HighwayCodeSelector(highwayCodeFactory.getHighwayCodes()), BorderLayout.CENTER);
	}
	
	private void addAcceptButton()
	{
		JButton button = new JButton("Wybierz");
		button.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				IHighwayCode highwayCode = highwayCodeFactory.createHighwayCode(highwayCodeSelector.getSelectedItem().toString());
				
				if (highwayCode != null)
				{
					for (IHighwayCodeChangeListener listener : listeners)
					{
						listener.onHighwayCodeChanged(highwayCode);
					}
				}
			}
		});
		
		add(button, BorderLayout.PAGE_END);
	}
}
