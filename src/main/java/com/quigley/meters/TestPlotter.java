package com.quigley.meters;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import com.seaglasslookandfeel.SeaGlassLookAndFeel;

public class TestPlotter {
	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel(new SeaGlassLookAndFeel());
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame frame = new JFrame();
				frame.setTitle("TestPlotter");
				frame.setBounds(1000, 500, 400, 200);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				JPanel content = new JPanel();
				content.setLayout(new BorderLayout());
				frame.setContentPane(content);
				
				PlotterMeter plotterMeter = new PlotterMeter();
				plotterMeter.addSampler(new RandomSampler());
				plotterMeter.addSampler(new RandomSampler());
				plotterMeter.setPlotter(new CurvePlotter());
				content.add(plotterMeter);
				
				frame.setVisible(true);
			}
		});
	}
}