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
public class Eversion extends edu.benedictine.game.engine.Player
{
	
	public Eversion(Scene scn, double xLoc, double yLoc, double xCng, double yCng)
	{
		super(scn,xLoc,yLoc,xCng,yCng);
		//super(scn, 5, 8, xLoc, yLoc, xCng, yCng, scn.store.heroStatic, -16.0, 16.0, -14.0, 14.0);
		//xForce = new AdvancedForce(0.0, 0.0, 45.0, -240.0, 240.0, 1);
	}

	@Override
	public void setJumpType()
	{
		if (!scn.a && yForce.value < 0.0)
			scn.mn.s.setValue(52);
		else
			scn.mn.s.setValue(26);
		scn.mn.s2.setValue(5);
		scn.mn.s3.setValue(9);
		scn.mn.s4.setValue(0);
		scn.mn.doubleGravity.setSelected(true);
		scn.mn.cancelType = "double";
	}

	@Override
	public void walk() 
	{
		xForce.upper = 270.0;
		xForce.lower = -270.0;
		xForce.accel = 30.0;
		
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
		gravity = 26.0;//?31.2?
		initialJump = 600.0;
		terminalVelocity = 600.0;

		if (yForce.value < terminalVelocity)
			yForce.value += gravity;

		//initial jump
		if ((scn.a) && (scn.aPressed <= 0) && (onGround))
		{
			yForce.value = -initialJump;
		}

		//apply gravity again if a is not down
		if ((!scn.a) && (yForce.value < 0.0))
		{
			yForce.value += gravity;
		}
	}
}
