package com.quigley.meters;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.List;

public interface HorizontalAxis {
	public int preferredHeight();
	public void paint(Graphics2D g2d, Color backgroundColor, Color foregroundColor,
					  Rectangle horizontalAxisLabelR, Rectangle plotR,
					  Labeler labeler,
					  List<Sample> samples,
					  double sampleWidth,
					  double offset);
}