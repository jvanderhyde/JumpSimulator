package edu.benedictine.game.util;

import java.util.ArrayList;

public class SortedList<E> extends ArrayList<E>
{
	int[] sections;
	
	public SortedList(int sections)
	{
		this.sections = new int[sections+1];
		for (int i=0; i<this.sections.length; i++)
			this.sections[i] = 0;
	}
	
	@Override
	public void add(int priority, E item)
	{
		if ((priority < sections.length-1) && (priority >= 0))
		{
			super.add(sections[priority], item);
			for (int i=priority+1; i<this.sections.length; i++)
				sections[i]++;
		}
		else
			throw new IllegalArgumentException("Invalid priority. Must be 0 - "+(sections.length-1));
	}
	
	public boolean remove(E item, int priority)
	{
		boolean val = super.remove(item);
		if (val == true)
			for (int i=priority+1; i<this.sections.length; i++)
				sections[i]--;
		return val;
	}
}
