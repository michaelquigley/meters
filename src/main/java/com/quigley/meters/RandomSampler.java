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
import java.util.Random;

/**
 * An example <code>Sampler</code> implementation, which produces random values.
 * 
 * This sampler produces random values within two distinct ranges. It starts out producing samples in the range
 * of 0.0..100.0. Once 20 samples have been emitted, it will switch to the range 0.0..1000.0. Every 20 samples,
 * <code>RandomSampler</code> will switch between these two ranges. This is useful for demonstrating and testing
 * the smooth vertical range animation in the <code>PlotterMeter</code>.
 * 
 * @author Michael Quigley
 */
public class RandomSampler extends Sampler {
	public RandomSampler() {
		super();
		count = 0;
		max = 100.0;
	}
	public RandomSampler(Color color) {
		super();
		setColor(color);
	}
	
	@Override
	public void sample() {
		if(count % 20 == 0) {
			if(max == 100.0) {
				max = 1000.0;
			} else {
				max = 100.0;
			}
		}
		addSample(new Sample(new Random().nextDouble() * max));
		count++;
	}
	
	private double max;
	private int count;
}