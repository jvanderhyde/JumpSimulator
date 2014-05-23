//A simulation variable that represents a floating-point value
//Created by James Vanderhyde, 22 May 2014

package edu.benedictine.jump;

public class SimVariableFloat
{
	private double value;
	
	public SimVariableFloat()
	{
		value=0.0;
	}
	
	public SimVariableFloat(double value)
	{
		this.value=value;
	}
	
	public double getValue()
	{
		return value;
	}
	
	public void setValue(double newValue)
	{
		value = newValue;
	}
	
}
