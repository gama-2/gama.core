/*******************************************************************************************************
 *
 * ElseStatement.java, in msi.gama.core, is part of the source code of the
 * GAMA modeling and simulation platform (v.1.9.0).
 *
 * (c) 2007-2022 UMI 209 UMMISCO IRD/SU & Partners (IRIT, MIAT, TLU, CTU)
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package gaml.core.statements;

import gama.annotations.common.interfaces.IKeyword;
import gama.annotations.precompiler.*;
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
@symbol(name = IKeyword.ELSE, kind = ISymbolKind.SEQUENCE_STATEMENT, with_sequence = true, concept = { IConcept.CONDITION })
@inside(symbols = IKeyword.IF)
@doc(value="This statement cannot be used alone",see={IKeyword.IF})
public class ElseStatement extends AbstractStatementSequence {

	/**
	 * Instantiates a new else statement.
	 *
	 * @param desc the desc
	 */
	public ElseStatement(final IDescription desc) {
		super(desc);
		setName(IKeyword.ELSE);
	}

}
