//created by Joseph Rioux, 22 October 2013
//This class will serve the main functions of the player. 
//  Specific powers and abilities will build off of this.
//Modified by James Vanderhyde, 21 May 2014
//  Refactored specialty movement code into subclasses

package edu.benedictine.game.engine;

import edu.benedictine.game.media.ImageSource;
import edu.benedictine.game.engine.collision.BoundingBox;
import edu.benedictine.game.util.AdvancedForce;
import edu.benedictine.game.gui.Scene;

public abstract class Player extends GameObject
{
	int life;
	int maxLife;
	int invincible;
	int gotHit;
	int jump;
	int jumpSwitch;
	boolean run = false;
	boolean runLeft = false;
	boolean runRight = false;
	boolean slowingDown = false;
	int sliding = -10;
	double xAcc = 0.0;
	double yAcc = 0.0;
	int weaponType = 3;
	int weaponUp = 1;
	int strike = 0;
	int locked = 0;
	boolean reelLock;
	int airLockX = 0;
	boolean onEdge, atEdge;
	
	int weaponPointing;
	
	int airControl = 0;
	
	int xLock, yLock, xControlLock, yControlLock; //locks for a certain number of frames
	boolean xAirLock, yAirLock, xAirConLock, yAirConLock; //locks until you hit the ground
	boolean leftAirLock, rightAirLock;
	
	int falling = 128;
	double shotgunDown;
	
	int dasher, dashDirection;
		
	protected double gravity = 26.0;//?31.2?
	protected double initialJump = 600.0;
	protected double terminalVelocity = 600.0;

	//jump project vars
	boolean big = true;
	protected double cancelingAcc = -1.0;
	int gems = 0;
		
	//hard code a lot of the player's attributes (e.g img, head, left, etc.)
	public Player(Scene scn, double xLoc, double yLoc, double xCng, double yCng)
	{
		super(scn, 5, 8, xLoc, yLoc, xCng, yCng, scn.store.heroStatic, -40.0, 16.0, -8.0, 8.0);
		if (!big)
			this.setTerrainLimits(-8, 8, -12, 16);
		box = new BoundingBox(scn, this, 1, new int[]{0, 2, 3}, 15, 0.0, 6.0, 8.0, 10.0);
		yForce = new AdvancedForce(0.0, 0.0, 0.0, -600.0, 600.0, 1);
		xForce = new AdvancedForce(0.0, 0.0, 60.0, -120.0, 120.0, 1);
		forceOn = true;
		
		maxLife = 10;
		life = 10;
		
		switchSize();
	}
	
	public void update()
	{
		//large or small
		if (scn.d && scn.downPressed <= 0 && onGround)
			switchSize();
		
		setJumpType();
		//System.out.println(scn.mn.xSpeed+" "+scn.mn.gravity+" "+scn.mn.jumpPower+" "+scn.mn.airDecel+" "+scn.mn.cancelType);
		
		onEdge = false;
		atEdge = false;
		if (onGround && (getEdge(true) > -8 || getEdge(false) < 8))
			onEdge = true;
		if ((!flipX && getEdge(true) > -8) || (flipX && getEdge(false) < 8))
			atEdge = true;
		
		if ((locked < 1) && (!reelLock))
		{
			walk();
			jump();
		}
		
		if (life < 1)
			die();
		if (life > maxLife)
			life = maxLife;
		
		//die off bottom of screen
		if (y > scn.screenBoundB+256.0)
			life = 0;
		if (y < -960)
		{
			y = -960;
			yForce.value = 0.0;
		}
		
		super.update();
	}
	
	public void switchSize()
	{
		if (big)
		{
			big = false;
			this.setTerrainLimits(-8, 8, -12, 16);
			y -= 4;
		}
		else
		{
			boolean under = false;
			for (int i=0; i<scn.collisionManager.walls[2].size(); i++)
				if (x < scn.collisionManager.walls[2].get(i).x0+16)
					if (x > scn.collisionManager.walls[2].get(i).x1-16)
						if (y > scn.collisionManager.walls[2].get(i).y0-16)
							if (y < scn.collisionManager.walls[2].get(i).y0+32)
								under = true;
			if (!under)
			{
				big = true;
				this.setTerrainLimits(-8, 8, -40, 16);
				y -= 4;
			}
		}
	}
	
	public abstract void setJumpType();

	public abstract void walk();
	
	public abstract void jump();
	
	private void setAnimations()
	{	
		ImageSource walk, stat, air;
		if (big)
		{
			walk = scn.store.heroWalk;
			stat = scn.store.heroStatic;
			air = scn.store.heroAir;
		}
		else
		{
			walk = scn.store.heroEmptyWalk;
			stat = scn.store.heroEmptyStatic;
			air = scn.store.heroEmptyAir;
		}
		
		//animations
		if (onTerrain == null)
		{
			if (imageTime != 1)
			{
				setImage(air);
			}
		}
		else
		{
			if (xSpeed == 0.0)
			{
				setImage(stat);
			}
			else
			{
				changeImage(walk);
			}
		}
		//setImage(scn.store.heroHit);
	}
	
	public void checkCollision()
	{
		//So, currently the player gets stopped by walls before landing on floors too much
		//e.g. if he's falling along a sheer wall and there is a 1-square tall opening
		//in the wall he will not land in that opening. I'd like to make it so that he does.
		
		//How?
		
		//What I could do is make the endpoints of side walls a bit "fuzzy". This means that,
		//if the player hits a side wall near the corner and is going fast enough in the
		//direction of the corner (up for an upper corner, etc.) he will pass through that
		//wall.
		
		//E.g. if the player jumps at a left wall and gets to the upper corner of a ledge. If
		//his current upward speed would take him up to the level of the ledge, he goes through
		//the wall and lands on the ledge.
		
		//Dangerous. If the player were to rapidly change velocity when traveling through the wall,
		//he would break through the collision boundary of the terrain and ironclad collision would
		//be broken. Possible solution: lock the player's ySpeed when passing through a fuzzy corner
		//so that he must get to the level of the ledge before changing ySpeed. ???
		
		//make two checks for sides, one at the proper width of the player (16 pixels),
		//another at the center of the player
		
		//Don't assume this is all good. Written on 3/27/13

		//initial check
		
		checkLeft();
		checkRight();
		checkCeiling();
		checkFloor();

		//make sure nothing slips through
		if ((onTerrain != null) || (atCeiling != null))
		{
			if (atLeftWall == null)
				checkLeft();
			if (atRightWall == null)
				checkRight();
		}
		
		//finally, adjust if in a wall a bit (which is supposed to happen sometimes
		//to make things look smoother)
		//wallAdjust();
		
		if (onTerrain != null)
			hitTerrain(onTerrain);
		if (atRightWall != null)
			hitTerrain(atRightWall);
		if (atCeiling != null)
			hitTerrain(atCeiling);
		if (atLeftWall != null)
			hitTerrain(atLeftWall);
	}
	
	public void checkFloor()
	{
		double xL = -1.0;
		double xR = 1.0;
		if (Math.abs(xSpeed) <= 8.0)
		{
			xL = -Math.abs(xSpeed*1.25);
			xR = Math.abs(xSpeed*1.25);
		}
		if (xL > -1.0)
			xL = -1.0;
		if (xR < 1.0)
			xR = 1.0;
		
		//check if the player was on the platform before the collision check
		//if so, the player is walking off a platform and variable skid should be
		//set to the direction that the player is walking
		int skid = 0;
		if (onTerrain != null)
			if (flipX)
				skid = 1;
			else
				skid = -1;
		
		onGround = false;
		
		Terrain floor = scn.collisionManager.collideFloor(this, head, feet, left, right);
		if ((floor != null) && (floor.suppressForPlayer <= 0))
		{
			if (ySpeed > 0.0)
			{
				//setImage(scn.store.heroWalk);
				//imageTime = 3;
			}
			y = floor.y0-feet;
			
			//if (yForce.value > 30.0)
				//System.out.println("hit floor: "+(System.currentTimeMillis()-start));

			falling = 128;
			shotgunDown = -240;
			
			jump = 0;
			yForce.value = 0.0;
			leftAirLock = false;
			rightAirLock = false;
			yAirLock = false;
		}
		if ((onTerrain != atCeiling) || (atCeiling == null))
		{
			onTerrain = floor;
			onGround = true;
		}
		
		//check skid to see if we must slide off the platform
		//(it looks weird to walk off a platform and be right against the wall)
		if (!onGround && xSpeed == 0.0 && skid != 0 && ySpeed >= 0.0)
			xSpeed = skid;
	}
	
	public void checkRight()
	{
		double priorSpeed = xSpeed;
		atRightWall = scn.collisionManager.collideRightWall(this, head, feet, left, right);
		if (atRightWall != null)
		{
			x = atRightWall.x0-left;
			xForce.value = 0.0;
		}
		else
		{
			atRightWall = scn.collisionManager.collideRightWall(this, head, feet, 0.0, 0.0);
			if (atRightWall != null)
			{
				x = atRightWall.x0;
				xForce.value = 0.0;
			}
		}
	}
	
	public void checkCeiling()
	{
		atCeiling = scn.collisionManager.collideCeiling(this, head, feet, left, right);
		if (atCeiling != null)
		{
			y = atCeiling.y0-head;
			if (ySpeed < -4.0)
			{
				yForce.value = -4.0;
			}
		}
	}
	
	public void checkLeft()
	{
		atLeftWall = scn.collisionManager.collideLeftWall(this, head, feet, left, right);
		if (atLeftWall != null)
		{
			x = atLeftWall.x0-right;
			xForce.value = 0.0;
		}
		else
		{
			atLeftWall = scn.collisionManager.collideLeftWall(this, head, feet, 0.0, 0.0);
			if (atLeftWall != null)
			{
				x = atLeftWall.x0;
				xForce.value = 0.0;
			}
		}
	}
	
	public void wallAdjust()
	{
		Terrain wall = scn.collisionManager.basicCollideLeftWall(this, head, feet, left, right);
		if (wall != null)
		{
			double speed = -4.0;
			if (speed < wall.x0 - (x+right))
				speed = wall.x0 - (x+right);
			x += speed;
		}
		
		wall = scn.collisionManager.basicCollideRightWall(this, head, feet, left, right);
		if (wall != null)
		{
			double speed = 4.0;
			if (speed > wall.x0 - (x+left))
				speed = wall.x0 - (x+left);
			x += speed;
		}
	}

	public void cleanUp()
	{
		super.cleanUp();
		
		if (gotHit < 1)
			invincible--;
		if (onTerrain != null)
			gotHit--;

		xLock--;
		yLock--;
		xControlLock--;
		yControlLock--;
		locked--;
		
		//invincible flashing
		if ((invincible > 0) && (invincible % 2 == 0) && (gotHit < 1))
		{
			if (visible == true)
				visible = false;
			else if (visible == false)
				visible = true;
		}
		if (invincible < 1)
			visible = true;
		
		setAnimations();
		
		//update save manager values
		//scn.mn.state.playerLife = life;
		//scn.mn.state.playerMaxLife = maxLife;
		
		//scn.cameraX = x-512.0;
		//scn.cameraY = y-320.0;
		
		boolean standingStill = true;
		//set components - can't alter things while moving
		if (!onGround || yForce.value != 0.0 || xForce.value != 0.0)
		{
			standingStill = false;
			scn.mn.custom.setEnabled(false);
			scn.mn.mario.setEnabled(false);
			scn.mn.samus.setEnabled(false);
			scn.mn.zeetee.setEnabled(false);
		}
		else
		{
			scn.mn.custom.setEnabled(true);
			scn.mn.mario.setEnabled(true);
			scn.mn.samus.setEnabled(true);
			scn.mn.zeetee.setEnabled(true);
		}
		
		//disable the sliders when using preset jumps
		if (!scn.mn.custom.isSelected() || !standingStill)
		{
			scn.mn.s.setEnabled(false);
			scn.mn.s2.setEnabled(false);
			scn.mn.s3.setEnabled(false);
			scn.mn.s4.setEnabled(false);
			//scn.mn.s5.setEnabled(false);
			scn.mn.noCancel.setEnabled(false);
			scn.mn.doubleGravity.setEnabled(false);
			scn.mn.fullCancel.setEnabled(false);
		}
		else
		{
			scn.mn.s.setEnabled(true);
			scn.mn.s2.setEnabled(true);
			scn.mn.s3.setEnabled(true);
			scn.mn.s4.setEnabled(true);
			//scn.mn.s5.setEnabled(true);
			scn.mn.noCancel.setEnabled(true);
			scn.mn.doubleGravity.setEnabled(true);
			scn.mn.fullCancel.setEnabled(true);
		}
	}

	public void takeDamage(GameObject foe, BoundingBox box, int damage) 
	{
		if (invincible < 1)
		{
			life -= damage;
			
			//find direction to the box
			int hitDirection = direction;
			double xDifference = priorX - foe.x+box.xOffset;
			if (xDifference != 0)
				hitDirection = (int)(xDifference/Math.abs(xDifference));
				
			//ySpeed = -6.0;
			//xSpeed = 16.0*hitDirection;
			//xForce.value = 4.0*hitDirection;
			//if (yForce.value > -180.0)
				//yForce.value = -180.0;
			//direction = -hitDirection;
			sliding = -10;
			flipY = false;
			//if (direction > 0)
			//	flipX = true;
			//else
			//	flipX = false;
			
			//scn.spEffects.radialParticles(7, scn.store.beamHook, x, y, 4.0, 6.0, 3, 5, 5, 16, 0, 0, 0);
			
			onTerrain = null; //is this too dangerous?
			
			//gotHit = 25; //timer for recovering from a hit
			invincible = 80; //invincibility frames
		}
	}
	
	public void die()
	{
		scn.spEffects.radialParticles(50, scn.store.jetStream, x, y, 240.0, 480.0, 20, 50, 16, 16, 1.0, 30.0, 480.0);
		//reload
		//scn.mn.state.load();
		//System.out.println("reloading game "+scn.mn.state.savedLevel);
		//scn.swapper.mark(scn.mn.state.savedLevel, 150, scn.mn.state.saveX, scn.mn.state.saveY);
		
		super.die();
	}

	public int getNumGems() 
	{
		return gems;
	}
	
	
}
