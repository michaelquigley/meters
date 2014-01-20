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
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.Timer;

/**
 * A Swing <code>JComponent</code>, which implements a live metering display.
 * 
 * <code>PlotterMeter</code> is the primary component in the Quigley Meters framework. It provides a live,
 * real-time display of numeric data from any source. Numeric data is represented through <code>Sample</code>
 * instances, which capture a value and a time "stamp" recording when the value was captured.
 * 
 * The PlotterMeter (referred to in this documentation as "meter") can contain any number of <code>Sampler</code>
 * instances, which are used to insert data into the meter. The PlotterMeter can be decorated with a "title area",
 * a "horizontal axis", a "vertical axis" and any number of "indicators". Data obtained from the contained
 * <code>Sampler</code> instances is plotted onto the "plotting area" of the meter using a <code>Plotter</code>
 * implementation.
 * 
 * @author Michael Quigley
 */
public class PlotterMeter extends JComponent {
	/**
	 * Construct a new <code>PlotterMeter</code> instance.
	 * 
	 * In order to use the new instance, you'll want to at least add a <code>Sampler</code> and a 
	 * <code>Plotter</code> to the new <code>PlotterMeter</code>.
	 */
	public PlotterMeter() {
		fps = 20.0;
		sampleWidth = 25;

		foregroundColor = Color.gray;
		backgroundColor = new Color(54, 48, 48);
		
		samplers = new ArrayList<Sampler>();
		
		PlainLabeler plainLabeler = new PlainLabeler();
		valueLabeler = plainLabeler;
		stampLabeler = plainLabeler;
		
		setSamplingInterval(1000);
		
		timer = new Timer((int) (1000.0 / fps), new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(samplers.size() > 0) {
					long now = System.currentTimeMillis();
					long elapsed = now - lastStamp;
					if(elapsed >= samplingInterval) {
						for(Sampler sampler : samplers) {
							sampler.sample();
						}
						lastStamp = now;
						
						offsetIncrement += (((sampleWidth - offset) / fps) * (1000.0 / samplingInterval));
						offset = 0;
						
					} else {
						offset += offsetIncrement;
					}
				}
				if(currentMax != targetMax) {
					currentMax += ((targetMax - currentMax) / 10.0);
				}
				repaint();
			}
		});
		timer.setRepeats(true);
		timer.start();
	}
	
	/**
	 * @return The value of the frames-per-second property.
	 */
	public double getFps() {
		return fps;
	}
	
	/**
	 * Set the value of the frames-per-second property.
	 * 
	 * @param fps the new value. Usable values range from 5 to 50.
	 * @throws IllegalArgumentException when an fps value less than 5.0 or greater than 50.0 is supplied.
	 */
	public void setFps(double fps) {
		if(fps < 5.0 || fps > 50.0) {
			throw new IllegalArgumentException("Unusable fps value.");
		}
		this.fps = fps;
		if(timer != null) {
			timer.setDelay((int) (1000.0 / fps));
		}
	}

	/**
	 * @return The value of the sample width property.
	 */
	public double getSampleWidth() {
		return sampleWidth;
	}
	
	/**
	 * Set the value of the sample width property. This property determines the spacing (in pixels) between
	 * samples along the horizontal axis.
	 * 
	 * @param sampleWidth the new value for the sample width property.
	 */
	public void setSampleWidth(double sampleWidth) {
		this.sampleWidth = sampleWidth;
	}

	/**
	 * @return The value of the foreground color property.
	 */
	public Color getForegroundColor() {
		return foregroundColor;
	}
	
	/**
	 * Set the value of the foreground color property.
	 * @param foregroundColor the new value of the foreground color property.
	 */
	public void setForegroundColor(Color foregroundColor) {
		this.foregroundColor = foregroundColor;
	}

	/**
	 * @return The value of the background color property.
	 */
	public Color getBackgroundColor() {
		return backgroundColor;
	}
	
	/**
	 * Set the value of the background color property.
	 * 
	 * @param backgroundColor the new value of the background color property.
	 */
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	/**
	 * Add a new <code>Sampler</code> to this <code>PlotterMeter</code>.
	 * 
	 * @param sampler the new <code>Sampler</code> to add.
	 */
	public void addSampler(Sampler sampler) {
		samplers.add(sampler);
	}
	
	/**
	 * Set the sampling interval.
	 * 
	 * @param milliseconds the new sampling interval (in milliseconds).
	 */
	public void setSamplingInterval(int milliseconds) {
		samplingInterval = milliseconds;
		offsetIncrement = (sampleWidth / fps) * (1000.0 / samplingInterval);
	}
	
	/**
	 * Set the <code>Plotter</code>.
	 * 
	 * @param plotter the <code>Plotter</code> to use for rendering sampler data.
	 */
	public void setPlotter(Plotter plotter) {
		this.plotter = plotter;
	}
	
	/**
	 * Set the <code>TitleArea</code>.
	 * 
	 * @param titleArea the <code>TitleArea</code> to use.
	 */
	public void setTitleArea(TitleArea titleArea) {
		this.titleArea = titleArea;
	}
	
	/**
	 * @param labeler the <code>ValueLabeler</code> to use for displaying values.
	 */
	public void setValueLabeler(ValueLabeler labeler) {
		this.valueLabeler = labeler;
	}
	
	/**
	 * @param labeler the <code>StampLabeler</code> to use for displaying stamps.
	 */
	public void setStampLabeler(StampLabeler labeler) {
		this.stampLabeler = labeler;
	}
	
	/**
	 * @param horizontalAxis the <code>HorizontalAxis</code> for the meter.
	 */
	public void setHorizontalAxis(HorizontalAxis horizontalAxis) {
		this.horizontalAxis = horizontalAxis;
	}
	
	/**
	 * @param verticalAxis the <code>VerticalAxis<code> for the meter.
	 */
	public void setVerticalAxis(VerticalAxis verticalAxis) {
		this.verticalAxis = verticalAxis;
	}
	
	/**
	 * Add an <code>Indicator</code> to the meter.
	 * @param indicator the new <code>Indicator</code> instance.
	 */
	public void addIndicator(Indicator indicator) {
		if(indicators == null) {
			indicators = new ArrayList<Indicator>();
		}
		indicators.add(indicator);
	}
	
	
	/**
	 * Lock the max (vertical) scale of the plotting area.
	 * 
	 * By default, the vertical scale of the <code>PlotterMeter</code> will automatically adjust to the maximum
	 * value currently shown. This method allows you to set a maximum displayed value and "lock" the vertical scale
	 * to a fixed value.
	 * 
	 * @param max the new maximum value to show on the plotting area.
	 */
	public synchronized void lockMaxScale(double max) {
		lockedMax = true;
		targetMax = max;
	}
	
	/**
	 * @see PlotterMeter#lockMaxScale(double)
	 */
	public synchronized void unlockMaxScale() {
		lockedMax = false;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		Rectangle r = new Rectangle(getWidth(), getHeight());
		g.setColor(backgroundColor);
		g.fillRect(r.x, r.y, r.width, r.height);
		
		Rectangle plotR = plotRectangle();
		if(plotter != null) {
			double newTargetMax = 0.0;
			for(Sampler sampler : samplers) {
				double max = plotter.paint(g2d, plotR, sampler.getColor(), sampler.getSamples(), sampleWidth, offset, currentMax);
				if(max > newTargetMax) {
					newTargetMax = max;
				}
			}
			synchronized(this) {
				if(!lockedMax && newTargetMax != targetMax) {
					targetMax = newTargetMax;
				}
			}
		}
		if(horizontalAxis != null) {
			Rectangle horizontalAxisLabelR = horizontalAxisLabelRectangle();
			if(samplers != null && samplers.size() > 0) {
				horizontalAxis.paint(g2d, backgroundColor, foregroundColor, horizontalAxisLabelR, plotR, stampLabeler, samplers.get(0).getSamples(), sampleWidth, offset);
			}
		}
		if(verticalAxis != null) {
			Rectangle verticalAxisLabelR = verticalAxisLabelRectangle();
			verticalAxis.paint(g2d, backgroundColor, foregroundColor, verticalAxisLabelR, plotR, valueLabeler, currentMax);
		}
		if(titleArea != null) {
			Rectangle titleAreaR = titleAreaRectangle();
			titleArea.paint(g2d, backgroundColor, foregroundColor, titleAreaR, valueLabeler, samplers);
		}
		if(indicators != null) {
			for(Indicator indicator : indicators) {
				indicator.paint(g2d, foregroundColor, plotR, currentMax);
			}
		}
	}
	
	/**
	 * Compute the title <code>Rectangle</code>.
	 * @return The title <code>Rectangle</code>. 
	 */
	protected Rectangle titleAreaRectangle() {
		Rectangle r = new Rectangle(0, 0, getWidth(), titleArea.preferredHeight());
		return r;
	}
	
	/**
	 * Compute the horizontal axis label <code>Rectangle</code>.
	 * 
	 * Account for any inserted components and compute the horizontal axis region.
	 * 
	 * @return the horizontal axis label <code>Rectangle</code>.
	 */
	protected Rectangle horizontalAxisLabelRectangle() {
		Rectangle r = new Rectangle(0, 0, getWidth(), horizontalAxis.preferredHeight());
		if(titleArea != null) {
			r.y += (titleArea.preferredHeight() + 1);
		}
		return r;
	}
	
	/**
	 * Compute the vertical axis label <code>Rectangle</code>.
	 * 
	 * Account for any inserted components and compute the vertical axis region.
	 * 
	 * @return the vertical axis label <code>Rectangle</code>.
	 */
	protected Rectangle verticalAxisLabelRectangle() {
		Rectangle r = new Rectangle(0, 0, verticalAxis.preferredWidth(), getHeight());
		if(titleArea != null) {
			r.y += (titleArea.preferredHeight() + 1);
			r.height -= (titleArea.preferredHeight() + 1);
		}
		if(horizontalAxis != null) {
			r.y += horizontalAxis.preferredHeight() + 1;
			r.height -= (horizontalAxis.preferredHeight() + 1);
		}
		return r;
	}
	
	/**
	 * Compute the plotting area <code>Rectangle</code>.
	 * 
	 * Account for any inserted components and compute the plotting area region.
	 * 
	 * @return the plotting area <code>Rectangle</code>.
	 */
	protected Rectangle plotRectangle() {
		Rectangle r = new Rectangle(getWidth(), getHeight());
		if(titleArea != null) {
			r.y += (titleArea.preferredHeight() + 2);
			r.height -= (titleArea.preferredHeight() + 2);
		}
		if(horizontalAxis != null) {
			r.y += (horizontalAxis.preferredHeight() + 2);
			r.height -= (horizontalAxis.preferredHeight() + 2);
		}
		if(verticalAxis != null) {
			r.x += verticalAxis.preferredWidth();
			r.width -= verticalAxis.preferredWidth();
		}
		return r;
	}

	private double fps;
	private double sampleWidth;

	private Color foregroundColor;
	private Color backgroundColor;
	
	private Timer timer;
	private List<Sampler> samplers;
	private Plotter plotter;
	private ValueLabeler valueLabeler;
	private StampLabeler stampLabeler;
	private TitleArea titleArea;
	private HorizontalAxis horizontalAxis;
	private VerticalAxis verticalAxis;
	private List<Indicator> indicators;
	
	private long samplingInterval;
	private long lastStamp;
	
	private double targetMax;
	private double currentMax;
	private boolean lockedMax;
	
	private double offset;
	private double offsetIncrement;
		
	private static final long serialVersionUID = -7436291254326309438L;
}