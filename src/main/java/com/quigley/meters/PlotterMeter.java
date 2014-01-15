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
		labeler = new PlainLabeler();
		setSamplingInterval(1000);
		currentMax = 1.0;
		targetMax = 1.0;
		offset = 0.0;
		
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
						
						offsetIncrement += (((sampleXSize - offset) / fps) * (1000.0 / samplingInterval));
						offset = 0;
						
					} else {
						offset += offsetIncrement;
					}
				}
				if(currentMax != targetMax) {
					currentMax += ((targetMax - currentMax) / 6.0);
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
		offsetIncrement = (sampleXSize / fps) * (1000.0 / samplingInterval);
	}
	public void setPlotter(Plotter plotter) {
		this.plotter = plotter;
	}
	public void setLabeler(Labeler labeler) {
		this.labeler = labeler;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		Rectangle r = new Rectangle(getWidth(), getHeight());
		g.setColor(backgroundColor);
		g.fillRect(r.x, r.y, r.width, r.height);
		
		Rectangle plotR = new Rectangle(0, 20, getWidth(), getHeight() - 21);
		if(plotter != null) {
			for(Sampler sampler : samplers) {
				double max = plotter.plot(g2d, plotR, Color.red, sampler.getSamples(), sampleXSize, offset, currentMax);
				if(max > targetMax) {
					targetMax = max;
				}
			}
		}
		if(labeler != null) {
			// Label.
		}
	}

	private Timer timer;
	private List<Sampler> samplers;
	private Plotter plotter;
	private Labeler labeler;
	
	private long samplingInterval;
	private long lastStamp;
	
	private double targetMax;
	private double currentMax;
	
	private double offset;
	private double offsetIncrement;
	
	private static double fps = 40.0;
	private static double sampleXSize = 25.0;
	
	private static Color backgroundColor = Color.black;
	
	private static final long serialVersionUID = -7436291254326309438L;
}