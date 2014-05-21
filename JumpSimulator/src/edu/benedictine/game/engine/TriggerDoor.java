//Created by Joseph Rioux, 24 September 2013

package edu.benedictine.game.engine;

import edu.benedictine.game.gui.Scene;

public class TriggerDoor extends Trigger
{
	String goTo;
	double nextX, nextY;
	
	public TriggerDoor(Scene scn, double xLoc, double yLoc, double width, double height, String source, double nextX, double nextY) 
	{
		super(scn, xLoc, yLoc, width, height);
		this.goTo = source;
		this.nextX = nextX;
		this.nextY = nextY;
	}
	
	public void fire()
	{
		scn.swapper.mark(goTo, 16, nextX, nextY);
	}
}
