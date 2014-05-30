//Created by Joseph Rioux, 14 July 2013
//To set up special objects in particular levels. For example, adding rain to one level, snow to another.

package edu.benedictine.jump;

import edu.benedictine.game.engine.*;
import edu.benedictine.game.gui.Scene;

public class LevelManagerJump extends LevelManager
{
	public LevelManagerJump(Scene scn, String level, String prev) 
	{
		super(scn, level, prev);
	}
	
	public void initiate()
	{
		if (level.equals("pictest"))
		{
			//set up the clouds. Two scrolling images per bank of clouds
			
			//clouds closest to the bottom
			WorldObject wo = new WorldObject(scn, 1, 0, -1024.0, 576.0, -120.0, 0.0, scn.store.nebulaImages[3]);
			wo.setScreenObject(true);
			wo.setBounds(-2048, 2048, -2048, 2048);
			wo = new WorldObject(scn, 1, 0, 1024.0, 576.0, -120.0, 0.0, scn.store.nebulaImages[3]);
			wo.setScreenObject(true);
			wo.setBounds(-2048, 2048, -2048, 2048);
			
			//next level
			wo = new WorldObject(scn, 1, 0, -960.0, 544.0, -90.0, 0.0, scn.store.nebulaImages[2]);
			wo.setScreenObject(true);
			wo.setFlipX(true);
			wo.setBounds(-2048, 2048, -2048, 2048);
			wo = new WorldObject(scn, 1, 0, 1088.0, 544.0, -90.0, 0.0, scn.store.nebulaImages[2]);
			wo.setScreenObject(true);
			wo.setFlipX(true);
			wo.setBounds(-2048, 2048, -2048, 2048);
			
			//next level
			wo = new WorldObject(scn, 1, 0, -1056.0, 512.0, -60.0, 0.0, scn.store.nebulaImages[1]);
			wo.setScreenObject(true);
			wo.setBounds(-2048, 2048, -2048, 2048);
			wo = new WorldObject(scn, 1, 0, 992.0, 512.0, -60.0, 0.0, scn.store.nebulaImages[1]);
			wo.setScreenObject(true);
			wo.setBounds(-2048, 2048, -2048, 2048);
			
			//clouds closest to the middle of the screen
			wo = new WorldObject(scn, 1, 0, -1056.0, 480.0, -30.0, 0.0, scn.store.nebulaImages[0]);
			wo.setScreenObject(true);
			wo.setBounds(-2048, 2048, -2048, 2048);
			wo = new WorldObject(scn, 1, 0, 992.0, 480.0, -30.0, 0.0, scn.store.nebulaImages[0]);
			wo.setScreenObject(true);
			wo.setBounds(-2048, 2048, -2048, 2048);
			
			//set up one mountain
			wo = new WorldObject(scn, 1, 0, 640.0, 240.0, 0.0, 0.0, scn.store.mountain);
			wo.setScreenObject(true);
			
			//set up the other mountain
			wo = new WorldObject(scn, 1, 0, 320.0, 320.0, 0.0, 0.0, scn.store.mountain);
			wo.setScreenObject(true);
		}
	}
}
