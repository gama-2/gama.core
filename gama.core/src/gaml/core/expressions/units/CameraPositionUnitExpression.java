/*******************************************************************************************************
 *
 * CameraPositionUnitExpression.java, in msi.gama.core, is part of the source code of the GAMA modeling and simulation
 * platform (v.1.9.0).
 *
 * (c) 2007-2023 UMI 209 UMMISCO IRD/SU & Partners (IRIT, MIAT, TLU, CTU)
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gaml.core.expressions.units;

import gama.core.common.interfaces.IDisplaySurface;
import gama.core.common.interfaces.IGraphics;
import gama.core.metamodel.shape.GamaPoint;
import gama.core.runtime.GAMA;
import gama.core.runtime.IScope;
import gama.core.runtime.IScope.IGraphicsScope;
import gaml.core.types.Types;

/**
 * The Class CameraPositionUnitExpression.
 */
public class CameraPositionUnitExpression extends UnitConstantExpression {

	/**
	 * Instantiates a new camera position unit expression.
	 *
	 * @param doc
	 *            the doc
	 */
	public CameraPositionUnitExpression(final String doc) {
		super(new GamaPoint(), Types.POINT, "camera_location", doc, null);
	}

	@Override
	public GamaPoint _value(final IScope sc) {
		if (sc == null || !sc.isGraphics()) {
			IDisplaySurface surface = GAMA.getGui().getFrontmostDisplaySurface();
			if (surface != null) return surface.getData().getCameraPos().yNegated();
			return null;
		}
		IGraphicsScope scope = (IGraphicsScope) sc;
		final IGraphics g = scope.getGraphics();
		if (g.is2D()) return null;
		return ((IGraphics.ThreeD) g).getCameraPos().yNegated();

	}

	@Override
	public boolean isConst() { return false; }

	@Override
	public boolean isAllowedInParameters() { return false; }

}
