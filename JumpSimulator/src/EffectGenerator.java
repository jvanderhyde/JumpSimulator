//Created by Joseph Rioux, 1 April 2013
//A "static" class for generating special effects in a Scene

public class EffectGenerator 
{
	Scene scn;
	
	public EffectGenerator(Scene scn)
	{
		this.scn = scn;
	}
	
	public void radialParticles(int num, ImageSource img, double x, double y,
								double minSpeed, double maxSpeed, int minLife, int maxLife,
								int minDraw, int maxDraw, double minGrav, double maxGrav, double maxFall)
	{
		Effect temp;
		for (int i=0; i<num; i++)
		{
			double angle = Math.random()*360;
			double speed = minSpeed+Math.random()*(maxSpeed-minSpeed);
			int draw = (int)(minDraw+Math.random()*(maxDraw-minDraw));
			int life = (int)(minLife+Math.random()*(maxLife-minLife));
			double grav = (minGrav+Math.random()*(maxGrav-minGrav));
			double xSpeed = speed*Math.cos(angle);
			double ySpeed = speed*Math.sin(angle);
			temp = new Effect(scn, draw, x, y, xSpeed, ySpeed, img, life, grav, maxFall);
		}
	}
	
	public void radialParticlesM(int num, ImageSource img, double x, double y,
			double minSpeed, double maxSpeed, int minLife, int maxLife,
			int minDraw, int maxDraw, double minGrav, double maxGrav, double maxFall, WorldObject mount)
	{
		WorldObject temp;
		for (int i=0; i<num; i++)
		{
			double angle = Math.random()*360;
			double speed = minSpeed+Math.random()*(maxSpeed-minSpeed);
			int draw = (int)(minDraw+Math.random()*(maxDraw-minDraw));
			int life = (int)(minLife+Math.random()*(maxLife-minLife));
			double grav = (minGrav+Math.random()*(maxGrav-minGrav));
			double xSpeed = speed*Math.cos(angle);
			double ySpeed = speed*Math.sin(angle);
			temp = new WorldObject(scn, mount.execOrder+1, draw, 0.0, 0.0, xSpeed, ySpeed, img, mount, x, y);
			temp.setTimer(life);
		}
	}
	
	public void radialPartRan(int num, ImageSource img, double x, double y,
			double minSpeed, double maxSpeed, int minLife, int maxLife,
			int minDraw, int maxDraw, double minGrav, double maxGrav, double maxFall)
	{
		Effect temp;
		for (int i=0; i<num; i++)
		{
		double angle = Math.random()*360;
		double speed = minSpeed+Math.random()*(maxSpeed-minSpeed);
		int draw = (int)(minDraw+Math.random()*(maxDraw-minDraw));
		int life = (int)(minLife+Math.random()*(maxLife-minLife));
		double grav = (minGrav+Math.random()*(maxGrav-minGrav));
		double xSpeed = speed*Math.cos(angle);
		double ySpeed = speed*Math.sin(angle);
		temp = new Effect(scn, draw, x, y, xSpeed, ySpeed, img, life, grav, maxFall);
		temp.setRandomFrame(128);
		}
	}
	
	public void angleParticles(int num, ImageSource img, double x, double y,
			double minSpeed, double maxSpeed, int minLife, int maxLife,
			int minDraw, int maxDraw, double minAngle, double maxAngle, double minGrav, double maxGrav, double maxFall)
	{
		Effect temp;
		double span = maxAngle - minAngle;
		for (int i=0; i<num; i++)
		{
			double angle = Math.toRadians(minAngle+(Math.random()*span));
			double speed = minSpeed+Math.random()*(maxSpeed-minSpeed);
			int draw = (int)(minDraw+Math.random()*(maxDraw-minDraw));
			int life = (int)(minLife+Math.random()*(maxLife-minLife));
			double grav = (minGrav+Math.random()*(maxGrav-minGrav));
			double xSpeed = speed*Math.cos(angle);
			double ySpeed = speed*Math.sin(angle);
			temp = new Effect(scn, draw, x, y, xSpeed, ySpeed, img, life, grav, maxFall);
		}
	}
}
