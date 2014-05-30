//Modified by Joseph Rioux, 13 January 2013
//   Added loadImage(), makeSpriteSheet()

package edu.benedictine.game.gui;

import edu.benedictine.jump.GraphicsResourceJump;
import java.awt.Frame;

public class Main 
{	
	public static GraphicsResourceJump aniStore;
	public static PixelCanvas gameCanvas;
	public static Director sceneMaster;
	
	public static final int CAN_WIDTH = 1024, CAN_HEIGHT = 512;
	
	public static void main(String[] args)
	{	
		
		gameCanvas = new PixelCanvas(CAN_WIDTH, CAN_HEIGHT);
		aniStore = new GraphicsResourceJump(gameCanvas);
		
		Frame f = new Frame("Jump Simulator");
        
        //Create director
        sceneMaster = new Director(f, gameCanvas, aniStore);
	}
}


