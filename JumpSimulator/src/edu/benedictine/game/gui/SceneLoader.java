//Created by Joseph Rioux, Jun 1, 2014

package edu.benedictine.game.gui;

public abstract class SceneLoader 
{
	protected Scene scn;
	
	public SceneLoader(Scene scn)
	{
		this.scn = scn;
	}
	
	public abstract void load(String filename);
}