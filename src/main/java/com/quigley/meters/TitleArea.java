package com.quigley.meters;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.List;

/**
 * A title area represents the region of a meter, which is typically used to display "header" information
 * about the <code>Sampler</code> instances in the meter.
 * 
 * The kinds of information that might want to be shown in a title area include: the name of the meter,
 * numeric representations of current (max, min) values for <code>Sampler</code> instances in the meter,
 * etc.
 * 
 * The title area is usually shown at the very top of the <code>PlotterMeter</code> and runs horizontally.
 * 
 * @author Michael Quigley
 */
public interface TitleArea {
	/**
	 * @return The preferred height of this title area.
	 */
	public int preferredHeight();
	
	/**
	 * Paint the title area.
	 * 
	 * @param g the <code>Graphics2D</code> instance to use for drawing.
	 * @param backgroundColor the background color of the meter.
	 * @param foregroundColor the foreground color of the meter.
	 * @param titleAreaR the <code>Rectangle</code> defining the area to use for drawing the title area.
	 * @param valueLabeler a <code>ValueLabeler</code> instance to use for translating <code>Sample</code>
	 * 		values into <code>String<code> values.
	 * @param samplers the list of <code>Sampler</code> instances in the meter.
	 */
	public void paint(Graphics2D g, Color backgroundColor, Color foregroundColor, Rectangle titleAreaR,
					   ValueLabeler valueLabeler, List<Sampler> samplers);
}