//Created by Joseph Rioux, 9 January 2013
//Contains a picture or series of pictures, and cycles through them based on the
//frame rate

package edu.benedictine.game.media;

import java.awt.image.BufferedImage;

public class Animation
{
    Image[] frames;
    BufferedImage[] pics;
    boolean cyclical;  
    double frameRate;
    int replayOn = -1;
    
    //precondition: start < pics.length
    public Animation(Image[] pics, boolean cyc, double frmRate, int rep)
    {
        //if start >= pics.length, throw illegalArgumentException?
    	this.pics = null;
        frames = pics;
        cyclical = cyc;
        frameRate = frmRate;
        replayOn = rep;
    }
    
    public Animation(BufferedImage[] pics, boolean cyc, double frmRate, int rep)
    {
        //if start >= pics.length, throw illegalArgumentException?
    	frames = null;
        this.pics = pics;
        cyclical = cyc;
        frameRate = frmRate;
        replayOn = rep;
    }
    
    //0.5 = 1 animation frame for every 2 game time frames (roughly 16 fps)
    //0.25 = 1 animation frame for every 4 game time frames (roughly 8 fps)
    //0.125 = 1 animation frame for every 8 game time frames (roughly 4 fps)
    public BufferedImage getFrame(int e, boolean flipX, boolean flipY)
    {
    	if (pics != null)
    		return pics[getFrameIndex(e)];
    	else
    		return frames[getFrameIndex(e)].getFrame(flipX, flipY);
    }
    
    public int getFrameIndex(int e)
    {	
    	int index = (int)(Math.floor(frameRate*e));
    	
    	if (pics != null)
    	{
    		if (index > pics.length-1)
        	{
        		if(cyclical)//Wrap it.
    	    	{
        			index = index % pics.length;
    	    	}
    	    	else //linear
    	    	{
    	    		index = pics.length-1;
    	    	}
        	}
        	return index;
    	}
    	else
    	{
	    	if (index > frames.length-1)
	    	{
	    		if(cyclical)//Wrap it.
		    	{
	    			index = index % frames.length;
		    	}
		    	else //linear
		    	{
		    		index = frames.length-1;
		    	}
	    	}
	    	return index;
    	}
    }
    
    public int getTime(int frame)
    {
    	return (int)Math.floor(frame/frameRate);
    }
    
    public int advance(int e)
    {
    	e++;
    	if ((replayOn != -1) && (e >= replayOn))
    		e = 0;
    	return e;
    }
}
