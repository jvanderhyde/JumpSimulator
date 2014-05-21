//The panel that contains the simulation and GUI elements
//Created by James Vanderhyde, 21 May 2014

package edu.benedictine.jump;

import edu.benedictine.game.gui.GamePanel;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import javax.swing.JPanel;

public class SimulatorPanel extends GamePanel
{
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
		
		this.setLayout(new BorderLayout());
		this.add(gameCanvas,BorderLayout.CENTER);
		this.add(new Button("Click me"),BorderLayout.SOUTH);
		this.add(new Button("Click me"),BorderLayout.EAST);
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
		else
		{
			g.setColor(Color.green);
			g.drawString("Click to start", 100, 100);
		}
	}

	@Override
	public void startGame()
	{
		ballLoc = new Point(100,100);
		ballVel = new Point(8,5);
	}

	@Override
	public void updateGame(long timeSinceLastUpdate)
	{
		if (ballLoc != null)
		{
			ballLoc.x += ballVel.x;
			ballLoc.y += ballVel.y;
			if (ballLoc.x+10>vRight || ballLoc.x-10<0)
				ballVel.x=-ballVel.x;
			if (ballLoc.y+10>vBottom || ballLoc.y-10<0)
				ballVel.y=-ballVel.y;
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
