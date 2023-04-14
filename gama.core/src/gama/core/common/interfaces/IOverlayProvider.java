/*******************************************************************************************************
 *
 * IOverlayProvider.java, in msi.gama.core, is part of the source code of the
 * GAMA modeling and simulation platform (v.1.9.0).
 *
 * (c) 2007-2022 UMI 209 UMMISCO IRD/SU & Partners (IRIT, MIAT, TLU, CTU)
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package gama.core.common.interfaces;

import gama.annotations.common.interfaces.IUpdaterTarget;
import gama.core.outputs.layers.OverlayStatement.OverlayInfo;

/**
 * Class IOverlay.
 *
 * @author drogoul
 * @since 9 mars 2014
 *
 */
public interface IOverlayProvider<C extends OverlayInfo> {

	/**
	 * Sets the target.
	 *
	 * @param overlay the overlay
	 * @param surface the surface
	 */
	public void setTarget(IUpdaterTarget<C> overlay, IDisplaySurface surface);
}
