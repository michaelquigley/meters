package com.quigley.meters;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PlainHorizontalAxis implements HorizontalAxis {
	@Override
	public int preferredHeight() {
		return 25;
	}

	@Override
	public void paint(Graphics2D g2d, Color backgroundColor, Color foregroundColor, 
			          Rectangle horizontalAxisLabelR, Rectangle plotR, 
			          Labeler labeler, 
			          List<Sample> samples,
			          double sampleWidth, 
			          double offset) {
		
		g2d.setColor(backgroundColor);
		g2d.fillRect(horizontalAxisLabelR.x, horizontalAxisLabelR.y, horizontalAxisLabelR.width, horizontalAxisLabelR.height);
		
		g2d.setStroke(dashStroke);
		g2d.setColor(foregroundColor);
		
		g2d.drawLine(0, horizontalAxisLabelR.y + horizontalAxisLabelR.height, 
				     horizontalAxisLabelR.x + horizontalAxisLabelR.width, horizontalAxisLabelR.y + horizontalAxisLabelR.height);
		
		int x = (int) Math.ceil(plotR.x + plotR.width + sampleWidth - offset);
		
		if(samples != null) {
			Font oldFont = g2d.getFont();
			Font font = oldFont.deriveFont(10.0F);
			g2d.setFont(font);

			for(int i = samples.size() - 1; i >= 0 && x > plotR.x; i--) {
				if(i % 5 == 0) {
					g2d.drawLine(x, plotR.y, x, plotR.y + plotR.height);

					Sample sample = samples.get(i);
					String label = timeFormat.format(new Date(sample.getTimeStamp()));
					g2d.drawString(label, x, (int) (horizontalAxisLabelR.y + horizontalAxisLabelR.height - 2));
				}
				x -= sampleWidth;
			}
			
			g2d.setFont(oldFont);
		}
	}

	private static BasicStroke dashStroke = new BasicStroke(1.0F, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0F, new float[] { 1.5F, 1.5F }, 0.0F);
	private static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
}