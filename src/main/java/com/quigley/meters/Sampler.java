package com.quigley.meters;

import java.util.ArrayList;
import java.util.List;

public abstract class Sampler {
	public abstract void sample();
	
	public List<Sample> getSamples() {
		return samples;
	}
	public void setSamples(List<Sample> samples) {
		this.samples = samples;
	}
	
	protected void addSample(Sample sample) {
		if(samples == null) {
			samples = new ArrayList<Sample>();
		}
		samples.add(sample);
		if(samples.size() > 4096) {
			samples.remove(0);
		}
	}

	private List<Sample> samples;
}