//Keeps track of some key presses and releases
// Provides the same function for arrow keys, WASD, and IJKL.
//Created by James Vanderhyde, 30 may 2010
//Modified by James Vanderhyde, 22 May 2014
//  Added keys for jump and fire.
//  Added KeyEventDispatcher functionality.

package edu.benedictine.game.input;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler
    implements KeyListener
{
    private static final int
        ACTION  =       0,
        UP      =       1,
        DOWN    =       2,
        LEFT    =       3,
        RIGHT   =       4,
        JUMP    =       5,
        FIRE    =       6;
    private static final int
        NUM_KEYS=       7;

    private boolean[] keyFlags;

    public KeyHandler()
    {
        keyFlags=new boolean[NUM_KEYS];
        releaseAll();
    }

    public final void releaseAll()
    {
        for (int i=0; i<NUM_KEYS; i++)
            keyFlags[i]=false;
    }

    public void keyPressed(KeyEvent evt)
    {
        //System.out.println("Key pressed: "+evt.getKeyCode());
        switch (evt.getKeyCode())
        {
        case KeyEvent.VK_SPACE:
            keyFlags[ACTION]=true;
            break;
        case KeyEvent.VK_UP:
        case KeyEvent.VK_I:
        case KeyEvent.VK_W:
            keyFlags[UP]=true;
            break;
        case KeyEvent.VK_DOWN:
        case KeyEvent.VK_K:
        case KeyEvent.VK_S:
            keyFlags[DOWN]=true;
            break;
        case KeyEvent.VK_LEFT:
        case KeyEvent.VK_J:
        case KeyEvent.VK_A:
            keyFlags[LEFT]=true;
            break;
        case KeyEvent.VK_RIGHT:
        case KeyEvent.VK_L:
        case KeyEvent.VK_D:
            keyFlags[RIGHT]=true;
            break;
        case KeyEvent.VK_Z:
            keyFlags[JUMP]=true;
            break;
        case KeyEvent.VK_X:
            keyFlags[FIRE]=true;
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
            keyFlags[ACTION]=false;
            break;
        case KeyEvent.VK_UP:
        case KeyEvent.VK_I:
        case KeyEvent.VK_W:
            keyFlags[UP]=false;
            break;
        case KeyEvent.VK_DOWN:
        case KeyEvent.VK_K:
        case KeyEvent.VK_S:
            keyFlags[DOWN]=false;
            break;
        case KeyEvent.VK_LEFT:
        case KeyEvent.VK_J:
        case KeyEvent.VK_A:
            keyFlags[LEFT]=false;
            break;
        case KeyEvent.VK_RIGHT:
        case KeyEvent.VK_L:
        case KeyEvent.VK_D:
            keyFlags[RIGHT]=false;
            break;
        case KeyEvent.VK_Z:
            keyFlags[JUMP]=false;
            break;
        case KeyEvent.VK_X:
            keyFlags[FIRE]=false;
            break;
        default:
        }
    }

    public void keyTyped(KeyEvent evt) {}

    public boolean isActionPressed()
    {
        return keyFlags[ACTION];
    }

    public boolean isUpPressed()
    {
        return keyFlags[UP];
    }

    public boolean isDownPressed()
    {
        return keyFlags[DOWN];
    }

    public boolean isLeftPressed()
    {
        return keyFlags[LEFT];
    }

    public boolean isRightPressed()
    {
        return keyFlags[RIGHT];
    }

    public boolean isJumpPressed()
    {
        return keyFlags[JUMP];
    }

    public boolean isFirePressed()
    {
        return keyFlags[FIRE];
    }

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
