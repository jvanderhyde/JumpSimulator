package edu.benedictine.game.engine;

import edu.benedictine.game.media.ImageSource;
import edu.benedictine.game.gui.Scene;

//Created by Joseph Rioux, 31 March 2013
//WorldObject with timer and gravity, easier constructor

public class Effect extends WorldObject
{	
	public Effect(Scene scn, int draw, double xLoc, double yLoc, double xCng, double yCng,
			      ImageSource img, int lifeTime, double gravity, double maxFall) 
	{
		super(scn, 0, draw, xLoc, yLoc, xCng, yCng, img);
		this.lifeTime = lifeTime;
		this.gravity = gravity;
		this.maxFall = maxFall;
	}
}
