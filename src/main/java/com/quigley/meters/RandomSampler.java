package com.quigley.meters;

import java.util.ArrayList;
import java.util.Random;

public class RandomSampler extends Sampler {
	@Override
	public void sample() {
		if(samples == null) {
			samples = new ArrayList<Sample>();
		}
		samples.add(new Sample(new Random().nextDouble() * 100.0));
	}
}