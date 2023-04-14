/*******************************************************************************************************
 *
 * SimulationPopulation.java, in msi.gama.core, is part of the source code of the GAMA modeling and simulation platform
 * (v.1.9.0).
 *
 * (c) 2007-2023 UMI 209 UMMISCO IRD/SU & Partners (IRIT, MIAT, TLU, CTU)
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gama.core.kernel.simulation;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import gama.annotations.common.interfaces.IKeyword;
import gama.core.common.interfaces.IScopedStepable;
import gama.core.kernel.experiment.ExperimentAgent;
import gama.core.kernel.experiment.ExperimentPlan;
import gama.core.metamodel.agent.IAgent;
import gama.core.metamodel.agent.SavedAgent;
import gama.core.metamodel.population.GamaPopulation;
import gama.core.metamodel.shape.GamaPoint;
import gama.core.metamodel.topology.continuous.AmorphousTopology;
import gama.core.runtime.IScope;
import gama.core.runtime.concurrent.GamaExecutorService;
import gama.core.runtime.concurrent.SimulationRunner;
import gama.core.runtime.concurrent.GamaExecutorService.Caller;
import gama.core.runtime.exceptions.GamaRuntimeException;
import gama.core.util.GamaListFactory;
import gama.core.util.IList;
import gaml.core.compilation.IAgentConstructor;
import gaml.core.species.ISpecies;
import gaml.core.statements.RemoteSequence;
import gaml.core.variables.IVariable;

/**
 * The Class SimulationPopulation.
 */
@SuppressWarnings ({ "rawtypes", "unchecked" })
public class SimulationPopulation extends GamaPopulation<SimulationAgent> {

	/** The current simulation. */
	private SimulationAgent currentSimulation;

	/** The runner. */
	private final SimulationRunner runner;

	/**
	 * Instantiates a new simulation population.
	 *
	 * @param agent
	 *            the agent
	 * @param species
	 *            the species
	 */
	public SimulationPopulation(final ExperimentAgent agent, final ISpecies species) {
		super(agent, species);
		runner = SimulationRunner.of(this);
	}

	/**
	 * Gets the max number of concurrent simulations.
	 *
	 * @return the max number of concurrent simulations
	 */
	public int getMaxNumberOfConcurrentSimulations() {
		return GamaExecutorService.getParallelism(getHost().getScope(), getHost().getSpecies().getConcurrency(),
				Caller.SIMULATION);
	}

	/**
	 * Method fireAgentRemoved()
	 *
	 * @see gama.core.metamodel.population.GamaPopulation#fireAgentRemoved(gama.core.metamodel.agent.IAgent)
	 */
	@Override
	protected void fireAgentRemoved(final IScope scope, final IAgent agent) {
		super.fireAgentRemoved(scope, agent);
		runner.remove((SimulationAgent) agent);
	}

	@Override
	public void initializeFor(final IScope scope) {
		super.initializeFor(scope);
		this.currentAgentIndex = 0;
	}

	@Override
	public void dispose() {
		runner.dispose();
		currentSimulation = null;
		super.dispose();
	}

	@Override
	public Iterable<SimulationAgent> iterable(final IScope scope) {
		return (Iterable<SimulationAgent>) getAgents(scope);
	}

	@Override
	public IList<SimulationAgent> createAgents(final IScope scope, final int number,
			final List<? extends Map<String, Object>> initialValues, final boolean isRestored,
			final boolean toBeScheduled, final RemoteSequence sequence) throws GamaRuntimeException {
		final IList<SimulationAgent> result = GamaListFactory.create(SimulationAgent.class);

		for (int i = 0; i < number; i++) {
			scope.getGui().getStatus().waitStatus(scope, "Initializing simulation");
			// Model do not only rely on SimulationAgent
			final IAgentConstructor<SimulationAgent> constr = species.getDescription().getAgentConstructor();

			currentSimulation = constr.createOneAgent(this, currentAgentIndex++);
			currentSimulation.setScheduled(toBeScheduled);
			currentSimulation.setName("Simulation " + currentSimulation.getIndex());
			add(currentSimulation);
			currentSimulation.setOutputs(((ExperimentPlan) host.getSpecies()).getOriginalSimulationOutputs());
			if (scope.interrupted()) return null;
			initSimulation(scope, currentSimulation, initialValues, i, isRestored, toBeScheduled, sequence);
			if (toBeScheduled) { runner.add(currentSimulation); }
			result.add(currentSimulation);
		}
		// Linked to Issue #2430. Should not return this, but the newly created simulations
		// return this;
		return result;
	}

	/**
	 * Inits the simulation.
	 *
	 * @param scope
	 *            the scope
	 * @param sim
	 *            the sim
	 * @param initialValues
	 *            the initial values
	 * @param index
	 *            the index
	 * @param isRestored
	 *            the is restored
	 * @param toBeScheduled
	 *            the to be scheduled
	 * @param sequence
	 *            the sequence
	 */
	private void initSimulation(final IScope scope, final SimulationAgent sim,
			final List<? extends Map<String, Object>> initialValues, final int index, final boolean isRestored,
			final boolean toBeScheduled, final RemoteSequence sequence) {
		scope.getGui().getStatus().waitStatus(scope, "Instantiating agents");
		// if (toBeScheduled) { sim.prepareGuiForSimulation(scope); }

		final Map<String, Object> firstInitValues = initialValues.isEmpty() ? null : initialValues.get(index);
		final Object firstValue =
				firstInitValues != null && !firstInitValues.isEmpty() ? firstInitValues.values().toArray()[0] : null;
		if (firstValue instanceof SavedAgent sa) {
			sim.updateWith(scope, sa);
		} else {
			createVariablesFor(sim.getScope(), Collections.singletonList(sim), Arrays.asList(firstInitValues));
		}

		if (toBeScheduled) {
			if (isRestored || firstValue instanceof SavedAgent) {
				sim.initOutputs();
			} else {
				sim.schedule(scope);
				if (sequence != null && !sequence.isEmpty()) { scope.execute(sequence, sim, null); }
			}
		}
	}

	@Override
	protected boolean allowVarInitToBeOverridenByExternalInit(final IVariable theVar) {
		return switch (theVar.getName()) {
			case IKeyword.SEED, IKeyword.RNG -> !theVar.hasFacet(IKeyword.INIT);
			default -> true;
		};
	}

	@Override
	public ExperimentAgent getHost() { return (ExperimentAgent) super.getHost(); }

	@Override
	public SimulationAgent getAgent(final IScope scope, final GamaPoint value) {
		return get(null, 0);
	}

	/**
	 * Sets the host.
	 *
	 * @param agent
	 *            the new host
	 */
	public void setHost(final ExperimentAgent agent) { host = agent; }

	@Override
	public void computeTopology(final IScope scope) throws GamaRuntimeException {
		// Temporary topology set before the world gets a shape
		topology = new AmorphousTopology();
	}

	@Override
	protected boolean stepAgents(final IScope scope) {
		runner.step();
		return true;
	}

	/**
	 * This method can be called by the batch experiments to temporarily stop (unschedule) a simulation
	 *
	 * @param sim
	 */
	public void unscheduleSimulation(final SimulationAgent sim) {
		runner.remove(sim);
	}

	/**
	 * Gets the number of active stepables.
	 *
	 * @return the number of active stepables
	 */
	public Set<IScopedStepable> getActiveStepables() { return runner.getStepable(); }

	/**
	 * Gets the number of active threads.
	 *
	 * @return the number of active threads
	 */
	public int getNumberOfActiveThreads() { return runner.getActiveThreads(); }

	/**
	 * @return
	 */
	public boolean hasScheduledSimulations() {
		return runner.hasSimulations();
	}

	/**
	 * Last simulation created.
	 *
	 * @return the simulation agent
	 */
	public SimulationAgent lastSimulationCreated() {
		return currentSimulation;
	}

	/**
	 * Gets the simulation at index.
	 *
	 * @param i
	 *            the i
	 * @return the simulation at index
	 */
	public SimulationAgent getSimulationAtIndex(final int i) {
		if (i >= size()) return null;
		return get(i);

	}

}