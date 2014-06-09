//Created by Joseph Rioux, Jun 8, 2014

package edu.benedictine.game.engine;

import edu.benedictine.game.gui.Scene;
import edu.benedictine.jump.players.Custom;
import edu.benedictine.jump.players.Eversion;
import edu.benedictine.jump.players.InputInfo;
import edu.benedictine.jump.players.Mario;
import edu.benedictine.jump.players.PlayerControl;
import edu.benedictine.jump.players.PlayerInfo;
import edu.benedictine.jump.players.Samus;

public class TestPlayer extends GameObject
{
	private PlayerControl control;
	private PlayerInfo playerInfo;
	private InputInfo inputInfo;
	
    public TestPlayer(Scene scn, double xLoc, double yLoc)
    {
		super(scn, 5, 8, xLoc, yLoc, 0.0, 0.0, null, -12.0, 12.0, -12.0, 12.0);
		this.playerInfo = new PlayerInfo();
		this.inputInfo = new InputInfo();
		this.control = new Samus();
		forceOn = true;
		halfWidth = 12;
		halfHeight = 12;
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
		inputInfo.jumpPressed = scn.panel.getInputManager().isJumpPressed();
		inputInfo.leftPressed = scn.panel.getInputManager().isLeftPressed();
		inputInfo.rightPressed = scn.panel.getInputManager().isRightPressed();
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

		final boolean leftPressed = scn.panel.getInputManager().isLeftPressed();
		final boolean rightPressed = scn.panel.getInputManager().isRightPressed();
		
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