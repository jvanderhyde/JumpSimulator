//Manages all the active input handlers
//Created by James Vanderhyde, 23 May 2014

package edu.benedictine.game.input;

import java.util.ArrayList;

public class InputManager
{
	private ArrayList<InputHandler> handlers = new ArrayList<InputHandler>();
	
	public void addHandler(InputHandler handler)
	{
		handlers.add(handler);
	}
	
	public void removeHandler(InputHandler handler)
	{
		handlers.remove(handler);
	}
	
    public boolean isActionPressed()
	{
        for (InputHandler h:handlers)
			if (h.isActionPressed())
				return true;
		return false;
    }

    public boolean isUpPressed()
    {
        for (InputHandler h:handlers)
			if (h.isUpPressed())
				return true;
		return false;
    }

    public boolean isDownPressed()
    {
        for (InputHandler h:handlers)
			if (h.isDownPressed())
				return true;
		return false;
    }

    public boolean isLeftPressed()
    {
        for (InputHandler h:handlers)
			if (h.isLeftPressed())
				return true;
		return false;
    }

    public boolean isRightPressed()
    {
        for (InputHandler h:handlers)
			if (h.isRightPressed())
				return true;
		return false;
    }

    public boolean isJumpPressed()
    {
        for (InputHandler h:handlers)
			if (h.isJumpPressed())
				return true;
		return false;
    }

    public boolean isFirePressed()
    {
        for (InputHandler h:handlers)
			if (h.isFirePressed())
				return true;
		return false;
    }

}
