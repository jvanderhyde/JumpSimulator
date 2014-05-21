//Created by Joseph Rioux, 27 March 2013

//think carefully about this one very important class

//Basic attack class. The hitbox is a very versatile class, a GameObject that is sorted for
//priority in collision. A given object can usually only collide with one hitbox per frame,
//the most prior one. Subclasses will make the hitbox more focused (e.g. melee hitboxes, 
//which are mounted to the attacker's position)

//Both enemy and player attacks will use hitBoxes. It has damage and power: damage is the actual
//amount dealt if it hits, while power dictates what kinds of things it can hit (e.g. the sword
//is powerful enough to break a pot, but not powerful enough to break a boulder).

//priorities: 0 - 14: hitboxes ; 15: player? ; 16 - 24: other boundingBoxes

package edu.benedictine.game.engine;

import edu.benedictine.game.media.ImageSource;
import edu.benedictine.game.engine.collision.BoundingBox;
import edu.benedictine.game.engine.collision.Hit;
import edu.benedictine.game.gui.Scene;
import java.util.ArrayList;

public class HitBox extends GameObject
{
	GameObject caster;
	int priority;
	int power;
	int damage;
	int delay = 0;
	
	//these key in to what the hitbox does on contact
    int hitFloor;
	int hitWalls;
	int hitCeiling;
	int hitCaster;
	int hitPlayer;
	int hitEnemies;
	int hitObjects;
	int hitHitBoxes;

	public HitBox(Scene scn, int exec, int draw, GameObject caster, double xLoc, double yLoc, double xCng, double yCng, ImageSource img, double width, double height, int priority, int power, int damage, int delay, int lifeTime, double gravity, double maxFall) 
	{
		super(scn, 0, draw, xLoc, yLoc, xCng, yCng, img, -(height/2.0), (height/2.0), -(width/2.0), (width/2.0));
		this.caster = caster;
		this.priority = priority;
		this.power = power;
		this.damage = damage;
		this.delay = delay;
		this.lifeTime = lifeTime;
		setFall(gravity, maxFall);
		box = new BoundingBox(scn, this, 0, new int[]{0}, priority, 0.0, 0.0, (width/2.0), (height/2.0));
	}
	
	public void setHits(int hitFloor, int hitWalls, int hitCeiling, int hitCaster, int hitPlayer, int hitEnemies, int hitObjects, int hitHitBoxes)
	{
		//note: 0 is default reaction, -1 is no collision
		ArrayList<Integer> cats = new ArrayList<Integer>();
		if (hitHitBoxes >= 0)
			cats.add(0);
		if (hitPlayer >= 0)
			cats.add(1);
		if (hitEnemies >= 0)
			cats.add(2);
		if (hitObjects >= 0)
			cats.add(3);
		int[] a = new int[cats.size()];
		for (int i=0; i<cats.size(); i++)
			a[i] = cats.get(i);
		box.setCategories(a);
		
		if (hitFloor == -1 && hitWalls == -1 && hitCeiling == -1)
			hitTerrain = false;

		this.hitFloor = hitFloor;
		this.hitWalls = hitWalls;
		this.hitCeiling = hitCeiling;
		this.hitCaster = hitCaster;
		this.hitPlayer = hitPlayer;
		this.hitEnemies = hitEnemies;
		this.hitObjects = hitObjects;
		this.hitHitBoxes = hitHitBoxes;
	}
	
	public void update()
	{
		if (delay < 1)
		{
			super.update();
		}
		delay--;
	}
	
	public void reactFloor()
	{
		if (hitFloor == 0)
		{
			super.reactFloor();
		}
		if (hitFloor == 1)
		{
			die();
		}
		if (hitFloor == 2)
		{
			lifeTime = 1;
			super.reactFloor();
			y = onTerrain.y0;
		}
		if (hitFloor == 3)
		{
			super.reactFloor();
			y = onTerrain.y0;
			lifeTime = 1;
			//setImage(scn.store.winkSheet[6]);
			//scn.spEffects.radialParticles(7, scn.store.winkSheet[2], x, y, 120.0, 180.0, 5, 8, 5, 16, 0, 0, 0);
		}
	}
	
	public void reactRightWall()
	{
		if (hitWalls == 0)
		{
			super.reactRightWall();
		}
		if (hitWalls == 1)
		{
			die();
		}
		if (hitWalls == 2)
		{
			lifeTime = 1;
			super.reactRightWall();
			
		}
		if (hitWalls == 3)
		{
			super.reactRightWall();
			x = atRightWall.x0;
			lifeTime = 1;
			//setImage(scn.store.winkSheet[6]);
			//scn.spEffects.radialParticles(7, scn.store.winkSheet[2], x, y, 120.0, 180.0, 5, 8, 5, 16, 0, 0, 0);
		}
	}
	
	public void reactLeftWall()
	{
		if (hitWalls == 0)
		{
			super.reactLeftWall();
		}
		if (hitWalls == 1)
		{
			die();
		}
		if (hitWalls == 2)
		{
			lifeTime = 1;
			super.reactLeftWall();
			x = atLeftWall.x0;
		}
		if (hitWalls == 3)
		{
			super.reactLeftWall();
			x = atLeftWall.x0;
			lifeTime = 1;
			//setImage(scn.store.winkSheet[6]);
			//scn.spEffects.radialParticles(7, scn.store.winkSheet[2], x, y, 120.0, 180.0, 5, 8, 5, 16, 0, 0, 0);
		}
	}
	
	public void reactCeiling()
	{
		if (hitCeiling == 0)
		{
			super.reactCeiling();
		}
		if (hitCeiling == 1)
		{
			die();
		}
		if (hitCeiling == 2)
		{
			lifeTime = 1;
			super.reactCeiling();
			y = atCeiling.y0;
		}
		if (hitCeiling == 3)
		{
			super.reactCeiling();
			y = atCeiling.y0;
			lifeTime = 1;
			//setImage(scn.store.winkSheet[6]);
			//scn.spEffects.radialParticles(7, scn.store.winkSheet[2], x, y, 120.0, 180.0, 5, 8, 5, 16, 0, 0, 0);
		}
	}
	
	public void handleHit(Hit h)
	{
		//check to see if the hit is against the object that cast the hitbox
		//if hitCaster is enabled, keep going as usual, otherwise break.
		if ((h.b.owner != caster) || (hitCaster == -1))
		{
			if ((h.b.owner instanceof Player) && (hitPlayer >= 0))
			{
				if (((Player)h.b.owner).invincible < 1)
				{
					if (hitPlayer == 0)
					{
						
					}
					if (hitPlayer == 1)
					{
						//h.b.owner.x = h.x1-h.b.xOffset;
						//h.b.owner.y = h.y1-h.b.yOffset;
						((Player)h.b.owner).takeDamage(this, box, damage);
					}
				}
			}
			if ((h.b.group == 2) && (hitEnemies >= 0))
			{
				hitEnemy();
			}
		}
	}
	
	public void hitEnemy()
	{
		
	}
	
	public void die()
	{
		//if (img != scn.store.blank)
			//scn.spEffects.radialParticles(7, scn.store.winkSheet[2], x, y, 120.0, 180.0, 5, 8, 5, 16, 0, 0, 0);
		super.die();
	}
}

/*
 * 
 * 
function hitBox::onAddToScene(%this, %scenegraph)
{
	%this.enableUpdateCallback();
	
	%this.mark = %this; //default is to mark itself
	%this.setCollisionActive(true, true);
	%this.setCollisionPhysics(false, false);
	%this.setGraphGroup(3);
}

//Basic properties
function hitBox::baseConstructor(%this, %caster, %positionX, %positionY, %sizeX, %sizeY, %sprite, %spriteSize, %animation)
{
	%this.caster = %caster;
	%this.setPosition(%positionX, %positionY);
	%this.setSize(%sizeX, %sizeY);
	
	%this.head = -%sizeY/2;
	%this.feet = %sizeY/2;
	%this.leftHand = -%sizeX/2;
	%this.rightHand = %sizeX/2;
	
	if (%sprite == true)
	{
		%this.hasSprite = true;
		%this.sprite = new t2dAnimatedSprite();
		%this.sprite.addToScene(Player.playerSceneGraph);
		%this.sprite.setPosition(%positionX, %positionY);
		%this.sprite.setWidth(%spriteSize);
		%this.sprite.setHeight(%spriteSize);
		%this.sprite.setLayer(15);
		%this.sprite.setAnimation(%animation);
		%this.sprite.setBlendColor(Player.getBlendColor());
	}
}

//more detailed
function hitBox::constructor(%this, %power, %priority, %timer, %xSpeed, %ySpeed, %gravity, %maxFall)
{
	%this.power = %power;
	%this.priority = %priority;
	%this.timer = %timer;
	%this.xSpeed = %xSpeed;
	%this.ySpeed = %ySpeed;
	%this.gravity = %gravity;
	%this.maxFall = %maxFall;
	
	//Other properties, like attack type, should also be set
	
	//set canHitSelf, canHitPlayer, canHitEnemies, canHitObjects
		//e.g. sword slash can hit enemies and objects, but not player or self,
		//but explosions can hit anything. Some enemy attacks (e.g. body slam) can hit
		//other enemies but not the attacker (caster).
	//other properties like hitsWalls, attackType, etc.?
	
	//A sprite (animatedSprite) can be associated with a hitBox (e.g. fireball)
	//In fact, it should _be_ a sprite? No, some are invisible
}

function hitBox::setHits(%this, %hitWalls, %hitCaster, %hitPlayer, %hitEnemies, %hitObjects, %onHitFloor, %onHitWalls, %onHitCeiling, %onHitSelf, %onHitPlayer, %onHitEnemies, %onHitObjects)
{
	%this.hitWalls = %hitWalls;
	%this.hitCaster = %hitCaster;
	%this.hitPlayer = %hitPlayer;
	%this.hitEnemies = %hitEnemies;
	%this.hitObjects = %hitObjects;
	
	%this.onHitFloor = %onHitFloor;
	%this.onHitWalls = %onHitWalls;
	%this.onHitWalls = %onHitCeiling;
	%this.onHitSelf = %onHitSelf;
	%this.onHitPlayer = %onHitPlayer;
	%this.onHitEnemies = %onHitEnemies;
	%this.onHitObjects = %onHitObjects;
}

function hitBox::setMark(%this, %mark, %dieOnMark)
{
	%this.mark = %mark;
	%this.dieOnMark = %dieOnMark;
	//%mark.trail = %this;?
}

function hitBox::onUpdate(%this)
{
	if (%this.delay < 1)
	{
		if (%this.mark == %this)
			%this.setPosition(%this.getPositionX()+%this.xSpeed, %this.getPositionY()+%this.ySpeed);
		else
			%this.setPosition(%this.mark.getPosition());
		
		//gravity
		if (%this.ySpeed < %this.maxFall)
			%this.ySpeed += %this.gravity;
		
		%this.checkCollision();
		
		if (%this.hasSprite == true)
			%this.sprite.setPosition(%this.getPosition());
		
		if (%this.timer == 0)
		{
			%this.safeDelete();
			if (%this.hasSprite == true)
				%this.sprite.safeDelete();
		}
		%this.timer--;
	}
	%this.delay--;
}

function hitBox::checkCollision(%this)
{
	//hitBoxes only send collisions to entities
	//they can recieve them from walls or other hitBoxes (deflection)
	%collision = %this.castCollisionList(0.0);
	%numObjects = getWordCount(%collision);
	for (%i = 0; %i < %numObjects; %i++)
	{
		%other = getWord(%collision, %i);
		if (%other.getGraphGroup() == 1)
			%this.collided(%other);
	}
}

function hitBox::collided(%this, %other)
{
	//trying something a bit new. Using eval() to dynamically execute code that is passed as a string
	//from the caster. This would allow for simple commands like "%this.sprite.setAnimation(SmallExplosion);"
	//or "%this.safeDelete();" to be executed upon collision with specific objects, like walls.
	if (%other.getGraphGroup() == 2)
	{
		
	}
	
	if ((%other.getGraphGroup() == 1) && (%this.hitWalls == true))
	{
		if (%other.getRotation() == 0.0)
			%this.floorCollision(%other);
		if (%other.getRotation() == 270.0)
			%this.leftCollision(%other);
		if (%other.getRotation() == 90.0)
			%this.rightCollision(%other);
		if (%other.getRotation() == 180.0)
			%this.ceilCollision(%other);
	}
}

function hitBox::floorCollision(%this, %other)
{
	if (%other.basicFloor(%this, %this.feet, %this.leftHand, %this.rightHand) == true)
	{
		%priorYSpeed = %this.ySpeed;
		%this.setPositionY(%other.y0-%this.feet);
		if (%this.ySpeed > 0)
			%this.ySpeed = 0;
		
		%this.onGround = true;
		%this.onTerrain = %other;
		%this.struckWall(%other, %this.onHitFloor, %this.xSpeed, %priorYSpeed);
	}
}

function hitBox::leftCollision(%this, %other)
{
	if (%other.basicLeft(%this, %this.rightHand, %this.head, %this.feet) == true)
	{
		%priorXSpeed = %this.xSpeed;
		%this.setPositionX(%other.x0-%this.rightHand);
		if (%this.xSpeed > 0)
			%this.xSpeed = 0;
		%this.struckWall(%other, %this.onHitWalls, %priorXSpeed, %this.ySpeed);
	}
}

function hitBox::rightCollision(%this, %other)
{
	if (%other.basicRight(%this, %this.leftHand, %this.head, %this.feet) == true)
	{
		%priorXSpeed = %this.xSpeed;
		%this.setPositionX(%other.x0-%this.leftHand);
		if (%this.xSpeed < 0)
			%this.xSpeed = 0;
		%this.struckWall(%other, %this.onHitWalls, %priorXSpeed, %this.ySpeed);
	}
}

function hitBox::ceilCollision(%this, %other)
{
	if (%other.basicCeil(%this, %this.head, %this.leftHand, %this.rightHand) == true)
	{
		%priorYSpeed = %this.ySpeed;
		%this.setPositionY(%other.y0-%this.head);
		if (%this.ySpeed < 0)
			%this.ySpeed = 0;
		%this.struckWall(%other, %this.onHitWalls, %this.xSpeed, %priorYSpeed);
	}
}

function hitBox::struckWall(%this, %other, %index, %xSpeed, %ySpeed)
{
	//check the index for the effect a wall has on this object
	//0 = just stop
	if (%index == 1) //just delete
	{
		%this.setPositionY(%other.y0);
		%this.timer = 0;
	}
	if (%index == 2) //bounce
	{
		if ((%other.getRotation() == 0) || (%other.getRotation() == 180))
			%this.ySpeed = -%ySpeed;
		if ((%other.getRotation() == 90) || (%other.getRotation() == 270))	
			%this.xSpeed  = -%xSpeed;
	}
	if (%index == 3) //small explosion
	{
		%this.setPositionY(%other.y0);
		SpecialEffects.smallExplosion(%this.getPositionX(), %this.getPositionY());
		%this.timer = 0;
	}
}

*/
