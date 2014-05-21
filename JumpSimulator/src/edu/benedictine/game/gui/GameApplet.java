//Applet for running the game
//James Vanderhyde, 15 July 2010
//Modified by James Vanderhyde, 10 July 2012
// Restructured for use as a library class.

package edu.benedictine.game.gui;

public abstract class GameApplet extends javax.swing.JApplet
{

	protected GamePanel game;
	private Thread gameLoop;

	/**
	 * Subclasses should override this method to initialize the GamePanel object
	 * and then call setup().
	 */
	@Override
	public abstract void init();

	protected final void setup(GamePanel gamePanel)
	{
		game = gamePanel;
		this.add(game);
		this.addFocusListener(new GetFocusToStart());

		gameLoop = null;
	}

	@Override
	public void start()
	{
		game.activateGame();
	}

	@Override
	public void stop()
	{
		game.deactivateGame();
	}

	@Override
	public void destroy()
	{
		game.destroy();
	}

	private void startGame()
	{
		if (gameLoop == null)
		{
			gameLoop = new Thread(game);
			gameLoop.start();
			game.startGame();
		}
	}

	private class GetFocusToStart extends java.awt.event.FocusAdapter
	{

		@Override
		public void focusGained(java.awt.event.FocusEvent evt)
		{
			startGame();
		}
	}
}
