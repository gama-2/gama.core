/*******************************************************************************************************
 *
 * TypeExpression.java, in msi.gama.core, is part of the source code of the GAMA modeling and simulation platform
 * (v.1.9.0).
 *
 * (c) 2007-2022 UMI 209 UMMISCO IRD/SU & Partners (IRIT, MIAT, TLU, CTU)
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gaml.core.expressions.types;

import gama.core.runtime.IScope;
import gama.core.runtime.exceptions.GamaRuntimeException;
import gaml.core.expressions.AbstractExpression;
import gaml.core.expressions.IExpression;
import gaml.core.types.IType;
import gaml.core.types.Types;

/**
 * Class TypeExpression.
 *
 * @author drogoul
 * @since 7 sept. 2013
 *
 */
public class TypeExpression extends AbstractExpression {

	/**
	 * Instantiates a new type expression.
	 *
	 * @param type
	 *            the type
	 */
	@SuppressWarnings ("rawtypes")
	public TypeExpression(final IType type) {
		this.type = type;
	}

	@Override
	public IType<?> _value(final IScope scope) throws GamaRuntimeException {
		// Normally never evaluated
		return getDenotedType();
	}

	@Override
	public String getDefiningPlugin() { return type.getDefiningPlugin(); }

	@Override
	public boolean shouldBeParenthesized() {
		return false;
	}

	@Override
	public boolean isConst() { return type.canCastToConst(); }

	@Override
	public String serialize(final boolean includingBuiltIn) {
		return type.serialize(includingBuiltIn);
	}

	@Override
	public String getTitle() { return type.getTitle(); }

	/**
	 * Method getDocumentation()
	 *
	 * @see gama.annotations.common.interfaces.IGamlDescription#getDocumentation()
	 */
	@Override
	public Doc getDocumentation() { return new ConstantDoc("Represents the data type " + type.getTitle()); }

	@Override
	public IType<?> getGamlType() { return Types.TYPE; }

	@Override
	public IType<?> getDenotedType() { return type; }

	@Override
	public String literalValue() {
		return type.serialize(false);
	}

	@Override
	public boolean isContextIndependant() { return isConst(); }

	@Override
	public IExpression resolveAgainst(final IScope scope) {
		return this;
	}

}
