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
public class Eversion extends edu.benedictine.jump.SimPlayer
{
	
	public Eversion(SimulatorPanel sim, Scene scn, double xLoc, double yLoc, double xCng, double yCng)
	{
		super(sim,scn,xLoc,yLoc,xCng,yCng);
		//super(scn, 5, 8, xLoc, yLoc, xCng, yCng, scn.store.heroStatic, -16.0, 16.0, -14.0, 14.0);
		//xForce = new AdvancedForce(0.0, 0.0, 45.0, -240.0, 240.0, 1);
	}

	@Override
	public void setJumpType(SimVariableFloat gravity, 
							SimVariableFloat jumpPower, 
							SimVariableFloat xSpeed, 
							SimVariableFloat airDecel, 
							SimVariableChoice jumpCancelType)
	{
		final boolean jumpPressed = sim.getInputManager().isJumpPressed();
		if (!jumpPressed && yForce.value < 0.0)
			gravity.setValue(52);
		else
			gravity.setValue(26);
		jumpPower.setValue(600);
		xSpeed.setValue(270);
		airDecel.setValue(0);
		jumpCancelType.setValue("double");
	}

	@Override
	public void walk() 
	{
		xForce.upper = 270.0;
		xForce.lower = -270.0;
		xForce.accel = 30.0;
		final boolean leftPressed = sim.getInputManager().isLeftPressed();
		final boolean rightPressed = sim.getInputManager().isRightPressed();
		
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
			//eversion
			//xForce.upper = 0.0;
			//xForce.lower = 0.0;
			//xForce.decel = 30.0;
			
			xForce.upper = 0.0;
			xForce.lower = 0.0;
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
	}

	@Override
	public void jump()
	{
		//Eversion jump:
		final double gravity = 26.0;//?31.2?
		final double initialJump = 600.0;
		final double terminalVelocity = 600.0;
		final boolean jumpPressed = sim.getInputManager().isJumpPressed();

		if (yForce.value < terminalVelocity)
			yForce.value += gravity;

		//initial jump
		if ((jumpPressed) && (onGround))
		{
			yForce.value = -initialJump;
		}

		//apply gravity again if a is not down
		if ((!jumpPressed) && (yForce.value < 0.0))
		{
			yForce.value += gravity;
		}
	}
}
