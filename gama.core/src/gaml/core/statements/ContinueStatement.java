/*******************************************************************************************************
 *
 * ContinueStatement.java, in msi.gama.core, is part of the source code of the GAMA modeling and simulation platform
 * (v.1.9.0).
 *
 * (c) 2007-2022 UMI 209 UMMISCO IRD/SU & Partners (IRIT, MIAT, TLU, CTU)
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gaml.core.statements;

import gama.annotations.common.interfaces.IGamlIssue;
import gama.annotations.common.interfaces.IKeyword;
import gama.annotations.precompiler.IConcept;
import gama.annotations.precompiler.ISymbolKind;
import gama.annotations.precompiler.GamlAnnotations.doc;
import gama.annotations.precompiler.GamlAnnotations.inside;
import gama.annotations.precompiler.GamlAnnotations.symbol;
import gama.core.runtime.IScope;
import gama.core.runtime.exceptions.GamaRuntimeException;
import gaml.core.compilation.IDescriptionValidator;
import gaml.core.compilation.annotations.serializer;
import gaml.core.compilation.annotations.validator;
import gaml.core.descriptions.IDescription;
import gaml.core.descriptions.StatementDescription;
import gaml.core.descriptions.StatementWithChildrenDescription;
import gaml.core.descriptions.SymbolDescription;
import gaml.core.descriptions.SymbolProto;
import gaml.core.descriptions.SymbolSerializer;
import gaml.core.statements.ContinueStatement.ContinueSerializer;
import gaml.core.statements.ContinueStatement.ContinueValidator;

/**
 * The class ContinueStatement.
 *
 * @author drogoul
 * @since 22 avr. 2012
 *
 */
@symbol (
		name = IKeyword.CONTINUE,
		kind = ISymbolKind.SINGLE_STATEMENT,
		with_sequence = false,
		concept = { IConcept.LOOP })
@inside (
		kinds = ISymbolKind.SEQUENCE_STATEMENT)
@doc (
		value = "`" + IKeyword.CONTINUE
				+ "` allows to skip the remaining statements inside a loop and an ask and directly move to the next element. Inside a switch, it has the same effect as break.")
@validator (ContinueValidator.class)
@serializer (ContinueSerializer.class)
public class ContinueStatement extends AbstractStatement {

	/**
	 * The Class BreakSerializer.
	 */
	public static class ContinueSerializer extends SymbolSerializer<StatementDescription> {

		@Override
		protected void serialize(final SymbolDescription desc, final StringBuilder sb, final boolean includingBuiltIn) {
			sb.append(CONTINUE).append(";");
		}
	}

	/**
	 * The Class BreakValidator.
	 */
	public static class ContinueValidator implements IDescriptionValidator<StatementDescription> {

		/**
		 * Method validate()
		 *
		 * @see gaml.core.compilation.IDescriptionValidator#validate(gaml.core.descriptions.IDescription)
		 */
		@Override
		public void validate(final StatementDescription description) {
			IDescription superDesc = description.getEnclosingDescription();
			while (superDesc instanceof StatementWithChildrenDescription) {
				if (((StatementWithChildrenDescription) superDesc).isContinuable()) return;
				superDesc = superDesc.getEnclosingDescription();
			}
			description.error("'continue' must be used in the context of " + SymbolProto.CONTINUABLE_STATEMENTS,
					IGamlIssue.WRONG_CONTEXT);
		}
	}

	/**
	 * @param desc
	 */
	public ContinueStatement(final IDescription desc) {
		super(desc);
	}

	/**
	 * @see gaml.core.commands.AbstractCommand#privateExecuteIn(gama.core.runtime.IScope)
	 */
	@Override
	protected Object privateExecuteIn(final IScope scope) throws GamaRuntimeException {
		scope.setContinueStatus();
		return null; // How to return the last object ??
	}

}
