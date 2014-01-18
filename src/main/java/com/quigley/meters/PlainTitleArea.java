package com.quigley.meters;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.List;

public class PlainTitleArea implements TitleArea {

	@Override
	public int preferredHeight() {
		return 15;
	}

	@Override
	public void paint(Graphics2D g2d, Color backgroundColor,
					   Color foregroundColor, Rectangle titleAreaR,
					   ValueLabeler valueLabeler, List<Sampler> samplers) {

		g2d.setColor(backgroundColor);
		g2d.fillRect(titleAreaR.x, titleAreaR.y, titleAreaR.width, titleAreaR.height);
		
		if(samplers != null) {
			Font oldFont = g2d.getFont();
			Font font = oldFont.deriveFont(10.0F);
			g2d.setFont(font);
			
			int x = titleAreaR.x + titleAreaR.width - 2;
			
			for(Sampler sampler : samplers) {
				Sample front = sampler.front();
				if(front != null) {
					String label = valueLabeler.label(front.getValue());
					Rectangle2D labelExtent = g2d.getFontMetrics().getStringBounds(label, g2d);
					x -= labelExtent.getWidth();
					g2d.setColor(sampler.getColor());
					g2d.drawString(label, x, (int) (titleAreaR.y + labelExtent.getHeight()));
					x -= (50 - labelExtent.getWidth());
				}
			}
			
			g2d.setFont(oldFont);
		}
	}
}