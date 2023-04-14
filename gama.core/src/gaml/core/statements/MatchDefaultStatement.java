/*******************************************************************************************************
 *
 * MatchDefaultStatement.java, in msi.gama.core, is part of the source code of the
 * GAMA modeling and simulation platform (v.1.9.0).
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
import gama.annotations.precompiler.GamlAnnotations.inside;
import gama.annotations.precompiler.GamlAnnotations.symbol;
import gama.core.runtime.IScope;
import gama.core.runtime.exceptions.GamaRuntimeException;
import gaml.core.descriptions.IDescription;

/**
 * The Class MatchDefaultStatement.
 */
@symbol (
		name = { IKeyword.DEFAULT },
		kind = ISymbolKind.SEQUENCE_STATEMENT,
		with_sequence = true,
		unique_in_context = true,
		concept = { IConcept.CONDITION })
@inside (
		symbols = IKeyword.SWITCH)
@doc (
		value = "Used in a switch match structure, the block prefixed by default is executed only if no other block has matched (otherwise it is not).",
		see = { "switch", "match" })
public class MatchDefaultStatement extends MatchStatement {

	/**
	 * Instantiates a new match default statement.
	 *
	 * @param desc the desc
	 */
	public MatchDefaultStatement(final IDescription desc) {
		super(desc);
	}

	@Override
	public boolean matches(final IScope scope, final Object switchValue) throws GamaRuntimeException {
		return false;
	}

}