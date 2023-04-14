/*******************************************************************************************************
 *
 * PlatformSpeciesDescription.java, in msi.gama.core, is part of the source code of the GAMA modeling and simulation
 * platform (v.1.9.0).
 *
 * (c) 2007-2022 UMI 209 UMMISCO IRD/SU & Partners (IRIT, MIAT, TLU, CTU)
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gaml.core.descriptions;

import static gaml.core.descriptions.VariableDescription.PREF_DEFINITIONS;
import static gaml.core.factories.DescriptionFactory.create;

import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;

import gama.annotations.common.interfaces.IKeyword;
import gama.core.common.preferences.GamaPreferences;
import gama.core.common.preferences.Pref;
import gama.core.kernel.root.PlatformAgent;
import gaml.core.compilation.IAgentConstructor;
import gaml.core.compilation.IGamaHelper;
import gaml.core.expressions.IExpression;
import gaml.core.factories.DescriptionFactory;
import gaml.core.statements.Facets;

/**
 * The Class PlatformSpeciesDescription.
 */
public class PlatformSpeciesDescription extends SpeciesDescription {

	/** The alternate var provider. */
	IVarDescriptionProvider alternateVarProvider;

	/**
	 * Instantiates a new platform species description.
	 *
	 * @param keyword
	 *            the keyword
	 * @param clazz
	 *            the clazz
	 * @param macroDesc
	 *            the macro desc
	 * @param parent
	 *            the parent
	 * @param cp
	 *            the cp
	 * @param source
	 *            the source
	 * @param facets
	 *            the facets
	 */
	public PlatformSpeciesDescription(final String keyword, final Class<?> clazz, final SpeciesDescription macroDesc,
			final SpeciesDescription parent, final Iterable<? extends IDescription> cp, final EObject source,
			final Facets facets) {
		super(keyword, clazz, macroDesc, parent, cp, source, facets);
	}

	/**
	 * Instantiates a new platform species description.
	 *
	 * @param name
	 *            the name
	 * @param clazz
	 *            the clazz
	 * @param superDesc
	 *            the super desc
	 * @param parent
	 *            the parent
	 * @param helper
	 *            the helper
	 * @param skills2
	 *            the skills 2
	 * @param ff
	 *            the ff
	 * @param plugin
	 *            the plugin
	 */
	public PlatformSpeciesDescription(final String name, final Class<?> clazz, final SpeciesDescription superDesc,
			final SpeciesDescription parent, final IAgentConstructor<?> helper, final Set<String> skills2,
			final Facets ff, final String plugin) {
		super(name, clazz, superDesc, parent, helper, skills2, ff, plugin);
	}

	@Override
	public void attachAlternateVarDescriptionProvider(final IVarDescriptionProvider vp) {
		alternateVarProvider = vp;
	}

	@Override
	public void copyJavaAdditions() {
		super.copyJavaAdditions();
		for (final Map.Entry<String, Pref<?>> pref : GamaPreferences.getAll().entrySet()) {
			addPref(pref.getKey(), pref.getValue());
		}
	}

	/**
	 * Adds the pref.
	 *
	 * @param key
	 *            the key
	 * @param entry
	 *            the entry
	 */
	public void addPref(final String key, final Pref<?> entry) {
		final VariableDescription var = (VariableDescription) create(entry.getType().toString(), this, NAME, key);
		PREF_DEFINITIONS.put(key, entry.getTitle());
		final IGamaHelper<?> get = (scope, agent, skill, values) -> GamaPreferences.get(key).getValue();
		final IGamaHelper<?> set = (scope, agent, skill, val) -> {
			if (agent instanceof PlatformAgent gama) {
				// Should be in any case
				gama.savePrefToRestore(key, GamaPreferences.get(key).getValue());
			}
			GamaPreferences.get(key).setValue(scope, val);
			return this;
		};
		final IGamaHelper<?> init = (scope, agent, skill, values) -> GamaPreferences.get(key).getValue();
		var.addHelpers(get, init, set);
		addChild(var);
	}

	@Override
	public ValidationContext getValidationContext() { return ValidationContext.NULL; }

	@Override
	public IVarDescriptionProvider getDescriptionDeclaringVar(final String name) {
		IVarDescriptionProvider provider = super.getDescriptionDeclaringVar(name);
		if (provider == null && alternateVarProvider != null && alternateVarProvider.hasAttribute(name)) {
			provider = alternateVarProvider;
		}
		return provider;
	}

	/**
	 * Gets the fake pref expression.
	 *
	 * @param key
	 *            the key
	 * @return the fake pref expression
	 */
	public IExpression getFakePrefExpression(final String key) {
		final VariableDescription var = (VariableDescription) DescriptionFactory.create(IKeyword.UNKNOWN,
				PlatformSpeciesDescription.this, NAME, key);
		PREF_DEFINITIONS.put(key, "This preference is not available in the current configuration of GAMA");
		return var.getVarExpr(true);
	}

	@Override
	protected boolean verifyAttributeCycles() {
		return true;
	}

}
