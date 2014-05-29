//Metroid-style player control

package edu.benedictine.jump.players;

import edu.benedictine.jump.SimVariableChoice;
import edu.benedictine.jump.SimVariableFloat;

public class Samus extends PlayerControl
{
	int wrapper;
	boolean samusFlipping = false;
	
	@Override
	public void getJumpType(SimVariableFloat gravity, 
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
	public void walk(PlayerInfo pInfo, InputInfo iInfo) 
	{
		//xForce.upper = 240.0;
		//down = -240.0;
		pInfo.xForce.upper = 180.0;
		pInfo.xForce.lower = -180.0;
		pInfo.xForce.accel = 120.0;
		final double airDecel = 0;
		final double xSpeed = 180.0;
		
		if (iInfo.leftPressed)
		{
			pInfo.xForce.accel = -pInfo.xForce.accel;
		}
		if (iInfo.rightPressed)
		{
		}
		
		if ((!iInfo.leftPressed) && (!iInfo.rightPressed))
		{
			//metroid
			pInfo.xForce.upper = xSpeed*airDecel;
			pInfo.xForce.lower = -xSpeed*airDecel;
			pInfo.xForce.accel = 0.0;
			pInfo.xForce.decel = 30.0;
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
			if (pInfo.onGround)
			{
				pInfo.xForce.upper = 0.0;
				pInfo.xForce.lower = 0.0;
				pInfo.xForce.decel = 30.0;
			}
		}
	}

	@Override
	public void jump(PlayerInfo pInfo, InputInfo iInfo)
	{
		final double gravity = 120.0;//?31.2?
		final double initialJump = 480.0;
		final double terminalVelocity = 600.0;

		if (pInfo.onGround)
			samusFlipping = false;

		if ((iInfo.jumpPressed) && (pInfo.onGround))
		{
			pInfo.yForce.value = -initialJump;
			wrapper = 0;
			if (Math.abs(pInfo.xForce.value) == 180.0)
				samusFlipping = true;
		}

		if (!pInfo.onGround)
			wrapper += 24;

		if (!iInfo.jumpPressed && pInfo.yForce.value < 0.0)
		{
			if (wrapper >= 256)
				pInfo.yForce.value = 0.0;
		}

		if (wrapper >= 256)
			wrapper -= 255;

		if (!pInfo.onGround)
		{
			if (pInfo.yForce.value < terminalVelocity)
				pInfo.yForce.value += gravity*(24.0/256.0);
		}
	}

	@Override
	public String getName()
	{
		return "Samus";
	}
	
}
