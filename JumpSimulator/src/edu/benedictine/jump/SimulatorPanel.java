//The panel that contains the simulation and GUI elements
//Created by James Vanderhyde, 21 May 2014

package edu.benedictine.jump;

import edu.benedictine.game.engine.GameObject;
import edu.benedictine.game.gui.GamePanelFixedFPS;
import edu.benedictine.game.gui.Scene;
import edu.benedictine.game.gui.SceneGraphics;
import edu.benedictine.game.input.*;
import edu.benedictine.jump.players.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.HashMap;
import javax.swing.JPanel;

public class SimulatorPanel extends GamePanelFixedFPS
{
	private Director director;
	private InputManager inputManager;
	private Scene currentScene;
	private SceneGraphics scnGraphics;

	//simulator values
	private SimVariableFloat gravity = new SimVariableFloat();
	private SimVariableFloat jumpPower = new SimVariableFloat();
	private SimVariableFloat xSpeed = new SimVariableFloat();
	private SimVariableFloat airDecel = new SimVariableFloat();
	private SimVariableChoice jumpCancelType = new SimVariableChoice("no","double","full");
	
	private void setDefaultValuesForVariables()
	{
		gravity.setValue(30.0);
		jumpPower.setValue(600.0);
		xSpeed.setValue(120.0);
		airDecel.setValue(50.0);
		jumpCancelType.setValue("full");
	}
	
	//game state variables
	private GameObject player;
	
	public SimulatorPanel()
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
		
		setDefaultValuesForVariables();
		
		//Set up user interface
		director = new Director();
		director.addSlider("Gravity: ", 1, 100, 100, gravity);
		director.addSlider("Jump Power: ", 120, 1200, 10, jumpPower);
		director.addSlider("Horizontal Inertia: ", 0, 100, 11, airDecel);
		director.addSlider("Max Horizontal Speed: ", 30, 600, 20, xSpeed);
		HashMap<String,String> jumpCancelLabelMap = new HashMap<String,String>();
		jumpCancelLabelMap.put("no", "No Canceling");
		jumpCancelLabelMap.put("double", "Double Gravity");
		jumpCancelLabelMap.put("full", "Full Canceling");
		director.addRadioGroup(" Jump Cancel:", jumpCancelType, jumpCancelLabelMap);
		
		Director.PlayerClassObserver obs = new Director.PlayerClassObserver()
		{
			public void playerClassChanged(PlayerControl playerClass)
			{
				if (player != null && player instanceof SimPlayer)
					((SimPlayer)player).setPlayerControl(playerClass);
				playerClass.getJumpType(gravity, jumpPower, xSpeed, airDecel, jumpCancelType);
			}
		};
		director.addPlayerClass(new Custom(this),obs);
		director.addPlayerClass(new Mario(),obs);
		director.addPlayerClass(new Samus(),obs);
		director.addPlayerClass(new Eversion(),obs);
		director.addPlayerClass(new Discrete(),obs);
		director.addPlayerClass(new MegaMan(),obs);
		
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
		this.add(director.getSouthPanel(),BorderLayout.SOUTH);
		this.add(director.getEastPanel(),BorderLayout.EAST);
		
	}
	
	private void paintGameCanvas(Graphics g)
	{
		if (currentScene != null)
			scnGraphics.paintScene(currentScene, g);
		//player position and speed info
		if (player != null)
		{
			Color c = g.getColor();
			g.setColor(Color.YELLOW);
			g.drawString("Player X: "+(int)player.getX(), vLeft+8, vTop+16);
			g.drawString("Player Y: "+(int)player.getY(), vLeft+8, vTop+32);
			g.drawString("Player X Speed: "+player.getXForce().value, vLeft+8, vTop+48);
			g.drawString("Player Y Speed: "+player.getYForce().value, vLeft+8, vTop+64);
			g.setColor(c);
		}
	}

	@Override
	public void createGame()
	{
		//Set up scene
		currentScene = new Scene(this.inputManager, "pictest.png", "nullLVL");
		player = new SimPlayer(this,currentScene,96,128);
		//player = new DefaultPlayer(this.getInputManager(),currentScene,96,128);
		currentScene.replacePlayer(player);
		scnGraphics = new SceneGraphics(vRight-vLeft, vBottom-vTop);
	}
	
	@Override
	public void updateGame()
	{
		currentScene.updateScene();
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
	
	//Accessor methods for sim variables
	// This may need to be refactored again, so we have a Map of
	// sim variables instead of having them hard-coded.
	public double getGravity()
	{
		return gravity.getValue();
	}
	
	public double getJumpPower()
	{
		return jumpPower.getValue();
	}
	
	public double getXSpeed()
	{
		return xSpeed.getValue();
	}
	
	public double getAirDecel()
	{
		return airDecel.getValue();
	}
	
	public boolean jumpCancelTypeIsNone()
	{
		return jumpCancelType.valueEquals("none");
	}
	
	public boolean jumpCancelTypeIsDoubleGravity()
	{
		return jumpCancelType.valueEquals("double");
	}
	
	public boolean jumpCancelTypeIsFull()
	{
		return jumpCancelType.valueEquals("full");
	}
	
}
