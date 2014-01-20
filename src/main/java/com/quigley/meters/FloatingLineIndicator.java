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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Stroke;

/**
 * A floating line indicator is a horizontal line, stretching across the plotting area of a meter,
 * which indicates the value of the newest sample for a <code>Sampler</code>. 
 * 
 * The line "floats" to the newest value, providing an organic and easy to parse visual indication 
 * of the state of a <code>Sampler</code>.
 * 
 * @author Michael Quigley
 */
public class FloatingLineIndicator implements Indicator {
	/**
	 * @param sampler the <code>Sampler</code> instance to monitor.
	 */
	public FloatingLineIndicator(Sampler sampler) {
		this.sampler = sampler;
	}
	
	/**
	 * @see Indicator
	 */
	@Override
	public void paint(Graphics2D g, Color foregroundColor, Rectangle plotR, double currentMax) {
		Sample front = sampler.front();
		if(front != null) {
			targetValue = front.getValue();
			if(currentValue != targetValue) {
				currentValue += ((targetValue - currentValue) / 10.0);
			}

			double fraction = currentValue / currentMax;
			int height = (int) (plotR.height * fraction);
			int y = plotR.y + plotR.height - height;
			
			g.setStroke(stroke);
			g.setColor(foregroundColor);
			g.drawLine(plotR.x, y, plotR.x + plotR.width, y);
			
			Polygon arrow = new Polygon();
			arrow.addPoint(plotR.x + plotR.width - 15, y + 5);
			arrow.addPoint(plotR.x + plotR.width - 15, y - 5);
			arrow.addPoint(plotR.x + plotR.width, y);
			arrow.addPoint(plotR.x + plotR.width - 15, y + 5);
			g.setColor(sampler.getColor());
			g.fillPolygon(arrow);
		}
	}	

	private Sampler sampler;
	private double targetValue;
	private double currentValue;
	
	private static Stroke stroke = new BasicStroke(1.0F);
}