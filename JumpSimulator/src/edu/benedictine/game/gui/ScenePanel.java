//The panel that contains the simulation and GUI elements
//Created by James Vanderhyde, 21 May 2014

package edu.benedictine.game.gui;

import edu.benedictine.game.gui.GamePanelFixedFPS;
import edu.benedictine.game.input.*;
import edu.benedictine.jump.players.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;
import javax.swing.JPanel;

public class ScenePanel extends GamePanelFixedFPS
{
	private InputManager inputManager;
	private Scene currentScene;
	private SceneGraphics scnGraphics;
	public Color backgroundColor=Color.BLACK;
	
	public ScenePanel()
	{
		currentScene = new Scene(this, "pictest.png", "nullLVL");
		
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
		currentScene = new Scene(this, scn, prev);
	}
	
	//world x limit 0 to 1024
	public int w2VX(double x)
	{
		return (int)Math.round(this.getWidth()*x/Main.CAN_WIDTH);
	}
	//world y limit 0 to 640
	public int w2VY(double y)
	{
		return (int)Math.round(this.getHeight()*y/Main.CAN_HEIGHT);
	}
	//world x limit 0 to 1.6
	public double v2WX(int x)
	{
		return ((double)x/(double)this.getWidth())*Main.CAN_WIDTH;
	}
	//world y limit 0 to 1
	public double v2WY(int y)
	{
		return ((double)y/(double)this.getHeight())*Main.CAN_HEIGHT;
	}
}
