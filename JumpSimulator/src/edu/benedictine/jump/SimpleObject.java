//Temporary replacement for Player from game package
//Created by James Vanderhyde, 28 May 2014

package edu.benedictine.jump;

import edu.benedictine.game.util.AdvancedForce;
import edu.benedictine.game.util.LinearForce;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class SimpleObject
{
	protected double x, y;
	protected double xSpeed, ySpeed;
	protected LinearForce xForce, yForce;
	protected final int floor;
	
	protected boolean onGround;
	
	public SimpleObject(int floor)
	{
		this.floor = floor;
		x = 100;
		y = 300;
		xSpeed = 0;
		ySpeed = 0;
		yForce = new AdvancedForce(0.0, 0.0, 0.0, -600.0, 600.0, 1);
		xForce = new AdvancedForce(0.0, 0.0, 60.0, -120.0, 120.0, 1);
		onGround = false;
	}
	
	private Rectangle getBoundingBox()
	{
		int width = 24;
		int height = 50;
		return new Rectangle((int)x-width/2,(int)y-1*height/4,width,height);
	}
	
	public void update()
	{
		//movement
		xForce.updateValue();
		yForce.updateValue();
		xSpeed = xForce.value;
		ySpeed = yForce.value;
		move();
		
		//collision detection and reaction
		onGround = false;
		Rectangle box=this.getBoundingBox();
		int pastFloor = box.y+box.height - floor;
		System.out.print(" "+pastFloor);
		if (pastFloor>=0)
		{
			y -= pastFloor;
			onGround = true;
		}
	}
	
	public void paint(Graphics g)
	{
		g.setColor(Color.LIGHT_GRAY);
		Rectangle box=this.getBoundingBox();
		g.drawRect(box.x, box.y, box.width, box.height);
		g.drawOval((int)x-5, (int)y-5, 10, 10);
	}
	
	private void move()
	{
		double xChange;
		double yChange;
		xChange = xSpeed;
		yChange = ySpeed;
		double fps=1000.0/16;
		
		double actualSpeedX = (xChange/fps);
		double actualSpeedY = (yChange/fps);
		x+=actualSpeedX;
		y+=actualSpeedY;
	}
	
	protected void setFlipX(boolean b)
	{
		
	}
		
}
