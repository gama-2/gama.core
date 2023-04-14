/*******************************************************************************************************
 *
 * GamaDirectoryType.java, in msi.gama.core, is part of the source code of the
 * GAMA modeling and simulation platform (v.1.9.0).
 *
 * (c) 2007-2022 UMI 209 UMMISCO IRD/SU & Partners (IRIT, MIAT, TLU, CTU)
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package gaml.core.types;

import gama.annotations.common.interfaces.IKeyword;
import gama.annotations.precompiler.IConcept;
import gama.annotations.precompiler.ISymbolKind;
import gama.annotations.precompiler.GamlAnnotations.doc;
import gama.annotations.precompiler.GamlAnnotations.type;
import gama.core.util.file.GamaFolderFile;

/**
 * Written by taillandier Modified on 10 Apr. 2021
 *
 */
@type (
		name = IKeyword.DIRECTORY,
		id = IType.DIRECTORY,
		wraps = { GamaFolderFile.class },
		kind = ISymbolKind.Variable.CONTAINER,
		concept = { IConcept.TYPE, IConcept.FILE },
		doc = @doc ("specific type of for folder"))
public class GamaDirectoryType extends GamaFileType {


}