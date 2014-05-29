//The manager of the UI elements.
//created by Joseph Rioux, 4 March 2013
//Modified by James Vanderhyde, 21 May 2014
//  Refactored

package edu.benedictine.jump;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Director
{
	private JPanel eastPanel;
	private JPanel southPanel;
	private final Font menlo;
	
	private ButtonGroup playerRadioGroup;
	private int numPlayerRadioButtons = 0;
	
	public Director()
	{
		menlo = new Font("Menlo", Font.PLAIN, 14);
		setUpSouthPanel();
		setUpEastPanel();
	}
	
	public void addSlider(final String label, final double minValue, final double maxValue,
			final int numPositions, final SimVariableFloat var)
	{
		double startValue = (minValue+maxValue)/2;
		if (var != null)
			startValue = var.getValue();
		
		final JLabel lab = new JLabel(label+startValue);
		lab.setFont(menlo);
		final int n = numPositions-1;
		
		final JSlider s = new JSlider(0, n);
		int startPosition = (int)Math.floor((startValue-minValue)/(maxValue-minValue)*n+0.5);
		if (startPosition<0)
			startPosition=0;
		if (startPosition>n)
			startPosition=n;
		s.setValue(startPosition);
		s.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent evt)
			{
				int position = s.getValue();
				double value;
				if (position==0)
					value=minValue;
				else if (position==n)
					value=maxValue;
				else
					value=s.getValue()/(double)(n)*(maxValue-minValue)+minValue;
				lab.setText(label+value);
				if (var != null)
					var.setValue(value);
			}
		});
		s.setOrientation(SwingConstants.HORIZONTAL);
		removeKeys(s);
		
		JPanel pan=new JPanel();
		pan.setLayout(new GridLayout(1,2));
		pan.add(s);
		pan.add(lab);
		southPanel.add(pan);
	}
	
	public void addRadioGroup(String label, 
			final SimVariableChoice var, Map<String,String> choices)
	{
		JLabel lab = new JLabel(label);
		lab.setFont(menlo);
		
		JPanel pan=new JPanel();
		pan.setLayout(new FlowLayout(FlowLayout.LEADING));
		pan.add(lab);

		ButtonGroup group = new ButtonGroup();
		ActionListener listener = new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				var.setValue(evt.getActionCommand());
			}
		};
		
		for (Map.Entry<String,String> ch:choices.entrySet())
		{
			JRadioButton b = new JRadioButton(ch.getValue());
			b.setFont(menlo);
			b.setActionCommand(ch.getKey());
			removeKeys(b);
			group.add(b);
			b.addActionListener(listener);
			pan.add(b);
			if (var.valueEquals(ch.getKey()))
				b.setSelected(true);
		}

		southPanel.add(pan);
	}

	private void setUpSouthPanel()
	{
		southPanel = new JPanel();
		southPanel.setLayout(new BoxLayout(southPanel,BoxLayout.Y_AXIS));
	}
	
	public void addPlayerClass(String label, final Class playerClass, final PlayerClassObserver obs)
	{
		JRadioButton button = new JRadioButton(label);
		button.setFont(menlo);
		button.setActionCommand(playerClass.getName());
		removeKeys(button);
		playerRadioGroup.add(button);
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				obs.playerClassChanged(playerClass);
			}
		});
		eastPanel.add(button);
		if (numPlayerRadioButtons==0)
			button.setSelected(true);
		numPlayerRadioButtons++;
	}
	
	private void setUpEastPanel()
	{
		playerRadioGroup = new ButtonGroup();
		eastPanel = new JPanel();
		eastPanel.setLayout(new BoxLayout(eastPanel,BoxLayout.Y_AXIS));
		JLabel lab=new JLabel("Player type:");
		lab.setFont(menlo);
		eastPanel.add(lab);
	}
	
	private void removeKeys(JComponent j)
	{
		javax.swing.Action doNothing = new javax.swing.AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				//do nothing
			}
		};
		j.getActionMap().put("doNothing",doNothing);
		int[] keyCodes = {KeyEvent.VK_UP,KeyEvent.VK_I,KeyEvent.VK_W,
						  KeyEvent.VK_DOWN, KeyEvent.VK_K,KeyEvent.VK_S,
						  KeyEvent.VK_LEFT,KeyEvent.VK_J,KeyEvent.VK_A,
						  KeyEvent.VK_RIGHT,KeyEvent.VK_L,KeyEvent.VK_D,
						  KeyEvent.VK_Z,KeyEvent.VK_X,KeyEvent.VK_SPACE};
		for (int code:keyCodes)
		{
			j.getInputMap().put(KeyStroke.getKeyStroke(code,0,true), "doNothing");
			j.getInputMap().put(KeyStroke.getKeyStroke(code,0,false), "doNothing");
		}
	}
	
	public JPanel getEastPanel()
	{
		return this.eastPanel;
	}
	
	public JPanel getSouthPanel()
	{
		return this.southPanel;
	}
	
	public static interface PlayerClassObserver
	{
		public void playerClassChanged(Class playerClass);
	}

}
