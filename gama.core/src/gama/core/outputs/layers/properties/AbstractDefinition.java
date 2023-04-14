/*******************************************************************************************************
 *
 * AbstractDefinition.java, in msi.gama.core, is part of the source code of the GAMA modeling and simulation platform
 * (v.1.9.0).
 *
 * (c) 2007-2022 UMI 209 UMMISCO IRD/SU & Partners (IRIT, MIAT, TLU, CTU)
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gama.core.outputs.layers.properties;

import gama.annotations.common.interfaces.IKeyword;
import gama.core.runtime.IScope;
import gaml.core.compilation.ISymbol;
import gaml.core.statements.draw.AttributeHolder;
import gaml.core.types.Types;

/**
 * The Class AbstractDefinition.
 */
public abstract class AbstractDefinition extends AttributeHolder {

	/** The dynamic. */
	Attribute<Boolean> dynamic;

	/** The initialized. */
	boolean initialized;

	/**
	 * Instantiates a new abstract definition.
	 *
	 * @param symbol
	 *            the symbol
	 */
	public AbstractDefinition(final ISymbol symbol) {
		super(symbol);
		dynamic = create(IKeyword.DYNAMIC, Types.BOOL, getDefaultDynamicValue());
	}

	/**
	 * Gets the default dynamic value.
	 *
	 * @return the default dynamic value
	 */
	protected abstract boolean getDefaultDynamicValue();

	/**
	 * Checks if is dynamic.
	 *
	 * @return the boolean
	 */
	public Boolean isDynamic() { return dynamic.get(); }

	/**
	 * Inits the.
	 *
	 * @param scope
	 *            the scope
	 */
	public final void init(final IScope scope) {
		super.refresh(scope);
	}

	@Override
	public final void refresh(final IScope scope) {
		dynamic.refresh(IKeyword.DYNAMIC, scope);
		if (shouldRefresh()) {
			super.refresh(scope);
			this.update(scope);
			initialized = true;
		}
	}

	/**
	 * Update.
	 *
	 * @param scope
	 *            the scope
	 */
	protected abstract void update(IScope scope);

	/**
	 * Should refresh.
	 *
	 * @return true, if successful
	 */
	protected boolean shouldRefresh() {
		return isDynamic() || !initialized;
	}

	/**
	 * Reset.
	 */
	protected abstract void reset();

}
