package pl.edu.pw.elka.sag.gui.panels;

import java.awt.GridLayout;
import java.util.Collection;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HighwayCodeSelector extends JPanel
{
	private static final long serialVersionUID = 6079665437362414846L;
	
	private JComboBox comboBox;
	
	public HighwayCodeSelector(Collection<?> values)
	{
		setLayoutManager();
		addCaption();
		addSelectorComboBox(values);
	}
	
	private void setLayoutManager()
	{
		setLayout(new GridLayout(1, 1));
	}
	
	private void addCaption()
	{
		JLabel label = new JLabel("Highway code");
		
		add(label);
	}
	
	private void addSelectorComboBox(Collection<?> values)
	{
		comboBox = new JComboBox(values.toArray());
		
		add(comboBox);
	}
	
	public Object getSelectedItem()
	{
		return comboBox.getSelectedItem();
	}
}
