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

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * A framework component, which is capable of harvesting <code>Sample</code> data and making it available to
 * a <code>PlotterMeter</code>.
 * 
 * @author Michael Quigley
 */
public abstract class Sampler {
	public Sampler() {
		color = Color.white;
	}
	
	/**
	 * Override this method in your class. Your implementation should retrieve data from the observed data
	 * structure and then insert it into the list of samples (using <code>addSample</code>).
	 * 
	 * This method is called from the user interface event queue. You should be very careful to make sure that
	 * your implementation of this method does not take "too long". Ideally, any "heavy lifting" should be done
	 * in a background thread and placed into a data structure where this method can simply (and quickly)
	 * retrieve the latest data.
	 * 
	 * If your method takes "too long", then the user interface could become unresponsive.
	 */
	public abstract void sample();
	
	public List<Sample> getSamples() {
		return samples;
	}
	public void setSamples(List<Sample> samples) {
		this.samples = samples;
	}
	public void addSample(Sample sample) {
		if(samples == null) {
			samples = new ArrayList<Sample>();
		}
		samples.add(sample);
		if(samples.size() > 4096) {
			samples.remove(0);
		}
	}
	
	/**
	 * @return the <code>Sample</code> at <code>size() - 1</code>.
	 */
	public Sample front() {
		if(samples != null && samples.size() > 0) {
			return samples.get(samples.size() - 1);
			
		} else {
			return null;
		}
	}
	
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}

	private List<Sample> samples;
	private Color color;
}