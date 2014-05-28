/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.benedictine.jump.players;

import edu.benedictine.game.gui.Scene;
import edu.benedictine.jump.SimulatorPanel;

/**
 *
 * @author jvanderhyde
 */
public class Custom extends edu.benedictine.jump.SimPlayer
{
	public Custom(SimulatorPanel sim, Scene scn, double xLoc, double yLoc, double xCng, double yCng)
	{
		super(sim,scn,xLoc,yLoc,xCng,yCng);
	}
	
	@Override
	public void walk() 
	{
		final double xSpeed = sim.getXSpeed();
		final double airDecel = sim.getAirDecel();
		final boolean leftPressed = sim.getInputManager().isLeftPressed();
		final boolean rightPressed = sim.getInputManager().isRightPressed();
		xForce.upper = xSpeed;
		xForce.lower = -xSpeed;
		xForce.accel = 30.0;
		
		if (leftPressed)
		{
			if (xForce.value > xSpeed*airDecel)
				xForce.value = xSpeed*airDecel;
			xForce.accel = -xForce.accel;
			setFlipX(false);
		}
		if (rightPressed)
		{
			if (xForce.value < -xSpeed*airDecel)
				xForce.value = -xSpeed*airDecel;
			setFlipX(true);
		}
		
		if ((!leftPressed) && (!rightPressed))
		{
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
	}

	@Override
	public void jump()
	{
		final double gravity = sim.getGravity();
		final double initialJump = sim.getJumpPower();
		final boolean jumpPressed = sim.getInputManager().isJumpPressed();
		
		if (onGround)
			System.out.print(" on ground");
		else
			System.out.print(" not on ground");
		if ((jumpPressed) && (onGround))
		{
			yForce.value = -initialJump;
			System.out.print(" jump!");
		}

		if (!jumpPressed && yForce.value < 0.0)
		{
			if (sim.jumpCancelTypeIsFull())
				yForce.value = 0.0;
			if (sim.jumpCancelTypeIsDoubleGravity())
				yForce.value += gravity;

			/*
			if (cancelingAcc < 0.0)
			{
				cancelingAcc = -yForce.value*scn.mn.jumpCancel;
				System.out.println("cancela: "+cancelingAcc);
			}
			if (cancelingAcc >= 0.0)
			{
				System.out.println("before: "+yForce.value);
				yForce.value += cancelingAcc;
				if (yForce.value > 0.0)
					yForce.value = 0.0;
				System.out.println("after: "+yForce.value);
			}
			*/
		}

		if (!onGround)
		{
			yForce.value += gravity;
		}
	}
}
