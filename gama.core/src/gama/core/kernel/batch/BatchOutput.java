/*******************************************************************************************************
 *
 * BatchOutput.java, in msi.gama.core, is part of the source code of the
 * GAMA modeling and simulation platform (v.1.9.0).
 *
 * (c) 2007-2022 UMI 209 UMMISCO IRD/SU & Partners (IRIT, MIAT, TLU, CTU)
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package gama.core.kernel.batch;

import gama.annotations.common.interfaces.IKeyword;
import gama.annotations.precompiler.IConcept;
import gama.annotations.precompiler.ISymbolKind;
import gama.annotations.precompiler.GamlAnnotations.facet;
import gama.annotations.precompiler.GamlAnnotations.facets;
import gama.annotations.precompiler.GamlAnnotations.inside;
import gama.annotations.precompiler.GamlAnnotations.symbol;
import gaml.core.compilation.ISymbol;
import gaml.core.compilation.Symbol;
import gaml.core.descriptions.IDescription;
import gaml.core.types.IType;

/**
 * The Class BatchOutput.
 */
@symbol (
		name = IKeyword.SAVE_BATCH,
		kind = ISymbolKind.BATCH_METHOD,
		with_sequence = false,
		concept = { IConcept.BATCH },
		internal = true)
@inside (
		kinds = { ISymbolKind.EXPERIMENT })
@facets (
		value = { @facet (
				name = IKeyword.TO,
				type = IType.LABEL,
				optional = false,
				internal = true),
				@facet (
						name = IKeyword.REWRITE,
						type = IType.BOOL,
						optional = true,
						internal = true),
				@facet (
						name = IKeyword.DATA,
						type = IType.NONE,
						optional = true,
						internal = true) },
		omissible = IKeyword.DATA)
public class BatchOutput extends Symbol {

	// A placeholder for a file output
	// TODO To be replaced by a proper "save" command, now that it accepts
	// new file types.

	/**
	 * Instantiates a new batch output.
	 *
	 * @param desc the desc
	 */
	public BatchOutput(final IDescription desc) {
		super(desc);
	}

	@Override
	public void setChildren(final Iterable<? extends ISymbol> commands) {}

}