//The player implementation for the simulator
//Created by James Vanderhyde, 28 May 2014
//  Refactored from the game engine Player class

package edu.benedictine.jump;

import edu.benedictine.game.engine.Player;
import edu.benedictine.game.gui.Scene;

public abstract class SimPlayer extends SimpleObject
{
	protected SimulatorPanel sim;

	public SimPlayer(int floor)
	{
		super(floor);
	}

	public SimPlayer(SimulatorPanel sim, Scene scn, double xLoc, double yLoc, double xCng, double yCng)
	{
		super(sim.getViewport().height+sim.getViewport().y);
		this.sim = sim;
	}
	
	public void setJumpType(SimVariableFloat gravity, 
							SimVariableFloat jumpPower, 
							SimVariableFloat xSpeed, 
							SimVariableFloat airDecel, 
							SimVariableChoice jumpCancelType)
	{
	}
	
	@Override
	public void update()
	{
		walk();
		jump();
		super.update();
	}
	
	//@Override
	public abstract void walk();
	
	//@Override
	public abstract void jump();
}
