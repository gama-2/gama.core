/*******************************************************************************************************
 *
 * DefaultPopulationFactory.java, in msi.gama.core, is part of the source code of the GAMA modeling and simulation
 * platform (v.1.9.0).
 *
 * (c) 2007-2023 UMI 209 UMMISCO IRD/SU & Partners (IRIT, MIAT, TLU, CTU)
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gama.core.metamodel.population;

import gama.core.metamodel.agent.IAgent;
import gama.core.metamodel.agent.IMacroAgent;
import gama.core.metamodel.topology.ITopology;
import gama.core.metamodel.topology.grid.GamaSpatialMatrix;
import gama.core.runtime.IScope;
import gaml.core.species.ISpecies;

/**
 * A factory for creating DefaultPopulation objects.
 */
public class DefaultPopulationFactory implements IPopulationFactory {

	@Override
	public <E extends IAgent> IPopulation<E> createRegularPopulation(final IScope scope, final IMacroAgent host,
			final ISpecies species) {
		return new GamaPopulation<>(host, species);
	}

	@Override
	public <E extends IAgent> IPopulation<E> createGridPopulation(final IScope scope, final IMacroAgent host,
			final ISpecies species) {
		final ITopology t = GamaPopulation.buildGridTopology(scope, species, host);
		final GamaSpatialMatrix m = (GamaSpatialMatrix) t.getPlaces();
		return m.new GridPopulation<>(t, host, species);
	}

}
