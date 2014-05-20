//Modified by Joseph Rioux, 13 January 2013
//   Added loadImage(), makeSpriteSheet()

import javax.swing.JFrame;

public class Main 
{	
	public static GraphicsResource aniStore;
	public static PixelCanvas gameCanvas;
	public static Director sceneMaster;
	
	public static final int CAN_WIDTH = 1024, CAN_HEIGHT = 512;
	
	public static void main(String[] args)
	{	
		
		gameCanvas = new PixelCanvas(CAN_WIDTH, CAN_HEIGHT);
		aniStore = new GraphicsResource(gameCanvas);
		
		JFrame f = new JFrame("Jump Simulator");
        
        //Create director
        sceneMaster = new Director(f, gameCanvas, aniStore);
	}
}


