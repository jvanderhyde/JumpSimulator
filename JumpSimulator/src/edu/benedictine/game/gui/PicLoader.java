//Created by Joseph Rioux, Jun 6, 2014
//loads a Scene from a picture

package edu.benedictine.game.gui;

import edu.benedictine.game.engine.Terrain;
import edu.benedictine.game.engine.WorldObject;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class PicLoader extends SceneLoader
{
    public PicLoader(Scene scn)
    {
		super(scn);
    }
	
	public void load(String filename)
	{
		loadFromImage(filename);
	}
	
	public void loadFromImage(String filename)
	{
		try
		{
			BufferedImage im = ImageIO.read(new File(filename));
			//screenBoundL = 32;
			//screenBoundR = im.getWidth()*32+32;
			//screenBoundT = 32;
			//screenBoundB = im.getHeight()*32+32;
			//camera.setBounds(screenBoundL, screenBoundR, screenBoundT, screenBoundB);
			boolean[][][] map = new boolean[im.getWidth()+2][im.getHeight()+2][5];
			for (int i=0; i<im.getWidth(); i++)
				for (int j=0; j<im.getHeight(); j++)
				{
					if (im.getRGB(i,j) == -65536)
					{
						//player.setX((i+1)*32.0+16);
						//player.setY((j+1)*32.0+16);
					}
					if (im.getRGB(i,j) == -16777216)
					{
						//create block image
						WorldObject spr = new WorldObject(scn, 5, 10, ((i+1)*32)+16.0, ((j+1)*32)+16.0, 0.0, 0.0, null);
						map[i+1][j+1][0] = true;
					}
				}
			constructTerrain(map);
		}
		catch (IOException e) 
		{
			System.out.println("Cannot load file");
		}
	}
	
	public void constructTerrain(boolean[][][] squares)
	{
		//find the border squares
		for (int i=0; i<squares.length; i++)
			for (int j=0; j<squares[i].length; j++)
				if (squares[i][j][0] == true)
				{
					if (squares[i][j-1][0] == false) //to the top
						squares[i][j-1][1] = true;
					if (squares[i+1][j][0] == false) //to the right
						squares[i+1][j][2] = true;
					if (squares[i][j+1][0] == false) //to the bottom
						squares[i][j+1][3] = true;
					if (squares[i-1][j][0] == false) //to the left
						squares[i-1][j][4] = true;
				}
		
		//now construct the terrain from the border squares
		//must find the left-most square in the line first
		//this loop should do this
		for (int i=0; i<squares.length; i++)
			for (int j=0; j<squares[i].length; j++)
			{
				if (squares[i][j][1] == true) //top
				{
					squares[i][j][1] = false;
					int x=1;
					while(squares[i+x][j][1] == true) //find the line
					{
						squares[i+x][j][1] = false;
						x++;
					}
					double x1 = i*32;
					double y1 = j*32+32;
					double x2 = (i+x)*32;
					double y2 = j*32+32;
					Terrain ter = new Terrain(scn, null, 0, x1, y1, x2, y2);
				}
				if (squares[i][j][2] == true) //right
				{
					squares[i][j][2] = false;
					int y=1;
					while(squares[i][j+y][2] == true) //find the line
					{
						squares[i][j+y][2] = false;
						y++;
					}
					double x1 = i*32;
					double y1 = j*32;
					double x2 = i*32;
					double y2 = (j+y)*32;
					Terrain ter = new Terrain(scn, null, 1, x1, y1, x2, y2);
				}
				if (squares[i][j][3] == true) //bottom
				{
					squares[i][j][3] = false;
					int x=1;
					while(squares[i+x][j][3] == true) //find the line
					{
						squares[i+x][j][3] = false;
						x++;
					}
					double x1 = (i+x)*32;
					double y1 = j*32;
					double x2 = i*32;
					double y2 = j*32;
					Terrain ter = new Terrain(scn, null, 2, x1, y1, x2, y2);
				}
				if (squares[i][j][4] == true) //left
				{
					squares[i][j][4] = false;
					int y=1;
					while(squares[i][j+y][4] == true) //find the line
					{
						squares[i][j+y][4] = false;
						y++;
					}
					double x1 = i*32+32;
					double y1 = (j+y)*32;
					double x2 = i*32+32;
					double y2 = j*32;
					Terrain ter = new Terrain(scn, null, 3, x1, y1, x2, y2);
				}
			}
	}
}
