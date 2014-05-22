//The manager of the UI elements.
//created by Joseph Rioux, 4 March 2013
//Modified by James Vanderhyde, 21 May 2014
//  Refactored

package edu.benedictine.jump;

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
	private JPanel sliderPanel;
	
	private JSlider s, s2, s3, s4, s5;
	private JRadioButton custom, mario, samus, zeetee;
	private JRadioButton noCancel, doubleGravity, fullCancel;
	private JLabel sl, sl2, sl3, sl4, sl5;

	//simulator values
	public double gravity = 30.0, jumpPower = 600.0, xSpeed = 120.0, airDecel = 1.0, jumpCancel = 1.0;
	public String jumpType = "custom", cancelType = "double";
	
	public Director()
	{
		setUpSouthPanel();
		setUpEastPanel();
	}
	
	public void addSlider(final String label, final double minValue, final double maxValue,
			double startValue, final int numPositions, final SimValueObserver obs)
	{
		final Font menlo = new Font("Menlo", Font.PLAIN, 14);
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
		sliderPanel.add(pan);
	}

	private void setUpSouthPanel()
	{
		southPanel = new JPanel();
		sliderPanel = new JPanel();
		sliderPanel.setLayout(new BoxLayout(sliderPanel,BoxLayout.Y_AXIS));
		
		this.addSlider("Gravity: ", 1, 100, 25, 100, null);
		this.addSlider("Jump Power: ", 1, 10, 6, 10, null);
		this.addSlider("Max Horizontal Speed: ", 1, 20, 10, 20, null);
		this.addSlider("Horizontal Inertia: ", 0, 10, 5, 11, null);
		
		JPanel p13 = new JPanel();
		southPanel.setLayout(new BoxLayout(southPanel,BoxLayout.Y_AXIS));
		p13.setLayout(new GridLayout(1,4));
		
		//jump cancel
		sl5 = new JLabel(" Jump Cancel:");
		sl5.setFont(new Font("Menlo", Font.PLAIN, 14));
		noCancel = new JRadioButton("No Canceling");
		noCancel.setActionCommand("no");
		doubleGravity = new JRadioButton("Double Gravity");
		doubleGravity.setActionCommand("double");
		doubleGravity.setSelected(true);
		fullCancel = new JRadioButton("Full Canceling");
		fullCancel.setActionCommand("full");
		removeKeys(noCancel);
		removeKeys(doubleGravity);
		removeKeys(fullCancel);
		
		ButtonGroup group = new ButtonGroup();
		group.add(noCancel);
		group.add(doubleGravity);
		group.add(fullCancel);
		
		CancelRadioListener cListen = new CancelRadioListener();
		noCancel.addActionListener(cListen);
		doubleGravity.addActionListener(cListen);
		fullCancel.addActionListener(cListen);
		
		p13.add(sl5);
		p13.add(noCancel);
		p13.add(doubleGravity);
		p13.add(fullCancel);
		southPanel.add(sliderPanel);
		southPanel.add(p13);
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
	
	class GravityListener implements ChangeListener
	{
		@Override
		public void stateChanged(ChangeEvent evt) 
		{
			gravity = ((JSlider)evt.getSource()).getValue()*1.0;
			sl.setText("Gravity: "+gravity);
		}
	}
	
	class JumpListener implements ChangeListener
	{
		@Override
		public void stateChanged(ChangeEvent evt) 
		{
			jumpPower = ((JSlider)evt.getSource()).getValue()*120.0;
			sl2.setText("Jump Power: "+jumpPower);
		}
	}
	
	class XSpeedListener implements ChangeListener
	{
		@Override
		public void stateChanged(ChangeEvent evt) 
		{
			xSpeed = ((JSlider)evt.getSource()).getValue()*30.0;
			sl3.setText("Max Horizontal Speed: "+xSpeed);
		}
	}
	
	class XAccelListener implements ChangeListener
	{
		@Override
		public void stateChanged(ChangeEvent evt) 
		{
			JSlider sTemp = ((JSlider)evt.getSource());
			airDecel = (double)sTemp.getValue()/(double)sTemp.getMaximum();
			sl4.setText("Horizontal Inertia: "+(int)(airDecel*100)+"%");
		}
	}
	
	class JumpCancelListener implements ChangeListener
	{
		@Override
		public void stateChanged(ChangeEvent evt) 
		{
			JSlider sTemp = ((JSlider)evt.getSource());
			jumpCancel = (double)sTemp.getValue()/(double)sTemp.getMaximum();
			//sl5.setText("Jump Cancel: "+(int)(jumpCancel*100)+"%");
		}
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
