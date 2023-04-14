/*******************************************************************************************************
 *
 * GamaSVGFile.java, in msi.gama.core, is part of the source code of the
 * GAMA modeling and simulation platform (v.1.9.0).
 *
 * (c) 2007-2022 UMI 209 UMMISCO IRD/SU & Partners (IRIT, MIAT, TLU, CTU)
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package gama.core.util.file;

import java.awt.Shape;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;

import org.locationtech.jts.awt.ShapeReader;
import org.locationtech.jts.geom.Geometry;

import com.kitfox.svg.SVGRoot;
import com.kitfox.svg.SVGUniverse;

import gama.annotations.precompiler.IConcept;
import gama.annotations.precompiler.GamlAnnotations.doc;
import gama.annotations.precompiler.GamlAnnotations.example;
import gama.annotations.precompiler.GamlAnnotations.file;
import gama.core.common.geometry.GeometryUtils;
import gama.core.common.geometry.Scaling3D;
import gama.core.metamodel.shape.GamaPoint;
import gama.core.metamodel.shape.GamaShape;
import gama.core.metamodel.shape.IShape;
import gama.core.runtime.IScope;
import gama.core.runtime.exceptions.GamaRuntimeException;
import gama.core.util.GamaListFactory;
import gama.core.util.IList;
import gaml.core.types.IType;
import gaml.core.types.Types;

/**
 * Class GamaSVGFile. Only loads vector shapes right now (and none of the associated elements: textures, colors, fonts,
 * etc.)
 *
 * @author drogoul
 * @since 30 déc. 2013
 *
 */
@file (
		name = "svg",
		extensions = "svg",
		buffer_type = IType.LIST,
		buffer_content = IType.GEOMETRY,
		buffer_index = IType.INT,
		concept = { IConcept.SVG },
		doc = @doc ("Represents 2D geometries described in a SVG file. The internal representation is a list of geometries"))
public class GamaSVGFile extends GamaGeometryFile {

	/** The size. */
	Scaling3D size;

	/**
	 * Instantiates a new gama SVG file.
	 *
	 * @param scope the scope
	 * @param pathName the path name
	 * @throws GamaRuntimeException the gama runtime exception
	 */
	@doc (
			value = "This file constructor allows to read a svg file",
			examples = { @example (
					value = "file f <-svg_file(\"file.svg\");",
					isExecutable = false) })
	public GamaSVGFile(final IScope scope, final String pathName) throws GamaRuntimeException {
		super(scope, pathName);
	}

	/**
	 * Instantiates a new gama SVG file.
	 *
	 * @param scope the scope
	 * @param pathName the path name
	 * @param size the size
	 * @throws GamaRuntimeException the gama runtime exception
	 */
	@doc (
			value = "This file constructor allows to read a svg file, specifying the size of the bounding box",
			examples = { @example (
					value = "file f <-svg_file(\"file.svg\", {10,10});",
					isExecutable = false) })
	public GamaSVGFile(final IScope scope, final String pathName, final GamaPoint size) throws GamaRuntimeException {
		super(scope, pathName);
		this.size = Scaling3D.of(size);
	}

	@Override
	protected IShape buildGeometry(final IScope scope) {
		return getBuffer().get(0);
	}

	@Override
	public IList<String> getAttributes(final IScope scope) {
		// TODO are there attributes ?
		return GamaListFactory.create(Types.STRING);
	}

	@Override
	protected void fillBuffer(final IScope scope) throws GamaRuntimeException {
		try (BufferedReader in = new BufferedReader(new FileReader(getFile(scope)))) {
			final SVGUniverse svg = new SVGUniverse();
			final URI uri = svg.loadSVG(in, getPath(scope));
			final SVGRoot diagram = svg.getDiagram(uri).getRoot();
			final Shape shape = diagram.getShape();
			final Geometry geom = ShapeReader.read(shape, 1.0, GeometryUtils.GEOMETRY_FACTORY); // flatness
			final IShape gs = new GamaShape(null, geom, null, new GamaPoint(0, 0), size, true);
			setBuffer(GamaListFactory.wrap(Types.GEOMETRY, gs));
		} catch (final IOException e) {
			throw GamaRuntimeException.create(e, scope);
		}
	}

}
