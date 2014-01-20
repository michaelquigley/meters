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
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.List;

/**
 * The Plotter interface defines the method that must be implemented by any component, which will plot
 * the values of a collection of samples for a meter. Plots are assumed to go from right to left, with
 * the newest sample values shown arriving at the right side of the plot.
 * 
 * @author Michael Quigley
 */
public interface Plotter {
	/**
	 * Paint the plot of the provided list of samples.
	 * 
	 * Smooth scrolling is implemented by starting the x position of the newest sample at the location
	 * <code>plotR.x + plotR.width - offset</code>. The meter automatically computes the relevant offset to
	 * align all of the Plotters.
	 * 
	 * The returned value is the highest observed value within the list of samples. This is used by the meter
	 * to adjust the vertical scaling.
	 * 
	 * @param g the Graphics2D instance for drawing the plot.
	 * @param plotR the rectangle for the plotting area.
	 * @param c the color for this plot.
	 * @param samples the list of current samples.
	 * @param sampleWidth the spacing between samples in the meter.
	 * @param offset the starting horizontal offset position.
	 * @param currentMax the current maximum value observed amongst all of the samplers in the meter.
	 * @return The maximum value observed within the list of samples.
	 */
	public double paint(Graphics2D g, Rectangle plotR, Color c, List<Sample> samples, double sampleWidth, double offset, double currentMax);
}