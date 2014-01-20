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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * An example Swing application showing a single <code>PlotterMeter</code>.
 *  
 * @author Michael Quigley
 */
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
				
				RandomSampler redSampler = new RandomSampler(Color.red);
				plotterMeter.addSampler(redSampler);
				
				RandomSampler orangeSampler = new RandomSampler(Color.orange);
				plotterMeter.addSampler(orangeSampler);
				
				plotterMeter.setPlotter(new CurvePlotter());
				plotterMeter.setTitleArea(new PlainTitleArea());
				plotterMeter.setHorizontalAxis(new PlainHorizontalAxis());
				plotterMeter.setVerticalAxis(new PlainVerticalAxis());
				plotterMeter.addIndicator(new FloatingLineIndicator(redSampler));
				plotterMeter.addIndicator(new FloatingLineIndicator(orangeSampler));
				plotterMeter.setSamplingInterval(1500);
				//plotterMeter.lockMaxScale(1000.0);
				content.add(plotterMeter);
				
				frame.setVisible(true);
			}
		});
	}
}