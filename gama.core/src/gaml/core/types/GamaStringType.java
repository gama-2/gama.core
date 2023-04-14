/*******************************************************************************************************
 *
 * GamaStringType.java, in msi.gama.core, is part of the source code of the
 * GAMA modeling and simulation platform (v.1.9.0).
 *
 * (c) 2007-2022 UMI 209 UMMISCO IRD/SU & Partners (IRIT, MIAT, TLU, CTU)
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package gaml.core.types;

import gama.annotations.common.interfaces.IKeyword;
import gama.annotations.common.interfaces.INamed;
import gama.annotations.precompiler.IConcept;
import gama.annotations.precompiler.ISymbolKind;
import gama.annotations.precompiler.GamlAnnotations.doc;
import gama.annotations.precompiler.GamlAnnotations.type;
import gama.core.common.interfaces.IValue;
import gama.core.runtime.IScope;
import gama.core.runtime.exceptions.GamaRuntimeException;

/**
 *
 *
 *
 * Written by drogoul Modified on 3 juin 2010
 *
 * @todo Description
 *
 */
@SuppressWarnings ("unchecked")
@type (
		name = IKeyword.STRING,
		id = IType.STRING,
		wraps = { String.class },
		kind = ISymbolKind.Variable.REGULAR,
		concept = { IConcept.TYPE, IConcept.STRING },
		doc = @doc ("Strings are ordered list of characters"))
public class GamaStringType extends GamaType<String> {

	@Override
	@doc ("Transforms the parameter into a string")
	public String cast(final IScope scope, final Object obj, final Object param, final boolean copy)
			throws GamaRuntimeException {
		return staticCast(scope, obj, copy);
	}

	/**
	 * Static cast.
	 *
	 * @param scope the scope
	 * @param obj the obj
	 * @param copy the copy
	 * @return the string
	 * @throws GamaRuntimeException the gama runtime exception
	 */
	public static String staticCast(final IScope scope, final Object obj, final boolean copy)
			throws GamaRuntimeException {
		if (obj == null) return null;
		if (obj instanceof IValue) return ((IValue) obj).stringValue(scope);
		if (obj instanceof INamed) return ((INamed) obj).getName();
		return obj.toString();
	}

	@Override
	public String getDefault() { return null; }

	@Override
	public boolean canCastToConst() {
		return true;
	}

	@Override
	public boolean isDrawable() { return true; }

}
