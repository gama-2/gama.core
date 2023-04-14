/*******************************************************************************************************
 *
 * SetStatement.java, in msi.gama.core, is part of the source code of the GAMA modeling and simulation platform
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
import gama.annotations.precompiler.GamlAnnotations.facet;
import gama.annotations.precompiler.GamlAnnotations.facets;
import gama.annotations.precompiler.GamlAnnotations.inside;
import gama.annotations.precompiler.GamlAnnotations.symbol;
import gama.core.runtime.IScope;
import gama.core.runtime.exceptions.GamaRuntimeException;
import gaml.core.compilation.GAML;
import gaml.core.compilation.IDescriptionValidator;
import gaml.core.compilation.annotations.serializer;
import gaml.core.compilation.annotations.validator;
import gaml.core.descriptions.IDescription;
import gaml.core.descriptions.IExpressionDescription;
import gaml.core.descriptions.ModelDescription;
import gaml.core.descriptions.SymbolDescription;
import gaml.core.descriptions.SymbolSerializer;
import gaml.core.expressions.IExpression;
import gaml.core.expressions.IVarExpression;
import gaml.core.operators.Cast;
import gaml.core.statements.SetStatement.AssignmentSerializer;
import gaml.core.statements.SetStatement.AssignmentValidator;
import gaml.core.types.IType;

/**
 * Written by drogoul Modified on 6 févr. 2010
 *
 * @todo Description
 *
 */

@facets (
		value = { /*
					 * @facet(name = IKeyword.VAR, type = IType.NONE, optional = true),
					 */
				@facet (
						name = IKeyword.NAME,
						type = IType.NONE,
						optional = false,
						doc = @doc ("the name of an existing variable or attribute to be modified")),
				@facet (
						name = IKeyword.VALUE,
						type = { IType.NONE },
						optional = false,
						doc = @doc ("the value to affect to the variable or attribute")) },
		omissible = IKeyword.NAME)
@symbol (
		name = { IKeyword.SET },
		kind = ISymbolKind.SINGLE_STATEMENT,
		concept = { IConcept.ATTRIBUTE },
		with_sequence = false)
@inside (
		kinds = { ISymbolKind.BEHAVIOR, ISymbolKind.SEQUENCE_STATEMENT, ISymbolKind.LAYER },
		symbols = IKeyword.CHART)
@validator (AssignmentValidator.class)
@doc (
		value = "Allows to assign a value to the variable or attribute specified")
@serializer (AssignmentSerializer.class)
public class SetStatement extends AbstractStatement {

	/**
	 * The Class AssignmentSerializer.
	 */
	public static class AssignmentSerializer extends SymbolSerializer<SymbolDescription> {

		@Override
		protected void serialize(final SymbolDescription desc, final StringBuilder sb, final boolean includingBuiltIn) {
			if (desc == null) return;
			final IExpressionDescription ed = desc.getFacet(VALUE);
			if (ed == null) return;
			final String exp = ed.serialize(includingBuiltIn);
			if (exp == null) return;
			sb.append(desc.getName());
			sb.append(" <- ");
			sb.append(exp);
			sb.append(";");
		}

	}

	/**
	 * The Class AssignmentValidator.
	 */
	public static class AssignmentValidator implements IDescriptionValidator<IDescription> {

		/**
		 * Method validate()
		 *
		 * @see gaml.core.compilation.IDescriptionValidator#validate(gaml.core.descriptions.IDescription)
		 */
		@Override
		public void validate(final IDescription cd) {
			final IExpressionDescription receiver = cd.getFacet(NAME);
			// String name = cd.getName();
			final IExpression expr = receiver.getExpression();
			if (!(expr instanceof IVarExpression var)) {
				cd.error("The expression " + cd.getLitteral(NAME) + " is not a reference to a variable ", NAME);
				return;
			}
			final IExpressionDescription assigned = cd.getFacet(VALUE);
			if (assigned != null) {
				Assert.typesAreCompatibleForAssignment(VALUE, cd, Cast.toGaml(expr), expr.getGamlType(), assigned);
			}

			// AD 19/1/13: test of the constants
			if (var.getVar().isNotModifiable()) {
				cd.error("The variable " + expr.serialize(false)
						+ " is a constant or a function and cannot be assigned a value.", IKeyword.NAME);
			}

			if (IKeyword.SHAPE.equals(var.getName()) && (cd.getSpeciesContext() instanceof ModelDescription)) {
				cd.warning(
						"Dynamically changing the shape of the world can lead to unexpected results. It is advised to redefine the attribute instead (e.g. 'geometry shape <- "
								+ (assigned == null ? "..." : assigned.serialize(false)) + "')",
						IKeyword.NAME);
			}

		}
	}

	/** The var expr. */
	protected final IVarExpression varExpr;

	/** The value. */
	protected final IExpression value;

	/**
	 * Instantiates a new sets the statement.
	 *
	 * @param desc
	 *            the desc
	 */
	public SetStatement(final IDescription desc) {
		super(desc);
		varExpr = (IVarExpression) getFacet(IKeyword.NAME);
		setName(IKeyword.SET + getVarName());
		final IExpression expr = getFacet(IKeyword.VALUE);
		if (expr == null) {
			value = GAML.getExpressionFactory().createConst(varExpr.getGamlType().getDefault(), varExpr.getGamlType());
		} else {
			value = expr;
		}

	}

	@Override
	protected Object privateExecuteIn(final IScope scope) throws GamaRuntimeException {
		final Object val = value.value(scope);
		varExpr.setVal(scope, val, false);
		return val;
	}

	/**
	 * Gets the var name.
	 *
	 * @return the var name
	 */
	public String getVarName() {
		if (varExpr != null) return varExpr.literalValue();
		return null;
	}

}
