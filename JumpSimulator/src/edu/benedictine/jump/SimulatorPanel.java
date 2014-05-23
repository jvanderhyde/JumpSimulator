//The panel that contains the simulation and GUI elements
//Created by James Vanderhyde, 21 May 2014

package edu.benedictine.jump;

import edu.benedictine.game.gui.GamePanelFixedFPS;
import edu.benedictine.game.input.KeyHandler;
import edu.benedictine.jump.players.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;
import javax.swing.JPanel;

public class SimulatorPanel extends GamePanelFixedFPS
{
	private Director director;
	private KeyHandler keyHandler;
	
	//simulator values
	private SimVariableFloat gravity = new SimVariableFloat();
	private SimVariableFloat jumpPower = new SimVariableFloat();
	private SimVariableFloat xSpeed = new SimVariableFloat();
	private SimVariableFloat airDecel = new SimVariableFloat();
	private SimVariableChoice jumpCancelType = new SimVariableChoice("no","double","full");
	
	private void setDefaultValuesForVariables()
	{
		gravity.setValue(30.0);
		jumpPower.setValue(600.0);
		xSpeed.setValue(120.0);
		airDecel.setValue(50.0);
		jumpCancelType.setValue("full");
	}
	
	//game state variables
	private Point ballLoc,ballVel;
	
	public SimulatorPanel()
	{
		//Set up game display
		vLeft = 0;
		vTop = 0;
		vRight = 1024;
		vBottom = 512;
		JPanel gameCanvas = new JPanel()
		{
			@Override
			public void paintComponent(Graphics g)
			{
				paintGameCanvas(g);
			}
		};
		gameCanvas.setPreferredSize(new Dimension(vRight-vLeft,vBottom-vTop));
		this.setPreferredSize(null);
		
		setDefaultValuesForVariables();
		
		//Set up user interface
		director = new Director();
		director.addSlider("Gravity: ", 1, 100, 100, gravity);
		director.addSlider("Jump Power: ", 120, 1200, 10, jumpPower);
		director.addSlider("Horizontal Inertia: ", 0, 100, 11, airDecel);
		director.addSlider("Max Horizontal Speed: ", 30, 600, 20, xSpeed);
		HashMap<String,String> jumpCancelLabelMap = new HashMap<String,String>();
		jumpCancelLabelMap.put("no", "No Canceling");
		jumpCancelLabelMap.put("double", "Double Gravity");
		jumpCancelLabelMap.put("full", "Full Canceling");
		director.addRadioGroup(" Jump Cancel:", jumpCancelType, jumpCancelLabelMap);
		
		Director.PlayerClassObserver obs = new Director.PlayerClassObserver()
		{
			public void playerClassChanged(Class playerClass)
			{
				System.out.println(playerClass.getName());
			}
		};
		director.addPlayerClass("Custom",Custom.class,obs);
		director.addPlayerClass("Mario",Mario.class,obs);
		director.addPlayerClass("Samus",Samus.class,obs);
		director.addPlayerClass("Zee Tee",Eversion.class,obs);
		
		//Set up game input
		keyHandler = new KeyHandler();
		keyHandler.captureAllKeyEvents();
		//gameCanvas.addKeyListener(keyHandler);
		
		//Add everything to the main panel
		this.setLayout(new BorderLayout());
		this.add(gameCanvas,BorderLayout.CENTER);
		this.add(director.getSouthPanel(),BorderLayout.SOUTH);
		this.add(director.getEastPanel(),BorderLayout.EAST);
	}
	
	private void paintGameCanvas(Graphics g)
	{
		g.setColor(Color.black);
		g.fillRect(vLeft, vTop, vRight-vLeft, vBottom-vTop);
		if (ballLoc != null)
		{
			//change color (test a choice variable)
			if (jumpCancelType.valueEquals("no"))
				g.setColor(Color.green);
			else if (jumpCancelType.valueEquals("full"))
				g.setColor(Color.blue);
			else
				g.setColor(Color.pink);

			g.fillOval(ballLoc.x-10,ballLoc.y-10,20,20);
		}
	}

	@Override
	public void startGame()
	{
		ballLoc = new Point(100,100);
		ballVel = new Point(8,5);
	}

	@Override
	public void updateGame()
	{
		if (ballLoc != null)
		{
			//apply gravity (test a slider variable)
			ballVel.y += (int)(gravity.getValue()/99);
			
			//move ball
			ballLoc.x += ballVel.x;
			ballLoc.y += ballVel.y;
			if (ballLoc.x+10>vRight || ballLoc.x-10<0)
				ballVel.x=-ballVel.x;
			if (ballLoc.y+10>vBottom || ballLoc.y-10<0)
				ballVel.y=-ballVel.y;
			
			//let the keys control the speed
			if (keyHandler.isDownPressed() && (Math.abs(ballVel.y)>2))
			{
				ballVel.x = (2*ballVel.x)/3;
				ballVel.y = (2*ballVel.y)/3;
			}
			if (keyHandler.isUpPressed() && (Math.abs(ballVel.x)<50))
			{
				ballVel.x = (3*ballVel.x)/2;
				ballVel.y = (3*ballVel.y)/2;
			}
		}
	}

	@Override
	public void destroyGame()
	{
	}

	@Override
	public void paintGame(Graphics g)
	{
		//do nothing, because it's handled by the canvas
	}
	
}
