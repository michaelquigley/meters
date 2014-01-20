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

/**
 *  A <code>VerticalAxis</code> defines the implementation used to decorate a meter with a vertical indication
 *  of scale.
 *  
 *  The <code>VerticalAxis</code> instance is provided with configuration items from the meter, including
 *  <code>Color</code> values, <code>Rectangle</code> instances defining the drawing areas, and the current
 *  maximum displayed <code>value</code> in the meter.
 *  
 *  @author Michael Quigley
 */
public interface VerticalAxis {
	/**
	 * @return The preferred width for the vertical axis label.
	 */
	public int preferredWidth();
	
	/**
	 * Paint the axis (and label are) using the provided <code>Graphics2D</code> instance.
	 * 
	 * @param g the <code>Graphics2D</code> to use for painting.
	 * @param backgroundColor the background color of the meter.
	 * @param foregroundColor the foreground color of the meter.
	 * @param axisLabelR the <code>Rectangle</code> defining the label area for the label of this axis.
	 * @param plotR the <code>Rectangle</code> defining the plotting area for the meter.
	 * @param valueLabeler the <code>ValueLabeler</code>, which can be used to format <code>value</code> data.
	 * @param currentMax the current maximum displayed <code>value</code>.
	 */
	public void paint(Graphics2D g, Color backgroundColor, Color foregroundColor, 
			          Rectangle axisLabelR, Rectangle plotR, 
			          ValueLabeler valueLabeler, 
			          double currentMax);
}