/*******************************************************************************************************
 *
 * CurrentErrorUnitExpression.java, in msi.gama.core, is part of the source code of the GAMA modeling and simulation
 * platform (v.1.9.0).
 *
 * (c) 2007-2023 UMI 209 UMMISCO IRD/SU & Partners (IRIT, MIAT, TLU, CTU)
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gaml.core.expressions.units;

import gama.core.runtime.IScope;
import gama.core.runtime.exceptions.GamaRuntimeException;
import gaml.core.types.Types;

/**
 * The Class CurrentErrorUnitExpression.
 */
public class CurrentErrorUnitExpression extends UnitConstantExpression {

	/**
	 * Instantiates a new current error unit expression.
	 *
	 * @param doc
	 *            the doc
	 */
	public CurrentErrorUnitExpression(final String doc) {
		super("", Types.STRING, "current_error", doc, null);
	}

	@Override
	public String _value(final IScope scope) {
		final GamaRuntimeException e = scope.getCurrentError();
		if (e == null) return "nil";
		return e.getMessage();
	}

	@Override
	public boolean isConst() { return false; }

	@Override
	public boolean isAllowedInParameters() { return false; }

}
