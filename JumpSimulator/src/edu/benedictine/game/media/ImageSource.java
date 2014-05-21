package edu.benedictine.game.media;

//Created by Joseph Rioux, 24 June 2013
//a wrapper class for either a BufferedImage, an Image(mine), or an Animation

import java.awt.image.BufferedImage;

public class ImageSource 
{
	BufferedImage pic;
	Image img;
	Animation ani;
	
	public ImageSource(BufferedImage pic)
	{
		this.pic = pic;
	}

	public ImageSource(Image img) 
	{
		this.img = img;
	}

	public ImageSource(Animation ani) 
	{
		this.ani = ani;
	}
	
	public ImageSource(ImageSource src) 
	{
		if (src.pic != null)
			this.pic = src.pic;
		if (src.img != null)
			this.img = src.img;
		if (src.ani != null)
			this.ani = src.ani;
	}
	
	public BufferedImage getFrame(int time, boolean flipX, boolean flipY)
	{
		if ((ani == null) && (img == null) && (pic == null))
			return null;
		else if ((ani == null) && (img == null))
			return pic;
		else if ((ani == null) && (pic == null))
			return img.getFrame(flipX, flipY);
		else if ((img == null) && (pic == null))
			return ani.getFrame(time, flipX, flipY);
		else
			return null;
	}
	
	public int getFrameIndex(int time)
	{
		if (ani != null)
			return ani.getFrameIndex(time);
		else
			return -1;
	}
	
	public int getImageTime(int frame)
	{
		if (ani != null)
			return ani.getTime(frame);
		else
			return -1;
	}
	
	public int advance(int e)
	{
		if (ani == null)
			return e+1;
		else
			return ani.advance(e);
	}
}
