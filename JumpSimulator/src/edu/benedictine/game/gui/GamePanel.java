//An abstract class for a game in a JPanel
//James Vanderhyde, 18 May 2000
//Modified by James Vanderhyde, 30 May 2010
// Changed superclass from Applet to JPanel
//Modified by James Vanderhyde, 10 July 2012
// Added startGame method.
// Added destroyGame method.

package edu.benedictine.game.gui;

import java.awt.*;

public abstract class GamePanel extends javax.swing.JPanel
		implements Runnable
{

	protected int vTop = 0;
	protected int vLeft = 0;
	protected int vBottom = 400;
	protected int vRight = 400;
	protected boolean threadDone;
	protected final long SLEEPTIME = 20;
	protected boolean active;
	protected GraphicsConfiguration gc;

	public GamePanel()
	{
		this.setPreferredSize(new Dimension(vRight - vLeft, vBottom - vTop));
		threadDone = false;
		active = false;
		gc = null;
	}

	public abstract void startGame();

	public abstract void paintGame(Graphics g);

	public abstract void updateGame(long timeSinceLastUpdate);

	public abstract void destroyGame();

	public void run()
	{
		//As long as run() is called after the component is on screen, this should work.
		gc = this.getGraphicsConfiguration();

		long oldTime, newTime;

		System.out.println("Game created.");
		oldTime = System.currentTimeMillis();
		while (!threadDone)
		{
			try
			{
				Thread.sleep(SLEEPTIME);
			}
			catch (InterruptedException e)
			{
			}
			newTime = System.currentTimeMillis();
			if (active)
			{
				updateGame(newTime - oldTime);
				repaint();
			}
			oldTime = newTime;
		}
		System.out.println("Game destroyed.");
	}

	void activateGame()
	{
		System.out.println("Game activated.");
		active = true;
	}

	void deactivateGame()
	{
		System.out.println("Game deactivated.");
		active = false;
	}

	void destroy()
	{
		threadDone = true;
		this.destroyGame();
	}

	@Override
	public void paintComponent(Graphics g)
	{
		paintGame(g);
	}

}
