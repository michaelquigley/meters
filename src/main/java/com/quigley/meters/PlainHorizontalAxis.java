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
import java.util.List;

/**
 * A plain horizontal axis implementation. Provides vertical grid elements, which time stamp representative
 * samples.
 * 
 * @author Michael Quigley
 */
public class PlainHorizontalAxis implements HorizontalAxis {
	/**
	 * @see HorizontalAxis
	 */
	@Override
	public int preferredHeight() {
		return 15;
	}

	/**
	 * @see HorizontalAxis
	 */
	@Override
	public void paint(Graphics2D g, Color backgroundColor, Color foregroundColor, 
			          Rectangle horizontalAxisLabelR, Rectangle plotR, 
			          StampLabeler stampLabeler, 
			          List<Sample> samples,
			          double sampleWidth, 
			          double offset) {
		
		g.setColor(backgroundColor);
		g.fillRect(horizontalAxisLabelR.x, horizontalAxisLabelR.y, horizontalAxisLabelR.width, horizontalAxisLabelR.height);
		
		g.setStroke(dashStroke);
		g.setColor(foregroundColor);
		
		g.drawLine(0, horizontalAxisLabelR.y + horizontalAxisLabelR.height, 
				     horizontalAxisLabelR.x + horizontalAxisLabelR.width, horizontalAxisLabelR.y + horizontalAxisLabelR.height);
		
		int x = (int) Math.ceil(plotR.x + plotR.width + sampleWidth - offset);
		
		if(samples != null) {
			Font oldFont = g.getFont();
			Font font = oldFont.deriveFont(10.0F);
			g.setFont(font);

			for(int i = samples.size() - 1; i >= 0 && x > plotR.x; i--) {
				if(i % 5 == 0) {
					g.drawLine(x, plotR.y, x, plotR.y + plotR.height);

					Sample sample = samples.get(i);
					String label = stampLabeler.label(sample.getStamp());
					g.drawString(label, x - 2, (int) (horizontalAxisLabelR.y + horizontalAxisLabelR.height - 2));
				}
				x -= sampleWidth;
			}
			
			g.setFont(oldFont);
		}
	}

	private static BasicStroke dashStroke = new BasicStroke(1.0F, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0F, new float[] { 1.5F, 1.5F }, 0.0F);
}