//created by Joseph Rioux, 6 June 2013
//an x OR y force that automatically increases or decreases over time

public class LinearForce
{
	double value, accel, decel, lower, upper;

	public LinearForce(double value, double accel, double decel, double lower, double upper)
	{
		this.value = value;
		this.accel = accel;
		this.decel = decel;
		this.lower = lower;
		this.upper = upper;
	}
	
	public void setLimits(double u, double l)
	{
		upper = u;
		lower = l;
	}

	public void updateValue()
	{
		//if the value is between the limits, act normally
		if ((value <= upper) && (value >= lower))
		{
			value += accel;
			if (value > upper)
				value = upper;
			if (value < lower)
				value = lower;
		}
		//if the value is outside the limits, decelerate to the limits
		else if (value > upper)
		{
			value -= decel;
			if (value < upper)
				value = upper;
		}
		else if (value < lower)
		{
			value += decel;
			if (value > lower)
				value = lower;
		}
	}
	
	public double getSpeed()
	{
		return value;
	}
	
	public String toString()
	{
		return "upper: "+upper+", lower: "+lower+", value: "+value+", accel: "+accel+", decel: "+decel;
	}
}
