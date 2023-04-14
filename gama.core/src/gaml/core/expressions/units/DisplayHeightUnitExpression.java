/*******************************************************************************************************
 *
 * DisplayHeightUnitExpression.java, in msi.gama.core, is part of the source code of the GAMA modeling and simulation
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
import gama.core.runtime.GAMA;
import gama.core.runtime.IScope;
import gama.core.runtime.IScope.IGraphicsScope;
import gaml.core.types.Types;

/**
 * The Class DisplayHeightUnitExpression.
 */
public class DisplayHeightUnitExpression extends UnitConstantExpression {

	/**
	 * Instantiates a new display height unit expression.
	 *
	 * @param doc
	 *            the doc
	 */
	public DisplayHeightUnitExpression(final String doc) {
		super(0.0, Types.FLOAT, "display_height", doc, null);
	}

	@Override
	public Double _value(final IScope sc) {
		if (sc == null || !sc.isGraphics()) {
			IDisplaySurface surface = GAMA.getGui().getFrontmostDisplaySurface();
			if (surface != null) return surface.getDisplayHeight();
			return 0d;
		}
		IGraphicsScope scope = (IGraphicsScope) sc;
		final IGraphics g = scope.getGraphics();
		if (g == null) return 0d;
		return (double) g.getDisplayHeight();
	}

	@Override
	public boolean isConst() {
		return false;

	}

	@Override
	public boolean isContextIndependant() { return false; }

	@Override
	public boolean isAllowedInParameters() { return false; }

}
