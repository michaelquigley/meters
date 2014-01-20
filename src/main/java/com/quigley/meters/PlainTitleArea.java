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
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.List;

/**
 * A simple title area, which lists the numeric representation of the value of the newest <code>Sample</code>
 * for each <code>Sampler</code> in a meter.
 * 
 * @author Michael Quigley
 */
public class PlainTitleArea implements TitleArea {
	@Override
	public int preferredHeight() {
		return 15;
	}

	@Override
	public void paint(Graphics2D g, Color backgroundColor,
					   Color foregroundColor, Rectangle titleAreaR,
					   ValueLabeler valueLabeler, List<Sampler> samplers) {

		g.setColor(backgroundColor);
		g.fillRect(titleAreaR.x, titleAreaR.y, titleAreaR.width, titleAreaR.height);
		
		if(samplers != null) {
			Font oldFont = g.getFont();
			Font font = oldFont.deriveFont(10.0F);
			g.setFont(font);
			
			int x = titleAreaR.x + titleAreaR.width - 2;
			
			for(Sampler sampler : samplers) {
				Sample front = sampler.front();
				if(front != null) {
					String label = valueLabeler.label(front.getValue());
					Rectangle2D labelExtent = g.getFontMetrics().getStringBounds(label, g);
					x -= labelExtent.getWidth();
					g.setColor(sampler.getColor());
					g.drawString(label, x, (int) (titleAreaR.y + labelExtent.getHeight()));
					x -= (50 - labelExtent.getWidth());
				}
			}
			
			g.setFont(oldFont);
		}
	}
}