package com.quigley.meters;

import java.util.Random;

public class RandomSampler extends Sampler {
	public RandomSampler() {
		count = 0;
		max = 100.0;
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