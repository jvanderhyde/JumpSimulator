//Jump from one tile to the next (less physics)
//  Similar to Prince of Persia or Oddworld
//Created by James Vanderhyde, 18 June 2014

package edu.benedictine.jump.players;

public class Discrete extends PlayerControl
{
	private final int stepTime = 16;
	private final int jumpTime = 32;
	private final int gravity = 20;
	private final int xSpeed = 120;
	
	private int jumpT = 0;
	private int stepT = 0;

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
		if (jumpT > 0)
		{
			jumpT--;
		}
		if (jumpT == 0)
		{
			if (stepT == 0)
			{
				//jump straight up
				if (iInfo.jumpPressed && pInfo.onGround)
				{
					double yVel = gravity*jumpTime/2;
					pInfo.yForce.upper = yVel;
					pInfo.yForce.lower = -yVel;
					pInfo.yForce.value = -yVel;
					pInfo.yForce.accel = gravity;
					jumpT = jumpTime;
				}
			}
			else if (stepT >= stepTime-2)
			{
				//jump over next tile
				if (iInfo.jumpPressed && pInfo.onGround)
				{
					double yVel = gravity*jumpTime/2;
					pInfo.yForce.upper = yVel;
					pInfo.yForce.lower = -yVel;
					pInfo.yForce.value = -yVel;
					pInfo.yForce.accel = gravity;
					jumpT = jumpTime;
					stepT += (jumpTime-stepTime);
				}
			}
		}
	}
	
	@Override
	public String getName()
	{
		return "Discrete steps";
	}
	
}
