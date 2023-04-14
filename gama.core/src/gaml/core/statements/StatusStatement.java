/*******************************************************************************************************
 *
 * StatusStatement.java, in msi.gama.core, is part of the source code of the GAMA modeling and simulation platform
 * (v.1.9.0).
 *
 * (c) 2007-2022 UMI 209 UMMISCO IRD/SU & Partners (IRIT, MIAT, TLU, CTU)
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
		name = IKeyword.STATUS,
		kind = ISymbolKind.SINGLE_STATEMENT,
		with_sequence = false,
		concept = { IConcept.DISPLAY, IConcept.TEXT })
@inside (
		kinds = { ISymbolKind.BEHAVIOR, ISymbolKind.SEQUENCE_STATEMENT, ISymbolKind.LAYER })
@facets (
		value = { @facet (
				name = IKeyword.COLOR,
				type = IType.COLOR,
				optional = true,
				doc = @doc ("The color used for displaying the background of the status message")),
				@facet (
						name = IKeyword.MESSAGE,
						type = IType.NONE,
						optional = false,
						doc = @doc ("Allows to display a necessarily short message in the status box in the upper left corner. No formatting characters (carriage returns, tabs, or Unicode characters) should be used, but a background color can be specified. The message will remain in place until it is replaced by another one or by nil, in which case the standard status (number of cycles) will be displayed again")) },
		omissible = IKeyword.MESSAGE)
@doc (
		value = "The statement makes the agent output an arbitrary message in the status box.",
		usages = { @usage (
				value = "Outputting a message",
				examples = { @example ("status (\"This is my status \" + self) color: #yellow;") }) })
public class StatusStatement extends AbstractStatement {

	@Override
	public String getTrace(final IScope scope) {
		// We dont trace status statements
		return "";
	}

	/** The message. */
	final IExpression message;

	/** The color. */
	final IExpression color;

	/**
	 * Instantiates a new status statement.
	 *
	 * @param desc
	 *            the desc
	 */
	public StatusStatement(final IDescription desc) {
		super(desc);
		message = getFacet(IKeyword.MESSAGE);
		color = getFacet(IKeyword.COLOR);
	}

	@Override
	public Object privateExecuteIn(final IScope scope) throws GamaRuntimeException {
		final IAgent agent = scope.getAgent();
		final String mes = null;
		if (agent != null && !agent.dead()) {
			final Object o = message.value(scope);
			final String msg = o == null ? null : Cast.asString(scope, o);
			scope.getGui().getStatus().setStatus(scope, msg,
					color == null ? null : Cast.asColor(scope, color.value(scope)));
		}
		return mes;
	}
}
