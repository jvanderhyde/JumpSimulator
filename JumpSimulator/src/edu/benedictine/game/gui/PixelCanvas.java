package edu.benedictine.game.gui;

public class PixelCanvas extends java.awt.Canvas
{
	public PixelCanvas(int width, int height)
	{
		super();
		this.setSize(width, height);
	}
	
	//world x limit 0 to 1024
	public int w2VX(double x)
	{
		return (int)Math.round(this.getWidth()*x/Main.CAN_WIDTH);
	}
	//world y limit 0 to 640
	public int w2VY(double y)
	{
		return (int)Math.round(this.getHeight()*y/Main.CAN_HEIGHT);
	}
	//world x limit 0 to 1.6
	public double v2WX(int x)
	{
		return ((double)x/(double)this.getWidth())*Main.CAN_WIDTH;
	}
	//world y limit 0 to 1
	public double v2WY(int y)
	{
		return ((double)y/(double)this.getHeight())*Main.CAN_HEIGHT;
	}
}
