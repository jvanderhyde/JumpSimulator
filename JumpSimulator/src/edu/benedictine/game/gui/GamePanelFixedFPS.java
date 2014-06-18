//Use a fixed framerate instead of variable as in the base class
//Created by James Vanderhyde, 21 May 2014

package edu.benedictine.game.gui;

public abstract class GamePanelFixedFPS extends GamePanel
{
	protected long timeBetweenFrames = 16;
	
	@Override
	public void run()
	{
		//As long as run() is called after the component is on screen, this should work.
		gc = this.getGraphicsConfiguration();

		long oldTime, newTime;

		System.out.println("Game loop started.");
		while (!threadDone)
		{
			oldTime = System.currentTimeMillis();
			if (active)
			{
				updateGame(timeBetweenFrames);
				repaint();
			}
			newTime = System.currentTimeMillis();
			long timeSpentUpdating = newTime-oldTime;
			if (timeSpentUpdating < timeBetweenFrames)
			{
				try
				{
					Thread.sleep(timeBetweenFrames-timeSpentUpdating);
				}
				catch (InterruptedException e)
				{
				}
			}
		}
		System.out.println("Game loop stopped.");
	}
	
	@Override
	public final void updateGame(long timeSinceLastUpdate)
	{
		updateGame();
	}
			
	public abstract void updateGame();

	
}
