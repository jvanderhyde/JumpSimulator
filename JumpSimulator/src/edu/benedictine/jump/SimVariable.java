//Superclass for simulation variables
//Created by James Vanderhyde, 29 May 2014

package edu.benedictine.jump;

import java.util.ArrayList;

public abstract class SimVariable
{
	public interface StateListener
	{
		public void stateChanged();
	}
	
	private ArrayList<StateListener> listeners;
	
	public void addStateListener(StateListener listener)
	{
		if (listeners==null)
			listeners=new ArrayList<StateListener>();
		listeners.add(listener);
	}
	
	public void removeStateListener(StateListener listener)
	{
		if (listeners!=null)
			listeners.remove(listener);
	}
	
	public StateListener[] getStateListeners()
	{
		if (listeners!=null)
			return listeners.toArray(new StateListener[listeners.size()]);
		else
			return new StateListener[0];
	}
	
	protected void notifyListeners()
	{
		if (listeners!=null)
			for (StateListener l:listeners)
				l.stateChanged();
	}
}
