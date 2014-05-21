//Main application to run the simulator
//Created by James Vanderhyde, 21 May 2014

package edu.benedictine.jump;

import edu.benedictine.game.gui.GameApplication;

public class JumpSimulatorApplication extends GameApplication
{
	public static void main(String[] args)
	{
		JumpSimulatorApplication app = new JumpSimulatorApplication();
		SimulatorPanel pan = new SimulatorPanel();
		
		app.setWindowTitle("Jump Simulator");
		app.setup(pan);
	}
	
}
