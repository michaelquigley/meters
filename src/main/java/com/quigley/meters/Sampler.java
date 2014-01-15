package com.quigley.meters;

import java.util.List;

public abstract class Sampler {
	public abstract void sample();
	
	public List<Sample> getSamples() {
		return samples;
	}
	public void setSamples(List<Sample> samples) {
		this.samples = samples;
	}

	protected List<Sample> samples;
}