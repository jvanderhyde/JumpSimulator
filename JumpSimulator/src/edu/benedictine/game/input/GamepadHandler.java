//Keeps track of controller input for movement, jump, and fire
//  Subclass to map buttons to actions
//Created by James Vanderhyde, 23 May 2014

package edu.benedictine.game.input;

import us.vanderhyde.gamepad.EventThread;
import us.vanderhyde.gamepad.GamepadButtonEvent;
import us.vanderhyde.gamepad.GamepadButtonListener;

public abstract class GamepadHandler extends InputHandler
	implements GamepadButtonListener
{
	public void startGamepadThread()
	{
		final EventThread t=new EventThread();
		t.addGamepadButtonListener(this);
		t.start();
	}
	
	public abstract int getActionFromButton(int number);
	
	public void buttonPressed(GamepadButtonEvent evt)
	{
		int action = getActionFromButton(evt.getButtonNumber());
		if (action != UNDEFINED)
			inputFlags[action]=true;
	}

	public void buttonReleased(GamepadButtonEvent evt)
	{
		int action = getActionFromButton(evt.getButtonNumber());
		if (action != UNDEFINED)
			inputFlags[action]=false;
	}
	
}
