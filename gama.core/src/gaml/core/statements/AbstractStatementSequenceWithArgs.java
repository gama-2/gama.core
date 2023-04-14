/*******************************************************************************************************
 *
 * AbstractStatementSequenceWithArgs.java, in msi.gama.core, is part of the source code of the GAMA modeling and
 * simulation platform (v.1.9.0).
 *
 * (c) 2007-2023 UMI 209 UMMISCO IRD/SU & Partners (IRIT, MIAT, TLU, CTU)
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gaml.core.statements;

import gama.core.runtime.IScope;
import gama.core.runtime.exceptions.GamaRuntimeException;
import gaml.core.descriptions.IDescription;
import gaml.core.statements.IStatement.WithArgs;

/**
 * Class AbstractStatementSequenceWithArgs.
 *
 * @author drogoul
 * @since 11 mai 2014
 *
 */
public class AbstractStatementSequenceWithArgs extends AbstractStatementSequence implements WithArgs {

	/** The actual args. */
	final ThreadLocal<Arguments> actualArgs = new ThreadLocal<>();

	/**
	 * @param desc
	 */
	public AbstractStatementSequenceWithArgs(final IDescription desc) {
		super(desc);
	}

	/**
	 * Method setFormalArgs()
	 *
	 * @see gaml.core.statements.IStatement.WithArgs#setFormalArgs(gaml.core.statements.Arguments)
	 */
	@Override
	public void setFormalArgs(final Arguments args) {}

	/**
	 * Method setRuntimeArgs()
	 *
	 * @see gaml.core.statements.IStatement.WithArgs#setRuntimeArgs(gaml.core.statements.Arguments)
	 */
	@Override
	public void setRuntimeArgs(final IScope scope, final Arguments args) {
		// TODO Verify that this copy of the arguments is required or not.
		actualArgs.set(new Arguments(args));
	}

	@Override
	public Object privateExecuteIn(final IScope scope) throws GamaRuntimeException {
		scope.stackArguments(actualArgs.get());
		return super.privateExecuteIn(scope);
	}

	@Override
	public void dispose() {
		Arguments args = actualArgs.get();
		if (args != null) { args.dispose(); }
		actualArgs.set(null);
		super.dispose();
	}

}
