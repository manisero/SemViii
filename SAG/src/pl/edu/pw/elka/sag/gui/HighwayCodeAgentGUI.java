package pl.edu.pw.elka.sag.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;

import pl.edu.pw.elka.sag.agents.highwaycode.IHighwayCodeChangeListener;
import pl.edu.pw.elka.sag.gui.constants.HighwayCodeGUIPaintSettings;
import pl.edu.pw.elka.sag.gui.panels.HighwayCodeSelector;

public class HighwayCodeAgentGUI extends JFrame
{
	private static final long serialVersionUID = -4799963876349719507L;
	
	private Set<IHighwayCodeChangeListener> listeners = new LinkedHashSet<IHighwayCodeChangeListener>();
	private HighwayCodeSelector highwayCodeSelector;
	
	public HighwayCodeAgentGUI(Collection<?> highwayCodes)
	{
		setupFrame();
		setLayoutManager();
		addHighwayCodeSelector(highwayCodes);
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
	
	private void addHighwayCodeSelector(Collection<?> highwayCodes)
	{
		add(highwayCodeSelector = new HighwayCodeSelector(highwayCodes), BorderLayout.CENTER);
	}
	
	private void addAcceptButton()
	{
		JButton button = new JButton("Wybierz");
		button.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				for (IHighwayCodeChangeListener listener : listeners)
				{
					listener.onHighwayCodeChanged(highwayCodeSelector.getSelectedItem().toString());
				}
			}
		});
		
		add(button, BorderLayout.PAGE_END);
	}
}
