//Super Mario Bros.-style player control

package edu.benedictine.jump.players;

import edu.benedictine.jump.SimVariableChoice;
import edu.benedictine.jump.SimVariableFloat;

public class MegaMan extends PlayerControl
{
	@Override
	public void getJumpType(SimVariableFloat gravity, 
							SimVariableFloat jumpPower, 
							SimVariableFloat xSpeed, 
							SimVariableFloat airDecel, 
							SimVariableChoice jumpCancelType)
	{
		gravity.setValue(30.0);
		jumpPower.setValue(600);
		xSpeed.setValue(90);
		airDecel.setValue(0);
		jumpCancelType.setValue("full");
	}

	@Override
	public void walk(PlayerInfo pInfo, InputInfo iInfo) 
	{
		pInfo.xForce.upper = 160.0;
		pInfo.xForce.lower = -160.0;
		pInfo.xForce.accel = 40.0;
		
		if (iInfo.leftPressed)
		{
			pInfo.xForce.accel = -pInfo.xForce.accel;
		}
		if (iInfo.rightPressed)
		{
		}
		
		if ((!iInfo.leftPressed) && (!iInfo.rightPressed))
		{
			pInfo.xForce.upper = 0.0;
			pInfo.xForce.lower = 0.0;
			pInfo.xForce.accel = 0.0;
			pInfo.xForce.decel = 30.0;
		}
	}

	@Override
	public void jump(PlayerInfo pInfo, InputInfo iInfo)
	{
		final double gravity = 30.0;
		final double initialJump = 600.0;
		final double terminalVelocity = 600.0;

		if ((iInfo.jumpPressed) && (pInfo.onGround))
		{
			pInfo.yForce.value = -initialJump;
		}
		
		if (!iInfo.jumpPressed && pInfo.yForce.value < -120.0)
		{
			pInfo.yForce.value = -120.0;
		}

		if (!pInfo.onGround)
		{
			if (pInfo.yForce.value < terminalVelocity)
				pInfo.yForce.value += gravity;
		}
	}

	@Override
	public String getName()
	{
		return "Mega Man";
	}
	
}
