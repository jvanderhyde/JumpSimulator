//Created by Joseph Rioux, 18 July 2013

public class Enemy extends GameObject
{
	int life, damage, invincible, gotHit;
	BoundingBox attackBox;
		
	public Enemy(Scene scn, int exec, int draw, double xLoc, double yLoc, double xSpeed, double ySpeed, ImageSource img, double head, double feet, double left, double right)
	{
		super(scn, exec, draw, xLoc, yLoc, xSpeed, ySpeed, img, head, feet, left, right);
		setCollision();
	}
	
	//override depending on the enemy
	public void setCollision()
	{
		
	}
	
	public void update()
	{	
		if (life <= 0)
			die();
		
		super.update();
	}

	public void handleHit(Hit h)
	{
		if (h.b.owner instanceof Player)
		{
			if (h.a == attackBox)
				hitPlayer(((Player)h.b.owner));
		}
		if ((h.a == box) && (h.b.owner instanceof HitBox) && (((HitBox)h.b.owner).hitEnemies > -1))
		{
			if (invincible <= 0)
			{
				takeDamage((HitBox)h.b.owner);
				h.b.owner.x = h.x1-h.b.xOffset;
				h.b.owner.y = h.y1-h.b.yOffset;
				((HitBox)h.b.owner).hitEnemy();
			}
		}
	}
	
	public void hitPlayer(Player o) 
	{
		if (o.invincible < 1)
			o.takeDamage(this, attackBox, damage);
	}
	
	public void takeDamage(HitBox foe) 
	{
		life -= foe.damage;
		foe.lifeTime = 1;
		foe.die();
	}
	
	public void cleanUp()
	{
		invincible--;
		gotHit--;
		super.cleanUp();
	}
	
	public void die()
	{
		super.die();
		if (attackBox != null)
			attackBox.die();
		
		//life drops
		if ((int)(Math.random()*2) == 0)
		{
			LifeCell lif = new LifeCell(scn, x, y, 0.0, -240.0, 1280, 3);
		}
	}
}
