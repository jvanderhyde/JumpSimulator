package edu.benedictine.game.util;

//Created by Joseph Rioux, 24 June 2013
//to manage object names

import java.util.ArrayList;

public class NameManager 
{
	ArrayList<String> names;
	ArrayList<Object> objs;

	public NameManager()
	{
		names = new ArrayList<String>();
		objs = new ArrayList<Object>();
	}
	
	public void add(Object o, String name)
	{
		names.add(name);
		objs.add(o);
	}
	
	public Object search(String target)
	{
		for (int i=0; i<names.size(); i++)
		{
			if (target.equals(names.get(i)))
				return objs.get(i);
		}
		return null;
	}
}
