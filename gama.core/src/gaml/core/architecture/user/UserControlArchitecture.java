/*******************************************************************************************************
 *
 * UserControlArchitecture.java, in msi.gama.core, is part of the source code of the
 * GAMA modeling and simulation platform (v.1.9.0).
 *
 * (c) 2007-2022 UMI 209 UMMISCO IRD/SU & Partners (IRIT, MIAT, TLU, CTU)
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package gaml.core.architecture.user;

import java.util.ArrayList;

import gama.annotations.common.interfaces.IKeyword;
import gama.annotations.precompiler.GamlAnnotations.doc;
import gama.annotations.precompiler.GamlAnnotations.getter;
import gama.annotations.precompiler.GamlAnnotations.setter;
import gama.annotations.precompiler.GamlAnnotations.variable;
import gama.annotations.precompiler.GamlAnnotations.vars;
import gama.core.metamodel.agent.IAgent;
import gama.core.runtime.IScope;
import gama.core.runtime.exceptions.GamaRuntimeException;
import gaml.core.architecture.finite_state_machine.FsmArchitecture;
import gaml.core.architecture.finite_state_machine.FsmStateStatement;
import gaml.core.species.ISpecies;
import gaml.core.types.IType;

/**
 * The Class UserControlArchitecture.
 */
@vars (@variable (
		name = IKeyword.USER_CONTROLLED,
		init = IKeyword.TRUE,
		type = IType.BOOL,
		doc = @doc ("Setting this attribute to false allows to deactivate the user control temporarily")))
public abstract class UserControlArchitecture extends FsmArchitecture {

	/** The init panel. */
	UserInitPanelStatement initPanel;

	@Override
	public void verifyBehaviors(final ISpecies context) {
		super.verifyBehaviors(context);
		if (initialState == null && states.size() == 1) {
			initialState = new ArrayList<FsmStateStatement>(states.values()).get(0);
			context.getVar(IKeyword.STATE).setValue(null, initialState.getName());
		}
		for (final FsmStateStatement s : states.values()) {
			if (s instanceof UserInitPanelStatement) {
				initPanel = (UserInitPanelStatement) s;
			}
		}
	}

	/**
	 * Checks if is user controlled.
	 *
	 * @param agent the agent
	 * @return the boolean
	 */
	@getter (IKeyword.USER_CONTROLLED)
	public Boolean isUserControlled(final IAgent agent) {
		return (Boolean) agent.getAttribute(IKeyword.USER_CONTROLLED);
	}

	/**
	 * Sets the user controlled.
	 *
	 * @param agent the agent
	 * @param b the b
	 */
	@setter (IKeyword.USER_CONTROLLED)
	public void setUserControlled(final IAgent agent, final Boolean b) {
		agent.setAttribute(IKeyword.USER_CONTROLLED, b);
	}

	@Override
	protected Object executeCurrentState(final IScope scope) throws GamaRuntimeException {
		final IAgent agent = getCurrentAgent(scope);
		if (agent.dead() || !isUserControlled(agent)) { return null; }
		return super.executeCurrentState(scope);
	}

}
