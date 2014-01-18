package com.quigley.meters;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PlainLabeler implements ValueLabeler, StampLabeler {
	@Override
	public String label(double value) {
		return valueFormat.format(value);
	}
	
	@Override
	public String label(Date stamp) {
		return stampFormat.format(stamp);
	}

	private static DecimalFormat valueFormat = new DecimalFormat("0.0");
	private static SimpleDateFormat stampFormat = new SimpleDateFormat("HH:mm:ss");
}