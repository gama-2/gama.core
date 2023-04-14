/*******************************************************************************************************
 *
 * CatchStatement.java, in msi.gama.core, is part of the source code of the
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
import gaml.core.descriptions.IDescription;

/**
 * Written by drogoul Modified on 8 févr. 2010
 * 
 * @todo Description
 * 
 */
@symbol (
		name = IKeyword.CATCH,
		kind = ISymbolKind.SEQUENCE_STATEMENT,
		with_sequence = true,
		concept = { IConcept.ACTION })
@inside (
		symbols = IKeyword.TRY)
@doc (
		value = "This statement cannot be used alone",
		see = { IKeyword.TRY })
public class CatchStatement extends AbstractStatementSequence {

	/**
	 * Instantiates a new catch statement.
	 *
	 * @param desc the desc
	 */
	public CatchStatement(final IDescription desc) {
		super(desc);
		setName(IKeyword.CATCH);
	}

}
