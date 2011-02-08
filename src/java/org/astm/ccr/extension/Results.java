/*
 * Copyright 2010 OpenHealthData, Inc.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.

 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.astm.ccr.extension;

import java.util.ArrayList;
import java.util.List;
import org.astm.ccr.ResultType;

/**
 * Helper class to distinguish between Results and VitalSigns
 * @author swaldren
 *
 */
public class Results {

	protected List<ResultType> result;

	/**
	 * Helper Class to group all results into a single list to ease rule
	 * development
	 * 
	 */
	public List<ResultType> getResult() {
		if (result == null) {
			result = new ArrayList<ResultType>();
		}
		return this.result;
	}

}
