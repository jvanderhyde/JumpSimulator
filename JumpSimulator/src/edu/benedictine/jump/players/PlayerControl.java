//Abstract class for player control mechanisms
//Created by James Vanderhyde, 29 May 2014

package edu.benedictine.jump.players;

import edu.benedictine.jump.SimVariableChoice;
import edu.benedictine.jump.SimVariableFloat;

public abstract class PlayerControl
{
	public abstract void walk(PlayerInfo pInfo, InputInfo iInfo);
	
	public abstract void jump(PlayerInfo pInfo, InputInfo iInfo);
	
	public void getJumpType(SimVariableFloat gravity, 
							SimVariableFloat jumpPower, 
							SimVariableFloat xSpeed, 
							SimVariableFloat airDecel, 
							SimVariableChoice jumpCancelType)
	{
	}
	
	public String getName()
	{
		return "no name";
	}

	/**
	 * Zeros out the y velocity when the player is on the ground.
	 * This is necessary so that when the player walks off an edge,
	 * the y velocity is initially 0.
	 * Call this method at the beginning the jump implementation.
	 * @param pInfo the player info
	 */
	protected void zeroYVelOnGround(PlayerInfo pInfo)
	{
		if (pInfo.onGround)
			pInfo.yForce.value = 0;
	}
	
	/**
	 * A flag that indicates whether the jump button has been released.
	 */
	protected boolean jumpReleasedSinceLastJump = true;
	
	/**
	 * Updates the jumpReleasedSinceLastJump value.
	 * This is necessary so that the player doesn't bounce by holding
	 * the jump button.
	 * Call this method at the end of the jump implementation.
	 * @param pInfo the player info
	 * @param iInfo the input info
	 */
	protected void stopBouncing(PlayerInfo pInfo, InputInfo iInfo)
	{
		if (pInfo.onGround && !iInfo.jumpPressed)
			jumpReleasedSinceLastJump = true;
		if (iInfo.jumpPressed)
			jumpReleasedSinceLastJump = false;
	}
	
}
