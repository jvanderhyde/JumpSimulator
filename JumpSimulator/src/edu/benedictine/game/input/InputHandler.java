//A class to keep track of game input
//Created by James Vanderhyde, 23 May 2014
//  Extracted from KeyHandler

package edu.benedictine.game.input;

public abstract class InputHandler
{
    protected static final int
		UNDEFINED  =      -1,
        ACTION     =       0,
        UP         =       1,
        DOWN       =       2,
        LEFT       =       3,
        RIGHT      =       4,
        JUMP       =       5,
        FIRE       =       6;
    protected static final int
        NUM_INPUTS =       7;

    protected boolean[] inputFlags;

    public InputHandler()
    {
        inputFlags=new boolean[NUM_INPUTS];
        releaseAll();
    }

    public final void releaseAll()
    {
        for (int i=0; i<NUM_INPUTS; i++)
            inputFlags[i]=false;
    }

    public boolean isActionPressed()
    {
        return inputFlags[ACTION];
    }

    public boolean isUpPressed()
    {
        return inputFlags[UP];
    }

    public boolean isDownPressed()
    {
        return inputFlags[DOWN];
    }

    public boolean isLeftPressed()
    {
        return inputFlags[LEFT];
    }

    public boolean isRightPressed()
    {
        return inputFlags[RIGHT];
    }

    public boolean isJumpPressed()
    {
        return inputFlags[JUMP];
    }

    public boolean isFirePressed()
    {
        return inputFlags[FIRE];
    }

	
}
