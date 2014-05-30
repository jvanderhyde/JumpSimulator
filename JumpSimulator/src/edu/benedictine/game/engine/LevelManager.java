//Created by Joseph Rioux, 14 July 2013
//To set up special objects in particular levels. For example, adding rain to one level, snow to another.

package edu.benedictine.game.engine;

import edu.benedictine.game.gui.Scene;

public abstract class LevelManager extends SceneObject
{	
	protected String level, prev;
	
	public LevelManager(Scene scn, String level, String prev) 
	{
		super(scn, 1);
		this.level = level;
		this.prev = prev;
	}
	
	public void initiate()
	{
		
	}
	
	public void update()
	{
		//depending on the level, set up some ad hoc occurrences
		//e.g.
		//if (lifeTime % 100 == 0) 
			//Gem gem = new Gem(scn, randomX, randomY);
		//this would create a gem object at a random location every 100 frames
		super.update();
	}
}
