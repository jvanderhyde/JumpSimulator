//The player implementation for the simulator
//Created by James Vanderhyde, 28 May 2014
//  Refactored from the game engine Player class
//Modified by James Vanderhyde, 17 June 2014
//  Wired into SceneObject hierarchy

package edu.benedictine.jump;

import edu.benedictine.game.engine.GameObject;
import edu.benedictine.game.gui.Scene;
import edu.benedictine.game.input.InputManager;
import edu.benedictine.jump.players.Custom;
import edu.benedictine.jump.players.InputInfo;
import edu.benedictine.jump.players.PlayerControl;
import edu.benedictine.jump.players.PlayerInfo;
import java.awt.Color;

public class SimPlayer extends GameObject
{
	private InputManager input;
	private PlayerControl control;
	private PlayerInfo playerInfo;
	private InputInfo inputInfo;
	
	private static final int boxWidth = 24;
	private static final int boxHeight = 50;

	public SimPlayer(SimulatorPanel sim, Scene scn, double xLoc, double yLoc)
	{
		//super(sim.getViewport().height+sim.getViewport().y,xLoc,yLoc,xCng,yCng);
		super(scn, 5, 8, xLoc, yLoc, 0, 0, null, -boxHeight/2.0, boxHeight/2.0, -boxWidth/2.0, boxWidth/2.0);
		this.input = sim.getInputManager();
		forceOn = true;
		halfWidth = boxWidth/2;
		halfHeight = boxHeight/2;
		this.playerInfo = new PlayerInfo();
		this.inputInfo = new InputInfo();
		this.control = new Custom(sim);
	}
	
	public void setPlayerControl(PlayerControl control)
	{
		this.control = control;
	}
	
	@Override
	public void update()
	{
		copyToPlayerInfo();
		
		walk();
		jump();
		
		copyFromPlayerInfo();
		
		super.update();
	}

	private void copyToPlayerInfo()
	{
		playerInfo.xForce = this.xForce;
		playerInfo.yForce = this.yForce;
		playerInfo.onGround = this.onGround;
		inputInfo.jumpPressed = input.isJumpPressed();
		inputInfo.leftPressed = input.isLeftPressed();
		inputInfo.rightPressed = input.isRightPressed();
	}
	
	private void copyFromPlayerInfo()
	{
		this.xForce = playerInfo.xForce;
		this.yForce = playerInfo.yForce;
		this.onGround = playerInfo.onGround;
	}

	//@Override
	public void walk()
	{
		control.walk(playerInfo, inputInfo);

		final boolean leftPressed = input.isLeftPressed();
		final boolean rightPressed = input.isRightPressed();
		
		if (leftPressed)
			setFlipX(false);
		if (rightPressed)
			setFlipX(true);
	}
	
	//@Override
	public void jump()
	{
		control.jump(playerInfo, inputInfo);
	}
	
	@Override
	public void draw(java.awt.Graphics g, int x, int y)
	{
		g.setColor(Color.LIGHT_GRAY);
		g.drawRect(x, y, boxWidth, boxHeight);
		int offset=7;
		if (getFlipX())
			offset=3;
		g.drawOval(x+boxWidth/2-offset, y+5, 10, 10);
	}
	
}
