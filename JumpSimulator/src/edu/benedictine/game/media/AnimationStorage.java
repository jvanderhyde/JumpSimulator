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
		/*
		tiles = makeSpriteSheetB(toScale("images/Tiles.png"),32,32);
		
		blank = toScale("images/Blank.png");
		BufferedImage[] heroSheet = makeSpriteSheet(toScale("images/RetroHero.png"),1,6);
		Image[] heroImages = new Image[heroSheet.length];
		for (int i=0; i<heroImages.length; i++)
			heroImages[i] = new Image(heroSheet[i]);
		Image[] heroWalkImages = new Image[2];
		heroWalkImages[1] = new Image(heroSheet[1]);
		heroWalkImages[0] = new Image(heroSheet[0]);
		
		heroStatic = new Image(heroSheet[1]);
		heroStaticEdge = new Image(heroSheet[0]);
		heroAir = new Image(heroSheet[2]);
		heroWalk = createAnimation(heroWalkImages,true,0.25);
		heroRun = createAnimation(heroWalkImages,true,0.5);
		heroSlide = new Image(heroSheet[3]);
		heroHit = new Image(heroSheet[4]);
		heroDown = new Image(heroSheet[5]);
		
		BufferedImage[] pistolSheet = makeSpriteSheet(toScale("images/SilverPistol.png"),4,3);
		silverPistol = new Image[pistolSheet.length];
		for (int i=0; i<silverPistol.length; i++)
			silverPistol[i] = new Image(pistolSheet[i]);
		
		shot = toScale("images/Shot.png");
		
		//weapons
		BufferedImage[] swordSheet = makeSpriteSheet(toScale("images/Weapons.png"),3,6);
		Image[] swordImages = new Image[swordSheet.length];
		for (int i=0; i<swordImages.length; i++)
			swordImages[i] = new Image(swordSheet[i]);
		Image[] swordSlashImages = {swordImages[0], swordImages[1], swordImages[2]};
		swordSlash = createAnimation(swordSlashImages,false,0.5);
		
		//weapons
		BufferedImage[] ratchSheet = makeSpriteSheet(toScale("images/BabyBlueRatch.png"),3,8);
		Image[] ratchImages = new Image[8];
		for (int i=0; i<8; i++)
			ratchImages[i] = new Image(ratchSheet[i]);
		blueRatch = createAnimation(ratchImages,true,0.5);
		
		Image[] ratchImages2 = new Image[8];
		for (int i=0; i<8; i++)
			ratchImages2[i] = new Image(ratchSheet[i+8]);
		blueRatchUp = createAnimation(ratchImages2,true,0.5);
		
		Image[] ratchImages3 = new Image[8];
		for (int i=0; i<8; i++)
			ratchImages3[i] = new Image(ratchSheet[i+16]);
		blueRatchDown = createAnimation(ratchImages3,true,0.5);
		
		BufferedImage[] slashSheet = makeSpriteSheet(toScale("images/SlashEffect.png"),1,2);
		slashEffect = createAnimation(new BufferedImage[]{slashSheet[0],slashSheet[1]},false,0.5);
		
		BufferedImage[] winkSheet = makeSpriteSheet(toScale("images/WinkingStar.png"),1,5);
		wink = createAnimation(winkSheet,false,0.5);
		
		//health
		BufferedImage[] healthSheet = makeSpriteSheet(toScale("images/LifeCell.png"),3,2);
		lifeCell = createAnimation(healthSheet,true,0.25);
		slideDispenser = toScale("images/Blocks.png");
		
		//muss
		mussSheet = makeSpriteSheet(toScale("images/Muss.png"),1,6);
		mussImages = new Image[mussSheet.length];
		for (int i=0; i<mussImages.length; i++)
			mussImages[i] = new Image(mussSheet[i]);
		Image[] mussA = {mussImages[1], mussImages[2], mussImages[3], mussImages[4]};
		mussAttack = createAnimation(mussA,true,0.25);
		Image[] mussB = {mussImages[1], mussImages[4]};
		mussGloat = createAnimation(mussB,true,0.25);
		
		//killn
		killnSheet = makeSpriteSheet(toScale("images/Killn.png"),1,6);
		killnFloat = createAnimation(new BufferedImage[]{killnSheet[1],killnSheet[2],killnSheet[3],killnSheet[2],killnSheet[1]},true,0.25);
		
		//killn
		zoomFloat = createAnimation(makeSpriteSheet(toScale("images/ZoomBall.png"),1,2),true,0.25);
		
		//Sprite-sheets
		terrainSheet = makeSpriteSheet(toScale("images/Terrain.png"), 16, 16);
		Image[] terrainImages = new Image[terrainSheet.length];
		
		metalSheet = makeSpriteSheet(toScale("images/MetalBlocks.png"), 2, 5);
		castleSheet = makeSpriteSheet(toScale("images/CastleBricks.png"), 2, 1);
		
		//Tilemaps
		BufferedImage[] sampleTiles = new BufferedImage[64];
		for (int i=0; i<64; i++)
			sampleTiles[i] = terrainSheet[0];
		terrainMap = createTileMap(sampleTiles, terrainSheet[0].getWidth(), terrainSheet[0].getHeight(), 2, 32);
	
		effectSheet = makeSpriteSheet(toScale("images/Effects.png"),1,12);
		limbImg = effectSheet[7];
		
		mountain = toScale("images/Mountain.png");
		sky = toScale("images/sky3.jpg");
		*/
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
