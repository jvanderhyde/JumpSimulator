//Created by Joseph Rioux, Jun 6, 2014
//Modified by James Vanderhyde, 17 June 2014
//  Removed dependence on other classes

package edu.benedictine.game.gui;

import edu.benedictine.game.engine.WorldObject;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class SceneGraphics 
{
	private BufferedImage buffer;
	private Graphics bufferGraphics;
	private Color backgroundColor=Color.BLACK;
	
    public SceneGraphics(int width, int height)
    {
		buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		this.bufferGraphics = buffer.createGraphics();
    }

	public void paintScene(Scene scn, Graphics g)
	{
		bufferGraphics.setColor(backgroundColor);
		bufferGraphics.fillRect(0, 0, buffer.getWidth(), buffer.getHeight());
		WorldObject[] toDraw = scn.getDraws();
		double cameraX = scn.getCamera().getX();
		double cameraY = scn.getCamera().getY();
		
		//draw objects to the offscreen buffer
		for (int i=0; i<toDraw.length; i++)
		{
			WorldObject obj = toDraw[i];
			if (obj != null)
			{
				int drawX = (int)(obj.getX()-obj.getHalfWidth()-cameraX+buffer.getWidth()/2);
				int drawY = (int)(obj.getY()-obj.getHalfHeight()-cameraY+buffer.getHeight()/2);
				obj.draw(bufferGraphics, drawX, drawY);
			}
		}
		
		g.drawImage(buffer, 0, 0, null);
	}
}
