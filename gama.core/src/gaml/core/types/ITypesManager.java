/*******************************************************************************************************
 *
 * ITypesManager.java, in msi.gama.core, is part of the source code of the GAMA modeling and simulation platform
 * (v.1.9.0).
 *
 * (c) 2007-2022 UMI 209 UMMISCO IRD/SU & Partners (IRIT, MIAT, TLU, CTU)
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gaml.core.types;

import java.util.Set;

import gama.annotations.common.interfaces.IDisposable;
import gama.core.metamodel.agent.IAgent;
import gaml.core.descriptions.ModelDescription;
import gaml.core.descriptions.SpeciesDescription;

/**
 * The Interface ITypesManager.
 */
public interface ITypesManager extends IDisposable {

	/**
	 * Alias.
	 *
	 * @param existingTypeName
	 *            the existing type name
	 * @param otherTypeName
	 *            the other type name
	 */
	void alias(String existingTypeName, String otherTypeName);

	/**
	 * Contains type.
	 *
	 * @param s
	 *            the s
	 * @return true, if successful
	 */
	boolean containsType(String s);

	/**
	 * Gets the.
	 *
	 * @param type
	 *            the type
	 * @return the i type
	 */
	IType<?> get(String type);

	/**
	 * Adds the species type.
	 *
	 * @param species
	 *            the species
	 * @return the i type<? extends I agent>
	 */
	IType<? extends IAgent> addSpeciesType(SpeciesDescription species);

	/**
	 * Inits the.
	 *
	 * @param model
	 *            the model
	 */
	void init(ModelDescription model);

	/**
	 * Sets the parent.
	 *
	 * @param typesManager
	 *            the new parent
	 */
	void setParent(ITypesManager typesManager);

	/**
	 * Inits the type.
	 *
	 * @param <Support>
	 *            the generic type
	 * @param keyword
	 *            the keyword
	 * @param typeInstance
	 *            the type instance
	 * @param id
	 *            the id
	 * @param varKind
	 *            the var kind
	 * @param support
	 *            the support
	 * @param pluginName
	 *            the plugin name
	 * @return the i type
	 */
	<Support> IType<Support> initType(String keyword, IType<Support> typeInstance, int id, int varKind,
			Class<Support> support, String pluginName);

	/**
	 * Gets the all types.
	 *
	 * @return the all types
	 */
	Set<IType<?>> getAllTypes();

}