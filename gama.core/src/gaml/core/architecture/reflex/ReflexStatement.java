/*******************************************************************************************************
 *
 * ReflexStatement.java, in msi.gama.core, is part of the source code of the
 * GAMA modeling and simulation platform (v.1.9.0).
 *
 * (c) 2007-2022 UMI 209 UMMISCO IRD/SU & Partners (IRIT, MIAT, TLU, CTU)
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package gaml.core.architecture.reflex;

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
import gama.core.runtime.IScope;
import gama.core.runtime.exceptions.GamaRuntimeException;
import gaml.core.compilation.IDescriptionValidator.ValidNameValidator;
import gaml.core.compilation.annotations.validator;
import gaml.core.descriptions.IDescription;
import gaml.core.expressions.IExpression;
import gaml.core.operators.Cast;
import gaml.core.statements.AbstractStatementSequence;
import gaml.core.types.IType;

/**
 * The Class ReflexStatement.
 */
@symbol (
		name = { IKeyword.REFLEX, IKeyword.INIT, IKeyword.ABORT },
		kind = ISymbolKind.BEHAVIOR,
		with_sequence = true,
		unique_name = true,
		concept = { IConcept.BEHAVIOR, IConcept.SCHEDULER })
@inside (
		kinds = { ISymbolKind.SPECIES, ISymbolKind.EXPERIMENT, ISymbolKind.MODEL })
@facets (
		value = { @facet (
				name = IKeyword.WHEN,
				type = IType.BOOL,
				optional = true,
				doc = @doc ("an expression that evaluates a boolean, the condition to fulfill in order to execute the statements embedded in the reflex.")),
				@facet (
						name = IKeyword.NAME,
						type = IType.ID,
						optional = true,
						doc = @doc ("the identifier of the reflex")) },
		omissible = IKeyword.NAME)
@validator (ValidNameValidator.class)
@doc (
		value = "Reflexes are sequences of statements that can be executed by the agent. Reflexes prefixed by the 'reflex' keyword are executed continuously. Reflexes prefixed by 'init' are executed only immediately after the agent has been created. Reflexes prefixed by 'abort' just before the agent is killed. If a facet when: is defined, a reflex is executed only if the boolean expression evaluates to true.",
		usages = { @usage (
				value = "Example:",
				examples = { @example (
						value = "reflex my_reflex when: flip (0.5){ 		//Only executed when flip returns true",
						isExecutable = false),
						@example (
								value = "    write \"Executing the unconditional reflex\";",
								isExecutable = false),
						@example (
								value = "}",
								isExecutable = false) }) })
public class ReflexStatement extends AbstractStatementSequence {

	/** The when. */
	private final IExpression when;

	/**
	 * Instantiates a new reflex statement.
	 *
	 * @param desc the desc
	 */
	public ReflexStatement(final IDescription desc) {
		super(desc);
		when = getFacet(IKeyword.WHEN);
		if (hasFacet(IKeyword.NAME)) {
			setName(getLiteral(IKeyword.NAME));
		}
	}

	@Override
	public Object privateExecuteIn(final IScope scope) throws GamaRuntimeException {
		return when == null || Cast.asBool(scope, when.value(scope)) ? super.privateExecuteIn(scope) : null;
	}

}
