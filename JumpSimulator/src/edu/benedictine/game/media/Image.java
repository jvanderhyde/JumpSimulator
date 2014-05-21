package edu.benedictine.game.media;

//created by Joseph Rioux, 6 March 2013
//	contains an array with the reversed images

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Image 
{
	BufferedImage[] pics;

	public Image(BufferedImage pic) 
	{
		this.pics = new BufferedImage[4];
		this.pics[0] = pic;
		this.pics[1] = reverseImage(pic, true);
		this.pics[2] = reverseImage(pic, false);
		this.pics[3] = reverseImage(pics[2], true);
	}
	
	public BufferedImage getFrame(boolean flipX, boolean flipY)
	{
		if ((flipX == true) && (flipY == true))
			return pics[3];
		else if (flipY == true)
			return pics[2];
		else if (flipX == true)
			return pics[1];
		else
			return pics[0];
	}
	
	public BufferedImage reverseImage(BufferedImage source, boolean x)
	{			
		BufferedImage result = new BufferedImage(source.getWidth(), source.getHeight(), BufferedImage.TYPE_INT_ARGB);
		for (int i=0; i<source.getWidth(); i++)
			for (int j=0; j<source.getHeight(); j++)
			{
				int rgb = source.getRGB(i, j);
				Color col = new Color(rgb);
				int alpha = getAlpha(rgb);
				col = new Color(col.getRed(), col.getGreen(), col.getBlue(), alpha);
				if (x == true)
					result.setRGB(result.getWidth()-i-1, j, col.getRGB());
				if (x == false)
					result.setRGB(i, result.getHeight()-j-1, col.getRGB());
			}
		return result;
	}
	
	public int getAlpha(int col)
	{
		return (col>>24)&0xff;
	}
}
