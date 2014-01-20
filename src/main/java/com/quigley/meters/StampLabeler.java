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

import java.util.Date;

/**
 * The <code>StampLabeler</code> interface defines the method used to convert the <code>stamp</code> field
 * of a <code>Sample</code> into a <code>String</code>. 
 *
 * @author Michael Quigley
 */
public interface StampLabeler {
	/**
	 * @param stamp a <code>Date</code> stamp from a <code>Sample</code>
	 * 		instance.
	 * @return A formatted <code>String</code>.
	 */
	public String label(Date stamp);
}