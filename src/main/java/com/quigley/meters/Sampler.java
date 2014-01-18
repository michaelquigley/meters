package com.quigley.meters;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public abstract class Sampler {
	public Sampler() {
		color = Color.white;
	}
	
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