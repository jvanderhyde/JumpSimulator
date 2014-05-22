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
public class Custom extends edu.benedictine.game.engine.Player
{
	public Custom(Scene scn, double xLoc, double yLoc, double xCng, double yCng)
	{
		super(scn,xLoc,yLoc,xCng,yCng);
	}
	
	@Override
	public void setJumpType()
	{

	}

	@Override
	public void walk() 
	{
		xForce.upper = scn.mn.xSpeed;
		xForce.lower = -scn.mn.xSpeed;
		xForce.accel = 30.0;
		
		if (scn.l)
		{
			if (xForce.value > scn.mn.xSpeed*scn.mn.airDecel)
				xForce.value = scn.mn.xSpeed*scn.mn.airDecel;
			xForce.accel = -xForce.accel;
			setFlipX(false);
		}
		if (scn.r)
		{
			if (xForce.value < -scn.mn.xSpeed*scn.mn.airDecel)
				xForce.value = -scn.mn.xSpeed*scn.mn.airDecel;
			setFlipX(true);
		}
		
		if ((!scn.l) && (!scn.r))
		{
			xForce.upper = scn.mn.xSpeed*scn.mn.airDecel;
			xForce.lower = -scn.mn.xSpeed*scn.mn.airDecel;
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
		if (onGround)
		{
			cancelingAcc = -1.0;
		}

		if ((scn.a) && (scn.aPressed <= 0) && (onGround))
		{
			yForce.value = -scn.mn.jumpPower;
		}

		if (!scn.a && yForce.value < 0.0)
		{
			if (scn.mn.cancelType.equals("full"))
				yForce.value = 0.0;
			if (scn.mn.cancelType.equals("double"))
				yForce.value += scn.mn.gravity;

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
			yForce.value += scn.mn.gravity;
		}
	}
}
