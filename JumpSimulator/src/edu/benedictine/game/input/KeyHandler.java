//Keeps track of some key presses and releases
// Provides the same function for arrow keys, WASD, and IJKL.
//Created by James Vanderhyde, 30 may 2010
//Modified by James Vanderhyde, 22 May 2014
//  Added keys for jump and fire.
//  Added KeyEventDispatcher functionality.
//Modified by James Vanderhyde, 23 May 2014
//  Refactored (created superclass)

package edu.benedictine.game.input;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler extends InputHandler
    implements KeyListener
{
    public void keyPressed(KeyEvent evt)
    {
        //System.out.println("Key pressed: "+evt.getKeyCode());
        switch (evt.getKeyCode())
        {
        case KeyEvent.VK_SPACE:
            inputFlags[ACTION]=true;
            break;
        case KeyEvent.VK_UP:
        case KeyEvent.VK_I:
        case KeyEvent.VK_W:
            inputFlags[UP]=true;
            break;
        case KeyEvent.VK_DOWN:
        case KeyEvent.VK_K:
        case KeyEvent.VK_S:
            inputFlags[DOWN]=true;
            break;
        case KeyEvent.VK_LEFT:
        case KeyEvent.VK_J:
        case KeyEvent.VK_A:
            inputFlags[LEFT]=true;
            break;
        case KeyEvent.VK_RIGHT:
        case KeyEvent.VK_L:
        case KeyEvent.VK_D:
            inputFlags[RIGHT]=true;
            break;
        case KeyEvent.VK_Z:
            inputFlags[JUMP]=true;
            break;
        case KeyEvent.VK_X:
            inputFlags[FIRE]=true;
            break;
        default:
        }
    }

    public void keyReleased(KeyEvent evt)
    {
        //System.out.println("Key released: "+evt.getKeyCode());
        switch (evt.getKeyCode())
        {
        case KeyEvent.VK_SPACE:
            inputFlags[ACTION]=false;
            break;
        case KeyEvent.VK_UP:
        case KeyEvent.VK_I:
        case KeyEvent.VK_W:
            inputFlags[UP]=false;
            break;
        case KeyEvent.VK_DOWN:
        case KeyEvent.VK_K:
        case KeyEvent.VK_S:
            inputFlags[DOWN]=false;
            break;
        case KeyEvent.VK_LEFT:
        case KeyEvent.VK_J:
        case KeyEvent.VK_A:
            inputFlags[LEFT]=false;
            break;
        case KeyEvent.VK_RIGHT:
        case KeyEvent.VK_L:
        case KeyEvent.VK_D:
            inputFlags[RIGHT]=false;
            break;
        case KeyEvent.VK_Z:
            inputFlags[JUMP]=false;
            break;
        case KeyEvent.VK_X:
            inputFlags[FIRE]=false;
            break;
        default:
        }
    }

    public void keyTyped(KeyEvent evt) {}

	//Custom dispatcher allows for an application to capture global key events
	//Source: http://portfolio.planetjon.ca/2011/09/16/java-global-jframe-key-listener/
	public void captureAllKeyEvents()
	{
		//Hijack the keyboard manager (Jonathan Weatherhead)
		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		manager.addKeyEventDispatcher(new KeyEventDispatcher()
		{
			public boolean dispatchKeyEvent(KeyEvent evt) 
			{
				if (evt.getID() == KeyEvent.KEY_PRESSED)
					keyPressed(evt);
				if (evt.getID() == KeyEvent.KEY_RELEASED)
					keyReleased(evt);
				if (evt.getID() == KeyEvent.KEY_TYPED)
					keyTyped(evt);
				return false;
			}
		});
	}
}
