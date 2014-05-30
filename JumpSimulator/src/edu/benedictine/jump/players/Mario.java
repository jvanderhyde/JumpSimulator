//Super Mario Bros.-style player control

package edu.benedictine.jump.players;

import edu.benedictine.jump.SimVariableChoice;
import edu.benedictine.jump.SimVariableFloat;

public class Mario extends PlayerControl
{
	

	@Override
	public void getJumpType(SimVariableFloat gravity, 
							SimVariableFloat jumpPower, 
							SimVariableFloat xSpeed, 
							SimVariableFloat airDecel, 
							SimVariableChoice jumpCancelType)
	{
		gravity.setValue(120.0*32/256);
		jumpPower.setValue(485);
		xSpeed.setValue(180);
		airDecel.setValue(5);
		jumpCancelType.setValue("double");
	}

	@Override
	public void walk(PlayerInfo pInfo, InputInfo iInfo) 
	{
		//mario
		//running
		//double up = 300.0;
		//double down = -300.0;
		//normal
		//double up = 180.0;
		//double down = -180.0;
		//double acc = 120.0;

		pInfo.xForce.upper = 180.0;
		pInfo.xForce.lower = -180.0;
		pInfo.xForce.accel = 5.0;
		
		if (iInfo.leftPressed)
		{
			pInfo.xForce.accel = -pInfo.xForce.accel;
		}
		if (iInfo.rightPressed)
		{
		}
		
		if ((!iInfo.leftPressed) && (!iInfo.rightPressed))
		{
			pInfo.xForce.accel = 0.0;
			//mario
			if (pInfo.onGround)
			{
				pInfo.xForce.upper = 0.0;
				pInfo.xForce.lower = 0.0;
				pInfo.xForce.decel = 5.0;
			}
		}
	}

	@Override
	public void jump(PlayerInfo pInfo, InputInfo iInfo)
	{
		final double gravity = 120.0;//?31.2?
		int gravLow = 32;
		//if run
		final double initialJump = 485.0;
		//else
		//initialJump = 480.0;
		final double terminalVelocity = 480.0;

		if ((iInfo.jumpPressed) && (pInfo.onGround))
		{
			pInfo.yForce.value = -initialJump;
		}

		if (!iInfo.jumpPressed || pInfo.yForce.value >= 0)
		{
			gravLow = 112;
		}

		if (!pInfo.onGround)
		{
			if (pInfo.yForce.value < terminalVelocity)
				pInfo.yForce.value += gravity*(gravLow/256.0);
		}
	}

	@Override
	public String getName()
	{
		return "Mario";
	}
	
}
