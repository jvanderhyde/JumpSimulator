//

import java.awt.image.BufferedImage;

public class WorldObject extends SceneObject
{
	int drawOrder;
	double x, y;
	double priorX, priorY;
	//double xSpeed;
	//double ySpeed;
	LinearForce xForce, yForce;
	boolean forceOn = false;
	double gravity = 0.0, maxFall = 0.0;
	double halfWidth;
	double halfHeight;
	boolean guiObj;
	boolean screenObj;
	Animation ani;
	Image spr;
	BufferedImage pic;
	
	ImageSource img;
	
	int imageTime;
	boolean flipX;
	boolean flipY;
	int direction = -1;
	boolean visible = true;
	double xOffset = 0.0, yOffset = 0.0;
	double leftBound, rightBound, topBound, bottomBound;
	double goalX = Double.NaN, goalY = Double.NaN;
	String type;
	
	WorldObject mark, mount;
	double markOffsetX, markOffsetY, mountOffsetX, mountOffsetY;
	
	public WorldObject(Scene scn, int exec, int draw, double xLoc, double yLoc, double xCng, double yCng, ImageSource img) 
	{
		super(scn, exec);
		this.x = xLoc;
		this.y = yLoc;
		this.xSpeed = xCng;
		this.ySpeed = yCng;
		this.img = img;
		flipX = false;
		flipY = false;
		scn.draws.add(draw, this);
		this.drawOrder = draw;
		guiObj = false;
		imageTime = 0;
		if (img != null)
		{
			halfWidth = scn.canvas.v2WX(this.img.getFrame(0, flipX, flipY).getWidth()/2);
			halfHeight = scn.canvas.v2WY(this.img.getFrame(0, flipX, flipY).getHeight()/2);
		}
		xForce = new LinearForce(0.0, 0.0, 0.0, 0.0, 0.0);
		yForce = new LinearForce(0.0, 0.0, 0.0, 0.0, 0.0);
	}
	
	//GUI object constructor
	public WorldObject(Scene scn, int draw, double xLoc, double yLoc, ImageSource img) 
	{
		super(scn, 5);
		this.x = xLoc;
		this.y = yLoc;
		this.xSpeed = 0.0;
		this.ySpeed = 0.0;
		this.img = img;
		flipX = false;
		flipY = false;
		scn.guis.add(draw, this);
		guiObj = true;
		this.drawOrder = draw;
		imageTime = 0;
		if (img != null)
		{
			halfWidth = scn.canvas.v2WX(this.img.getFrame(0, flipX, flipY).getWidth()/2);
			halfHeight = scn.canvas.v2WY(this.img.getFrame(0, flipX, flipY).getHeight()/2);
		}
		xForce = new LinearForce(0.0, 0.0, 0.0, 0.0, 0.0);
		yForce = new LinearForce(0.0, 0.0, 0.0, 0.0, 0.0);
	}
	
	//Constructor for mounting
	public WorldObject(Scene scn, int exec, int draw, double xLoc, double yLoc, double xCng, double yCng, ImageSource img, WorldObject mount, double offX, double offY) 
	{
		this(scn, exec, draw, xLoc, yLoc, xCng, yCng, img);
		setMount(mount, offX, offY);
	}
	
	public void setType(String type)
	{
		this.type = type;
	}
	
	public void setScreenObject(boolean val)
	{
		screenObj = val;
	}
	
	public void setMount(WorldObject mount, double offX, double offY)
	{
		this.mount = mount;
		mountOffsetX = offX;
		mountOffsetY = offY;
	}
	
	public void setRadialSpeed(double speed, double angle)
	{
		xSpeed = speed*Math.cos(Math.toRadians(angle));
		ySpeed = speed*Math.sin(Math.toRadians(angle));
	}
	
	public double getSpeedAngle()
	{
		return AngleFunctions.getTargetAngle(x, y, x+xSpeed, y+ySpeed);
	}
	
	public void setFall(double g, double max)
	{
		gravity = g;
		maxFall = max;
	}
	
	public void setBounds(double l, double r, double t, double b)
	{
		leftBound = l;
		rightBound = r;
		topBound = t;
		bottomBound = b;
	}
	
	public void setMoveTo(double x, double y)
	{
		goalX = x;
		goalY = y;
	}
	
	public void setLayer(int l)
	{
		if (guiObj)
		{
			scn.guis.remove(this, drawOrder);
			drawOrder = l;
			scn.guis.add(drawOrder, this);
		}
		else
		{
			scn.draws.remove(this, drawOrder);
			drawOrder = l;
			scn.draws.add(drawOrder, this);
		}
	}

	public void update()
	{
		air();
		if (img != null)
			imageTime = img.advance(imageTime);
		timer();
		
		//use force if wanted
		if (forceOn)
		{
			xForce.updateValue();
			yForce.updateValue();
			xSpeed = xForce.value;
			ySpeed = yForce.value;
		}
	}
	
	public void move(double xCng, double yCng)
	{
		priorX = x;
		priorY = y;
		
		double actualSpeedX = (xCng/(scn.fps));
		double actualSpeedY = (yCng/(scn.fps));
		
		//if unmounted, move normally. If mounted, move relative to the mount
		if (mount == null)
		{
			x+=actualSpeedX;
			y+=actualSpeedY;
		}
		else
		{
			mountOffsetX+=actualSpeedX;
			mountOffsetY+=actualSpeedY;
			x = mount.x+mountOffsetX;
			y = mount.y+mountOffsetY;
		}
		//scroll looping
		if (x < leftBound)
			x = rightBound + (x-leftBound);
		if (x > rightBound)
			x = leftBound + (x-rightBound);
		if (y < topBound)
			y = bottomBound + (y-topBound);
		if (y > bottomBound)
			y = topBound + (y-bottomBound);
		//tracking
		if (mark != null)
		{
			x = mark.x+markOffsetX;
			y = mark.y+markOffsetY;
		}
		
		//tailing
		//tailing means moving with the player, but lagging a bit due to parallax
		//e.g. a building in the background: moves slowly across the screen as the player moves
		//to accomplish: when the level is loaded, save the player's position. As the player moves,
		//check the distance between him and the origin. Depending on the parallax coefficient,
		//fix the tailing object's position at 
		
		//player origin+(tailer's offset)+((current player position - player origin) * coefficient);
		
		//goals
		if (!Double.isNaN(goalX))
		{
			if (priorX < goalX && x >= goalX)
				onGoal(true);
			else if (priorX > goalX && x <= goalX)
				onGoal(true);
		}
		if (!Double.isNaN(goalY))
		{
			if (priorY < goalY && y >= goalY)
				onGoal(false);
			else if (priorY > goalY && y <= goalY)
				onGoal(false);
		}
	}
	
	//object has reached its x or y goal. This is the reaction.
	//reset the goal to NaN after the object reaches it
	public void onGoal(boolean isX)
	{
		if (isX)
		{
			x = goalX;
			xSpeed = 0.0;
			goalX = Double.NaN;
		}
		else
		{
			y = goalY;
			ySpeed = 0.0;
			goalY = Double.NaN;
		}
	}
	
	public void setX(double v)
	{
		x = v;
	}
	
	public void setY(double v)
	{
		y = v;
	}
	
	public void air()
	{
		if (gravity > 0.0)
		{
			if (ySpeed < maxFall)
			{
				ySpeed += gravity;
				if (ySpeed > maxFall)
					ySpeed = maxFall;
			}
		}
	}
	
	public void setRandomFrame(int range)
	{
		imageTime = 0+(int)(Math.random()*(range+1));
	}
	
	public void setImageFrame(int frame)
	{
		imageTime = img.getImageTime(frame);
	}
	
	public void replay()
	{
		imageTime = 0;
	}
	
	public void setImage(ImageSource im)
	{
		img = im;
		imageTime = 0;
		halfWidth = scn.canvas.v2WX(this.img.getFrame(imageTime, flipX, flipY).getWidth()/2);
		halfHeight = scn.canvas.v2WY(this.img.getFrame(imageTime, flipX, flipY).getHeight()/2);
	}
	
	//sets a new image only if that image is not the current one
	public void changeImage(ImageSource im)
	{
		if (img != im)
			setImage(im);
	}
	
	/*
	public void setImage(Animation im)
	{
		ani = im;
		spr = null;
		pic = null;
		imageTime = 0;
		halfWidth = scn.canvas.v2WX(this.ani.getFrame(0, flipX, flipY).getWidth()/2);
		halfHeight = scn.canvas.v2WY(this.ani.getFrame(0, flipX, flipY).getHeight()/2);
	}
	
	public void setImage(Image im)
	{
		spr = im;
		ani = null;
		pic = null;
		halfWidth = scn.canvas.v2WX(this.spr.getFrame(flipX, flipY).getWidth()/2);
		halfHeight = scn.canvas.v2WY(this.spr.getFrame(flipX, flipY).getHeight()/2);
	}
	
	public void setImage(BufferedImage im)
	{
		pic = im;
		ani = null;
		spr = null;
		halfWidth = scn.canvas.v2WX(pic.getWidth()/2);
		halfHeight = scn.canvas.v2WY(pic.getHeight()/2);
	}
	*/
	
	public BufferedImage getFrame()
	{
		if (visible && img != null)
			return img.getFrame(imageTime,  flipX, flipY);
		else
			return null;
	}
	
	public void setFlipX(boolean val)
	{
		flipX = val;
		direction = ((flipX? 1:0)*2-1);
	}
	
	public void setFlipY(boolean val)
	{
		flipY = val;
	}
	
	//flips X
	public void turnX()
	{
		if (flipX)
			setFlipX(false);
		else
			setFlipX(true);
	}
	
	public void die()
	{
		if (guiObj)
			scn.guis.remove(this, drawOrder);
		else
			scn.draws.remove(this, drawOrder);
		super.die();
	}
}
