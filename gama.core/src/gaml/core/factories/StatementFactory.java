/*******************************************************************************************************
 *
 * StatementFactory.java, in msi.gama.core, is part of the source code of the GAMA modeling and simulation platform
 * (v.1.9.0).
 *
 * (c) 2007-2022 UMI 209 UMMISCO IRD/SU & Partners (IRIT, MIAT, TLU, CTU)
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gaml.core.factories;

import org.eclipse.emf.ecore.EObject;

import gama.annotations.common.interfaces.IKeyword;
import gaml.core.descriptions.ActionDescription;
import gaml.core.descriptions.IDescription;
import gaml.core.descriptions.PrimitiveDescription;
import gaml.core.descriptions.StatementDescription;
import gaml.core.descriptions.StatementRemoteWithChildrenDescription;
import gaml.core.descriptions.StatementWithChildrenDescription;
import gaml.core.descriptions.SymbolProto;
import gaml.core.statements.Facets;

/**
 * Written by drogoul Modified on 8 févr. 2010
 *
 * @todo Description
 *
 */
// @factory (
// handles = { ISymbolKind.SEQUENCE_STATEMENT, ISymbolKind.SINGLE_STATEMENT, ISymbolKind.BEHAVIOR,
// ISymbolKind.ACTION, ISymbolKind.LAYER, ISymbolKind.BATCH_METHOD, ISymbolKind.OUTPUT })
public class StatementFactory extends SymbolFactory implements IKeyword {

	@Override
	protected StatementDescription buildDescription(final String keyword, final Facets facets, final EObject element,
			final Iterable<IDescription> children, final IDescription enclosing, final SymbolProto proto) {
		if (proto.isPrimitive()) return new PrimitiveDescription(enclosing, element, children, facets, null);
		if (ACTION.equals(keyword)) return new ActionDescription(keyword, enclosing, children, element, facets);
		if (proto.hasSequence() && children != null) {
			if (proto.isRemoteContext()) return new StatementRemoteWithChildrenDescription(keyword, enclosing, children,
					proto.hasArgs(), element, facets, null);
			return new StatementWithChildrenDescription(keyword, enclosing, children, proto.hasArgs(), element, facets,
					null);
		}
		return new StatementDescription(keyword, enclosing, proto.hasArgs(), /* children, */ element, facets, null);
	}

}
