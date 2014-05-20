//created by Joseph Rioux, 2 August 2013
//stores information about a line-terrain intersection

public class Slice 
{
	Terrain wall;
	double x, y;
	
	public Slice(Terrain wall, double interX, double interY) 
	{
		this.wall = wall;
		this.x = interX;
		this.y = interY;
	}
}
