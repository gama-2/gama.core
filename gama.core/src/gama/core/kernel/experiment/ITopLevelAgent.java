/*******************************************************************************************************
 *
 * ITopLevelAgent.java, in msi.gama.core, is part of the source code of the
 * GAMA modeling and simulation platform (v.1.9.0).
 *
 * (c) 2007-2022 UMI 209 UMMISCO IRD/SU & Partners (IRIT, MIAT, TLU, CTU)
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package gama.core.kernel.experiment;

import gama.core.common.interfaces.IScopedStepable;
import gama.core.common.util.RandomUtils;
import gama.core.kernel.simulation.SimulationAgent;
import gama.core.kernel.simulation.SimulationClock;
import gama.core.metamodel.agent.IMacroAgent;
import gama.core.outputs.IOutputManager;
import gama.core.util.GamaColor;
import gaml.core.statements.IExecutable;

/**
 * Class ITopLevelAgent Addition (Aug 2021): explicit inheritance of IScopedStepable
 *
 * @author drogoul
 * @since 27 janv. 2016
 *
 */
public interface ITopLevelAgent extends IMacroAgent, IScopedStepable {

	/**
	 * Gets the clock.
	 *
	 * @return the clock
	 */
	SimulationClock getClock();

	/**
	 * Gets the color.
	 *
	 * @return the color
	 */
	GamaColor getColor();

	/**
	 * Gets the random generator.
	 *
	 * @return the random generator
	 */
	RandomUtils getRandomGenerator();

	/**
	 * Gets the output manager.
	 *
	 * @return the output manager
	 */
	IOutputManager getOutputManager();

	/**
	 * Post end action.
	 *
	 * @param executable the executable
	 */
	void postEndAction(IExecutable executable);

	/**
	 * Post dispose action.
	 *
	 * @param executable the executable
	 */
	void postDisposeAction(IExecutable executable);

	/**
	 * Post one shot action.
	 *
	 * @param executable the executable
	 */
	void postOneShotAction(IExecutable executable);

	/**
	 * Execute action.
	 *
	 * @param executable the executable
	 */
	void executeAction(IExecutable executable);

	/**
	 * Checks if is on user hold.
	 *
	 * @return true, if is on user hold
	 */
	boolean isOnUserHold();

	/**
	 * Sets the on user hold.
	 *
	 * @param state the new on user hold
	 */
	void setOnUserHold(boolean state);

	/**
	 * Gets the simulation.
	 *
	 * @return the simulation
	 */
	SimulationAgent getSimulation();

	/**
	 * Gets the experiment.
	 *
	 * @return the experiment
	 */
	IExperimentAgent getExperiment();

}
