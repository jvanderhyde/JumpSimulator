//Created by Joseph Rioux, 30 March 2013
//Modified by Joseph Rioux, 23 May 2014
//added comments

//a PickUp is an object that performs some action when it touches the player.
//subclasses would be coins, heart, etc.

package edu.benedictine.game.engine;

import edu.benedictine.game.media.ImageSource;
import edu.benedictine.game.engine.collision.Hit;
import edu.benedictine.game.gui.Scene;


public class PickUp extends GameObject
{
	public PickUp(Scene scn, double xLoc, double yLoc, double xCng, double yCng, ImageSource img, 
				  double head, double feet, double left, double right, int lifeTime, double gravity, double maxFall) 
	{
		super(scn, 10, 12, xLoc, yLoc, xCng, yCng, img, head, feet, left, right);
		this.lifeTime = lifeTime;
		setFall(gravity, maxFall);
	}
	
	public void handleHit(Hit h)
	{
		//if the other member of the object collision is the player, call hitPlayer()
		if (h.b.owner instanceof Player)
			hitPlayer(((Player)h.b.owner));
	}
	
	public void hitPlayer(Player o)
	{
		//implmented by subclasses (e.g. Gem)
		die(); //the default is to get rid of the pickup
	}
}
