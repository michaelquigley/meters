package com.quigley.meters;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

public class PlainVerticalAxis implements VerticalAxis {
	@Override
	public int preferredWidth() {
		return 50;
	}

	@Override
	public void paint(Graphics2D g2d, Color backgroundColor, Color foregroundColor, 
			          Rectangle verticalAxisLabelR, Rectangle plotR, 
			          ValueLabeler valueLabeler, 
			          double currentMax) {
		
		g2d.setColor(backgroundColor);
		g2d.fillRect(verticalAxisLabelR.x, verticalAxisLabelR.y, verticalAxisLabelR.width, verticalAxisLabelR.height);

		g2d.setStroke(dashStroke);
		g2d.setColor(foregroundColor);

		g2d.drawLine(verticalAxisLabelR.x + verticalAxisLabelR.width - 1, verticalAxisLabelR.y, 
				     verticalAxisLabelR.x + verticalAxisLabelR.width - 1, verticalAxisLabelR.y + verticalAxisLabelR.height - 1);
		
		int midHeight = verticalAxisLabelR.y + (verticalAxisLabelR.height / 2);
		g2d.drawLine(0, midHeight, plotR.x + plotR.width, midHeight);

		if(valueLabeler != null) {
			Font oldFont = g2d.getFont();
			Font font = oldFont.deriveFont(10.0F);
			g2d.setFont(font);
			
			String maxLabel = valueLabeler.label(currentMax);
			Rectangle2D maxExtent = g2d.getFontMetrics().getStringBounds(maxLabel, g2d);
			g2d.drawString(maxLabel, verticalAxisLabelR.x + 2, (int) (verticalAxisLabelR.y + maxExtent.getHeight()));
			
			String midLabel = valueLabeler.label(currentMax / 2.0);
			Rectangle2D midExtent = g2d.getFontMetrics().getStringBounds(midLabel, g2d);
			g2d.drawString(midLabel, verticalAxisLabelR.x + 2, (int) (midHeight + midExtent.getHeight()));
			
			g2d.setFont(oldFont);
		} 
	}
	
	private static BasicStroke dashStroke = new BasicStroke(1.0F, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0F, new float[] { 1.5F, 1.5F }, 0.0F); 
}