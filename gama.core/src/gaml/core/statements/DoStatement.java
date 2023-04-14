/*******************************************************************************************************
 *
 * DoStatement.java, in msi.gama.core, is part of the source code of the GAMA modeling and simulation platform
 * (v.1.9.0).
 *
 * (c) 2007-2023 UMI 209 UMMISCO IRD/SU & Partners (IRIT, MIAT, TLU, CTU)
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gaml.core.statements;

import java.util.Set;

import gama.annotations.common.interfaces.IGamlIssue;
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
import gama.core.runtime.ExecutionResult;
import gama.core.runtime.IScope;
import gama.core.runtime.exceptions.GamaRuntimeException;
import gaml.core.compilation.IDescriptionValidator;
import gaml.core.compilation.annotations.serializer;
import gaml.core.compilation.annotations.validator;
import gaml.core.descriptions.ActionDescription;
import gaml.core.descriptions.IDescription;
import gaml.core.descriptions.IExpressionDescription;
import gaml.core.descriptions.PrimitiveDescription;
import gaml.core.descriptions.SpeciesDescription;
import gaml.core.descriptions.StatementDescription;
import gaml.core.descriptions.SymbolDescription;
import gaml.core.descriptions.SymbolSerializer.StatementSerializer;
import gaml.core.expressions.IExpression;
import gaml.core.factories.DescriptionFactory;
import gaml.core.operators.Strings;
import gaml.core.species.ISpecies;
import gaml.core.statements.DoStatement.DoSerializer;
import gaml.core.statements.DoStatement.DoValidator;
import gaml.core.types.IType;

/**
 * Written by drogoul Modified on 7 févr. 2010
 *
 * @todo Description
 *
 */
@symbol (
		name = { IKeyword.DO, IKeyword.INVOKE },
		kind = ISymbolKind.SINGLE_STATEMENT,
		with_sequence = true,
		with_scope = false,
		concept = { IConcept.ACTION },
		with_args = true)
@inside (
		kinds = { ISymbolKind.BEHAVIOR, ISymbolKind.SEQUENCE_STATEMENT },
		symbols = IKeyword.CHART)
@facets (
		value = { @facet (
				name = IKeyword.ACTION,
				type = IType.ID,
				optional = false,
				doc = @doc ("the name of an action or a primitive")),
				@facet (
						name = IKeyword.WITH,
						type = IType.MAP,
						of = IType.NONE,
						index = IType.STRING,
						optional = true,
						doc = @doc (
								value = "a map expression containing the parameters of the action")),
				@facet (
						name = IKeyword.INTERNAL_FUNCTION,
						type = IType.NONE,
						optional = true,
						internal = true),
				@facet (
						name = IKeyword.RETURNS,
						type = IType.NEW_TEMP_ID,
						optional = true,
						doc = @doc (
								deprecated = "declare a temporary variable and use assignement of the execution of the action instead",
								value = "create a new variable and assign to it the result of the action")) },
		omissible = IKeyword.ACTION)
@doc (
		value = "Allows the agent to execute an action or a primitive.  For a list of primitives available in every species, see this [BuiltIn161 page]; for the list of primitives defined by the different skills, see this [Skills161 page]. Finally, see this [Species161 page] to know how to declare custom actions.",
		usages = { @usage (
				value = "The simple syntax (when the action does not expect any argument and the result is not to be kept) is:",
				examples = { @example (
						value = "do name_of_action_or_primitive;",
						isExecutable = false) }),
				@usage (
						value = "In case the action expects one or more arguments to be passed, they are defined by using facets (enclosed tags or a map are now deprecated):",
						examples = { @example (
								value = "do name_of_action_or_primitive arg1: expression1 arg2: expression2;",
								isExecutable = false) }),
				@usage (
						value = "In case the result of the action needs to be made available to the agent, the action can be called with the agent calling the action (`self` when the agent itself calls the action) instead of `do`; the result should be assigned to a temporary variable:",
						examples = { @example (
								value = "type_returned_by_action result <- self name_of_action_or_primitive [];",
								isExecutable = false) }),
				@usage (
						value = "In case of an action expecting arguments and returning a value, the following syntax is used:",
						examples = { @example (
								value = "type_returned_by_action result <- self name_of_action_or_primitive [arg1::expression1, arg2::expression2];",
								isExecutable = false) }),
				@usage (
						value = "Deprecated uses: following uses of the `do` statement (still accepted) are now deprecated:",
						examples = { @example (
								value = "// Simple syntax: "),
								@example (
										value = "do action: name_of_action_or_primitive;",
										isExecutable = false),
								@example (""), @example (
										value = "// In case the result of the action needs to be made available to the agent, the `returns` keyword can be defined; the result will then be referred to by the temporary variable declared in this attribute:"),
								@example (
										value = "do name_of_action_or_primitive returns: result;",
										isExecutable = false),
								@example (
										value = "do name_of_action_or_primitive arg1: expression1 arg2: expression2 returns: result;",
										isExecutable = false),
								@example (
										value = "type_returned_by_action result <- name_of_action_or_primitive(self, [arg1::expression1, arg2::expression2]);",
										isExecutable = false),
								@example (""), @example (
										value = "// In case the result of the action needs to be made available to the agent"),
								@example (
										value = "let result <- name_of_action_or_primitive(self, []);",
										isExecutable = false),
								@example (""), @example (
										value = "// In case the action expects one or more arguments to be passed, they can also be defined by using enclosed `arg` statements, or the `with` facet with a map of parameters:"),
								@example (
										value = "do name_of_action_or_primitive with: [arg1::expression1, arg2::expression2];",
										isExecutable = false),
								@example (
										value = "",
										isExecutable = false),
								@example (
										value = "or",
										isExecutable = false),
								@example (
										value = "",
										isExecutable = false),
								@example (
										value = "do name_of_action_or_primitive {",
										isExecutable = false),
								@example (
										value = "     arg arg1 value: expression1;",
										isExecutable = false),
								@example (
										value = "     arg arg2 value: expression2;",
										isExecutable = false),
								@example (
										value = "     ...",
										isExecutable = false),
								@example (
										value = "}",
										isExecutable = false) }) })
@validator (DoValidator.class)
@serializer (DoSerializer.class)
public class DoStatement extends AbstractStatementSequence implements IStatement.WithArgs {

	/**
	 * The Class DoSerializer.
	 */
	public static class DoSerializer extends StatementSerializer {

		@Override
		protected void serializeArg(final IDescription desc, final IDescription arg, final StringBuilder sb,
				final boolean includingBuiltIn) {
			final String name = arg.getName();
			final IExpressionDescription value = arg.getFacet(VALUE);
			if (Strings.isGamaNumber(name)) {
				sb.append(value.serialize(includingBuiltIn));
			} else {
				sb.append(name).append(":").append(value.serialize(includingBuiltIn));
			}

		}

		@Override
		protected String serializeFacetValue(final SymbolDescription s, final String key,
				final boolean includingBuiltIn) {
			if (!DO_FACETS.contains(key)) return null;
			return super.serializeFacetValue(s, key, includingBuiltIn);
		}

	}

	/**
	 * The Class DoValidator.
	 */
	public static class DoValidator implements IDescriptionValidator<StatementDescription> {

		/**
		 * Method validate()
		 *
		 * @see gaml.core.compilation.IDescriptionValidator#validate(gaml.core.descriptions.IDescription)
		 */
		@Override
		public void validate(final StatementDescription desc) {
			final String action = desc.getLitteral(ACTION);
			final boolean isSuperInvocation = desc.isSuperInvocation();
			SpeciesDescription sd = desc.getSpeciesContext();
			if (sd != null && isSuperInvocation) {
				sd = sd.getParent();
				// if (sd != null && sd.isBuiltIn()) {
				// desc.error("Action " + action + " cannot be invoked on " + sd.getName(), IGamlIssue.UNKNOWN_ACTION,
				// ACTION, action, sd.getName());
				// return;
				// }

			}
			if (sd == null) return;
			// TODO What about actions defined in a macro species (not the
			// global one, which is filtered before) ?
			if (!sd.hasAction(action, false)) {
				desc.error("Action " + action + " does not exist in " + sd.getName(), IGamlIssue.UNKNOWN_ACTION, ACTION,
						action, sd.getName());
			}
			final ActionDescription ad = sd.getAction(action);
			if (ad instanceof PrimitiveDescription pd) {
				final String dep = pd.getDeprecated();
				if (dep != null) {
					desc.warning("Action " + action + " is deprecated: " + dep, IGamlIssue.DEPRECATED, ACTION);
				}
			}
		}

	}

	/** The args. */
	Arguments args;

	/** The return string. */
	String returnString;

	/** The target species. */
	final String targetSpecies;

	/** The function. */
	final IExpression function;

	/** The Constant DO_FACETS. */
	public static final Set<String> DO_FACETS = DescriptionFactory.getAllowedFacetsFor(IKeyword.DO, IKeyword.INVOKE);

	/**
	 * Instantiates a new do statement.
	 *
	 * @param desc
	 *            the desc
	 */
	public DoStatement(final IDescription desc) {
		super(desc);

		if (((StatementDescription) desc).isSuperInvocation()) {
			final SpeciesDescription s = desc.getSpeciesContext().getParent();
			targetSpecies = s.getName();
		} else {
			targetSpecies = null;
		}
		returnString = getLiteral(IKeyword.RETURNS);
		function = getFacet(IKeyword.INTERNAL_FUNCTION);

		setName(getLiteral(IKeyword.ACTION));
	}

	@Override
	public void enterScope(final IScope scope) {
		if (returnString != null) { scope.addVarWithValue(returnString, null); }
		super.enterScope(scope);
	}

	@Override
	public void setFormalArgs(final Arguments args) { this.args = args; }

	/**
	 * Gets the runtime args.
	 *
	 * @param scope
	 *            the scope
	 * @return the runtime args
	 */
	public Arguments getRuntimeArgs(final IScope scope) {
		if (args == null) return null;
		// Dynamic arguments necessary (see #2943, #2922, plus issue with multiple parallel simulations)
		return args.resolveAgainst(scope);
	}

	/**
	 * Returns the species on which to find the action. If a species target (desc) exists, then it is a super invocation
	 * and we have to find the corresponding action. Otherwise, we return the species of the agent
	 */
	private ISpecies getContext(final IScope scope) {
		// if ((targetSpecies != null) && "model".equals(targetSpecies)) {
		//
		// DEBUG.OUT("Model found to execute " + name);
		//
		// }

		return targetSpecies != null ? scope.getModel().getSpecies(targetSpecies) : scope.getAgent().getSpecies();
	}

	@Override
	public Object privateExecuteIn(final IScope scope) throws GamaRuntimeException {
		final ISpecies species = getContext(scope);
		if (species == null)
			throw GamaRuntimeException.error("Impossible to find a species to execute " + getName(), scope);
		final IStatement.WithArgs executer = species.getAction(name);
		Object result = null;
		if (executer != null) {
			final ExecutionResult er = scope.execute(executer, getRuntimeArgs(scope));
			result = er.getValue();
		} else if (function != null) {
			result = function.value(scope);
		} else
			throw GamaRuntimeException.error("Impossible to find action " + getName() + " in " + species.getName(),
					scope);
		if (returnString != null) { scope.setVarValue(returnString, result); }
		return result;
	}

	@Override
	public void setRuntimeArgs(final IScope scope, final Arguments args) {}

	@Override
	public void dispose() {
		if (args != null) { args.dispose(); }
		args = null;
		super.dispose();
	}

}
