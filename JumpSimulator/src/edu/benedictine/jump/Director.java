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
	
	private JSlider s, s2, s3, s4, s5;
	private JRadioButton custom, mario, samus, zeetee;
	private JRadioButton noCancel, doubleGravity, fullCancel;
	private JLabel sl, sl2, sl3, sl4, sl5;
	private final Font menlo;

	//simulator values
	public double jumpCancel = 1.0;
	public String jumpType = "custom", cancelType = "double";
	
	public Director()
	{
		menlo = new Font("Menlo", Font.PLAIN, 14);
		setUpSouthPanel();
		setUpEastPanel();
	}
	
	public void addSlider(final String label, final double minValue, final double maxValue,
			double startValue, final int numPositions, final SimValueObserver obs)
	{
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
				if (obs != null)
					obs.valueChanged(value);
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
	
	public void addRadioGroup(String label, String startValue, String... choices)
	{
		JLabel lab = new JLabel(label);
		lab.setFont(menlo);
		
		JPanel pan=new JPanel();
		pan.setLayout(new FlowLayout(FlowLayout.LEADING));
		pan.add(lab);

		ButtonGroup group = new ButtonGroup();
		CancelRadioListener cListen = new CancelRadioListener();
		
		for (String ch:choices)
		{
			JRadioButton b = new JRadioButton(ch);
			b.setActionCommand(ch);
			removeKeys(b);
			group.add(b);
			b.addActionListener(cListen);
			pan.add(b);
			if (ch.equals(startValue))
				b.setSelected(true);
		}

		southPanel.add(pan);
	}

	private void setUpSouthPanel()
	{
		southPanel = new JPanel();
		southPanel.setLayout(new BoxLayout(southPanel,BoxLayout.Y_AXIS));
	}
	
	private void setUpEastPanel()
	{
		eastPanel = new JPanel();
		eastPanel.setLayout(new GridLayout(5,1));

		//radio buttons
	    custom = new JRadioButton("Custom");
	    custom.setActionCommand("custom");
	    custom.setSelected(true); 
	    mario = new JRadioButton("Mario");
	    mario.setActionCommand("mario");
	    samus = new JRadioButton("Samus");
	    samus.setActionCommand("samus");
	    zeetee = new JRadioButton("Zee Tee");
	    zeetee.setActionCommand("zeetee");
	    removeKeys(custom);
	    removeKeys(mario);
	    removeKeys(samus);
	    removeKeys(zeetee);

	    //Group the radio buttons.
	    ButtonGroup group = new ButtonGroup();
	    group.add(custom);
	    group.add(mario);
	    group.add(samus);
	    group.add(zeetee);

	    //add listeners to the radio buttons.
	    RadioListener listen = new RadioListener();
	    custom.addActionListener(listen);
	    mario.addActionListener(listen);
	    samus.addActionListener(listen);
	    zeetee.addActionListener(listen);
	    
		eastPanel.add(custom);
		eastPanel.add(mario);
		eastPanel.add(samus);
		eastPanel.add(zeetee);
	}
	
	private void removeKeys(JComponent j)
	{
		j.getInputMap().put(KeyStroke.getKeyStroke("UP"), "none");
		j.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "none");
		j.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "none");
		j.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "none");
		j.getInputMap().put(KeyStroke.getKeyStroke("Z"), "none");
	}
	
	public JPanel getEastPanel()
	{
		return this.eastPanel;
	}
	
	public JPanel getSouthPanel()
	{
		return this.southPanel;
	}

	public static interface SimValueObserver
	{
		public void valueChanged(double newValue);
	}
	
	class RadioListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			jumpType = e.getActionCommand();
		}
	}
	
	class CancelRadioListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			cancelType = e.getActionCommand();
		}
	}

}
