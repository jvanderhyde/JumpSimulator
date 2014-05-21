package edu.benedictine.game.util;

//created by Joseph Rioux, 26 May 2013
//2D vector with basic functions

public class Vector 
{
	double x, y;
	
	public Vector(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	//adds the argument to this vector
	public void add(Vector v)
	{
		x+=v.x;
		y+=v.y;
	}
	
	//subtracts the argument from this vector
	public void sub(Vector v)
	{
		x-=v.x;
		y-=v.y;
	}
	
	public static double cross(Vector v, Vector u)
	{
		return (v.x*u.y)-(u.x-v.y);
	}
}
