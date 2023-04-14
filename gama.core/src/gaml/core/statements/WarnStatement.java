/*******************************************************************************************************
 *
 * WarnStatement.java, in msi.gama.core, is part of the source code of the GAMA modeling and simulation platform
 * (v.1.9.0).
 *
 * (c) 2007-2023 UMI 209 UMMISCO IRD/SU & Partners (IRIT, MIAT, TLU, CTU)
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gaml.core.statements;

import gama.annotations.common.interfaces.IKeyword;
import gama.annotations.precompiler.IConcept;
import gama.annotations.precompiler.ISymbolKind;
import gama.annotations.precompiler.GamlAnnotations.doc;
import gama.annotations.precompiler.GamlAnnotations.example;
import gama.annotations.precompiler.GamlAnnotations.facet;
import gama.annotations.precompiler.GamlAnnotations.facets;
import gama.annotations.precompiler.GamlAnnotations.inside;
import gama.annotations.precompiler.GamlAnnotations.symbol;
import gama.annotations.precompiler.GamlAnnotations.usage;
import gama.core.metamodel.agent.IAgent;
import gama.core.runtime.GAMA;
import gama.core.runtime.IScope;
import gama.core.runtime.exceptions.GamaRuntimeException;
import gaml.core.descriptions.IDescription;
import gaml.core.expressions.IExpression;
import gaml.core.operators.Cast;
import gaml.core.types.IType;

/**
 * Written by drogoul Modified on 6 févr. 2010
 *
 * @todo Description
 *
 */

@symbol (
		name = IKeyword.WARNING,
		kind = ISymbolKind.SINGLE_STATEMENT,
		with_sequence = false,
		concept = { IConcept.SYSTEM })
@inside (
		kinds = { ISymbolKind.BEHAVIOR, ISymbolKind.SEQUENCE_STATEMENT, ISymbolKind.LAYER })
@facets (
		value = { @facet (
				name = IKeyword.MESSAGE,
				type = IType.STRING,
				optional = false,
				doc = @doc ("the message to display as a warning.")) },
		omissible = IKeyword.MESSAGE)
@doc (
		value = "The statement makes the agent output an arbitrary message in the error view as a warning.",
		usages = { @usage (
				value = "Emmitting a warning",
				examples = { @example ("warn \"This is a warning from \" + self;") }) })
public class WarnStatement extends AbstractStatement {

	/** The message. */
	final IExpression message;

	/**
	 * Instantiates a new warn statement.
	 *
	 * @param desc
	 *            the desc
	 */
	public WarnStatement(final IDescription desc) {
		super(desc);
		message = getFacet(IKeyword.MESSAGE);
	}

	@Override
	public Object privateExecuteIn(final IScope scope) throws GamaRuntimeException {
		IAgent agent = scope.getAgent();
		String mes = null;
		if (agent != null && !agent.dead()) {
			mes = Cast.asString(scope, message.value(scope));
			GAMA.reportAndThrowIfNeeded(scope, GamaRuntimeException.warning(mes, scope), false);
		}
		return mes;
	}

	// @Override
	// public IType getType() {
	// return Types.STRING;
	// }

}
