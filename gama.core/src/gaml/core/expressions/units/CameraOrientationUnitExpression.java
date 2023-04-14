/*******************************************************************************************************
 *
 * CameraOrientationUnitExpression.java, in msi.gama.core, is part of the source code of the GAMA modeling and
 * simulation platform (v.1.9.0).
 *
 * (c) 2007-2023 UMI 209 UMMISCO IRD/SU & Partners (IRIT, MIAT, TLU, CTU)
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gaml.core.expressions.units;

import gama.core.common.interfaces.IGraphics;
import gama.core.metamodel.shape.GamaPoint;
import gama.core.runtime.IScope;
import gama.core.runtime.IScope.IGraphicsScope;
import gaml.core.types.Types;

/**
 *
 * @author Drogoul
 * @revision Now provides the camera_orientation even if the code is not run within a graphics context but the current
 *           experiment only one OpenGL display
 */
public class CameraOrientationUnitExpression extends UnitConstantExpression {

	/**
	 * Instantiates a new camera orientation unit expression.
	 *
	 * @param doc
	 *            the doc
	 */
	public CameraOrientationUnitExpression(final String doc) {
		super(new GamaPoint(), Types.POINT, "camera_orientation", doc, null);
	}

	@Override
	public GamaPoint _value(final IScope sc) {
		if (sc == null || !sc.isGraphics()) return null;
		IGraphicsScope scope = (IGraphicsScope) sc;
		final IGraphics g = scope.getGraphics();
		if (g.is2D()) return null;
		return ((IGraphics.ThreeD) g).getCameraOrientation().yNegated();
	}

	@Override
	public boolean isConst() { return false; }

	@Override
	public boolean isAllowedInParameters() { return false; }

}
