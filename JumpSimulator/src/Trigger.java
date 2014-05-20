//Created by Joseph Rioux, 14 July 2013

public class Trigger extends GameObject
{
	boolean fired = false;
	
	public Trigger(Scene scn, double xLoc, double yLoc, double width, double height) 
	{
		super(scn, 5, 0, xLoc, yLoc, 0.0, 0.0, null, -16, 16, -8, 8);
		box = new BoundingBox(scn, this, 3, new int[]{1}, 16, 0.0, 0.0, width/2, height/2);
	}
	
	public void handleHit(Hit h)
	{
		if (h.b.owner instanceof Player)
			hitPlayer(((Player)h.b.owner));
	}
	
	public void hitPlayer(Player o)
	{
		if (o instanceof Player)
		{
			if (!fired)
			{
				fired = true;
				fire();
			}
		}
	}
	
	public void fire()
	{
		
	}
}
