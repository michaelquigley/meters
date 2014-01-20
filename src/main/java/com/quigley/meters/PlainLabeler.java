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

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A class that implements both <code>ValueLabeler</code> and <code>StampLabeler</code>. Provides a 
 * "plain" rendering of both <code>value</code> and <code>stamp</code> properties from <code>Sample</code>
 * instances.
 * 
 *  @author Michael Quigley
 */
public class PlainLabeler implements ValueLabeler, StampLabeler {
	/**
	 * @see ValueLabeler
	 */
	@Override
	public String label(double value) {
		return valueFormat.format(value);
	}
	
	/**
	 * @see StampLabeler
	 */
	@Override
	public String label(Date stamp) {
		return stampFormat.format(stamp);
	}

	private static DecimalFormat valueFormat = new DecimalFormat("0.0");
	private static SimpleDateFormat stampFormat = new SimpleDateFormat("HH:mm:ss");
}