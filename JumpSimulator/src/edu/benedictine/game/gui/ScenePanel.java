//The panel that contains the simulation and GUI elements
//Created by James Vanderhyde, 21 May 2014

package edu.benedictine.game.gui;

import edu.benedictine.game.input.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

public class ScenePanel extends GamePanelFixedFPS
{
	private InputManager inputManager;
	private Scene currentScene;
	private SceneGraphics scnGraphics;
	public Color backgroundColor=Color.BLACK;
	
	public ScenePanel()
	{
		//Set up game display
		vLeft = 0;
		vTop = 0;
		vRight = 1024;
		vBottom = 512;
		JPanel gameCanvas = new JPanel()
		{
			@Override
			public void paintComponent(Graphics g)
			{
				paintGameCanvas(g);
			}
		};
		gameCanvas.setPreferredSize(new Dimension(vRight-vLeft,vBottom-vTop));
		this.setPreferredSize(null);
		
		//set up scene graphics
		scnGraphics = new SceneGraphics(this, vRight, vBottom);
		
		//Set up game input
		inputManager = new InputManager();
		KeyHandler keyHandler = new KeyHandler();
		keyHandler.captureAllKeyEvents();
		inputManager.addHandler(keyHandler);
		GamepadHandler gamepadHandler = new CHProductsUSBGamepad();
		gamepadHandler.startGamepadThread();
		if (gamepadHandler.isGamepadAttached())
			inputManager.addHandler(gamepadHandler);
		
		//Add everything to the main panel
		this.setLayout(new BorderLayout());
		this.add(gameCanvas,BorderLayout.CENTER);
		
		//Set up scene
		currentScene = new Scene(this.inputManager, "pictest.png", "nullLVL");
	}
	
	private void paintGameCanvas(Graphics g)
	{
		scnGraphics.paintScene(currentScene, g);
	}

	@Override
	public void startGame()
	{

	}

	@Override
	public void updateGame()
	{
		if (currentScene != null)
		{
			currentScene.updateScene();
		}
	}

	@Override
	public void destroyGame()
	{
	}

	@Override
	public void paintGame(Graphics g)
	{
		//do nothing, because it's handled by the canvas
	}
	
	public InputManager getInputManager()
	{
		return inputManager;
	}
	
	public void changeScene(String scn, String prev, double nX, double nY)
	{
		currentScene = new Scene(this.inputManager, scn, prev);
	}
	
}
