//Frame for running the game
//James Vanderhyde, 22 May 2010
//Modified by James Vanderhyde, 10 July 2012
// Restructured for use as a library class.
// Changed from JFrame to Frame.
//Modified by James Vanderhyde, 22 May 2014
// Removed focus workarounds (use KeyboardFocusManager instead)

package edu.benedictine.game.gui;

import java.awt.Graphics;
import java.awt.Frame;
import java.awt.event.WindowEvent;

public class GameApplication
{

	/**
	 * Subclasses should create their own main method that initializes the
	 * GamePanel object and then calls setup().
	 *
	 * @param args command-line arguments (not used)
	 */
	public static void main(String[] args)
	{
		(new GameApplication()).setup(new GamePanel()
		{
			public void createGame()
			{
			}

			public void paintGame(Graphics g)
			{
			}

			public void updateGame(long timeSinceLastUpdate)
			{
			}

			public void destroyGame()
			{
			}
		});
	}

	private GamePanel game;
	private Thread gameLoop;
	private Frame f;

	protected GameApplication()
	{
		f = new Frame();
		game = null;
		gameLoop = null;
	}

	protected void setWindowTitle(String title)
	{
		this.f.setTitle(title);
	}

	protected void setup(GamePanel gamePanel)
	{
		game = gamePanel;

		f.add(game);
		f.addWindowListener(new java.awt.event.WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent evt)
			{
				f.setVisible(false);
				f.dispose();
				game.destroy();
				System.out.println("Game destroyed.");
			}

			@Override
			public void windowDeactivated(WindowEvent evt)
			{
				game.deactivateGame();
			}

			@Override
			public void windowActivated(WindowEvent evt)
			{
				startGame();
				game.activateGame();
			}
		});

		f.pack();
		f.setVisible(true);
	}

	private void startGame()
	{
		if (gameLoop == null)
		{
			gameLoop = new Thread(game);
			game.createGame();
			System.out.println("Game created.");
			gameLoop.start();
		}
	}
}
