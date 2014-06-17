//Default player implementation (Samus-like)
//Created by James Vanderhyde, 17 June 2014
//  Copied from earlier code

package edu.benedictine.game.engine;

import edu.benedictine.game.gui.Scene;
import edu.benedictine.game.input.InputManager;
import java.awt.Color;
import java.awt.Graphics;

public class DefaultPlayer extends GameObject
{
	private boolean jumpPressed, leftPressed, rightPressed;
	private InputManager input;
	private int wrapper;
	
    public DefaultPlayer(InputManager input, Scene scn, double xLoc, double yLoc)
    {
		super(scn, 5, 8, xLoc, yLoc, 0.0, 0.0, null, -12.0, 12.0, -12.0, 12.0);
		this.input = input;
		forceOn = true;
		halfWidth = 12;
		halfHeight = 12;
    }
	
	@Override
	public void update()
	{
		this.jumpPressed = input.isJumpPressed();
		this.leftPressed = input.isLeftPressed();
		this.rightPressed = input.isRightPressed();
		
		walk();
		jump();
		
		super.update();
	}

	public void walk()
	{
		xForce.upper = 180.0;
		xForce.lower = -180.0;
		xForce.accel = 120.0;
		final double airDecel = 0;
		final double xSpeed = 180.0;
		
		if (leftPressed)
		{
			xForce.accel = -xForce.accel;
		}
		if (rightPressed)
		{
		}
		
		if ((!leftPressed) && (!rightPressed))
		{
			//metroid
			xForce.upper = xSpeed*airDecel;
			xForce.lower = -xSpeed*airDecel;
			xForce.accel = 0.0;
			xForce.decel = 30.0;

			//basic
			if (onGround)
			{
				xForce.upper = 0.0;
				xForce.lower = 0.0;
				xForce.decel = 30.0;
			}
		}

		if (leftPressed)
			setFlipX(false);
		if (rightPressed)
			setFlipX(true);
	}
	
	public void jump()
	{
		final double gravity = 120.0;//?31.2?
		final double initialJump = 480.0;
		final double terminalVelocity = 600.0;

		if ((jumpPressed) && (this.onGround))
		{
			yForce.value = -initialJump;
			wrapper = 0;
		}

		if (!onGround)
			wrapper += 24;

		if (!jumpPressed && yForce.value < 0.0)
		{
			if (wrapper >= 256)
				yForce.value = 0.0;
		}

		if (wrapper >= 256)
			wrapper -= 255;

		if (!onGround)
		{
			if (yForce.value < terminalVelocity)
				yForce.value += gravity*(24.0/256.0);
		}
	}
	
	@Override
	public void draw(Graphics g, int x, int y)
	{
		g.setColor(Color.RED);
		int size = 24;
		g.drawOval(x, y, size, size);
	}

}