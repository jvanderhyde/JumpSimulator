//Mapping for the gamepad I happen to have on hand
//Created by James Vanderhyde, 23 May 2014

package edu.benedictine.game.input;

public class CHProductsUSBGamepad extends GamepadHandler
{
	public int getActionFromButton(int number)
	{
		switch (number)
		{
			case 13:
				return UP;
			case 1:
				return DOWN;
			case 12:
				return LEFT;
			case 0:
				return RIGHT;
			case 3:
			case 4:
				return JUMP;
			case 2:
			case 5:
				return FIRE;
			case 11:
			case 10:
				return ACTION;
			default:
				return UNDEFINED;
		}
	}
}
