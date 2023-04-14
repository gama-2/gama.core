/*******************************************************************************************************
 *
 * GamaActionType.java, in msi.gama.core, is part of the source code of the GAMA modeling and simulation platform
 * (v.1.9.0).
 *
 * (c) 2007-2022 UMI 209 UMMISCO IRD/SU & Partners (IRIT, MIAT, TLU, CTU)
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gaml.core.types;

import gama.annotations.common.interfaces.IKeyword;
import gama.annotations.precompiler.IConcept;
import gama.annotations.precompiler.ISymbolKind;
import gama.annotations.precompiler.GamlAnnotations.doc;
import gama.annotations.precompiler.GamlAnnotations.type;
import gama.core.runtime.IScope;
import gama.core.runtime.exceptions.GamaRuntimeException;
import gaml.core.descriptions.IDescription;

/**
 * The Class GamaActionType.
 */
@type (
		name = IKeyword.ACTION,
		id = IType.ACTION,
		wraps = { IDescription.class },
		kind = ISymbolKind.Variable.REGULAR,
		doc = { @doc ("The type of the variables that denote an action or an aspect of a species") },
		concept = { IConcept.TYPE, IConcept.ACTION, IConcept.SPECIES })
public class GamaActionType extends GamaType<IDescription> {

	@Override
	public boolean canCastToConst() {
		return false;
	}

	@Override
	@doc ("Converts the argument into the reference to an action. If it is a string, its name is looked up in the current agent species")
	public IDescription cast(final IScope scope, final Object obj, final Object param, final boolean copy)
			throws GamaRuntimeException {
		if (obj == null) return null;
		if (obj instanceof IDescription) return (IDescription) obj;
		if (obj instanceof String name) {
			final IDescription action = scope.getAgent().getSpecies().getDescription().getAction(name);
			if (action != null) return action;
			return scope.getAgent().getSpecies().getDescription().getAspect(name);
		}
		return null;
	}

	@Override
	public IDescription getDefault() {
		
		return null;
	}

}
