/*******************************************************************************************************
 *
 * GamaJsonList.java, in msi.gama.core, is part of the source code of the
 * GAMA modeling and simulation platform (v.1.9.0).
 *
 * (c) 2007-2022 UMI 209 UMMISCO IRD/SU & Partners (IRIT, MIAT, TLU, CTU)
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package gama.core.util.file.json;

import java.util.Collection;
import java.util.Iterator;

import gama.core.util.GamaList;
import gaml.core.types.Types;

/**
 * GamaJsonList is a GAMA compatible implementation of a json array
 *
 * @author A. Drogoul, adapted from json-simple library
 */
public class GamaJsonList extends GamaList<Object> implements Jsonable {
	
	/**
	 * Instantiates a new gama json list.
	 */
	public GamaJsonList() {
		super();
	}

	/**
	 * Instantiates a new gama json list.
	 *
	 * @param collection the collection
	 */
	public GamaJsonList(final Collection<?> collection) {
		super(collection.size(), Types.NO_TYPE);
		addAll(collection);
	}

	@Override
	public String toJson() {
		final StringBuilder writable = new StringBuilder();
		boolean isFirstElement = true;
		final Iterator<Object> elements = this.iterator();
		writable.append('[');
		while (elements.hasNext()) {
			if (isFirstElement) {
				isFirstElement = false;
			} else {
				writable.append(',');
			}
			writable.append(Jsoner.serialize(elements.next()));
		}
		writable.append(']');
		return writable.toString();
	}
}
