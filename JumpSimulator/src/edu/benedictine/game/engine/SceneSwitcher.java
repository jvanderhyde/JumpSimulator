//Modified by Joseph Rioux, 23 May 2014
//added comments

package edu.benedictine.game.engine;

import edu.benedictine.game.gui.Director;

public class SceneSwitcher
{
	int timeToGo;
	String nextScene;
	double nextX, nextY;
	Director mn;
	
	public SceneSwitcher(Director main) 
	{
		this.mn = main;
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
			mn.changeScene(nextScene, mn.currentScene.currentLevel, nextX, nextY);
		timeToGo--;
	}

	public int getTimeToGo() 
	{
		return timeToGo;
	}
	
}
