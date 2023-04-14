/*******************************************************************************************************
 *
 * PlatformFactory.java, in msi.gama.core, is part of the source code of the GAMA modeling and simulation platform
 * (v.1.9.0).
 *
 * (c) 2007-2022 UMI 209 UMMISCO IRD/SU & Partners (IRIT, MIAT, TLU, CTU)
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gaml.core.factories;

import java.util.Set;

import gaml.core.compilation.IAgentConstructor;
import gaml.core.descriptions.PlatformSpeciesDescription;
import gaml.core.descriptions.SpeciesDescription;
import gaml.core.statements.Facets;

//
// @factory (
/**
 * A factory for creating Platform objects.
 */
// handles = { PLATFORM })
public class PlatformFactory extends SpeciesFactory {

	@Override
	public SpeciesDescription createBuiltInSpeciesDescription(final String name, final Class clazz,
			final SpeciesDescription superDesc, final SpeciesDescription parent, final IAgentConstructor helper,
			final Set<String> skills, final Facets userSkills, final String plugin) {
		DescriptionFactory.addSpeciesNameAsType(name);
		return new PlatformSpeciesDescription(name, clazz, superDesc, parent, helper, skills, userSkills, plugin);
	}

}
