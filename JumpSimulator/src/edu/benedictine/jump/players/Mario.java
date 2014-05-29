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
public class Mario extends edu.benedictine.jump.SimPlayer
{
	
	public Mario(SimulatorPanel sim, Scene scn, double xLoc, double yLoc, double xCng, double yCng)
	{
		super(sim,scn,xLoc,yLoc,xCng,yCng);
	}

	@Override
	public void getJumpType(SimVariableFloat gravity, 
							SimVariableFloat jumpPower, 
							SimVariableFloat xSpeed, 
							SimVariableFloat airDecel, 
							SimVariableChoice jumpCancelType)
	{
		final boolean jumpPressed = sim.getInputManager().isJumpPressed();
		if (!jumpPressed && yForce.value < 0.0)
			gravity.setValue(120.0*112/256);
		else
			gravity.setValue(120.0*32/256);
		jumpPower.setValue(485);
		xSpeed.setValue(180);
		airDecel.setValue(5);
		jumpCancelType.setValue("double");
	}

	@Override
	public void walk() 
	{
		//mario
		//running
		//double up = 300.0;
		//double down = -300.0;
		//normal
		//double up = 180.0;
		//double down = -180.0;
		//double acc = 120.0;

		xForce.upper = 180.0;
		xForce.lower = -180.0;
		xForce.accel = 5.0;
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
			//mario
			if (onGround)
			{
				xForce.upper = 0.0;
				xForce.lower = 0.0;
				xForce.decel = 5.0;
			}
		}
	}

	@Override
	public void jump()
	{
		final double gravity = 120.0;//?31.2?
		int gravLow = 32;
		//if run
		final double initialJump = 485.0;
		//else
		//initialJump = 480.0;
		final double terminalVelocity = 480.0;
		final boolean jumpPressed = sim.getInputManager().isJumpPressed();

		if ((jumpPressed) && (onGround))
		{
			yForce.value = -initialJump;
		}

		if (!jumpPressed || yForce.value >= 0)
		{
			gravLow = 112;
		}

		if (!onGround)
		{
			if (yForce.value < terminalVelocity)
				yForce.value += gravity*(gravLow/256.0);
		}
	}

}
