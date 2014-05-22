/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.benedictine.jump.players;

import edu.benedictine.game.gui.Scene;

/**
 *
 * @author jvanderhyde
 */
public class Mario extends edu.benedictine.game.engine.Player
{
	int gravLow = 32;
	
	public Mario(Scene scn, double xLoc, double yLoc, double xCng, double yCng)
	{
		super(scn,xLoc,yLoc,xCng,yCng);
	}

	@Override
	public void setJumpType()
	{
		if (onGround)
		{
			scn.mn.s.setValue(15);
		}
		else
		{
			if ((!scn.a) || (yForce.value >= 0))
				scn.mn.s.setValue(53);
			else if (scn.a)
				scn.mn.s.setValue(15);
		}
		scn.mn.s2.setValue(4);
		scn.mn.s3.setValue(6);
		scn.mn.s4.setValue(10);
		scn.mn.fullCancel.setSelected(true);
		scn.mn.cancelType = "full";
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
		
		if (scn.l)
		{
			xForce.accel = -xForce.accel;
			setFlipX(false);
		}
		if (scn.r)
		{
			setFlipX(true);
		}
		
		if ((!scn.l) && (!scn.r))
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
		gravity = 120.0;//?31.2?
		gravLow = 32;
		//if run
		initialJump = 485.0;
		//else
		//initialJump = 480.0;
		terminalVelocity = 480.0;

		if ((scn.a) && (scn.aPressed <= 0) && (onGround))
		{
			yForce.value = -initialJump;
		}

		if (!scn.a || yForce.value >= 0)
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
