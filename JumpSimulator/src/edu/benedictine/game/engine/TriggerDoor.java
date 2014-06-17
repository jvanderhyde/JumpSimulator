//Created by Joseph Rioux, 24 September 2013

package edu.benedictine.game.engine;

import edu.benedictine.game.gui.Scene;


public class TriggerDoor extends Trigger
{
	String goTo;
	double nextX, nextY;
	SceneSwitcher swapper;
	
	public TriggerDoor(Scene scn, SceneSwitcher swapper, double xLoc, double yLoc, double width, double height, String source, double nextX, double nextY) 
	{
		super(scn, xLoc, yLoc, width, height);
		this.goTo = source;
		this.nextX = nextX;
		this.nextY = nextY;
		this.swapper = swapper;
	}
	
	public void fire()
	{
		swapper.mark(goTo, 16, nextX, nextY);
	}
}
