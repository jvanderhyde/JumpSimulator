/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.benedictine.jump.players;

import edu.benedictine.game.gui.Scene;
import edu.benedictine.jump.SimVariableChoice;
import edu.benedictine.jump.SimVariableFloat;
import edu.benedictine.jump.SimulatorPanel;

/**
 *
 * @author jvanderhyde
 */
public class Samus extends edu.benedictine.jump.SimPlayer
{
	int wrapper;
	boolean samusFlipping = false;
	
	public Samus(SimulatorPanel sim, Scene scn, double xLoc, double yLoc, double xCng, double yCng)
	{
		super(sim,scn,xLoc,yLoc,xCng,yCng);
	}
	
	@Override
	public void setJumpType(SimVariableFloat gravity, 
							SimVariableFloat jumpPower, 
							SimVariableFloat xSpeed, 
							SimVariableFloat airDecel, 
							SimVariableChoice jumpCancelType)
	{
		gravity.setValue(120);
		jumpPower.setValue(480);
		xSpeed.setValue(180);
		if (samusFlipping)
			airDecel.setValue(120);
		else
			airDecel.setValue(0);
		jumpCancelType.setValue("full");
	}

	@Override
	public void walk() 
	{
		//xForce.upper = 240.0;
		//down = -240.0;
		xForce.upper = 180.0;
		xForce.lower = -180.0;
		xForce.accel = 120.0;
		final double airDecel = 0;
		final double xSpeed = 180.0;
		final boolean leftPressed = sim.getInputManager().isRightPressed();
		final boolean rightPressed = sim.getInputManager().isLeftPressed();
		
		if (leftPressed)
		{
			xForce.accel = -xForce.accel;
			setFlipX(false);
		}
		if (rightPressed)
		{
			setFlipX(true);
		}
		
		if ((!leftPressed) && (!rightPressed))
		{
			//metroid
			xForce.upper = xSpeed*airDecel;
			xForce.lower = -xSpeed*airDecel;
			xForce.accel = 0.0;
			xForce.decel = 30.0;
			/*if (!samusFlipping)
			{
				xForce.upper = 0.0;
				xForce.lower = 0.0;
				xForce.decel = 30.0;
			}
			else
			{
				xForce.upper = up;
				xForce.lower = down;
				xForce.decel = 0.0;
			}*/

			//basic
			if (onGround)
			{
				xForce.upper = 0.0;
				xForce.lower = 0.0;
				xForce.decel = 30.0;
			}
		}
	}

	@Override
	public void jump()
	{
		final double gravity = 120.0;//?31.2?
		final double initialJump = 480.0;
		final double terminalVelocity = 600.0;
		final boolean jumpPressed = sim.getInputManager().isJumpPressed();

		if (onGround)
			samusFlipping = false;

		if ((jumpPressed) && (onGround))
		{
			yForce.value = -initialJump;
			wrapper = 0;
			if (Math.abs(xForce.value) == 180.0)
				samusFlipping = true;
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
		System.out.println(wrapper);

		if (!onGround)
		{
			if (yForce.value < terminalVelocity)
				yForce.value += gravity*(24.0/256.0);
		}
	}

}
