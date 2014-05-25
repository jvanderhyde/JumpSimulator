//Mapping for the Wii remote using WJoy
//Created by James Vanderhyde, 24 May 2014

package edu.benedictine.game.input;

public class WiiRemoteWJoy extends GamepadHandler
{
	public int getActionFromButton(int number)
	{
		switch (number)
		{
			case 1:
				return UP;
			case 0:
				return DOWN;
			case 2:
				return LEFT;
			case 3:
				return RIGHT;
			case 10:
				return JUMP;
			case 9:
				return FIRE;
			case 4:
				return ACTION;
			default:
				return UNDEFINED;
		}
	}
}
