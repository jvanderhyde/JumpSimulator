
public class LifeCell extends PickUp
{
	int value;
	
	public LifeCell(Scene scn, double xLoc, double yLoc, double xCng, double yCng, int lifeTime, int value) 
	{
		super(scn, xLoc, yLoc, xCng, yCng, scn.store.vialSheet[0], 2.0, 8.0, -1.0, 1.0, lifeTime, 15.0, 720.0);
		this.value = value;
		box = new BoundingBox(scn, this, 3, new int[]{1}, 16, 0.0, 0.0, 8.0, 8.0);
		setRandomFrame(6);
	}
	
	public void reactFloor()
	{
		y = onTerrain.y0-feet;
		ySpeed = -180.0;
		/*if (xSpeed > 0.0)
			xSpeed--;
		if (xSpeed < 0.0)
			xSpeed++;*/
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
		o.life += value;
		super.hitPlayer(o);
	}
	
	public void die()
	{
		scn.spEffects.radialParticles(20, scn.store.jetStream, x, y, 60.0, 180.0, 16, 24, 10, 15, 1.0, 30.0, 480.0);
		super.die();
	}
}
