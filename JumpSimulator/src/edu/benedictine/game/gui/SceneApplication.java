//Main application to run the simulator
//Created by James Vanderhyde, 21 May 2014

package edu.benedictine.game.gui;

import edu.benedictine.game.gui.GameApplication;

public class SceneApplication extends GameApplication
{
	public static void main(String[] args)
	{
		SceneApplication app = new SceneApplication();
		ScenePanel pan = new ScenePanel();
		
		app.setWindowTitle("Scene");
		app.setup(pan);
	}
}
