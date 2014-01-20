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
 * The <code>HorizontalAxis</code> defines the implementation used to decorate a meter with a horizontal indication
 * of time passing.
 * 
 * The <code>HorizontalAxis</code> is provided with configuration items from the meter, including <code>Color</code>
 * values, <code>Rectangle</code> instances defining the drawing areas, a list of representative <code>Sample</code>
 * instances from the meter, and information about the arrangement of the horizontal plotting axis.
 * 
 * @author Michael Quigley
 */
public interface HorizontalAxis {
	/**
	 * @return The preferred height for the horizontal axis label.
	 */
	public int preferredHeight();
	
	/**
	 * Paint the axis (and label area) using the provided <code>Graphics2D</code> instance.
	 * 
	 * @param g the provided <code>Graphics2D</code> instance.
	 * @param backgroundColor the background color of the meter.
	 * @param foregroundColor the foreground color of the meter.
	 * @param horizontalAxisLabelR the <code>Rectangle</code> defining the label area for this
	 * 		axis label.
	 * @param plotR the <code>Rectangle</code> definining the plotting area for the meter.
	 * @param stampLabeler the <code>StampLabeler</code>, which can be used to format <code>Sample</code>
	 * 		<code>stamp</code> values.
	 * @param samples the list of <code>Sample</code> instances for one of the samplers in the meter.
	 * @param sampleWidth the currently configured display width for samples.
	 * @param offset the current offset from the right edge of the plotter (to faciliate smooth scrolling).
	 */
	public void paint(Graphics2D g, Color backgroundColor, Color foregroundColor,
					  Rectangle horizontalAxisLabelR, Rectangle plotR,
					  StampLabeler stampLabeler,
					  List<Sample> samples,
					  double sampleWidth,
					  double offset);
}