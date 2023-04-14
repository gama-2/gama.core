/*******************************************************************************************************
 *
 * NAryOperator.java, in msi.gama.core, is part of the source code of the
 * GAMA modeling and simulation platform (v.1.9.0).
 *
 * (c) 2007-2022 UMI 209 UMMISCO IRD/SU & Partners (IRIT, MIAT, TLU, CTU)
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package gaml.core.expressions.operators;

import gama.core.common.preferences.GamaPreferences;
import gaml.core.compilation.GAML;
import gaml.core.descriptions.OperatorProto;
import gaml.core.expressions.IExpression;

/**
 * The Class NAryOperator.
 */
public class NAryOperator extends AbstractNAryOperator {

	/**
	 * Creates the.
	 *
	 * @param proto the proto
	 * @param child the child
	 * @return the i expression
	 */
	public static IExpression create(final OperatorProto proto, final IExpression... child) {
		final NAryOperator u = new NAryOperator(proto, child);
		if (u.isConst() && GamaPreferences.External.CONSTANT_OPTIMIZATION.getValue()) {
			return GAML.getExpressionFactory().createConst(u.getConstValue(), u.getGamlType(), u.serialize(false));
		}
		return u;
	}

	/**
	 * Instantiates a new n ary operator.
	 *
	 * @param proto the proto
	 * @param exprs the exprs
	 */
	public NAryOperator(final OperatorProto proto, final IExpression... exprs) {
		super(proto, exprs);
	}

	@Override
	public NAryOperator copy() {
		return new NAryOperator(prototype, exprs);
	}

}
