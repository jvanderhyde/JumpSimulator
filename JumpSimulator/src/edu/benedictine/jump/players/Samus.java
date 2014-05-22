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
public class Samus extends edu.benedictine.game.engine.Player
{
	int wrapper;
	long start;
	boolean samusFlipping = false;
	
	public Samus(Scene scn, double xLoc, double yLoc, double xCng, double yCng)
	{
		super(scn,xLoc,yLoc,xCng,yCng);
	}
	
	@Override
	public void setJumpType()
	{
		//scn.mn.s.setValue((int)((120.0*(24.0/256.0))/5.0)); 11.25
		scn.mn.s.setValue(11);
		scn.mn.s2.setValue(4);
		scn.mn.s3.setValue(6);
		if (samusFlipping)
			scn.mn.s4.setValue(10);
		else
			scn.mn.s4.setValue(0);
		scn.mn.fullCancel.setSelected(true);
		scn.mn.cancelType = "full";
	}

	@Override
	public void walk() 
	{
		//xForce.upper = 240.0;
		//down = -240.0;
		xForce.upper = 180.0;
		xForce.lower = -180.0;
		xForce.accel = 120.0;
		
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
			//metroid
			xForce.upper = scn.mn.xSpeed*scn.mn.airDecel;
			xForce.lower = -scn.mn.xSpeed*scn.mn.airDecel;
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
		gravity = 120.0;//?31.2?
		initialJump = 480.0;
		terminalVelocity = 600.0;

		if (onGround)
			samusFlipping = false;

		if ((scn.a) && (scn.aPressed <= 0) && (onGround))
		{
			yForce.value = -initialJump;
			wrapper = 0;
			if (Math.abs(xForce.value) == 180.0)
				samusFlipping = true;
		}

		if (!onGround)
			wrapper += 24;

		if (!scn.a && yForce.value < 0.0)
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
