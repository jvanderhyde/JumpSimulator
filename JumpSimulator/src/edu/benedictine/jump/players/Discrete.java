//Jump from one tile to the next (less physics)
//  Similar to Prince of Persia or Oddworld
//Created by James Vanderhyde, 18 June 2014

package edu.benedictine.jump.players;

import edu.benedictine.jump.SimVariableChoice;
import edu.benedictine.jump.SimVariableFloat;

public class Discrete extends PlayerControl
{
	private final int xSpeed = 120;
	private final int tilesPerStep = 1;
	private final int stepTime = 2*tilesPerStep*xSpeed/15;

	private final int gravity = 20;
	private final int yTerminalVel = 640;
	private final int yInitialVel = 320;
	private final int jumpTime = 2*yInitialVel/gravity;
	
	private int jumpT = 0;
	private int stepT = 0;

	@Override
	public void getJumpType(SimVariableFloat gravity, 
							SimVariableFloat jumpPower, 
							SimVariableFloat xSpeed, 
							SimVariableFloat airDecel, 
							SimVariableChoice jumpCancelType)
	{
		gravity.setValue(120.0*32/256);
		jumpPower.setValue(yInitialVel);
		xSpeed.setValue(this.xSpeed);
		airDecel.setValue(0);
		jumpCancelType.setValue("no");
	}

	@Override
	public void walk(PlayerInfo pInfo, InputInfo iInfo)
	{
		if (stepT > 0)
		{
			stepT--;
		}
		if (stepT == 0)
		{
			//You can only change X motion if you're on the ground
			if (pInfo.onGround)
			{
				//stop
				pInfo.xForce.upper = 0;
				pInfo.xForce.lower = 0;
				pInfo.xForce.accel = 0;
				pInfo.xForce.decel = xSpeed/3.0;
				
				if (jumpT <= 1)
				{
					//You can only step if not jumping
					if (iInfo.rightPressed || iInfo.leftPressed)
					{
						//move
						pInfo.xForce.upper = xSpeed;
						pInfo.xForce.lower = -xSpeed;
						pInfo.xForce.accel = xSpeed/3.0;
						pInfo.xForce.decel = 0;
						stepT = stepTime;
					}
					if (iInfo.leftPressed)
					{
						pInfo.xForce.accel = -pInfo.xForce.accel;
						pInfo.xForce.decel = -pInfo.xForce.decel;
					}
				}
			}
		}
	}

	@Override
	public void jump(PlayerInfo pInfo, InputInfo iInfo)
	{
		//set up gravity force
		pInfo.yForce.upper = yTerminalVel;
		pInfo.yForce.lower = -yInitialVel;
		pInfo.yForce.accel = gravity;
		
		zeroYVelOnGround(pInfo);
		
		if (jumpT > 0)
		{
			jumpT--;
		}
		if (jumpT == 0)
		{
			if (stepT == 0)
			{
				//jump straight up
				if (iInfo.jumpPressed && pInfo.onGround && jumpReleasedSinceLastJump)
				{
					pInfo.yForce.value = -yInitialVel;
					jumpT = jumpTime;
				}
				stopBouncing(pInfo, iInfo);
			}
			else if (stepT >= stepTime-2)
			{
				//jump over next tile
				if (iInfo.jumpPressed && pInfo.onGround && jumpReleasedSinceLastJump)
				{
					pInfo.yForce.value = -yInitialVel;
					jumpT = jumpTime;
					stepT += (jumpTime-stepTime);
				}
				stopBouncing(pInfo, iInfo);
			}
		}
	}
	
	@Override
	public String getName()
	{
		return "Discrete steps";
	}
	
}

//Prince of Persia notes

//Prince of Persia has gravity 3 and terminal velocity 33,
// but I'm not sure what the frame rate is.
// Death velocity: 33
// Oof velocity:   22
//Most of the control code in Prince of Persia is handling jumping
// so that the animation looks good. All characters, including the
// player, are controlled by scripts, so the initial velocity
// for jumping and running speeds are in the script, I think.

//Relevant code (https://github.com/adamgreen/Prince-of-Persia-Apple-II)
//  SEQTABLE.S:440
//  CTRL.S:1586
//  FRAMEDEF.S
//  CTRLSUBS.S:700
//  COLL.S:1075
