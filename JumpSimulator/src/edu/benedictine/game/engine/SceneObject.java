//created by Joseph Rioux, 4 March 2013
//modified by Joseph Rioux, 23 May 2014
	//added isAlive(), added comments

package edu.benedictine.game.engine;

import edu.benedictine.game.gui.Scene;

//any object updated by the scene

public class SceneObject 
{
	int execOrder, lifeTime = -1;
	public boolean alive=true;
	double xSpeed, ySpeed;
	protected Scene scn;
	
	public SceneObject(Scene scn, int exec)
	{
		scn.objs.add(exec, this);
		this.scn = scn;
		execOrder = exec;
	}
	
	public void setTimer(int time)
	{
		lifeTime = time;
	}
	
	public void update()
	{
		timer();
	}
	
	public void timer()
	{
		if (lifeTime == 0)
			die();
		lifeTime--;
	}
	
	public void move(double xCng, double yCng)
	{
		
	}
	
	public void die()
	{
		//set alive to false so that any object referencing this one through
		//another pointer can still tell that it has been removed from the current scene
		alive = false;
		//remove from the current scene
		scn.objs.remove(this, execOrder);
	}
	
	public void cleanUp()
	{
		
	}

	public double getXSpeed() 
	{
		return xSpeed;
	}

	public double getYSpeed() 
	{
		return ySpeed;
	}
	
	public boolean isAlive()
	{
		//this is used to for check whether an object that you are referencing through
		//another pointer is still in the current scene.
		return alive;
	}
}
