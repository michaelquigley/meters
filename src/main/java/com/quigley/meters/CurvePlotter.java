package com.quigley.meters;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.CubicCurve2D;
import java.util.List;

public class CurvePlotter implements Plotter {
	@Override
	public double paint(Graphics2D g2d, Rectangle r, Color c, List<Sample> samples, double sampleWidth, double offset, double currentMax) {
		double max = 0.0;
		if(samples != null) {
			g2d.setColor(c);
			g2d.setStroke(new BasicStroke(1.75F));
			
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
					g2d.draw(curve);
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