package edu.benedictine.game.media;

import edu.benedictine.game.gui.PixelCanvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class AnimationStorage 
{
	public Image heroStatic;
	public Image heroStaticEdge;
	public Image heroAir;
	public Image heroSlide;
	public Image heroHit;
	public Image heroDown;
	public Animation heroWalk;
	public Animation heroRun;
	public Animation lifeCell;
	public Animation swordSlash;
	public Animation slashEffect;
	
	public Animation blueRatch;
	public Animation blueRatchUp;
	public Animation blueRatchDown;
	
	public Image[] silverPistol;
	public BufferedImage shot;
	
	public Image[] mussImages;
	public Animation mussAttack;
	public Animation mussGloat;
	
	public Animation killnFloat;
	
	public Animation zoomFloat;
	public Animation wink;
	
	//Sprite-sheets
	BufferedImage[] castleSheet;
	BufferedImage[] terrainSheet;
	BufferedImage[] metalSheet;
	BufferedImage[] effectSheet;
	BufferedImage[] mussSheet;
	BufferedImage[] killnSheet;
	private PixelCanvas gameCanvas;
	
	BufferedImage[] tiles;
	
	BufferedImage blank;
	BufferedImage terrainMap;
	BufferedImage limbImg;
	BufferedImage slideDispenser;
	BufferedImage mountain;
	BufferedImage sky;
	
	public AnimationStorage(PixelCanvas gameCanvas)
	{
		this.gameCanvas = gameCanvas;
	}

	
	//loads and returns a BufferedImage from a file.
	public static BufferedImage loadImage(String fileName)
	{
		try
		{
			BufferedImage source = ImageIO.read(new File(fileName));
			return source;
		}
		catch (IOException e) 
		{
			System.out.println("Cannot load file: "+fileName);
		}
		return null;
	}
	
	//loads and resizes a Buffered image to match the current resolution
	public BufferedImage toScale(String filePath)
	{			
		BufferedImage bigPic = loadImage(filePath);
		
		//Scale image depending upon resolution.  1024/640 is point of reference...
		int canvasWidth = gameCanvas.getWidth();
		
		double factor = (double)canvasWidth/(double)1024;
		
		BufferedImage scaledImage = new BufferedImage((int)(bigPic.getWidth()*factor), 
				(int)(bigPic.getHeight()*factor),BufferedImage.TYPE_INT_ARGB);
		
		// Paint scaled version of image to new image
		Graphics2D graphics2D = scaledImage.createGraphics();
		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
		RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics2D.drawImage(bigPic, 0, 0, (int)(bigPic.getWidth()*factor), (int)(bigPic.getHeight()*factor), null);
		// clean up
		graphics2D.dispose();
		
		return scaledImage;
	}
	
	//creates a spritesheet of a given image. The spritesheet is an array of BufferedImages.
	//all of them are the same width and height, and they are stored using a single index,
	//which reads like text (i.e. 0 = top left corner sprite, n = bottom right corner sprite).
	public static BufferedImage[] makeSpriteSheet(BufferedImage source, int rows, int cols)
	{
		BufferedImage[] sheet = new BufferedImage[rows*cols];
		int width = source.getWidth()/cols;
		int height = source.getHeight()/rows;
		int t = 0;
		for (int i=0; i<rows; i++)
			for (int j=0; j<cols; j++)
			{
				sheet[t] = source.getSubimage(j*width, i*height, width, height);
				t++;
			}
		return sheet;
	}
	
	//creates a spritesheet of a given image. The spritesheet is an array of BufferedImages.
	//all of them are the same width and height, and they are stored using a single index,
	//which reads like text (i.e. 0 = top left corner sprite, n = bottom right corner sprite).
	public static BufferedImage[] makeSpriteSheetB(BufferedImage source, int width, int height)
	{
		int cols = source.getWidth()/width;
		int rows = source.getHeight()/height;
		BufferedImage[] sheet = new BufferedImage[rows*cols];
		int t = 0;
		for (int i=0; i<rows; i++)
			for (int j=0; j<cols; j++)
			{
				sheet[t] = source.getSubimage(j*width, i*height, width, height);
				t++;
			}
		return sheet;
	}
	
	public static Image[] makeImages(BufferedImage[] source)
	{
		Image[] sheet = new Image[source.length];
		for (int i=0; i<source.length; i++)
			sheet[i] = new Image(source[i]);
		return sheet;
	}
	
	public static Image[] makeImages(ImageSource[] source)
	{
		Image[] sheet = new Image[source.length];
		for (int i=0; i<source.length; i++)
			sheet[i] = new Image(source[i].pic);
		return sheet;
	}
	
	//creates an animation given an array of Images
	public Animation createAnimation(Image[] source, boolean cyclical, double frameRate, int replay)
	{			
		return new Animation(source, cyclical, frameRate, replay);
	}
	
	//creates an animation given an array of BufferedImages
	public Animation createAnimation(BufferedImage[] source, boolean cyclical, double frameRate, int replay)
	{			
		return new Animation(source, cyclical, frameRate, replay);
	}
	
	//creates an animation given an array of BufferedImages
	public Animation createAnimation(ImageSource[] source, boolean cyclical, double frameRate, int replay)
	{
		BufferedImage[] buffs = new BufferedImage[source.length];
		Image[] images = new Image[source.length];
		if (source[0].pic != null)
		{
			for (int i=0; i<source.length; i++)
				buffs[i] = source[i].pic;		
			return new Animation(buffs, cyclical, frameRate, replay);
		}
		if (source[0].img != null)
		{
			for (int i=0; i<source.length; i++)
				images[i] = source[i].img;		
			return new Animation(images, cyclical, frameRate, replay);
		}
		else
			return null;
	}
	
	//creates a tile map given an array of BufferedImages
	public BufferedImage createTileMap(BufferedImage[] source, int width, int height, int rows, int cols)
	{	
		BufferedImage tile = new BufferedImage(width*cols, height*rows, BufferedImage.TYPE_INT_ARGB);
		Graphics g = tile.createGraphics();
		int index = 0;
		for (int i=0; i<rows; i++)
			for (int j=0; j<cols; j++)
			{
				g.drawImage(source[index], j*width, i*height, null);
				index++;
			}
		return tile;
	}
	
	public ImageSource[] makeSourceArray(BufferedImage[] images)
	{
		ImageSource[] sources = new ImageSource[images.length];
		for (int i=0; i<images.length; i++)
		{
			sources[i] = new ImageSource(images[i]);
		}
		return sources;
	}
	
	public ImageSource[] makeSourceArray(Image[] images)
	{
		ImageSource[] sources = new ImageSource[images.length];
		for (int i=0; i<images.length; i++)
		{
			sources[i] = new ImageSource(images[i]);
		}
		return sources;
	}
}
