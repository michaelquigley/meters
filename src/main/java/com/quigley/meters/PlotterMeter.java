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

public class PlotterMeter extends JComponent {
	public PlotterMeter() {
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
	
	public void addSampler(Sampler sampler) {
		samplers.add(sampler);
	}
	public void setSamplingInterval(int milliseconds) {
		samplingInterval = milliseconds;
		offsetIncrement = (sampleWidth / fps) * (1000.0 / samplingInterval);
	}
	public void setPlotter(Plotter plotter) {
		this.plotter = plotter;
	}
	public void setTitleArea(TitleArea titleArea) {
		this.titleArea = titleArea;
	}
	public void setValueLabeler(ValueLabeler labeler) {
		this.valueLabeler = labeler;
	}
	public void setStampLabeler(StampLabeler labeler) {
		this.stampLabeler = labeler;
	}
	public void setHorizontalAxis(HorizontalAxis horizontalAxis) {
		this.horizontalAxis = horizontalAxis;
	}
	public void setVerticalAxis(VerticalAxis verticalAxis) {
		this.verticalAxis = verticalAxis;
	}
	public void addIndicator(Indicator indicator) {
		if(indicators == null) {
			indicators = new ArrayList<Indicator>();
		}
		indicators.add(indicator);
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
			targetMax = 0.0;
			for(Sampler sampler : samplers) {
				double max = plotter.paint(g2d, plotR, sampler.getColor(), sampler.getSamples(), sampleWidth, offset, currentMax);
				if(max > targetMax) {
					targetMax = max;
				}
			}
		}
		if(horizontalAxis != null) {
			Rectangle horizontalAxisLabelR = horizontalAxisLabelRectangle();
			horizontalAxis.paint(g2d, backgroundColor, foregroundColor, horizontalAxisLabelR, plotR, stampLabeler, samplers.get(0).getSamples(), sampleWidth, offset);
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
	
	protected Rectangle titleAreaRectangle() {
		Rectangle r = new Rectangle(0, 0, getWidth(), titleArea.preferredHeight());
		return r;
	}
	
	protected Rectangle horizontalAxisLabelRectangle() {
		Rectangle r = new Rectangle(0, 0, getWidth(), horizontalAxis.preferredHeight());
		if(titleArea != null) {
			r.y += (titleArea.preferredHeight() + 1);
		}
		return r;
	}
	
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
	
	private double offset;
	private double offsetIncrement;
	
	private static double fps = 20.0;
	private static double sampleWidth = 25.0;
	
	private static Color backgroundColor = new Color(32, 32, 32);
	private static Color foregroundColor = Color.gray;
	
	private static final long serialVersionUID = -7436291254326309438L;
}