/*******************************************************************************************************
 *
 * GraphicLayer.java, in msi.gama.core, is part of the source code of the GAMA modeling and simulation platform
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
import gama.core.metamodel.agent.IAgent;
import gama.core.runtime.IScope.IGraphicsScope;
import gama.core.runtime.exceptions.GamaRuntimeException;

/**
 * The Class GraphicLayer.
 */
public class GraphicLayer extends AbstractLayer {

	/**
	 * Instantiates a new graphic layer.
	 *
	 * @param layer
	 *            the layer
	 */
	public GraphicLayer(final ILayerStatement layer) {
		super(layer);
	}

	@Override
	protected FramedLayerData createData() {
		return new FramedLayerData(definition);
	}

	@Override
	protected void privateDraw(final IGraphicsScope scope, final IGraphics g) throws GamaRuntimeException {
		final IAgent agent = scope.getAgent();
		scope.execute(((GraphicLayerStatement) definition).getAspect(), agent, null);
	}

	@Override
	public String getType() { return IKeyword.GRAPHICS; }

	// Just a trial to make sure that graphics + chart produce not proportional results.
	@Override
	public boolean stayProportional() {
		return false;
	}
}
