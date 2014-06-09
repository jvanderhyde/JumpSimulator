package edu.benedictine.game.engine;

import edu.benedictine.game.gui.Scene;
import edu.benedictine.game.gui.Main;

//Created by Joseph Rioux, 11 September 2013

public class Camera extends WorldObject
{	
	double targetX, targetY, offsetX, offsetY, targetOffsetX, priorTarget, offSpeed;
	boolean onPlayer, targetLocked;
	WorldObject target;
	int change = 0;
	int screenShakeX, magnitudeX, screenShakeY, magnitudeY;
	double leftBound=-512, topBound=-320, rightBound=512, bottomBound=320;
	
	public Camera(Scene scn, double x, double y, boolean on) 
	{
		super(scn, 15, x, y, null);
		this.x = x;
		this.y = y;
		onPlayer = on;
		halfWidth = 8;
		halfHeight = 8;
	}
	
	public void setBounds(double l, double r, double t, double b)
	{
		leftBound = l;
		rightBound = r;
		topBound = t;
		bottomBound = b;
	}
	
	public void update()
	{
		//targetOffsetX = scn.player.direction*128.0;
		//if (targetOffsetX != priorTarget)
		//	change--;
		//if (change <= 0)
		//	track();
		
		if (screenShakeX > 0)
			offsetX = (int)((Math.random()*magnitudeX)-(magnitudeX/2.0));
		else
			offsetX = 0.0;
		screenShakeX--;
		
		if (screenShakeY > 0)
			offsetY = (int)((Math.random()*magnitudeY)-(magnitudeY/2.0));
		else
			offsetY = 0.0;
		screenShakeY--;
		
		if (targetLocked)
		{
			x = target.x;
			y = target.y;
		}
		super.update();
	}
	
	public void track()
	{
		if (onPlayer)
		{
			if (offsetY < -128.0)
			{
				offsetY = -128.0;
			}
			if (offsetY > 128.0)
			{
				offsetY = 128.0;
			}
			if (offsetX < targetOffsetX)
			{
				if (offSpeed < 4.0)
					offSpeed += 0.05;
				offsetX += offSpeed;
				if (offsetX >= targetOffsetX)
				{
					offsetX = targetOffsetX;
					priorTarget = targetOffsetX;
					change = 32;
					offSpeed = 0.0;
				}
			}
			else if (offsetX > targetOffsetX)
			{
				if (offSpeed > -4.0)
					offSpeed -= 0.05;
				offsetX += offSpeed;
				if (offsetX <= targetOffsetX)
				{
					offsetX = targetOffsetX;
					priorTarget = targetOffsetX;
					change = 32;
					offSpeed = 0.0;
				}
			}
		}
	}
	
	public void shake(int frames, int mag)
	{
		screenShakeX = frames;
		magnitudeX = mag;
		screenShakeY = frames;
		magnitudeY = mag;
	}
	
	public void shake(int framesX, int framesY, int magX, int magY)
	{
		screenShakeX = framesX;
		magnitudeX = magX;
		screenShakeY = framesY;
		magnitudeY = magY;
	}
	
	public void move()
	{
		//x = scn.player.x+offsetX;
		//y = scn.player.y+offsetY;
		
		//modify these values in the level definition
	
		if (x < leftBound+Main.CAN_WIDTH/2.0)
			x = leftBound+Main.CAN_WIDTH/2.0;
		if (x > rightBound-Main.CAN_WIDTH/2.0)
			x = rightBound-Main.CAN_WIDTH/2.0;
		if (y < topBound+Main.CAN_HEIGHT/2.0)
			y = topBound+Main.CAN_HEIGHT/2.0;
		if (y > bottomBound-Main.CAN_HEIGHT/2.0)
			y = bottomBound-Main.CAN_HEIGHT/2.0;
		
		
		//+offsetX;
		//y = scn.player.y-320.0;
		//if (y > scn.player.y+64.0)
		//	y = scn.player.y+64.0;
		//if (y < scn.player.y-64.0)
		//	y = scn.player.y-64.0;
	}

	public double getX() 
	{
		return x;
	}

	public double getY() 
	{
		return y;
	}
	
}
