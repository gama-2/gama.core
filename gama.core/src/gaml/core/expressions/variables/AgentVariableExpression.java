/*******************************************************************************************************
 *
 * AgentVariableExpression.java, in msi.gama.core, is part of the source code of the GAMA modeling and simulation
 * platform (v.1.9.0).
 *
 * (c) 2007-2022 UMI 209 UMMISCO IRD/SU & Partners (IRIT, MIAT, TLU, CTU)
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gaml.core.expressions.variables;

import gama.annotations.precompiler.GamlProperties;
import gama.core.runtime.IScope;
import gama.core.runtime.exceptions.GamaRuntimeException;
import gama.core.util.ICollector;
import gaml.core.descriptions.IDescription;
import gaml.core.descriptions.IVarDescriptionUser;
import gaml.core.descriptions.SpeciesDescription;
import gaml.core.descriptions.VariableDescription;
import gaml.core.expressions.IExpression;
import gaml.core.expressions.IVarExpression;
import gaml.core.types.IType;

/**
 * The Class AgentVariableExpression.
 */
public class AgentVariableExpression extends VariableExpression implements IVarExpression.Agent {

	/**
	 * Instantiates a new agent variable expression.
	 *
	 * @param n
	 *            the n
	 * @param type
	 *            the type
	 * @param notModifiable
	 *            the not modifiable
	 * @param def
	 *            the def
	 */
	@SuppressWarnings ("rawtypes")
	public AgentVariableExpression(final String n, final IType type, final boolean notModifiable,
			final IDescription def) {
		super(n, type, notModifiable, def);
	}

	@Override
	public IExpression getOwner() {
		return new SelfExpression(this.getDefinitionDescription().getSpeciesContext().getGamlType());
	}

	@Override
	public Object _value(final IScope scope) throws GamaRuntimeException {
		return scope.getAgentVarValue(scope.getAgent(), getName());
	}

	@Override
	public void setVal(final IScope scope, final Object v, final boolean create) throws GamaRuntimeException {
		scope.setAgentVarValue(scope.getAgent(), getName(), v);
	}

	@Override
	public Doc getDocumentation() {
		final IDescription desc = getDefinitionDescription();
		if (desc == null) return new ConstantDoc("Type " + type.getTitle());
		Doc doc = new RegularDoc(new StringBuilder());
		final VariableDescription var = desc.getSpeciesContext().getAttribute(name);
		doc.append("Type ").append(type.getTitle()).append("<br/>");
		String builtInDoc = null;
		if (var != null) { builtInDoc = var.getBuiltInDoc(); }
		if (builtInDoc != null) { doc.append(builtInDoc).append("<br/>"); }
		doc.append(desc.isBuiltIn() ? "Built in " : builtInDoc == null ? "Defined in " : "Redefined in ")
				.append(desc.getTitle());
		return doc;
	}

	/**
	 * Method collectPlugins()
	 *
	 * @see gama.annotations.common.interfaces.IGamlDescription#collectPlugins(java.util.Set)
	 */
	@Override
	public void collectMetaInformation(final GamlProperties meta) {
		if (getDefinitionDescription().isBuiltIn()) { meta.put(GamlProperties.ATTRIBUTES, getName()); }
	}

	@Override
	public void collectUsedVarsOf(final SpeciesDescription species,
			final ICollector<IVarDescriptionUser> alreadyProcessed, final ICollector<VariableDescription> result) {
		if (alreadyProcessed.contains(this)) return;
		alreadyProcessed.add(this);
		final SpeciesDescription sd = this.getDefinitionDescription().getSpeciesContext();
		if (species.equals(sd) || species.hasParent(sd)) { result.add(sd.getAttribute(getName())); }
	}

	@Override
	public IExpression resolveAgainst(final IScope scope) {
		return this;
	}

}
