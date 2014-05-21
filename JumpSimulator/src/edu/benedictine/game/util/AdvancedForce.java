//created by Joseph Rioux, 10 December 2013

package edu.benedictine.game.util;

public class AdvancedForce extends LinearForce
{
	int frequency, counter;
	
	public AdvancedForce(double value, double accel, double decel, double lower, double upper, int frequency)
	{
		super(value, accel, decel, lower, upper);
		this.frequency = frequency;
	}
	
	public void updateValue()
	{
		//only update when the counter gets to frequency variable
		//basically, frequency means "once every x frames"
		if (counter == frequency)
		{
			super.updateValue();
			counter = 0;
		}
		counter++;
	}
}
