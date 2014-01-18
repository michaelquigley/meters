package com.quigley.meters;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class PlotterMeterExample {
	public static void main(String[] args) throws Exception {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame frame = new JFrame();
				frame.setTitle("PlotterMeter Example");
				frame.setBounds(1000, 500, 400, 200);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				JPanel content = new JPanel();
				content.setLayout(new BorderLayout());
				frame.setContentPane(content);
				
				PlotterMeter plotterMeter = new PlotterMeter();
				plotterMeter.addSampler(new RandomSampler(Color.red));
				plotterMeter.addSampler(new RandomSampler(Color.orange));
				plotterMeter.addSampler(new RandomSampler(Color.magenta));
				plotterMeter.setPlotter(new CurvePlotter());
				plotterMeter.setTitleArea(new PlainTitleArea());
				plotterMeter.setHorizontalAxis(new PlainHorizontalAxis());
				plotterMeter.setVerticalAxis(new PlainVerticalAxis());
				plotterMeter.setSamplingInterval(1500);
				content.add(plotterMeter);
				
				frame.setVisible(true);
			}
		});
	}
}