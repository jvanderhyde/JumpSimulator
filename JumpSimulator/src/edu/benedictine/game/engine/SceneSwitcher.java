//Modified by Joseph Rioux, 23 May 2014
//added comments

package edu.benedictine.game.engine;

import edu.benedictine.game.gui.Scene;
import edu.benedictine.game.gui.ScenePanel;

public class SceneSwitcher extends SceneObject
{
	int timeToGo;
	String nextScene;
	double nextX, nextY;
	private ScenePanel scenePanel;
	
	public SceneSwitcher(Scene scn, ScenePanel scenePanel) 
	{
		super(scn, 0);
		this.scenePanel = scenePanel;
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
			scenePanel.changeScene(nextScene, scn.currentLevel, nextX, nextY);
		timeToGo--;
	}

	public int getTimeToGo() 
	{
		return timeToGo;
	}
	
}
