//Eversion-style player control

package edu.benedictine.jump.players;

import edu.benedictine.jump.SimVariableChoice;
import edu.benedictine.jump.SimVariableFloat;

public class Eversion extends PlayerControl
{
	
	@Override
	public void getJumpType(SimVariableFloat gravity, 
							SimVariableFloat jumpPower, 
							SimVariableFloat xSpeed, 
							SimVariableFloat airDecel, 
							SimVariableChoice jumpCancelType)
	{
		gravity.setValue(26);
		jumpPower.setValue(600);
		xSpeed.setValue(270);
		airDecel.setValue(0);
		jumpCancelType.setValue("double");
	}

	@Override
	public void walk(PlayerInfo pInfo, InputInfo iInfo) 
	{
		pInfo.xForce.upper = 270.0;
		pInfo.xForce.lower = -270.0;
		pInfo.xForce.accel = 30.0;
		
		if (iInfo.leftPressed)
		{
			pInfo.xForce.accel = -pInfo.xForce.accel;
		}
		if (iInfo.rightPressed)
		{
		}
		
		if ((!iInfo.leftPressed) && (!iInfo.rightPressed))
		{
			//eversion
			//xForce.upper = 0.0;
			//xForce.lower = 0.0;
			//xForce.decel = 30.0;
			
			pInfo.xForce.upper = 0.0;
			pInfo.xForce.lower = 0.0;
			pInfo.xForce.accel = 0.0;
			pInfo.xForce.decel = 30.0;
			
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
		//Eversion jump:
		final double gravity = 26.0;//?31.2?
		final double initialJump = 600.0;
		final double terminalVelocity = 600.0;

		if (pInfo.yForce.value < terminalVelocity)
			pInfo.yForce.value += gravity;

		//initial jump
		if ((iInfo.jumpPressed) && (pInfo.onGround))
		{
			pInfo.yForce.value = -initialJump;
		}

		//apply gravity again if a is not down
		if ((!iInfo.jumpPressed) && (pInfo.yForce.value < 0.0))
		{
			pInfo.yForce.value += gravity;
		}
	}

	@Override
	public String getName()
	{
		return "Zee-tee";
	}
	
}
