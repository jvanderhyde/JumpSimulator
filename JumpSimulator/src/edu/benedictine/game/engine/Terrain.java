//created by Joseph Rioux, 9 March 2013

package edu.benedictine.game.engine;

import edu.benedictine.game.gui.Scene;


public class Terrain extends SceneObject
{
	public double x0, y0, x1, y1, priorX0, priorX1, priorY0, priorY1, xSpeed, ySpeed;
	Scene scn;
	GameObject owner;
	int orientation;
	double width, height;
	int suppressForPlayer;
	String type;

	public Terrain(Scene scn, GameObject owner, int orientation, double x0, double y0, double x1, double y1) 
	{
		super(scn, 1);
		this.owner = owner;
		this.x0 = x0;
		this.y0 = y0;
		this.x1 = x1;
		this.y1 = y1;
		priorX0 = x0;
		priorX1 = x1;
		priorY0 = y0;
		priorY1 = y1;
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		this.scn = scn;
		this.orientation = orientation;
		scn.physics.add(this, orientation);
		width = Math.abs(x1-x0);
		height = Math.abs(y1-y0);
	}
	
	public void setType(String type)
	{
		this.type = type;
	}
	
	public void update()
	{
		suppressForPlayer--;
		super.update();
	}
	
	public void getReady()
	{
		xSpeed = 0.0;
		ySpeed = 0.0;
	}
	
	public void move(double xCng, double yCng)
	{
		priorX0 = x0;
		priorX1 = x1;
		priorY0 = y0;
		priorY1 = y1;
		x0+=xCng;
		x1+=xCng;
		y0+=yCng;
		y1+=yCng;
		xSpeed = xCng;
		ySpeed = yCng;
	}
	
	public void setPosition(double xLoc0, double yLoc0, double xLoc1, double yLoc1)
	{
		priorX0 = x0;
		priorX1 = x1;
		priorY0 = y0;
		priorY1 = y1;
		x0 = xLoc0;
		x1 = xLoc1;
		y0 = yLoc0;
		y1 = yLoc1;
		xSpeed = x0-priorX0;
		ySpeed = y0-priorY0;
	}
	
	public void collided(GameObject o)
	{
		if (owner != null)
		{
			owner.terrainCollide(o, this);
		}
	}
	
	public void die()
	{
		scn.physics.remove(this, orientation);
	}
}
