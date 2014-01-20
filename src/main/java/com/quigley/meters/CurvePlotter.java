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
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.CubicCurve2D;
import java.util.List;

/**
 * A <code>Plotter</code> implementation, which uses linked cubic curves to produce a smooth, curved plot.
 * 
 * Inspired by the implementation of the <a href="https://www.gnome.org/">Gnome System Monitor</a>.
 * 
 * @author Michael Quigley
 */
public class CurvePlotter implements Plotter {
	/**
	 * @see Plotter
	 */
	@Override
	public double paint(Graphics2D g, Rectangle r, Color c, List<Sample> samples, double sampleWidth, double offset, double currentMax) {
		double max = 0.0;
		if(samples != null) {
			g.setColor(c);
			g.setStroke(new BasicStroke(1.75F));
			
			int x = (int) Math.ceil(r.x + r.width + sampleWidth - offset);
			
			Point lastPoint = null;
			for(int i = samples.size() - 1; i >= 0 && x >= r.x - sampleWidth; i--) {
				Sample s = samples.get(i);
				double fraction = s.getValue() / currentMax;
				int height = (int) (r.getHeight() * fraction);
				
				if(lastPoint == null) {
					lastPoint = new Point(x, (int) (r.y + r.getHeight() - height - 1));
					
				} else {
					Point p = new Point(x, (int) (r.y + r.getHeight() - height - 1));
					CubicCurve2D.Double curve = new CubicCurve2D.Double(lastPoint.x, lastPoint.y,
																		lastPoint.x - (sampleWidth / 2.0), lastPoint.y,
																		p.x + (sampleWidth / 2.0), p.y,
																		p.x, p.y);
					g.draw(curve);
					lastPoint = p;
				}
	
				if(s.getValue() > max) {
					max = s.getValue();
				}
				
				x -= sampleWidth;
			}
		}
		return max;
	}
}