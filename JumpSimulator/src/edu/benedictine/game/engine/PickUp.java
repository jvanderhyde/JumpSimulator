package edu.benedictine.game.engine;


import edu.benedictine.game.engine.collision.Hit;
import edu.benedictine.game.gui.Scene;
import edu.benedictine.game.media.ImageSource;

//Created by Joseph Rioux, 30 March 2013

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
		if (h.b.owner instanceof Player)
			hitPlayer(((Player)h.b.owner));
	}
	
	public void hitPlayer(Player o)
	{
		//implmented by subclasses (e.g. Life Cell)
		die(); //gets rid of item
	}
}
