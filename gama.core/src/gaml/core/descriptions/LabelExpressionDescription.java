/*******************************************************************************************************
 *
 * LabelExpressionDescription.java, in msi.gama.core, is part of the source code of the GAMA modeling and simulation
 * platform (v.1.9.0).
 *
 * (c) 2007-2022 UMI 209 UMMISCO IRD/SU & Partners (IRIT, MIAT, TLU, CTU)
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gaml.core.descriptions;

import java.util.Set;

import gama.core.common.util.StringUtils;
import gama.core.runtime.IScope;
import gama.core.runtime.exceptions.GamaRuntimeException;
import gama.core.util.Collector;
import gaml.core.expressions.IExpression;
import gaml.core.types.IType;
import gaml.core.types.Types;

/**
 * The class LabelExpressionDescription.
 *
 * @author drogoul
 * @since 31 mars 2012
 *
 */
public class LabelExpressionDescription extends BasicExpressionDescription implements IExpression {

	@Override
	public IType<?> getDenotedType(final IDescription context) {
		return context.getTypeNamed(value);
	}

	/** The value. */
	final String value;

	/**
	 * Instantiates a new label expression description.
	 *
	 * @param label
	 *            the label
	 */
	private LabelExpressionDescription(final String label) {
		super((IExpression) null);
		value = StringUtils.unescapeJava(label);
	}

	@Override
	public IExpressionDescription cleanCopy() {
		// TODO Check that it does not cause any problem
		return this;
	}

	@Override
	public IExpressionDescription compileAsLabel() {
		return this;
	}

	@Override
	public String toString() {
		return value;
	}

	@Override
	public String serialize(final boolean includingBuiltIn) {
		return value;
		// return StringUtils.toGamlString(value);
	}

	@Override
	public IExpression getExpression() {
		if (expression == null) { expression = this; }
		return expression;
	}

	@Override
	public IExpression compile(final IDescription context) {
		return getExpression();
	}

	@Override
	public boolean equalsString(final String o) {
		return value.equals(o);
	}

	// @Override
	// public void setTarget(final EObject newTarget) {
	// super.setTarget(newTarget);
	// }

	@Override
	public Set<String> getStrings(final IDescription context, final boolean skills) {
		// Assuming of the form [aaa, bbb]
		try (Collector.AsSet<String> result = Collector.getSet()) {
			final StringBuilder b = new StringBuilder();
			for (final char c : value.toCharArray()) {
				switch (c) {
					case '[':
					case ' ':
						break;
					case ']':
					case ',': {
						result.add(b.toString());
						b.setLength(0);
						break;
					}
					default:
						b.append(c);
				}
			}
			return result.items();
		}
	}

	/**
	 * Creates the.
	 *
	 * @param s
	 *            the s
	 * @return the i expression description
	 */
	public static IExpressionDescription create(final String s) {
		return new LabelExpressionDescription(s);
	}

	@Override
	public Doc getDocumentation() { return new ConstantDoc("Constant string: " + getName()); }

	@Override
	public String getTitle() { return "constant string '" + getName() + "'"; }

	@Override
	public String getDefiningPlugin() { return null; }

	@Override
	public String getName() { return value; }

	@Override
	public void setName(final String newName) {}

	@Override
	public IType<String> getGamlType() { return Types.STRING; }

	@Override
	public Object value(final IScope scope) throws GamaRuntimeException {
		return value;
	}

	@Override
	public boolean isConst() { return true; }

	@Override
	public String literalValue() {
		return value;
	}

	@Override
	public IExpression resolveAgainst(final IScope scope) {
		return this;
	}

	@Override
	public boolean shouldBeParenthesized() {
		return false;
	}

}
