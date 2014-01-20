/*
	Copyright (c) 2014 Michael F. Quigley Jr.
	
	This file is part of Quigley Meters.
	
	Quigley Meters is free software: you can redistribute it and/or modify
	it under the terms of the GNU Lesser General Public License as 
	published by the Free Software Foundation, either version 3 of 
	the License, or (at your option) any later version.
	
	Quigley Meters is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU Lesser General Public License for more details.
	
	You should have received a copy of the GNU Lesser General Public 
	License along with Quigley Meters. If not, see <http://www.gnu.org/licenses/>.
 */

package com.quigley.meters;

import java.util.Date;

/**
 * A point-in-time data element, to be shown on a <code>PlotterMeter</code>.
 * 
 * A <code>Sample</code> contains two properties: a "value", and a "stamp". The value property captures
 * a numeric data point. The stamp property represents the point-in-time when the value was read.
 * 
 * @author Michael Quigley
 */
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