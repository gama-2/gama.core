/*******************************************************************************************************
 *
 * IProjection.java, in msi.gama.core, is part of the source code of the
 * GAMA modeling and simulation platform (v.1.9.0).
 *
 * (c) 2007-2022 UMI 209 UMMISCO IRD/SU & Partners (IRIT, MIAT, TLU, CTU)
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package gama.core.metamodel.topology.projection;

import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;

import gama.core.common.geometry.Envelope3D;
import gama.core.runtime.IScope;

import org.locationtech.jts.geom.Geometry;

/**
 * Class IProjection.
 * 
 * @author drogoul
 * @since 17 déc. 2013
 * 
 */
public interface IProjection {

	/**
	 * Creates the transformation.
	 *
	 * @param t the t
	 */
	public abstract void createTransformation(final MathTransform t);

	/**
	 * Transform.
	 *
	 * @param g the g
	 * @return the geometry
	 */
	public abstract Geometry transform(final Geometry g);

	/**
	 * Inverse transform.
	 *
	 * @param g the g
	 * @return the geometry
	 */
	public abstract Geometry inverseTransform(final Geometry g);

	/**
	 * Gets the initial CRS.
	 *
	 * @param scope the scope
	 * @return the initial CRS
	 */
	public abstract CoordinateReferenceSystem getInitialCRS(IScope scope);

	/**
	 * Gets the target CRS.
	 *
	 * @param scope the scope
	 * @return the target CRS
	 */
	public abstract CoordinateReferenceSystem getTargetCRS(IScope scope);

	/**
	 * Gets the projected envelope.
	 *
	 * @return the projected envelope
	 */
	public abstract Envelope3D getProjectedEnvelope();

	/**
	 * @param geom
	 */
	public abstract void translate(Geometry geom);

	/**
	 * Inverse translate.
	 *
	 * @param geom the geom
	 */
	public abstract void inverseTranslate(Geometry geom);
	

	/**
	 * Convert unit.
	 *
	 * @param geom the geom
	 */
	public abstract void convertUnit(Geometry geom);

	/**
	 * Inverse convert unit.
	 *
	 * @param geom the geom
	 */
	public abstract void inverseConvertUnit(Geometry geom);

}