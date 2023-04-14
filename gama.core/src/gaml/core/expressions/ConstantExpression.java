/*******************************************************************************************************
 *
 * ConstantExpression.java, in msi.gama.core, is part of the source code of the GAMA modeling and simulation platform
 * (v.1.9.0).
 *
 * (c) 2007-2022 UMI 209 UMMISCO IRD/SU & Partners (IRIT, MIAT, TLU, CTU)
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gaml.core.expressions;

import gama.core.common.util.StringUtils;
import gama.core.runtime.IScope;
import gaml.core.types.GamaType;
import gaml.core.types.IType;

/**
 * ConstantValueExpr.
 *
 * @author drogoul 22 août 07
 */

public class ConstantExpression extends AbstractExpression {

	/** The value. */
	protected Object value;

	/**
	 * Instantiates a new constant expression.
	 *
	 * @param val
	 *            the val
	 * @param t
	 *            the t
	 * @param name
	 *            the name
	 */
	public ConstantExpression(final Object val, final IType<?> t, final String name) {
		value = val;
		type = t;
		setName(name);
	}

	/**
	 * Instantiates a new constant expression.
	 *
	 * @param val
	 *            the val
	 * @param t
	 *            the t
	 */
	public ConstantExpression(final Object val, final IType<?> t) {
		this(val, t, val == null ? "nil" : val.toString());
	}

	/**
	 * Instantiates a new constant expression.
	 *
	 * @param val
	 *            the val
	 */
	public ConstantExpression(final Object val) {
		this(val, GamaType.of(val));
	}

	@Override
	public Object _value(final IScope scope) {
		return value;
	}

	@Override
	public boolean isConst() { return true; }

	@Override
	public String toString() {
		return value == null ? "nil" : value.toString();
	}

	@Override
	public String serialize(final boolean includingBuiltIn) {
		return StringUtils.toGaml(value, includingBuiltIn);
	}

	/**
	 * @see gaml.core.expressions.IExpression#getDocumentation()
	 */
	@Override
	public Doc getDocumentation() { return new ConstantDoc("Literal expression of type " + getGamlType().getTitle()); }

	@Override
	public String getTitle() { return literalValue(); }

	@Override
	public boolean shouldBeParenthesized() {
		return false;
	}

	@Override
	public IExpression resolveAgainst(final IScope scope) {
		return this;
	}

}
