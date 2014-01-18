package com.quigley.meters;

import java.util.Date;

public class Sample {
	public Sample(double value) {
		this.value = value;
		stamp = new Date();
	}
	public Sample(double value, Date stamp) {
		this.value = value;
		this.stamp = stamp;
	}
	
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}

	public Date getStamp() {
		return stamp;
	}
	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}

	private double value;
	private Date stamp;
}