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
 * An <code>Indicator</code> provides an overlaid visual cue on a meter.
 * 
 * @author Michael Quigley
 */
public interface Indicator {
	/**
	 * Paint the indicator onto the provided <code>Graphics2D</code> instance.
	 * 
	 * @param g the <code>Graphics2D</code> instance to use for painting.
	 * @param foregroundColor the foreground color of the meter.
	 * @param plotR the <code>Rectangle</code> defining the plotting area for the meter.
	 * @param currentMax the current maximum observed sample value within the meter.
	 */
	public void paint(Graphics2D g, Color foregroundColor, Rectangle plotR, double currentMax);
}