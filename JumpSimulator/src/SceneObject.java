//created by Joseph Rioux, 4 March 2013
//	any object updated by the scene

public class SceneObject 
{
	int execOrder, lifeTime = -1;
	double xSpeed, ySpeed;
	Scene scn;
	
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
		scn.objs.remove(this, execOrder);
	}
	
	public void cleanUp()
	{
		
	}
}
