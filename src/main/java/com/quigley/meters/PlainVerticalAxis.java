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
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

/**
 * A plain vertical axis implementation. Provides horizontal grid elements, which denote the maximum and midpoint
 * values shown in the plotting area.
 * 
 * @author Michael Quigley
 */
public class PlainVerticalAxis implements VerticalAxis {
	@Override
	public int preferredWidth() {
		return 50;
	}

	@Override
	public void paint(Graphics2D g, Color backgroundColor, Color foregroundColor, 
			          Rectangle verticalAxisLabelR, Rectangle plotR, 
			          ValueLabeler valueLabeler, 
			          double currentMax) {
		
		g.setColor(backgroundColor);
		g.fillRect(verticalAxisLabelR.x, verticalAxisLabelR.y, verticalAxisLabelR.width, verticalAxisLabelR.height);

		g.setStroke(dashStroke);
		g.setColor(foregroundColor);

		g.drawLine(verticalAxisLabelR.x + verticalAxisLabelR.width - 1, verticalAxisLabelR.y, 
				     verticalAxisLabelR.x + verticalAxisLabelR.width - 1, verticalAxisLabelR.y + verticalAxisLabelR.height - 1);
		
		int midHeight = verticalAxisLabelR.y + (verticalAxisLabelR.height / 2);
		g.drawLine(0, midHeight, plotR.x + plotR.width, midHeight);

		if(valueLabeler != null) {
			Font oldFont = g.getFont();
			Font font = oldFont.deriveFont(10.0F);
			g.setFont(font);
			
			String maxLabel = valueLabeler.label(currentMax);
			Rectangle2D maxExtent = g.getFontMetrics().getStringBounds(maxLabel, g);
			g.drawString(maxLabel, verticalAxisLabelR.x + 2, (int) (verticalAxisLabelR.y + maxExtent.getHeight()));
			
			String midLabel = valueLabeler.label(currentMax / 2.0);
			Rectangle2D midExtent = g.getFontMetrics().getStringBounds(midLabel, g);
			g.drawString(midLabel, verticalAxisLabelR.x + 2, (int) (midHeight + midExtent.getHeight()));
			
			g.setFont(oldFont);
		} 
	}
	
	private static BasicStroke dashStroke = new BasicStroke(1.0F, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0F, new float[] { 1.5F, 1.5F }, 0.0F); 
}