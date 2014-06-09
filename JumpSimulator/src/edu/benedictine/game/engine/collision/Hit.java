//Created by Joseph Rioux, 18 April 2013
//(the owner, the opponent, owner contact x, owner contact y, opponent contact x, opponent contact y)

package edu.benedictine.game.engine.collision;

public class Hit implements Comparable
{
	public int priority;
	public BoundingBox a, b;
	public double x0, y0, x1, y1;
	
	public Hit(BoundingBox a, BoundingBox b, double x0, double y0, double x1, double y1, int priority) 
	{
		this.a = a;
		this.b = b;
		this.x0 = x0;
		this.y0 = y0;
		this.x1 = x1;
		this.y1 = y1;
		this.priority = priority;
	}

	public int compareTo(Object o)
	{
		if (o instanceof Hit)
			return this.priority-((Hit)o).priority;
		else
			throw new IllegalArgumentException("Cannot compare to an object that is not a SceneObject.");
	}
}
