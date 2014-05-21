//Created by Joseph Rioux, 22 August 2013

package edu.benedictine.game.util;

public class AngleFunctions 
{
	public AngleFunctions()
	{
		
	}
	
	public static double getTargetAngle(double x0, double y0, double x1, double y1)
	{
		double rise = y1-y0;
		double run = x1-x0;
		return (Math.toDegrees(Math.atan2(rise, run))+1080.0) % 360.0;
	}
}
