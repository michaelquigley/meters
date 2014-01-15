package com.quigley.meters;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.List;

public interface Plotter {
	/**
	 * @return the maximum sample value from the provided set.
	 */
	public double plot(Graphics2D g2d, Rectangle r, Color c, List<Sample> samples, double sampleWidth, double offset, double currentMax);
}