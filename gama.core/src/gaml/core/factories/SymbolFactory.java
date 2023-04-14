/*******************************************************************************************************
 *
 * SymbolFactory.java, in msi.gama.core, is part of the source code of the
 * GAMA modeling and simulation platform (v.1.9.0).
 *
 * (c) 2007-2022 UMI 209 UMMISCO IRD/SU & Partners (IRIT, MIAT, TLU, CTU)
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package gaml.core.factories;

import org.eclipse.emf.ecore.EObject;

import gaml.core.descriptions.IDescription;
import gaml.core.descriptions.SymbolProto;
import gaml.core.statements.Facets;

/**
 * Written by Alexis Drogoul Modified on 11 mai 2010
 *
 * @todo Description
 *
 */
public abstract class SymbolFactory {

	/**
	 * Builds the description.
	 *
	 * @param keyword the keyword
	 * @param facets the facets
	 * @param element the element
	 * @param children the children
	 * @param enclosing the enclosing
	 * @param proto the proto
	 * @return the i description
	 */
	protected abstract IDescription buildDescription(String keyword, Facets facets, EObject element,
			Iterable<IDescription> children, IDescription enclosing, SymbolProto proto);

}
