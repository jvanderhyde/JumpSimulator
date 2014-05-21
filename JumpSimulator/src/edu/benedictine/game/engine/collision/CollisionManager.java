package edu.benedictine.game.engine.collision;

//created by Joseph Rioux, 9 March 2013

import edu.benedictine.game.util.Vector;
import edu.benedictine.game.engine.Terrain;
import edu.benedictine.game.engine.GameObject;
import edu.benedictine.game.gui.Scene;
import java.util.ArrayList;

public class CollisionManager 
{
	ArrayList<GameObject>[] groups;
	ArrayList<BoundingBox>[] boxes;
	ArrayList<Terrain>[] walls, wallsOriginal;
	Scene scn;
	
	public CollisionManager(Scene scn)
	{
		/*groups = new ArrayList[5];
		groups[0] = new ArrayList<GameObject>(); //player, and anything else
		groups[1] = new ArrayList<GameObject>(); //enemies
		groups[2] = new ArrayList<GameObject>(); //hitboxes
		groups[3] = new ArrayList<GameObject>(); //hitzones
		groups[4] = new ArrayList<GameObject>(); //other objects*/
		
		boxes = new ArrayList[4];
		boxes[0] = new ArrayList<BoundingBox>(); //active
		boxes[1] = new ArrayList<BoundingBox>(); //passive - player
		boxes[2] = new ArrayList<BoundingBox>(); //passive - enemies
		boxes[3] = new ArrayList<BoundingBox>(); //passive - other
		
		wallsOriginal = new ArrayList[4];
		wallsOriginal[0] = new ArrayList<Terrain>(); //floor
		wallsOriginal[1] = new ArrayList<Terrain>(); //ceiling
		wallsOriginal[2] = new ArrayList<Terrain>(); //left wall
		wallsOriginal[3] = new ArrayList<Terrain>(); //right wall
		this.scn = scn;
	}
	
	public void add(GameObject obj, int group)
	{
		groups[group].add(obj);
	}
	
	public void add(Terrain obj, int orientation)
	{
		wallsOriginal[orientation].add(obj);
	}
	
	public void remove(Terrain obj, int orientation)
	{
		//note: remove from the original. A copy must be made
		walls[orientation].remove(obj);
	}
	
	public void prepTerrain()
	{
		walls = wallsOriginal.clone();
	}
	
	public void getReady()
	{
		for (int i=0; i<walls.length; i++)
			for (int j=0; j<walls[i].size(); j++)
				walls[i].get(j).getReady();
	}
	
	public void checkCollision()
	{
		for (int i=0; i<groups[0].size(); i++)
			groups[0].get(i).checkCollision();
	}
	
	public Terrain collideFloor(GameObject obj, double h, double f, double l, double r)
	{
		Terrain wall;
		Terrain winner = null;
		for (int i=0; i<walls[0].size(); i++)
		{
			wall = walls[0].get(i);
			
			double head = obj.y+h;
			double feet = obj.y+f;
			double left = obj.x+l;
			double right = obj.x+r;
			double oldHead = obj.priorY+h;
			double oldFeet = obj.priorY+f;
			double oldLeft = obj.priorX+l;		
			double oldRight = obj.priorX+r;
			
			//supersampling
			//for this to work, it must be guaranteed that no terrain is shorter than 32 px
			
			//find the sampling number, the number of times to divide the object's
			//size until full coverage is gained
			int sample = 1;
			double width = Math.abs(right-left);
			while (width/sample >= 32.0)
				sample++;
			//now find the value to increment each time
			double increment = width/sample;
			//now make the checks, as many as needed for full sampling
			boolean hit = false;
			for (int s=0; s<=sample; s++)
			{
				if (verticalCheck(feet, left+(increment*s), oldFeet, oldLeft+(increment*s), wall.y0, wall.x0, wall.x1, wall.priorY0, wall.priorX0, wall.priorX1, 0))
					hit = true;
			}
			//if hit, check whether this terrain is closer than the current winner
			if (hit)
			{
				if (winner == null)
					winner = wall;
				else if ((winner != null) && ((wall.y0 - feet) < (winner.y0 - feet)))
					winner = wall;
			}
				
			/*
			//verticalCheck can be invoked for floor, walls, and ceiling by using different parameters
			if ((verticalCheck(feet, left, oldFeet, oldLeft, floor.y0, floor.x0, floor.x1, floor.priorY0, floor.priorX0, floor.priorX1, 0))
			|| (verticalCheck(feet, right, oldFeet, oldRight, floor.y0, floor.x0, floor.x1, floor.priorY0, floor.priorX0, floor.priorX1, 0)))
			{
				if (winner == null)
					winner = floor;
				else if ((winner != null) && ((floor.y0 - feet) < (winner.y0 - feet)))
					winner = floor;
			}
			*/
		}
		return winner;
	}
	
	public Terrain collideRightWall(GameObject obj, double h, double f, double l, double r)
	{
		Terrain wall;
		Terrain winner = null;
		for (int i=0; i<walls[1].size(); i++)
		{
			wall = walls[1].get(i);
			
			double head = obj.y+h;
			double feet = obj.y+f;
			double left = obj.x+l;
			double right = obj.x+r;
			double oldHead = obj.priorY+h;
			double oldFeet = obj.priorY+f;
			double oldLeft = obj.priorX+l;		
			double oldRight = obj.priorX+r;
			
			//supersampling
			//for this to work, it must be guaranteed that no terrain is shorter than 32 px
			
			//find the sampling number, the number of times to divide the object's
			//size until full coverage is gained
			int sample = 1;
			double width = Math.abs(feet-head);
			while (width/sample >= 32.0)
				sample++;
			//now find the value to increment each time
			double increment = width/sample;
			//now make the checks, as many as needed for full sampling
			boolean hit = false;
			for (int s=0; s<=sample; s++)
			{
				if (verticalCheck(left, head+(increment*s), oldLeft, oldHead+(increment*s), wall.x0, wall.y0, wall.y1, wall.priorX0, wall.priorY0, wall.priorY1, 1))
					hit = true;
			}
			//if hit, check whether this terrain is closer than the current winner
			if (hit)
			{
				if (winner == null)
					winner = wall;
				else if ((winner != null) && ((wall.x0 - oldLeft) > (winner.x0 - oldLeft)))
					winner = wall;
			}
			
			/*
			if ((verticalCheck(left, head, oldLeft, oldHead, wall.x0, wall.y0, wall.y1, wall.priorX0, wall.priorY0, wall.priorY1, 1))
			|| (verticalCheck(left, feet, oldLeft, oldFeet, wall.x0, wall.y0, wall.y1, wall.priorX0, wall.priorY0, wall.priorY1, 1)))
			{
				if (winner == null)
					winner = wall;
				else if ((winner != null) && ((wall.x0 - oldLeft) > (winner.x0 - oldLeft)))
					winner = wall;
			}
			*/
		}
		return winner;
	}
	
	public Terrain collideCeiling(GameObject obj, double h, double f, double l, double r)
	{
		Terrain wall;
		Terrain winner = null;
		for (int i=0; i<walls[2].size(); i++)
		{
			wall = walls[2].get(i);
			
			double head = obj.y+h;
			double feet = obj.y+f;
			double left = obj.x+l;
			double right = obj.x+r;
			double oldHead = obj.priorY+h;
			double oldFeet = obj.priorY+f;
			double oldLeft = obj.priorX+l;		
			double oldRight = obj.priorX+r;
			
			//supersampling
			//for this to work, it must be guaranteed that no terrain is shorter than 32 px
			
			//find the sampling number, the number of times to divide the object's
			//size until full coverage is gained
			int sample = 1;
			double width = Math.abs(right-left);
			while (width/sample >= 32.0)
				sample++;
			//now find the value to increment each time
			double increment = width/sample;
			//now make the checks, as many as needed for full sampling
			boolean hit = false;
			for (int s=0; s<=sample; s++)
			{
				if (verticalCheck(head, left+(increment*s), oldHead, oldLeft+(increment*s), wall.y0, wall.x1, wall.x0, wall.priorY0, wall.priorX1, wall.priorX0, 2))
					hit = true;
			}
			//if hit, check whether this terrain is closer than the current winner
			if (hit)
			{
				if (winner == null)
					winner = wall;
				else if ((winner != null) && ((wall.y0 - head) > (winner.y0 - head)))
					winner = wall;
			}

			/*
			if ((verticalCheck(head, left, oldHead, oldLeft, wall.y0, wall.x1, wall.x0, wall.priorY0, wall.priorX1, wall.priorX0, 2))
			|| (verticalCheck(head, right, oldHead, oldRight, wall.y0, wall.x1, wall.x0, wall.priorY0, wall.priorX1, wall.priorX0, 2)))
			{
				if (winner == null)
					winner = wall;
				else if ((winner != null) && ((wall.y0 - head) > (winner.y0 - head)))
					winner = wall;
			}
			*/
		}
		return winner;
	}
	
	public Terrain collideLeftWall(GameObject obj, double h, double f, double l, double r)
	{
		Terrain wall;
		Terrain winner = null;
		for (int i=0; i<walls[3].size(); i++)
		{
			wall = walls[3].get(i);
			
			double head = obj.y+h;
			double feet = obj.y+f;
			double left = obj.x+l;
			double right = obj.x+r;
			double oldHead = obj.priorY+h;
			double oldFeet = obj.priorY+f;
			double oldLeft = obj.priorX+l;		
			double oldRight = obj.priorX+r;

			//supersampling
			//for this to work, it must be guaranteed that no terrain is shorter than 32 px
			
			//find the sampling number, the number of times to divide the object's
			//size until full coverage is gained
			int sample = 1;
			double width = Math.abs(feet-head);
			while (width/sample >= 32.0)
				sample++;
			//now find the value to increment each time
			double increment = width/sample;
			//now make the checks, as many as needed for full sampling
			boolean hit = false;
			for (int s=0; s<=sample; s++)
			{
				if (verticalCheck(right, head+(increment*s), oldRight, oldHead+(increment*s), wall.x0, wall.y1, wall.y0, wall.priorX0, wall.priorY1, wall.priorY0, 3))
					hit = true;
			}
			//if hit, check whether this terrain is closer than the current winner
			if (hit)
			{
				if (winner == null)
					winner = wall;
				else if ((winner != null) && ((wall.x0 - oldRight) < (winner.x0 - oldRight)))
					winner = wall;
			}
			
			/*
			if ((verticalCheck(right, head, oldRight, oldHead, wall.x0, wall.y1, wall.y0, wall.priorX0, wall.priorY1, wall.priorY0, 3))
			|| (verticalCheck(right, feet, oldRight, oldFeet, wall.x0, wall.y1, wall.y0, wall.priorX0, wall.priorY1, wall.priorY0, 3)))
			{
				if (winner == null)
					winner = wall;
				else if ((winner != null) && ((wall.x0 - oldRight) < (winner.x0 - oldRight)))
					winner = wall;
			}
			*/
		}
		return winner;
	}
	
	public Terrain basicCollideRightWall(GameObject obj, double h, double f, double l, double r)
	{
		for (int i=0; i<walls[1].size(); i++)
		{
			//if (walls[1].get(i).x0 == -128.0)
				//System.out.println("wwwwwwwww: "+walls[1].get(i).x0+" "+walls[1].get(i).y0+" "+walls[1].get(i).y1+" "+obj.x+" "+l+" "+obj.y+" "+h+" "+f);
			if (((obj.x+l < walls[1].get(i).x0) && (obj.x >= walls[1].get(i).x0))
			&& ((obj.y+h < walls[1].get(i).y1) && (obj.y+f > walls[1].get(i).y0)))
				return walls[1].get(i);
		}
		return null;
	}
	
	public Terrain basicCollideLeftWall(GameObject obj, double h, double f, double l, double r)
	{
		for (int i=0; i<walls[3].size(); i++)
		{
			if (((obj.x+r > walls[3].get(i).x0) && (obj.x <= walls[3].get(i).x0))
			&& ((obj.y+h < walls[3].get(i).y0) && (obj.y+f > walls[3].get(i).y1)))
				return walls[3].get(i);
		}
		return null;
	}
	
	//note: at extreme speeds this function can lead to going through walls
	//this is because it checks the terrain in order, but does not sort based on
	//distance. That means that if there is more than one legitimate collision
	//per frame, it will accept the first one that comes in the list, not the 
	//closest in actual world distance. Fix this, or ensure speed never gets that high?
	//     update: implemented distance checking
	public boolean verticalCheck(double x, double y, double pX, double pY, double x0, double y0, double y1, double pX0, double pY0, double pY1, int type)
	{
		//prior location relative to the terrain
		double priorX = pX-pX0;
		double priorY = pY-pY0;
		//current location relative to the terrain
		double currentX = x-x0;
		double currentY = y-y0;
		//relative terrain: x0, y0 = 0.0
		double relY1 = y1-y0;
		
		//get the slope, find the y intersect. If the intersect lies between 0 and relY1
		//(the other point of the terrain line segment), then there is a collision
		double slope, yIntercept;
		slope = 0.0;
		yIntercept = currentY;
		boolean straddles = false;
		
		if (((type == 0) || (type == 3)) && ((currentX >= -0.0000001) && (priorX <= 0.0000001))) //straddles
			straddles = true;
		if (((type == 2) || (type == 1)) && ((currentX <= 0.0000001) && (priorX >= -0.0000001))) //straddles
			straddles = true;
		
		if (straddles == true) //straddles
		{
			if ((priorX-currentX) == 0.0) //run = 0
			{
				yIntercept = currentY;
			}
			else if ((priorY-currentY) != 0.0)
			{
				slope = (priorY-currentY)/(priorX-currentX);
				yIntercept = currentY-(slope*currentX);
			}
			else
				yIntercept = currentY;
			
			//note: implement yielding. terrain must yield differently based on type
			//(ceiling yields to walls, walls yield to floor)
			boolean hit = false;
			if (type == 0)
				hit = ((yIntercept > 0.0) && (yIntercept < relY1));
			if (type == 1)
				hit = ((yIntercept > 0.0) && (yIntercept <= relY1));
			if (type == 2)
				hit = ((yIntercept > 0.0) && (yIntercept < relY1));
			if (type == 3)
				hit = ((yIntercept > 0.0) && (yIntercept <= relY1));
			return hit;
		}
		return false;
	}
	
	//checks intersection
	public Vector slice2(double x, double y, double pX, double pY, double x0, double y0, double y1, int type)
	{
		//prior location relative to the terrain
		double priorX = pX;
		double priorY = pY;
		//current location relative to the terrain
		double currentX = x;
		double currentY = y;
		//relative terrain: x0, y0 = 0.0
		//double relY1 = y1-y0;
		
		//get the slope, find the y intersect. If the intersect lies between 0 and relY1
		//(the other point of the terrain line segment), then there is a collision
		double slope, yIntercept;
		slope = 0.0;
		yIntercept = currentY;
		boolean straddles = false;
		
		if (((type == 0) || (type == 3)) && ((currentX >= x0-0.0000001) && (priorX <= x0+0.0000001))) //straddles
			straddles = true;
		if (((type == 2) || (type == 1)) && ((currentX <= x0+0.0000001) && (priorX >= x0-0.0000001))) //straddles
			straddles = true;
		
		if (straddles == true) //straddles
		{
			if ((priorX-currentX) == 0.0) //run = 0
			{
				yIntercept = currentY;
			}
			else if ((priorY-currentY) != 0.0)
			{
				slope = (priorY-currentY)/(priorX-currentX);
				yIntercept = currentY-(slope*currentX);
			}
			else
				yIntercept = currentY;
			
			//note: implement yielding. terrain must yield differently based on type
			//(ceiling yields to walls, walls yield to floor)
			boolean hit = false;
			if (type == 0)
			{
				if ((yIntercept >= y0) && (yIntercept <= y1))
					return new Vector(yIntercept, x0);
			}
			if (type == 1)
			{
				if ((yIntercept > y0) && (yIntercept < y1))
					return new Vector(x0, yIntercept);
			}
			if (type == 2)
			{
				if ((yIntercept >= y0) && (yIntercept <= y1))
					return new Vector(yIntercept, x0);
			}
			if (type == 3)
			{
				if ((yIntercept > y0) && (yIntercept < y1))
					return new Vector(x0, yIntercept);
			}
		}
		return null;
	}
	
	public Vector slice(double x, double y, double pX, double pY, double x0, double y0, double y1, int type)
	{
		//if ((y0 == 64.0) && (y1 == 160.0))
			//System.out.println("para: "+x+" "+y+" "+pX+" "+pY+" "+x0+" "+y0+" "+y1);
		//prior location relative to the terrain
		double priorX = pX-x0;
		double priorY = pY-y0;
		//current location relative to the terrain
		double currentX = x-x0;
		double currentY = y-y0;
		//relative terrain: x0, y0 = 0.0
		double relY1 = y1-y0;
		//if ((y0 == 64.0) && (y1 == 160.0))
			//System.out.println("rel: "+priorX+" "+priorY+" "+currentX+" "+currentY+" "+relY1);
		
		//get the slope, find the y intersect. If the intersect lies between 0 and relY1
		//(the other point of the terrain line segment), then there is a collision
		double slope, yIntercept;
		slope = 0.0;
		yIntercept = currentY;
		boolean straddles = false;
		
		if (((type == 0) || (type == 3)) && ((currentX >= -0.0000001) && (priorX <= 0.0000001))) //straddles
			straddles = true;
		if (((type == 2) || (type == 1)) && ((currentX <= 0.0000001) && (priorX >= -0.0000001))) //straddles
			straddles = true;
		
		if (straddles == true) //straddles
		{
			if ((priorX-currentX) == 0.0) //run = 0
			{
				yIntercept = currentY;
			}
			else if ((priorY-currentY) != 0.0)
			{
				slope = (priorY-currentY)/(priorX-currentX);
				yIntercept = currentY-(slope*currentX);
			}
			else
				yIntercept = currentY;
			
			if (type == 0)
			{
				if ((yIntercept >= 0.0) && (yIntercept <= relY1))
					return new Vector(y0+yIntercept, x0);
			}
			if (type == 1)
			{
				if ((yIntercept > 0.0) && (yIntercept < relY1))
					return new Vector(x0, y0+yIntercept);
			}
			if (type == 2)
			{
				if ((yIntercept >= 0.0) && (yIntercept <= relY1))
					return new Vector(y0+yIntercept, x0);
			}
			if (type == 3)
			{
				if ((yIntercept > 0.0) && (yIntercept < relY1))
					return new Vector(x0, y0+yIntercept);
			}
		}
		return null;
	}
	
	//hitbox handling
	public void checkBoxes(BoundingBox box, int[] cats, ArrayList<BoundingBox> ignores)
	{
		for (int c : cats)
			for (int i=0; i<boxes[c].size(); i++)
			{
				BoundingBox target = boxes[c].get(i);
				//check if target box is on
				if ((box != target) && (target.on)) //can't collide with yourself, genius
				{
					//ignore if already checked
					boolean alreadyHit = false;
					for (BoundingBox o : ignores)
						if (target == o)
							alreadyHit = true; //already checked, ignore
					
					if (!alreadyHit)
					{
						target.checks.add(box);
						Hit h = checkBox(box, target);
						if (h != null)
						{
							scn.hits.add(h.a.priority, new Hit(h.a, h.b, h.x0, h.y0, h.x1, h.y1));
							scn.hits.add(h.b.priority, new Hit(h.b, h.a, h.x1, h.y1, h.x0, h.y0));
						}
						/*Hit h = checkBox(box, target);
						if (h != null)
						{
							box.owner.hits.add(new Hit(h.a, h.b, h.x0, h.y0, h.x1, h.y1));
							target.owner.hits.add(new Hit(h.b, h.a, h.x1, h.y1, h.x0, h.y0));
						}*/
					}	
				}
			}
	}

	//check individual hits - with supersampling
	public Hit checkBox(BoundingBox a, BoundingBox b)
	{
		int sample;
		if (a.sample > b.sample)
			sample = a.sample;
		else
			sample = b.sample;

		//System.out.print(a.owner+" "+b.owner);
		//System.out.print("   "+a.owner.x+" "+a.owner.y+" "+b.owner.x+" "+b.owner.y);
		//System.out.print("   "+a.x+" "+a.y+" "+b.x+" "+b.y);
		//System.out.print("   "+a.sample+" "+b.sample+" "+sample);
		double increment = 1.0/sample;
		for (double i=increment; i<=1.0; i+=increment)
		{
			//System.out.print("   "+a.halfWidth+" "+a.halfHeight+" "+b.halfWidth+" "+b.halfHeight);
			double x0 = a.priorX+(a.xCng*i);
			double y0 = a.priorY+(a.yCng*i);
			double x1 = b.priorX+(b.xCng*i);
			double y1 = b.priorY+(b.yCng*i);
			//System.out.print("   "+x0+" "+y0+" "+x1+" "+y1);
			if (((x0+a.halfWidth > x1-b.halfWidth) && (x0-a.halfWidth < x1+b.halfWidth))
			&& ((y0+a.halfHeight > y1-b.halfHeight) && (y0-a.halfHeight < y1+b.halfHeight)))
			{
				//System.out.print("--got hit\n");
				return new Hit(a, b, x0, y0, x1, y1);
			}
		}
		return null;
	}
	
	//returns the location of the first point of intersection with terrain
	//null if no intersection
	public Slice lineOfSight(double fromX, double fromY, double toX, double toY)
	{
		ArrayList<Slice> slices = new ArrayList<Slice>();
		
		//get all walls
		
		//floors
		Slice winner = null;
		for (Terrain wall : walls[0])
		{
			Vector v = slice(toY, toX, fromY, fromX, wall.y0, wall.x0, wall.x1, 0);
			if (v != null)
			{
				if (winner == null)
					winner = new Slice(wall, v.x, v.y);
				else if ((winner != null) && ((wall.y0 - fromY) < (winner.wall.y0 - fromY)))
					winner = new Slice(wall, v.x, v.y);
			}
		}
		if (winner != null)
			slices.add(winner);
		
		//right walls
		winner = null;
		for (Terrain wall : walls[1])
		{
			Vector v = slice(toX, toY, fromX, fromY, wall.x0, wall.y0, wall.y1, 1);
			if (v != null)
			{
				if (winner == null)
					winner = new Slice(wall, v.x, v.y);
				else if ((winner != null) && ((wall.x0 - fromX) > (winner.wall.x0 - fromX)))
					winner = new Slice(wall, v.x, v.y);
			}
		}
		if (winner != null)
			slices.add(winner);
		
		//ceilings
		winner = null;
		for (Terrain wall : walls[2])
		{
			Vector v = slice(toY, toX, fromY, fromX, wall.y0, wall.x1, wall.x0, 2);
			if (v != null)
			{
				if (winner == null)
					winner = new Slice(wall, v.x, v.y);
				else if ((winner != null) && ((wall.y0 - fromY) > (winner.wall.y0 - fromY)))
					winner = new Slice(wall, v.x, v.y);
			}
		}
		if (winner != null)
			slices.add(winner);
		
		//left walls
		winner = null;
		for (Terrain wall : walls[3])
		{
			Vector v = slice(toX, toY, fromX, fromY, wall.x0, wall.y1, wall.y0, 3);
			if (v != null)
			{
				if (winner == null)
					winner = new Slice(wall, v.x, v.y);
				else if ((winner != null) && ((wall.x0 - fromX) < (winner.wall.x0 - fromX)))
					winner = new Slice(wall, v.x, v.y);
			}
		}
		if (winner != null)
			slices.add(winner);
		
		//among the four winners, select the champion (the closest intersect)
		winner = null;
		for (Slice s : slices)
		{
			if (winner == null)
				winner = s;
			else
				if (Math.hypot(fromX-s.x, fromY-s.y) < Math.hypot(fromX-winner.x, fromY-winner.y))
					winner = s;	
		}
		return winner;
	}
}