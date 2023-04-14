/*******************************************************************************************************
 *
 * AbstractStatement.java, in msi.gama.core, is part of the source code of the
 * GAMA modeling and simulation platform (v.1.9.0).
 *
 * (c) 2007-2022 UMI 209 UMMISCO IRD/SU & Partners (IRIT, MIAT, TLU, CTU)
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package gaml.core.statements;

import gama.core.runtime.GAMA;
import gama.core.runtime.IScope;
import gama.core.runtime.exceptions.GamaRuntimeException;
import gaml.core.compilation.ISymbol;
import gaml.core.compilation.Symbol;
import gaml.core.descriptions.IDescription;
import gaml.core.descriptions.StatementDescription;

/**
 * Written by drogoul Modified on 6 févr. 2010
 *
 */

public abstract class AbstractStatement extends Symbol implements IStatement {

	/**
	 * Instantiates a new abstract statement.
	 *
	 * @param desc the desc
	 */
	public AbstractStatement(final IDescription desc) {
		super(desc);
		if (desc != null) {
			final String k = getKeyword();
			final String n = desc.getName();
			setName(k == null ? "" : k + " " + (n == null ? "" : n));
		}
	}

	@Override
	public Object executeOn(final IScope scope) throws GamaRuntimeException {
		try {
			scope.setCurrentSymbol(this);
			return privateExecuteIn(scope);
		} catch (final GamaRuntimeException e) {
			e.addContext(this);
			GAMA.reportAndThrowIfNeeded(scope, e, true);
			return null;
		}
	}

	/**
	 * Private execute in.
	 *
	 * @param scope the scope
	 * @return the object
	 * @throws GamaRuntimeException the gama runtime exception
	 */
	protected abstract Object privateExecuteIn(IScope scope) throws GamaRuntimeException;

	@Override
	public void setChildren(final Iterable<? extends ISymbol> commands) {}

	@Override
	public String toString() {
		return description.serialize(true);
	}

	@Override
	public StatementDescription getDescription() {
		return (StatementDescription) super.getDescription();
	}

}
