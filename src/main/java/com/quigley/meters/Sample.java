package com.quigley.meters;

public class Sample {
	public Sample(double value, long timeStamp) {
		this.value = value;
		this.timeStamp = timeStamp;
	}
	
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	
	public long getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	private double value;
	private long timeStamp;
}