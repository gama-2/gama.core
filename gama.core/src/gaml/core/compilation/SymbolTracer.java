/*******************************************************************************************************
 *
 * SymbolTracer.java, in msi.gama.core, is part of the source code of the
 * GAMA modeling and simulation platform (v.1.9.0).
 *
 * (c) 2007-2022 UMI 209 UMMISCO IRD/SU & Partners (IRIT, MIAT, TLU, CTU)
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package gaml.core.compilation;

import gama.annotations.common.interfaces.IKeyword;
import gama.core.runtime.IScope;
import gaml.core.expressions.IExpression;
import gaml.core.operators.Cast;

/**
 * The Class SymbolTracer.
 */
public class SymbolTracer {

	/**
	 * Trace.
	 *
	 * @param scope the scope
	 * @param statement the statement
	 * @return the string
	 */
	public String trace(final IScope scope, final ISymbol statement) {

		final String k = statement.getKeyword(); // getFacet(IKeyword.KEYWORD).literalValue();
		final StringBuilder sb = new StringBuilder(100);
		sb.append(k).append(' ');
		if (statement.getDescription() != null) {
			statement.getDescription().visitFacets((name, ed) -> {
				if (name.equals(IKeyword.NAME)) {
					final String n = statement.getFacet(IKeyword.NAME).literalValue();
					if (n.startsWith(IKeyword.INTERNAL)) { return true; }
				}
				IExpression expr = null;
				if (ed != null) {
					expr = ed.getExpression();
				}
				final String exprString = expr == null ? "N/A" : expr.serialize(false);
				final String exprValue = expr == null ? "nil" : Cast.toGaml(expr.value(scope));
				sb.append(name).append(": [ ").append(exprString).append(" ] ").append(exprValue).append(" ");

				return true;
			});
		}

		return sb.toString();

	}

}
