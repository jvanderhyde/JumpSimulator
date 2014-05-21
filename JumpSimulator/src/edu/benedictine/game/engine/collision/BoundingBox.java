package edu.benedictine.game.engine.collision;

//Created by Joseph Rioux, 30 March 2013

import edu.benedictine.game.engine.GameObject;
import edu.benedictine.game.gui.Scene;
import java.util.ArrayList;

public class BoundingBox
{
	Scene scn;
	double xOffset, yOffset, halfWidth, halfHeight;
	double x, y, priorX, priorY, xSpeed, ySpeed, xCng, yCng;
	int priority;
	int sample, group;
	int[] categories;
	GameObject owner;
	ArrayList<BoundingBox> checks;
	boolean on = true; //default is on

	public BoundingBox(Scene scn, GameObject owner, int group, int[] cats, int priority, double xOffset, double yOffset, double halfWidth, double halfHeight) 
	{
		this.scn = scn;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.halfWidth = halfWidth;
		this.halfHeight = halfHeight;
		this.owner = owner;
		this.group = group;
		categories = cats;
		this.priority = priority;
		x = owner.x+xOffset;
		y = owner.y+yOffset;
		scn.collisionManager.boxes[group].add(this);
		scn.boxes.add(this);
		checks = new ArrayList<BoundingBox>();
	}
	
	public void setCategories(int[] groups)
	{
		categories = groups;
	}
	
	public void getReady()
	{	
		priorX = x;
		priorY = y;
		x = owner.x+xOffset;
		y = owner.y+yOffset;
		setSample();
	}

	public void setSample()
	{
		//calculate supersampling factor for collision
		//field *sample* = fraction of the distance traveled this frame
		//that is covered
		xCng = x-priorX;
		yCng = y-priorY;
		int sampleX = 1, sampleY = 1;
		sampleX = sample(Math.abs(xCng), sampleX, (halfWidth*2));
		sampleY = sample(Math.abs(yCng), sampleY, (halfHeight*2));
		
		if (sampleX > sampleY)
			sample = sampleX;
		else
			sample = sampleY;
	}
	
	public int sample(double distance, int fraction, double size)
	{
		//it is definitely covered by half-size at the start and end points
		if (distance-size-((fraction-1)*size) < 0.0)
			return fraction;
		else
			return sample(distance, fraction+1, size);
	}
	
	public void checkHits()
	{
		//hits may already have objects placed in it by other checks
		//append any found in this check to it
		if (on)
		{
			scn.collisionManager.checkBoxes(this, categories, checks);
			//reinstantiate checks to be ready for next frame
			checks = new ArrayList<BoundingBox>();
		}
		
		//explanation for checking:
		
		//field *categories* is an array of ints, representing the groups this object 
		//can collide with
		
		//field *hits* is an arraylist of GameObjects that stores the objects this object
		//has collided with this frame. It will be updated each frame using this method
			//in addition, however, it will store any hits calculated by other objects
			//that checked collision before this one.
		
		//field *checks* is an arraylist of GameObjects that stores the objects this object
		//has already been checked by (to avoid double checks)
		
		//CollisionManager.checkBoxes(this, categories, checks.toArray());
			//checkBoxes will first load all the checks that have been calculated
			//by previous collision checks (no double checking)
		
			//checkBoxes will then loop through categories
				//for each, it will loop through all objects in that category,
				//ignoring any objects that have already been checked (whether 
				//they were hits or not). It will do this by searching checks[].
		
				//It will compare this collision box with each (supersampling as
				//necessary). Any hits will be stored in an arraylist. This arraylist
				//will contain all objects from all valid categories that collided
				//this object this frame.
			
			//When all hits in all valid categories have been calculated,
			//an array will be returned to this object using the arraylist's toArray() 
			//method. It will be appended to the hits arraylist.
		
		//GameObjects will then finally run through the returned list and handle each
		//hit in hits[] accordingly using the handleHits() method. GameObjects will not have
		//implemented this method. That's for the subclasses like Player, Hitbox, and Muss
		//to do.
		
		//finally, hits will be initialized back to 0 to prepare for the next frame
	}
	
	public void die()
	{
		scn.boxes.remove(this);
		scn.collisionManager.boxes[group].remove(this);
	}
}
