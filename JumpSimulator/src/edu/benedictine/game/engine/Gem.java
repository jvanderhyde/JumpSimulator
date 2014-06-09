package edu.benedictine.game.engine;

import edu.benedictine.game.engine.collision.BoundingBox;
import edu.benedictine.game.gui.Scene;


public class Gem extends PickUp
{
	int value;
	
	public Gem(Scene scn, double xLoc, double yLoc) 
	{
		super(scn, xLoc, yLoc, 0.0, 0.0, scn.store.vialSheet[0], 2.0, 10.0, -1.0, 1.0, -1, 15.0, 720.0);
		this.value = value;
		box = new BoundingBox(scn, this, 3, new int[]{1}, 16, 0.0, 0.0, 8.0, 8.0);
		setRandomFrame(6);
	}
	
	public void reactFloor()
	{
		y = onTerrain.y0-feet;
	}
	
	public void reactRightWall()
	{
		x = atRightWall.x0-left;
		xSpeed = -xSpeed;
	}
	
	public void reactLeftWall()
	{
		x = atLeftWall.x0-right;
		xSpeed = -xSpeed;
	}

	@Override
	public void hitPlayer(Player o) 
	{
		o.gems++;
		super.hitPlayer(o);
	}
	
	public void die()
	{
		Gem gem = new Gem(scn, (int)(32+Math.random()*(256)), 64.0);
		//scn.spEffects.radialParticles(20, scn.store.jetStream, x, y, 60.0, 180.0, 16, 24, 10, 15, 1.0, 30.0, 480.0);
		super.die();
	}
}
