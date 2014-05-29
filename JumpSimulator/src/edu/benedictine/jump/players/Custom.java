//Basic player control that can be modified by slider controls

package edu.benedictine.jump.players;

import edu.benedictine.jump.SimulatorPanel;

public class Custom extends PlayerControl
{
	SimulatorPanel sim;
	
	public Custom(SimulatorPanel sim)
	{
		this.sim=sim;
	}
	
	@Override
	public void walk(PlayerInfo pInfo, InputInfo iInfo) 
	{
		final double xSpeed = sim.getXSpeed();
		final double airDecel = sim.getAirDecel();
		
		pInfo.xForce.upper = xSpeed;
		pInfo.xForce.lower = -xSpeed;
		pInfo.xForce.accel = 30.0;
		
		if (iInfo.leftPressed)
		{
			if (pInfo.xForce.value > xSpeed*airDecel)
				pInfo.xForce.value = xSpeed*airDecel;
			pInfo.xForce.accel = -pInfo.xForce.accel;
		}
		if (iInfo.rightPressed)
		{
			if (pInfo.xForce.value < -xSpeed*airDecel)
				pInfo.xForce.value = -xSpeed*airDecel;
		}
		
		if ((!iInfo.leftPressed) && (!iInfo.rightPressed))
		{
			pInfo.xForce.upper = xSpeed*airDecel;
			pInfo.xForce.lower = -xSpeed*airDecel;
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
		final double gravity = sim.getGravity();
		final double initialJump = sim.getJumpPower();
		
		if ((iInfo.jumpPressed) && (pInfo.onGround))
		{
			pInfo.yForce.value = -initialJump;
		}

		if (!iInfo.jumpPressed && pInfo.yForce.value < 0.0)
		{
			if (sim.jumpCancelTypeIsFull())
				pInfo.yForce.value = 0.0;
			if (sim.jumpCancelTypeIsDoubleGravity())
				pInfo.yForce.value += gravity;

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

		if (!pInfo.onGround)
		{
			pInfo.yForce.value += gravity;
		}
	}

	@Override
	public String getName()
	{
		return "custom";
	}
	
}
