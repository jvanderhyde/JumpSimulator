package edu.benedictine.game.media;

//Created by Joseph Rioux, 22 March 2013
//A simple image displayer

import edu.benedictine.game.gui.Scene;
import java.awt.image.BufferedImage;

public class Sprite
{
	BufferedImage pic;
	double x, y;
	
	public Sprite(Scene scn, int draw, BufferedImage pic, double x, double y) 
	{
		this.pic = pic;
		this.x = x;
		this.y = y;
		scn.sprites.add(draw, this);
	}
}
