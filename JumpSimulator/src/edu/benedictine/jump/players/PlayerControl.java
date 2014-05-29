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
	
}
