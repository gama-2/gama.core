/*******************************************************************************************************
 *
 * OverlayLayerData.java, in msi.gama.core, is part of the source code of the GAMA modeling and simulation platform
 * (v.1.9.0).
 *
 * (c) 2007-2022 UMI 209 UMMISCO IRD/SU & Partners (IRIT, MIAT, TLU, CTU)
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gama.core.outputs.layers;

import gama.annotations.common.interfaces.IKeyword;
import gama.core.common.interfaces.IGraphics;
import gama.core.runtime.exceptions.GamaRuntimeException;
import gaml.core.types.Types;

/**
 * The Class OverlayLayerData.
 */
public class OverlayLayerData extends FramedLayerData {

	/** The rounded. */
	final Attribute<Boolean> rounded;

	/** The computed. */
	boolean computed;

	/**
	 * Instantiates a new overlay layer data.
	 *
	 * @param def
	 *            the def
	 * @throws GamaRuntimeException
	 *             the gama runtime exception
	 */
	public OverlayLayerData(final ILayerStatement def) throws GamaRuntimeException {
		super(def);
		rounded = create(IKeyword.ROUNDED, Types.BOOL, true);
	}

	@Override
	public void computePixelsDimensions(final IGraphics g) {
		if (computed) return;
		super.computePixelsDimensions(g);
		computed = true;
	}

	/**
	 * Checks if is rounded.
	 *
	 * @return true, if is rounded
	 */
	public boolean isRounded() { return rounded.get(); }

}
