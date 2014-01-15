package com.quigley.meters;

import java.text.DecimalFormat;

public class PlainLabeler implements Labeler {
	@Override
	public String label(Sample sample) {
		return numberFormat.format(sample.getValue());
	}

	private static DecimalFormat numberFormat = new DecimalFormat("0.0");
}