package com.quigley.meters;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Stroke;

public class FloatingLineIndicator implements Indicator {
	public FloatingLineIndicator(Sampler sampler) {
		this.sampler = sampler;
	}
	
	@Override
	public void paint(Graphics2D g, Color foreground, Rectangle plotR, double currentMax) {
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
			g.setColor(foreground);
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