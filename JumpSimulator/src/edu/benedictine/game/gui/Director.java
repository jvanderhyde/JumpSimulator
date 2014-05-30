//created by Joseph Rioux, 4 March 2013

package edu.benedictine.game.gui;

import edu.benedictine.jump.GraphicsResourceJump;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
	public Scene currentScene;
	PixelCanvas gameCanvas;
	GraphicsResourceJump aniStore;
	Frame f;
	JPanel p;
	public JSlider s, s2, s3, s4, s5;
	public JRadioButton custom, mario, samus, zeetee;
	public JRadioButton noCancel, doubleGravity, fullCancel;
	JLabel sl, sl2, sl3, sl4, sl5;
	
	//simulator values
	public double gravity = 30.0, jumpPower = 600.0, xSpeed = 120.0, airDecel = 1.0, jumpCancel = 1.0;
	public String jumpType = "custom", cancelType = "double";
	
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

	public Director(final Frame f, PixelCanvas canvas, GraphicsResourceJump animations)
	{
		aniStore = animations;
		gameCanvas = canvas;
		//state = new SaveManager("saves/saveA.txt");
		//state.load();
		this.f = f;
		
		f.setResizable(false);
		p = new JPanel();
		JPanel p1 = new JPanel();
		JPanel p11 = new JPanel();
		JPanel p12 = new JPanel();
		JPanel p13 = new JPanel();
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel();
		p.setLayout(new BorderLayout());
		p1.setLayout(new GridLayout(1,2));
		p11.setLayout(new GridLayout(5,1));
		p12.setLayout(new GridLayout(5,1));
		p13.setLayout(new GridLayout(1,4));
		p2.setLayout(new BorderLayout());
		p3.setLayout(new GridLayout(5,1));
		
		//slider labels
		sl = new JLabel(""+gravity);
		sl.setFont(new Font("Menlo", Font.PLAIN, 14));
		sl2 = new JLabel(""+jumpPower);
		sl2.setFont(new Font("Menlo", Font.PLAIN, 14));
		sl3 = new JLabel(""+xSpeed);
		sl3.setFont(new Font("Menlo", Font.PLAIN, 14));
		sl4 = new JLabel(""+airDecel);
		sl4.setFont(new Font("Menlo", Font.PLAIN, 14));
		sl5 = new JLabel(" Jump Cancel:");
		sl5.setFont(new Font("Menlo", Font.PLAIN, 14));
		
		//sliders
		s = new JSlider();
		s.setMinimum(1);
		s.setMaximum(100);
		s.addChangeListener(new GravityListener());
		s.setValue(25);
		s.setOrientation(SwingConstants.HORIZONTAL);
		removeKeys(s);
		
		s2 = new JSlider();
		s2.setMinimum(1);
		s2.setMaximum(10);
		s2.addChangeListener(new JumpListener());
		s2.setValue(6);
		s2.setOrientation(SwingConstants.HORIZONTAL);
		removeKeys(s2);
		
		s3 = new JSlider();
		s3.setMinimum(1);
		s3.setMaximum(20);
		s3.addChangeListener(new XSpeedListener());
		s3.setValue(10);
		s3.setOrientation(SwingConstants.HORIZONTAL);
		removeKeys(s3);
		
		s4 = new JSlider();
		s4.setMinimum(0);
		s4.setMaximum(10);
		s4.addChangeListener(new XAccelListener());
		s4.setValue(5);
		s4.setOrientation(SwingConstants.HORIZONTAL);
		removeKeys(s4);
		
		/*
		s5 = new JSlider();
		s5.setMinimum(0);
		s5.setMaximum(10);
		s5.addChangeListener(new JumpCancelListener());
		s5.setValue(5);
		s5.setOrientation(SwingConstants.HORIZONTAL);
		removeKeys(s5);
		*/
		
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
	    
	    //jump cancel
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
	    
	    group = new ButtonGroup();
	    group.add(noCancel);
	    group.add(doubleGravity);
	    group.add(fullCancel);
	    
	    CancelRadioListener cListen = new CancelRadioListener();
	    noCancel.addActionListener(cListen);
	    doubleGravity.addActionListener(cListen);
	    fullCancel.addActionListener(cListen);
		
		p.setLayout(new BorderLayout());
		p13.add(sl5);
		p13.add(noCancel);
		p13.add(doubleGravity);
		p13.add(fullCancel);
		p11.add(s);
		p11.add(s2);
		p11.add(s4);
		p11.add(s3);
		p11.add(p13);
		p12.add(sl);
		p12.add(sl2);
		p12.add(sl4);
		p12.add(sl3);
		p1.add(p11);
		p1.add(p12);
		p2.add(gameCanvas, BorderLayout.CENTER);
		p3.add(custom);
		p3.add(mario);
		p3.add(samus);
		p3.add(zeetee);
		p.add(p1, BorderLayout.SOUTH);
		p.add(p2, BorderLayout.CENTER);
		p.add(p3, BorderLayout.EAST);
		f.add(p);
		f.pack();
		f.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent evt)
			{
				if (currentScene != null)
					currentScene.stop();
				f.setVisible(false);
				f.dispose();
			}
		});
        f.setVisible(true);
		
		//currentScene = new Scene(this, "first", "", gameCanvas, aniStore, 0.0, 0.0);
		//currentScene = new Scene(this, "Metroid", "", gameCanvas, aniStore, 432.0, 192.0);
		//currentScene = new Scene(this, "Playground", "", gameCanvas, aniStore, 0.0, 0.0);
		//currentScene = new Scene(this, "Mario", "", gameCanvas, aniStore, -512.0, 0.0);
        currentScene = new Scene(this, "pictest", "", gameCanvas, aniStore, 0.0, 0.0);
		currentScene.start();
	}
	
	public void removeKeys(JComponent j)
	{
		j.getInputMap().put(KeyStroke.getKeyStroke("UP"), "none");
		j.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "none");
		j.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "none");
		j.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "none");
		j.getInputMap().put(KeyStroke.getKeyStroke("Z"), "none");
	}
	
	public void changeScene(String scn, String prev, double nX, double nY)
	{
		currentScene = new Scene(this, scn, prev, gameCanvas, aniStore, nX, nY);
		currentScene.start();
	}
}
