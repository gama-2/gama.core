/*******************************************************************************************************
 *
 * GamaGraphGexf.java, in msi.gama.core, is part of the source code of the GAMA modeling and simulation platform
 * (v.1.9.0).
 *
 * (c) 2007-2022 UMI 209 UMMISCO IRD/SU & Partners (IRIT, MIAT, TLU, CTU)
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gama.core.util.file;

import gama.annotations.precompiler.IConcept;
import gama.annotations.precompiler.GamlAnnotations.doc;
import gama.annotations.precompiler.GamlAnnotations.file;
import gama.core.runtime.IScope;
import gama.core.runtime.exceptions.GamaRuntimeException;
import gaml.core.species.ISpecies;
import gaml.core.types.IType;

/**
 * The Class GamaGraphGexf.
 */
@file (
		name = "graphgexf",
		extensions = { "gexf" },
		buffer_type = IType.GRAPH,
		concept = { IConcept.GRAPH, IConcept.FILE },
		doc = @doc ("Represents files that contain Graph information. The internal representation is a graph"))
@SuppressWarnings ({ "unchecked", "rawtypes" })
public class GamaGraphGexf extends GamaGraphFile {

	/**
	 * Instantiates a new gama graph gexf.
	 *
	 * @param scope
	 *            the scope
	 * @param pn
	 *            the pn
	 * @throws GamaRuntimeException
	 *             the gama runtime exception
	 */
	@doc ("References a gexf graph file by its filename")
	public GamaGraphGexf(final IScope scope, final String pn) throws GamaRuntimeException {
		super(scope, pn);
	}

	/**
	 * Instantiates a new gama graph gexf.
	 *
	 * @param scope
	 *            the scope
	 * @param pathName
	 *            the path name
	 * @param nodeSpecies
	 *            the node species
	 */
	@doc ("References a gexf graph file by its filename and the species to use to instantiate the nodes")
	public GamaGraphGexf(final IScope scope, final String pathName, final ISpecies nodeSpecies) {
		super(scope, pathName, nodeSpecies);
	}

	/**
	 * Instantiates a new gama graph gexf.
	 *
	 * @param scope
	 *            the scope
	 * @param pathName
	 *            the path name
	 * @param nodeSpecies
	 *            the node species
	 * @param edgeSpecies
	 *            the edge species
	 */
	@doc ("References a gexf graph file by its filename and the 2 species to use to instantiate the nodes and the edges")
	public GamaGraphGexf(final IScope scope, final String pathName, final ISpecies nodeSpecies,
			final ISpecies edgeSpecies) {
		super(scope, pathName, nodeSpecies, edgeSpecies);
	}

	@Override
	protected String getFileType() { return "gexf"; }

}
