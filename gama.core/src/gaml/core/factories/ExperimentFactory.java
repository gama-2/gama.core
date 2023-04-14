/*******************************************************************************************************
 *
 * ExperimentFactory.java, in msi.gama.core, is part of the source code of the GAMA modeling and simulation platform
 * (v.1.9.0).
 *
 * (c) 2007-2022 UMI 209 UMMISCO IRD/SU & Partners (IRIT, MIAT, TLU, CTU)
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gaml.core.factories;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;

import gaml.core.compilation.IAgentConstructor;
import gaml.core.descriptions.ExperimentDescription;
import gaml.core.descriptions.IDescription;
import gaml.core.descriptions.SpeciesDescription;
import gaml.core.descriptions.SymbolProto;
import gaml.core.statements.Facets;

/**
 * The Class EnvironmentFactory.
 *
 * @author drogoul
 */
// @factory (
// handles = { ISymbolKind.EXPERIMENT })
public class ExperimentFactory extends SpeciesFactory {

	@Override
	public ExperimentDescription createBuiltInSpeciesDescription(final String name, final Class clazz,
			final SpeciesDescription superDesc, final SpeciesDescription parent, final IAgentConstructor helper,
			final Set<String> skills, final Facets userSkills, final String plugin) {
		DescriptionFactory.addSpeciesNameAsType(name);
		return new ExperimentDescription(name, clazz, superDesc, parent, helper, skills, userSkills, plugin);
	}

	@Override
	protected ExperimentDescription buildDescription(final String keyword, final Facets facets, final EObject element,
			final Iterable<IDescription> children, final IDescription sd, final SymbolProto proto) {
		return new ExperimentDescription(keyword, (SpeciesDescription) sd, children, element, facets);
	}

}
