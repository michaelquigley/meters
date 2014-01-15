package com.quigley.meters;

public class Sample {
	public Sample(double value) {
		this.value = value;
	}
	
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}

	private double value;
}