package com.quigley.meters;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public interface Indicator {
	public void paint(Graphics2D g, Color foreground, Rectangle plot, double currentMax);
}