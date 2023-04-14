/*******************************************************************************************************
 *
 * LetStatement.java, in msi.gama.core, is part of the source code of the GAMA modeling and simulation platform
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
import gama.annotations.precompiler.GamlAnnotations.facet;
import gama.annotations.precompiler.GamlAnnotations.facets;
import gama.annotations.precompiler.GamlAnnotations.inside;
import gama.annotations.precompiler.GamlAnnotations.symbol;
import gama.core.runtime.IScope;
import gama.core.runtime.exceptions.GamaRuntimeException;
import gaml.core.compilation.IDescriptionValidator;
import gaml.core.compilation.annotations.serializer;
import gaml.core.compilation.annotations.validator;
import gaml.core.descriptions.IDescription;
import gaml.core.descriptions.IExpressionDescription;
import gaml.core.descriptions.SymbolDescription;
import gaml.core.expressions.IExpression;
import gaml.core.expressions.IVarExpression;
import gaml.core.operators.Cast;
import gaml.core.statements.LetStatement.LetSerializer;
import gaml.core.statements.LetStatement.LetValidator;
import gaml.core.types.IType;

/**
 * Written by drogoul Modified on 6 févr. 2010
 *
 * @todo Description
 *
 */

@facets (
		value = { /* @facet(name = IKeyword.VAR, type = IType.NEW_TEMP_ID, optional = true), */
				@facet (
						name = IKeyword.NAME,
						type = IType.NEW_TEMP_ID,
						optional = false,
						doc = @doc ("The name of the variable declared ")),
				@facet (
						name = IKeyword.VALUE,
						type = { IType.NONE },
						optional = /* AD change false */true,
						doc = @doc ("The value assigned to this variable")),

				@facet (
						name = IKeyword.OF,
						type = { IType.TYPE_ID },
						optional = true,
						doc = @doc ("The type of the contents if this declaration concerns a container")),
				@facet (
						name = IKeyword.INDEX,
						type = IType.TYPE_ID,
						optional = true,
						doc = @doc ("The type of the index if this declaration concerns a container")),
				@facet (
						name = IKeyword.TYPE,
						type = { IType.TYPE_ID },
						optional = true,
						doc = @doc ("The type of the variable")) },
		omissible = IKeyword.NAME)
@symbol (
		name = { IKeyword.LET },
		kind = ISymbolKind.SINGLE_STATEMENT,
		concept = { IConcept.SYSTEM },
		with_sequence = false,
		doc = @doc ("Allows to declare a temporary variable and to initialize it with a value. The type can be provided, otherwise it is inferred from the right-hand expression. "))
@inside (
		kinds = { ISymbolKind.BEHAVIOR, ISymbolKind.SEQUENCE_STATEMENT, ISymbolKind.LAYER })
@validator (LetValidator.class)
@serializer (LetSerializer.class)
@doc ("Allows to declare a temporary variable of the specified type and to initialize it with a value")
public class LetStatement extends SetStatement {

	/**
	 * The Class LetSerializer.
	 */
	public static class LetSerializer extends AssignmentSerializer {

		@Override
		protected void serialize(final SymbolDescription desc, final StringBuilder sb, final boolean includingBuiltIn) {
			sb.append(desc.getGamlType().serialize(includingBuiltIn)).append(" ");
			super.serialize(desc, sb, includingBuiltIn);

		}

	}

	/**
	 * The Class LetValidator.
	 */
	public static class LetValidator implements IDescriptionValidator<IDescription> {

		/**
		 * Method validate()
		 *
		 * @see gaml.core.compilation.IDescriptionValidator#validate(gaml.core.descriptions.IDescription)
		 */
		@Override
		public void validate(final IDescription cd) {
			if (Assert.nameIsValid(cd)) {
				final IExpressionDescription receiver = cd.getFacet(NAME);
				final IExpression expr = receiver.getExpression();
				if (!(expr instanceof IVarExpression var)) {
					cd.error("The expression " + cd.getLitteral(NAME) + " is not a reference to a variable ", NAME);
					return;
				}
				final IExpressionDescription assigned = cd.getFacet(VALUE);
				if (assigned != null) {
					Assert.typesAreCompatibleForAssignment(VALUE, cd, Cast.toGaml(expr), expr.getGamlType(), assigned);
				}

			}
		}
	}

	/**
	 * Instantiates a new let statement.
	 *
	 * @param desc
	 *            the desc
	 */
	public LetStatement(final IDescription desc) {
		super(desc);
		setName(IKeyword.LET + getVarName());
	}

	@Override
	protected Object privateExecuteIn(final IScope scope) throws GamaRuntimeException {
		final Object val = value.value(scope);
		varExpr.setVal(scope, val, true);
		return val;
	}

}
