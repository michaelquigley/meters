package com.quigley.meters;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public interface VerticalAxis {
	public int preferredWidth();
	public void paint(Graphics2D g2d, Color backgroundColor, Color foregroundColor, 
			          Rectangle axisLabelR, Rectangle plotR, 
			          Labeler labeler, 
			          double currentMax);
}