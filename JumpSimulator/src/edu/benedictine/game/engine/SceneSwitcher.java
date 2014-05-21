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
		timeToGo = time;
		nextScene = index;
		nextX = nX;
		nextY = nY;
	}
	
	public void update()
	{
		if (timeToGo == 0)
			mn.changeScene(nextScene, mn.currentScene.currentLevel, nextX, nextY);
		timeToGo--;
	}

	public int getTimeToGo() 
	{
		return timeToGo;
	}
	
}
