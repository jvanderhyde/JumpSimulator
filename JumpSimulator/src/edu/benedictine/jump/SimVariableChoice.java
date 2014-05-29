//A simulation variable that represents one of several choices
//Created by James Vanderhyde, 22 May 2014

package edu.benedictine.jump;

import java.util.Arrays;
import java.util.HashSet;

public class SimVariableChoice extends SimVariable
{
	private String value;
	private HashSet<String> choices;
	
	public SimVariableChoice(String... choices)
	{
		this.choices = new HashSet<String>();
		this.choices.addAll(Arrays.asList(choices));
	}
	
	public boolean valueEquals(String target)
	{
		if (value != null)
			return value.equals(target);
		else
			return false;
	}
	
	public void setValue(String newValue)
	{
		if (choices.contains(newValue))
			value=newValue;
		else
			throw new IllegalArgumentException("Not a valid choice: "+newValue);
		this.notifyListeners();
	}
}
