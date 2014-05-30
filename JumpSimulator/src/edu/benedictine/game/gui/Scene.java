//Modified by Joseph Rioux, 12 January 2013
//	Note: x or y speed of 0.0015625 = 1 pixel per frame (at standard resolution, 1024 x 640)

package edu.benedictine.game.gui;

import edu.benedictine.game.engine.*;
import edu.benedictine.game.engine.collision.*;
import edu.benedictine.game.media.*;
import edu.benedictine.game.util.*;
import edu.benedictine.jump.GraphicsResourceJump;
import edu.benedictine.jump.LevelManagerJump;
import edu.benedictine.jump.players.Custom;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;


public class Scene 
{
	public Director mn;
	public String currentLevel, prevLevel;  //level load source
	public int time;
	public SortedList<SceneObject> objs;
	public SortedList<GameObject> games;
	public SortedList<WorldObject> draws;
	public SortedList<WorldObject> guis;
	public ArrayList<BoundingBox> boxes;
	public SortedList<Hit> hits;
	public ArrayList<SceneObject> objCurrents;
	public ArrayList<GameObject> gameCurrents;
	public ArrayList<WorldObject> drawCurrents;
	public ArrayList<WorldObject> guiCurrents;
	public ArrayList<BoundingBox> boxCurrents;
	public PixelCanvas canvas;
	public Graphics g, g2;
	private BufferedImage screenDraw;
	Color backgroundColor;
    boolean running;
    int rate = 16;
    public double fps;
	private int t = 0;
	int sum = 0, sum2 = 0;
	public GraphicsResourceJump store;
	public Player player;
	Camera camera;
	public EffectGenerator spEffects;
	public SceneSwitcher swapper;
	
	double angle;
	GameObject junk;
	int lightning, lightChance = 32;
	boolean rain = false;
	
	boolean debugView = false;
	boolean easyTiles = true;
	boolean frameInfo = false;
	
	//collision
	public CollisionManager collisionManager;
	LevelManagerJump levelManager;
	
	//camera
	double cameraX = -512.0;
	double cameraY = -320.0;
	double screenBoundL = -512;
	public double screenBoundR = 512;
	double screenBoundT = -320;
	public double screenBoundB = 320;
	
	//keys
	KeyChecker keys;
	public int escPressed = 5;
	public boolean aDown, bDown, wDown, cDown, xDown, yDown, cmdDown;
	public int aPressed, bPressed, wPressed, cPressed, xPressed, yPressed, leftPressed, rightPressed, downPressed, upPressed;
	public int leftDowned, rightDowned;
	boolean leftDoubleTapped, rightDoubleTapped;
	int leftTapLock = 0, rightTapLock = 0;
	public boolean leftDown;
	public boolean rightDown;
	public boolean upDown;
	public boolean downDown;
	public boolean a, b, d, u, l, r, w, c, x, y;
	
	
	//Custom dispatcher
	class KeyDispatcher implements KeyEventDispatcher 
	{
		public KeyChecker check;
		
		public KeyDispatcher(KeyChecker check)
		{
			this.check = check;
		}
		
	    public boolean dispatchKeyEvent(KeyEvent e) 
	    {
	    	if (e.getID() == KeyEvent.KEY_PRESSED)
	    		check.keyPressed(e);
	    	if (e.getID() == KeyEvent.KEY_RELEASED)
	    		check.keyReleased(e);
	    	if (e.getID() == KeyEvent.KEY_TYPED)
	    		check.keyTyped(e);
	        return false;
	    }
	}
	
	
	class KeyChecker implements KeyListener
	{
		@Override
		public void keyPressed(KeyEvent e)
		{
			if (e.getKeyCode()==KeyEvent.VK_Z)
			{
				aDown = true;
			}
			if (e.getKeyCode()==KeyEvent.VK_X)
			{
				bDown = true;
			}
			if (e.getKeyCode()==KeyEvent.VK_LEFT)
			{
				leftDown = true;
			}
			if (e.getKeyCode()==KeyEvent.VK_RIGHT)
			{
				rightDown = true;
			}		
			if (e.getKeyCode()==KeyEvent.VK_UP)
			{
				upDown = true;
			}
			if (e.getKeyCode()==KeyEvent.VK_DOWN)
			{
				downDown = true;
			}
			if (e.getKeyCode()==KeyEvent.VK_W)
			{
				wDown = true;
			}
			if (e.getKeyCode()==KeyEvent.VK_C)
			{
				cDown = true;
			}
			if (e.getKeyCode()==KeyEvent.VK_A)
			{
				xDown = true;
			}
			if (e.getKeyCode()==KeyEvent.VK_Q)
			{
				yDown = true;
			}
			if (e.getKeyCode()==KeyEvent.VK_META)
			{
				cmdDown = true;
				//mn.ogg.setGain(0f);
			}
			//exit program
			if (e.getKeyCode()==KeyEvent.VK_ESCAPE)
			{
				//mn.ogg.setGain(0f);
				System.exit(0);
			}
		}

		@Override
		public void keyReleased(KeyEvent e)
		{
			if (e.getKeyCode()==KeyEvent.VK_Z)
			{
				aDown = false;
			}
			if (e.getKeyCode()==KeyEvent.VK_X)
			{
				bDown = false;
			}
			if (e.getKeyCode()==KeyEvent.VK_LEFT)
			{
				leftDown = false;
			}
			if (e.getKeyCode()==KeyEvent.VK_RIGHT)
			{
				rightDown = false;
			}
			if (e.getKeyCode()==KeyEvent.VK_UP)
			{
				upDown = false;
			}
			if (e.getKeyCode()==KeyEvent.VK_DOWN)
			{
				downDown = false;
			}
			if (e.getKeyCode()==KeyEvent.VK_W)
			{
				wDown = false;
			}
			if (e.getKeyCode()==KeyEvent.VK_C)
			{
				cDown = false;
			}
			if (e.getKeyCode()==KeyEvent.VK_A)
			{
				xDown = false;
			}
			if (e.getKeyCode()==KeyEvent.VK_Q)
			{
				yDown = false;
			}
			if (e.getKeyCode()==KeyEvent.VK_META)
			{
				cmdDown = false;
				//mn.ogg.setDefaultGain();
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {}
	}
    
	public Scene(Director mn, String source, String prev, PixelCanvas can, GraphicsResourceJump animations, double pX, double pY)
	{
		this.mn = mn;
		swapper = new SceneSwitcher(mn);
		objs = new SortedList<SceneObject>(16);
		games = new SortedList<GameObject>(16);
		draws = new SortedList(32);
		boxes = new ArrayList<BoundingBox>();
		guis = new SortedList(32);
		hits = new SortedList(25);
		this.store = animations;
		canvas = can;
		this.g2 = this.canvas.getGraphics();
		screenDraw = new BufferedImage(canvas.getWidth(), canvas.getHeight(), BufferedImage.TYPE_INT_ARGB);
		this.g = screenDraw.createGraphics();
		currentLevel = source;
		prevLevel = prev;
		backgroundColor = Color.BLACK;
		
		fps = 1000.0/rate;
		
		collisionManager = new CollisionManager(this);
		levelManager = new LevelManagerJump(this, currentLevel, prevLevel);
		spEffects = new EffectGenerator(this);
		
		
		
		
		
		//keyboard stuff - Jonathan Weatherhead
		//Hijack the keyboard manager
		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		manager.addKeyEventDispatcher(new KeyDispatcher(new KeyChecker()));
		
		//this.mn.f.addKeyListener(new KeyChecker()); //add it to the frame
		//canvas.addKeyListener(new KeyChecker());
		//add the listener to all components 
		/*mn.s.addKeyListener(new KeyChecker());
		mn.s2.addKeyListener(new KeyChecker());
		mn.s3.addKeyListener(new KeyChecker());
		mn.custom.addKeyListener(new KeyChecker());
		mn.mario.addKeyListener(new KeyChecker());
		mn.samus.addKeyListener(new KeyChecker());
		mn.megaman.addKeyListener(new KeyChecker());
		mn.zeetee.addKeyListener(new KeyChecker());*/
		
		//rain back color
		//backgroundColor = new Color(153, 102, 102);
		ImageSource k = store.heroAir;
		backgroundColor = new Color(0, 153, 255);
		//backgroundColor = new Color(255, 153, 0);
		//TriggerReel ree = new TriggerReel(this, 0.0, 512.0, 4096.0, 64.0, "test2");
		player = new Player(this, pX, pY, 0.0, 0.0);
		camera = new Camera(this, 0.0, 0.0, true);
		screenBoundL = -3200;
		screenBoundR = 3200;
		screenBoundT = -3200;
		screenBoundB = 3200;
		camera.setBounds(screenBoundL, screenBoundR, screenBoundT, screenBoundB);
		//camera.setBounds(-3200, 3200, -3200, 3200);

		Gem gem;
		for (int i=0; i<1; i++)
			gem = new Gem(this, (int)(Math.random()*screenBoundR), 64.0);
		
		levelManager.initiate();
		
		loadFromImage(currentLevel+".png");
	}
	
	//starts the scene running. We've run into some issues when we call this in the constructor
	//(i.e. the constructor never finishes!)
	public void start()
	{
		running = true;
		try
		{
			runScene();
		} 
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	public void stop()
	{
		running = false;
	}
	
	public void runScene() throws InterruptedException
	{
		while (running == true)
		{
			//initial time
	        long time = System.currentTimeMillis();
	      
	        //if the command key is down, kill controls
	        //probably pause too
	        if (cmdDown)
	        {
				aDown = false;
				bDown = false;
				cDown = false;
				downDown = false;
				upDown = false;
				leftDown = false;
				rightDown = false;
				wDown = false;
				xDown = false;
				yDown = false;
	        }
	        
			//set input values
			a = aDown;
			b = bDown;
			c = cDown;
			d = downDown;
			u = upDown;
			l = leftDown;
			r = rightDown;
			w = wDown;
			x = xDown;
			y = yDown;
			
			//temporary - so that I can draw things to the screen during update
			/*
			if ((int)(Math.random()*lightChance) == 1)
			{
				//lightning = 5;
				//lightChance = 10;
			}
			if (lightning > 0)
				backgroundColor = Color.WHITE;
			else
				backgroundColor = new Color(153, 102, 102);
			lightning--;
			if (lightChance < 32)
				lightChance++;
			*/
			
			g.setColor(backgroundColor);
			g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
			collisionManager.prepTerrain();
			collisionManager.getReady();
			
			objCurrents = (ArrayList)objs.clone();
			gameCurrents = (ArrayList)games.clone();
			
			updateSceneObjects();
			moveObjects();
			postMove();
			checkSceneCollision();
			postCollision();
			checkSceneHits();
			handleSceneHits();
			sceneCleanUp();
			
			updateInput();

			if (camera != null)
			{
				camera.move();
				cameraX = camera.getX()-Main.CAN_WIDTH/2.0;
				cameraY = camera.getY()-Main.CAN_HEIGHT/2.0;
			}
			drawFrame(g, screenDraw);
			
			swapper.update();

			//this is a good way to keep the fps consistent at about 32 ms per frame
			long clock = System.currentTimeMillis()-time;
			if (rate-clock > 0)
				Thread.sleep(rate-clock);
			t++;
			if (clock > rate+1)
				sum++;
			if (clock > rate+8)
				sum2++;
			
			if (frameInfo)
				System.out.println("frame: "+(t-1)+", ms: "+(System.currentTimeMillis()-time)+", outliers: "+sum+", extremes: "+sum2);
		}
	}
	
	public void updateSceneObjects()
	{
		objCurrents = (ArrayList)objs.clone();
		for (int i=0; i<objCurrents.size(); i++)
		{
			objCurrents.get(i).update();
		}
	}
	
	public void moveObjects()
	{
		objCurrents = (ArrayList)objs.clone();
		for (int i=0; i<objCurrents.size(); i++)
		{
			objCurrents.get(i).move(objCurrents.get(i).getXSpeed(), objCurrents.get(i).getYSpeed());
		}
	}
	
	public void postMove()
	{
		gameCurrents = (ArrayList)games.clone();
		for (int i=0; i<gameCurrents.size(); i++)
		{
			gameCurrents.get(i).postMove();
		}
	}
	
	public void checkSceneCollision()
	{
		for (int i=0; i<gameCurrents.size(); i++)
		{
			if (gameCurrents.get(i).hitTerrain)
				gameCurrents.get(i).checkCollision();
		}
	}
	
	public void postCollision()
	{
		for (int i=0; i<gameCurrents.size(); i++)
		{
			gameCurrents.get(i).postCollision();
		}
	}
	
	public void checkSceneHits()
	{
		hits = new SortedList(25);
		boxCurrents = (ArrayList)boxes.clone();
		for (int i=0; i<boxCurrents.size(); i++)
		{
			boxCurrents.get(i).getReady();
		}
		for (int i=0; i<boxCurrents.size(); i++)
		{
			boxCurrents.get(i).checkHits();
		}
	}
	
	public void handleSceneHits()
	{
		for (int i=0; i<hits.size(); i++)
		{
			//System.out.println("-----"+hits.get(i).a.owner+" "+hits.get(i).b.owner+" "+hits.get(i).a.owner.x+" "+hits.get(i).a.owner.y+" "+hits.get(i).b.owner.x+" "+hits.get(i).b.owner.y);
			hits.get(i).a.owner.handleHit(hits.get(i));
		}
	}
	
	public void sceneCleanUp()
	{
		objCurrents = (ArrayList)objs.clone();
		for (int i=0; i<objCurrents.size(); i++)
		{
			objCurrents.get(i).cleanUp();
		}
	}
	
	private void updateInput() 
	{
		//this allows for a key to be "tapped"
		if (a == true)
			aPressed = 1;
		else
			aPressed--;
		
		if (b == true)
			bPressed = 1;
		else
			bPressed--;
		
		if (c == true)
			cPressed = 1;
		else
			cPressed--;
		
		if (w == true)
			wPressed = 1;
		else
			wPressed--;
		
		if (x == true)
			xPressed = 1;
		else
			xPressed--;
		
		if (y == true)
			yPressed = 1;
		else
			yPressed--;
		
		//down
		if (d == true)
			downPressed = 1;
		else
			downPressed--;
		
		if (u == true)
			upPressed = 1;
		else
			upPressed--;
		
		//left
		leftDoubleTapped = false;
		if (l == true)
		{
			if (leftTapLock == 0)
				leftTapLock = 1;
			if (leftTapLock == 2)
			{
				leftDoubleTapped = true;
				leftTapLock = 0;
			}
			if (leftDowned > 8)
				leftTapLock = 0;
			leftDowned++;
			leftPressed = 1;
		}
		else
		{
			if (leftTapLock == 1)
				leftTapLock = 2;
			
			if (leftPressed == -10)
				leftTapLock = 0;
			leftDowned = 0;
			leftPressed--;
		}
		
		//right
		rightDoubleTapped = false;
		if (r == true)
		{
			if (rightTapLock == 0)
				rightTapLock = 1;
			if (rightTapLock == 2)
			{
				rightDoubleTapped = true;
				rightTapLock = 0;
			}
			if (rightDowned > 8)
				rightTapLock = 0;
			rightDowned++;
			rightPressed = 1;
		}
		else
		{
			if (rightTapLock == 1)
				rightTapLock = 2;
			
			if (rightPressed == -10)
				rightTapLock = 0;
			rightDowned = 0;
			rightPressed--;
		}
	}
	
	public void drawFrame(Graphics off, BufferedImage im)
	{
		drawCurrents = (ArrayList)draws.clone();
		guiCurrents = (ArrayList)guis.clone();
		//draw to screen-sized BufferedImage
		//off.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		//draw objects to the offscreen buffer
		for (int i=0; i<draws.size(); i++)
		{
			WorldObject obj = (WorldObject)draws.get(i);
			if (obj.getFrame() != null)
			{
				//if (obj instanceof Player)
					//System.out.println(canvas.w2VY(obj.y-obj.halfHeight-cameraY));
				if (obj.screenObj)
					off.drawImage(obj.getFrame(), canvas.w2VX(obj.getX()-obj.getHalfWidth()), canvas.w2VY(obj.getY()-obj.getHalfHeight()), null);
				else
					off.drawImage(obj.getFrame(), canvas.w2VX(obj.getX()-obj.getHalfWidth()-cameraX), canvas.w2VY(obj.getY()-obj.getHalfHeight()-cameraY), null);
			}
		}
		
		if (debugView)
		{
			off.setColor(Color.RED);
			for (int j=0; j<4; j++)
				for (int i=0; i<collisionManager.walls[j].size(); i++)
				{
					Terrain wall = collisionManager.walls[j].get(i);
					//off.drawLine(canvas.w2VX(wall.x0), canvas.w2VX(wall.y0), canvas.w2VX(wall.x1), canvas.w2VX(wall.y1));
					off.drawLine(canvas.w2VX(wall.x0-cameraX), canvas.w2VX(wall.y0-cameraY), canvas.w2VX(wall.x1-cameraX), canvas.w2VX(wall.y1-cameraY));
				}
			off.setColor(Color.BLACK);
			
			off.setColor(Color.GREEN);
			for (int i=0; i<boxCurrents.size(); i++)
			{
				BoundingBox b = boxCurrents.get(i);
				if (b.on)
					off.drawRect(canvas.w2VX(b.x-b.halfWidth-cameraX), canvas.w2VY(b.y-b.halfHeight-cameraY), (int)(b.halfWidth*2.0), (int)(b.halfHeight*2.0));
			}
			off.setColor(Color.BLACK);
			
			off.setColor(Color.BLUE);
			for (int i=0; i<gameCurrents.size(); i++)
			{
				GameObject b = gameCurrents.get(i);
				if (b.hitTerrain)
					off.drawRect(canvas.w2VX(b.getX()+b.getLeft()-cameraX), canvas.w2VY(b.getY()+b.getHead()-cameraY), (int)(b.getRight()-b.getLeft()), (int)(b.getFeet()-b.getHead()));
			}
			off.setColor(Color.BLACK);
		}
		
		//text
		off.setColor(Color.BLACK);
		off.drawString("X Speed: "+player.getXForce().value, 16, 20);
		off.drawString("Y Speed: "+player.getYForce().value, 16, 40);
		off.drawString("X: "+((double)Math.round(player.getX()*100)/100), 16, 60);
		off.drawString("Y: "+((double)Math.round(player.getY()*100)/100), 16, 80);
		off.setColor(Color.GREEN);
		off.drawString("Gems: "+player.getNumGems(), 16, 100);
		
		
		//draw gui objects - over everything else
		for (int i=0; i<guis.size(); i++)
		{
			WorldObject obj = (WorldObject)guis.get(i);
			if (obj.getFrame() != null)
				off.drawImage(obj.getFrame(), canvas.w2VX(obj.getX()), canvas.w2VY(obj.getY()), null);
		}
		
		off.setColor(Color.BLACK);
		if (swapper.getTimeToGo() <= 20 && swapper.getTimeToGo() > -1)
			off.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		off.setColor(backgroundColor);
		
		//draw BufferedImage to screen
		g2.drawImage(im, 0, 0, null);
	}
	
	public void loadFromImage(String filename)
	{
		try
		{
			BufferedImage im = ImageIO.read(new File(filename));
			screenBoundL = 32;
			screenBoundR = im.getWidth()*32+32;
			screenBoundT = 32;
			screenBoundB = im.getHeight()*32+32;
			camera.setBounds(screenBoundL, screenBoundR, screenBoundT, screenBoundB);
			boolean[][][] map = new boolean[im.getWidth()+2][im.getHeight()+2][5];
			for (int i=0; i<im.getWidth(); i++)
				for (int j=0; j<im.getHeight(); j++)
				{
					if (im.getRGB(i,j) == -65536)
					{
						player.setX((i+1)*32.0+16);
						player.setY((j+1)*32.0+16);
					}
					if (im.getRGB(i,j) == -16777216)
					{
						//create block image
						WorldObject spr = new WorldObject(this, 5, 10, ((i+1)*32)+16.0, ((j+1)*32)+16.0, 0.0, 0.0, store.tiles[0]);
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
					Terrain ter = new Terrain(this, null, 0, x1, y1, x2, y2);
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
					Terrain ter = new Terrain(this, null, 1, x1, y1, x2, y2);
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
					Terrain ter = new Terrain(this, null, 2, x1, y1, x2, y2);
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
					Terrain ter = new Terrain(this, null, 3, x1, y1, x2, y2);
				}
			}
	}
}
