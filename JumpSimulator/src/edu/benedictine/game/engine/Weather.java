package edu.benedictine.game.engine;

import edu.benedictine.game.gui.Scene;

//Created by Joseph Rioux, 14 July 2013

public class Weather extends SceneObject
{	
	String level;
	
	public Weather(Scene scn, String level) 
	{
		super(scn, 1);
		this.level = level;
		
		initiate();
	}
	
	public void initiate()
	{
		
	}
	
	public void update()
	{
		super.update();
	}
}
