//Modified by Joseph Rioux, 23 May 2014
//added comments

package edu.benedictine.game.engine;

import edu.benedictine.game.gui.Scene;

public class SceneSwitcher extends SceneObject
{
	int timeToGo;
	String nextScene;
	double nextX, nextY;
	
	public SceneSwitcher(Scene scn) 
	{
		super(scn, 0);
		timeToGo = -1;
		nextScene = "0";
	}
	
	public void mark(String index, int time, double nX, double nY)
	{
		//set the number of frames that will elapse before the scene is changed
		timeToGo = time;
		//set the next scene to load, as a string
		nextScene = index;
		//set the player's position in the new scene
		nextX = nX;
		nextY = nY;
	}
	
	public void update()
	{
		//when the timer runs out, change the scene
		if (timeToGo == 0)
			scn.panel.changeScene(nextScene, scn.currentLevel, nextX, nextY);
		timeToGo--;
	}

	public int getTimeToGo() 
	{
		return timeToGo;
	}
	
}
