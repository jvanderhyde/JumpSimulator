//The player implementation for the simulator
//Created by James Vanderhyde, 28 May 2014
//  Refactored from the game engine Player class

package edu.benedictine.jump;

import edu.benedictine.game.gui.Scene;
import edu.benedictine.game.input.InputManager;
import edu.benedictine.jump.players.Custom;
import edu.benedictine.jump.players.InputInfo;
import edu.benedictine.jump.players.PlayerControl;
import edu.benedictine.jump.players.PlayerInfo;

public class SimPlayer extends SimpleObject
{
	private InputManager input;
	private PlayerControl control;
	private PlayerInfo playerInfo;
	private InputInfo inputInfo;

	public SimPlayer(SimulatorPanel sim, Scene scn, double xLoc, double yLoc, double xCng, double yCng)
	{
		super(sim.getViewport().height+sim.getViewport().y,xLoc,yLoc,xCng,yCng);
		this.input = sim.getInputManager();
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
}
