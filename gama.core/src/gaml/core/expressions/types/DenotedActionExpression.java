/*******************************************************************************************************
 *
 * DenotedActionExpression.java, in msi.gama.core, is part of the source code of the GAMA modeling and simulation
 * platform (v.1.9.0).
 *
 * (c) 2007-2022 UMI 209 UMMISCO IRD/SU & Partners (IRIT, MIAT, TLU, CTU)
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gaml.core.expressions.types;

import gama.core.runtime.IScope;
import gaml.core.descriptions.StatementDescription;
import gaml.core.expressions.IExpression;
import gaml.core.expressions.variables.VariableExpression;
import gaml.core.types.Types;

/**
 * The Class DenotedActionExpression.
 */
public class DenotedActionExpression extends VariableExpression {

	/**
	 * Instantiates a new denoted action expression.
	 *
	 * @param action
	 *            the action
	 */
	public DenotedActionExpression(final StatementDescription action) {
		super(action.getName(), Types.ACTION, true, action);
	}

	@Override
	public Object _value(final IScope scope) {
		return getDefinitionDescription();
	}

	@Override
	public String getTitle() { return getDefinitionDescription().getTitle(); }

	/**
	 * @see gaml.core.expressions.IExpression#getDocumentation()
	 */
	@Override
	public Doc getDocumentation() { return getDefinitionDescription().getDocumentation(); }

	@Override
	public void setVal(final IScope scope, final Object v, final boolean create) {}

	@Override
	public IExpression resolveAgainst(final IScope scope) {
		return this;
	}

}
