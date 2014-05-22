//The panel that contains the simulation and GUI elements
//Created by James Vanderhyde, 21 May 2014

package edu.benedictine.jump;

import edu.benedictine.game.gui.GamePanelFixedFPS;
import edu.benedictine.game.input.KeyHandler;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import javax.swing.JPanel;

public class SimulatorPanel extends GamePanelFixedFPS
{
	private Director director;
	private KeyHandler keyHandler;
	
	//simulator values
	private double gravity = 30.0, jumpPower = 600.0, xSpeed = 120.0, airDecel = 50.0;
	
	//game state variables
	private Point ballLoc,ballVel;
	
	public SimulatorPanel()
	{
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
		
		director = new Director();
		director.addSlider("Gravity: ", 1, 100, gravity, 100, new Director.SimValueObserver()
			{public void valueChanged(double newValue){ gravity=newValue; }});
		director.addSlider("Jump Power: ", 120, 1200, jumpPower, 10, new Director.SimValueObserver()
			{public void valueChanged(double newValue){ jumpPower=newValue; }});
		director.addSlider("Horizontal Inertia: ", 0, 100, airDecel, 11, new Director.SimValueObserver()
			{public void valueChanged(double newValue){ airDecel=newValue; }});
		director.addSlider("Max Horizontal Speed: ", 30, 600, xSpeed, 20, new Director.SimValueObserver()
			{public void valueChanged(double newValue){ xSpeed=newValue; }});
		
		keyHandler = new KeyHandler();
		keyHandler.captureAllKeyEvents();
		//gameCanvas.addKeyListener(keyHandler);
		
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
			g.setColor(Color.green);
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
			//apply gravity
			ballVel.y += (int)(gravity/99);
			
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
