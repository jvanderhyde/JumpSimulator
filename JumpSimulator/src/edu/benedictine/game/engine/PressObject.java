//Created by Joseph Rioux, 27 June 2013

package edu.benedictine.game.engine;

import edu.benedictine.game.engine.collision.Hit;
import edu.benedictine.game.gui.Scene;
import edu.benedictine.game.media.ImageSource;

public class PressObject extends GameObject
{

	public PressObject(Scene scn, int exec, int draw, double xLoc, double yLoc, ImageSource img) 
	{
		super(scn, exec, draw, xLoc, yLoc, 0.0, 0.0, img, -16, 16, -8, 8);
	}
	
	public void handleHit(Hit h)
	{
		if (h.b.owner instanceof Player)
		{
			hitPlayer(((Player)h.b.owner));
		}
	}
	
	public void hitPlayer(Player o)
	{
		if (scn.d && scn.downPressed <= 0 && o.onGround && o.locked <= 0)
		{
			press();
		}
	}
	
	public void press()
	{
		
	}
}
