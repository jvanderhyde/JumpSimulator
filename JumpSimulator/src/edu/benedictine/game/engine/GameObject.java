//created by Joseph Rioux, 9 March 2013
//	has terrain-collision capabilities

package edu.benedictine.game.engine;

import edu.benedictine.game.media.ImageSource;
import edu.benedictine.game.engine.collision.BoundingBox;
import edu.benedictine.game.engine.collision.Hit;
import edu.benedictine.game.util.Vector;
import edu.benedictine.game.gui.Scene;
import java.util.ArrayList;

public class GameObject extends WorldObject
{
	double addSpeedX, addSpeedY;
	double xCng, yCng;
	double feet;
	double head;
	double left;
	double right;
	public boolean hitTerrain = true; //default is to hit terrain
	boolean hitsBoxes = false;
	Terrain onTerrain = null;
	Terrain atRightWall = null;
	Terrain atCeiling = null;
	Terrain atLeftWall = null;
	protected boolean onGround;
	boolean atRight;
	boolean atCeil;
	boolean atLeft;
	
	BoundingBox box;
	int sample;
	//int group;
	int[] categories;
	ArrayList<Hit> hits;
	ArrayList<GameObject> checks;
	
	ArrayList<WorldObject> mounts;
	ArrayList<Vector> mountOffsets;
	
	ArrayList<Vector> speeds;
	
	public GameObject(Scene scn, int exec, int draw, double xLoc, double yLoc, double xCng, double yCng, ImageSource img, double head, double feet, double left, double right) 
	{
		super(scn, exec, draw, xLoc, yLoc, xCng, yCng, img);
		scn.addGame(this);
		this.feet = feet;
		this.head = head;
		this.left = left;
		this.right = right;
		hits = new ArrayList<Hit>();
		speeds = new ArrayList<Vector>();
		mounts = new ArrayList<WorldObject>();
		box = null;
		type = "";
	}
	
	public void setCategories(int[] groups)
	{
		categories = groups;
		for (int i=0; i<categories.length; i++)
			if (i == 2)
				hitsBoxes = true;
	}
	
	public void setTerrainLimits(double l, double r, double h, double f)
	{
		left = l;
		right = r;
		head = h;
		feet = f;
	}
	
	public void update()
	{	
		super.update();
	}
	
	public void move(double xCng, double yCng)
	{
		double xChange;
		double yChange;
		//add speed from terrain
		if (onTerrain != null)
		{
			xChange = xCng+onTerrain.xSpeed;
			yChange = yCng+onTerrain.ySpeed;
		}
		else
		{
			xChange = xSpeed;
			yChange = ySpeed;
		}	
		//add all the speeds to get the total speed for the object
		Vector add = new Vector(0,0);
		for (Vector v : speeds)
			add.add(v);
		//reset speeds
		speeds = new ArrayList<Vector>();
		//setup for position update
		xChange+=add.x;
		yChange+=add.y;
		priorX = x;
		priorY = y;
		
		super.move(xChange, yChange);
	}
	
	public void checkCollision()
	{	
		onGround = false;
		atRight = false;
		atCeil = false;
		atLeft = false;
		
		atLeftWall = scn.physics.collideLeftWall(this, head, feet, left, right);
		if (atLeftWall != null)
			reactLeftWall();
		atRightWall = scn.physics.collideRightWall(this, head, feet, left, right);
		if (atRightWall != null)
			reactRightWall();
		onTerrain = scn.physics.collideFloor(this, head, feet, left, right);
		if (onTerrain != null)
			reactFloor();
		atCeiling = scn.physics.collideCeiling(this, head, feet, left, right);
		if (atCeiling != null)
			reactCeiling();
		
		//if it hit the floor or ceiling, check again, because the situation for the wall
		//will be different in cases of a corner
		if ((onTerrain != null) || (atCeiling != null))
		{
			if (atLeftWall == null)
			{
				atLeftWall = scn.physics.collideLeftWall(this, head, feet, left, right);
				if (atLeftWall != null)
					reactLeftWall();
			}
			if (atRightWall == null)
			{
				atRightWall = scn.physics.collideRightWall(this, head, feet, left, right);
				if (atRightWall != null)
					reactRightWall();
			}
		}
		
		if (onTerrain != null)
			hitTerrain(onTerrain);
		if (atRightWall != null)
			hitTerrain(atRightWall);
		if (atCeiling != null)
			hitTerrain(atCeiling);
		if (atLeftWall != null)
			hitTerrain(atLeftWall);
	}
	
	public void reactFloor()
	{
		onGround = true;
		setY(onTerrain.y0-feet);
		ySpeed = 0.0;
	}
	
	public void reactRightWall()
	{
		atRight = true;
		setX(atRightWall.x0-left);
		xSpeed = 0.0;
	}
	
	public void reactCeiling()
	{
		atCeil = true;
		setY(atCeiling.y0-head);
		ySpeed = 0.0;
	}
	
	public void reactLeftWall()
	{
		atLeft = true;
		setX(atLeftWall.x0-right);
		xSpeed = 0.0;
	}
	
	public void hitTerrain(Terrain t)
	{
		t.collided(this);
	}
	
	//called by an object that collides with terrain owned by this object
	public void terrainCollide(GameObject o, Terrain t)
	{
		//implemented by subclasses like MoveBlock
	}
	
	public void handleHit(Hit h)
	{
		
	}
	
	public void postMove()
	{
		for (WorldObject o : mounts)
		{
			o.x = x+o.xOffset;
			o.y = y+o.yOffset;
		}
	}
	
	public void postCollision()
	{
		for (WorldObject o : mounts)
		{
			o.x = x+o.xOffset;
			o.y = y+o.yOffset;
		}
	}
	
	public double getEdge(boolean left)
	{
		//return distance to the edge
		if (onGround)
		{
			if (left)
				return onTerrain.x0-x;
			else
				return onTerrain.x1-x;
		}
		return 0;
	}
	
	public void cleanUp()
	{
		if (onTerrain != null)
			onGround = true;
		else
			onGround = false;
	}

	public void die()
	{
		scn.removeGame(this);
		if (box != null)
			box.die();
		super.die();
	}

	public double getLeft() 
	{
		return left;
	}

	public double getRight() 
	{
		return right;
	}

	public double getFeet() 
	{
		return feet;
	}

	public double getHead() 
	{
		return head;
	}
	
	@Override
	public int compareTo(Object o)
	{
		if (o instanceof GameObject)
			return this.execOrder-((GameObject)o).execOrder;
		else
			return super.compareTo(o);
	}
}
