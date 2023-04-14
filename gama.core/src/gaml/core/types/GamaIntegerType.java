/*******************************************************************************************************
 *
 * GamaIntegerType.java, in msi.gama.core, is part of the source code of the GAMA modeling and simulation platform
 * (v.1.9.0).
 *
 * (c) 2007-2022 UMI 209 UMMISCO IRD/SU & Partners (IRIT, MIAT, TLU, CTU)
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gaml.core.types;

import java.awt.Color;

import gama.annotations.common.interfaces.IKeyword;
import gama.annotations.precompiler.IConcept;
import gama.annotations.precompiler.ISymbolKind;
import gama.annotations.precompiler.GamlAnnotations.doc;
import gama.annotations.precompiler.GamlAnnotations.type;
import gama.core.metamodel.agent.IAgent;
import gama.core.runtime.IScope;
import gama.core.runtime.exceptions.GamaRuntimeException;
import gama.core.util.GamaDate;
import gama.core.util.GamaFont;
import gaml.core.descriptions.IDescription;

/**
 * Written by drogoul Modified on 1 ao�t 2010
 *
 * @todo Description
 *
 */
@type (
		name = IKeyword.INT,
		id = IType.INT,
		wraps = { Integer.class, int.class, Long.class },
		kind = ISymbolKind.Variable.NUMBER,
		concept = { IConcept.TYPE },
		doc = @doc ("Type of integer numbers"))
public class GamaIntegerType extends GamaType<Integer> {

	@Override
	@doc ("Returns the parameter casted to an int value. If it is an integer, returns it. A float, returns its integer value. "
			+ "A color, its rgb value. An agent, its index. A string, attempts to parse it (with a radix of 16 if it begins with '#'). "
			+ "A bool, return 1 if true. A font, return its size.  Otherwise return 0")
	public Integer cast(final IScope scope, final Object obj, final Object param, final boolean copy)
			throws GamaRuntimeException {
		return staticCast(scope, obj, param, copy);
	}

	/**
	 * Static cast.
	 *
	 * @param scope
	 *            the scope
	 * @param obj
	 *            the obj
	 * @param param
	 *            the param
	 * @param copy
	 *            the copy
	 * @return the integer
	 */
	public static Integer staticCast(final IScope scope, final Object obj, final Object param, final boolean copy) {
		if (obj instanceof Integer) return (Integer) obj;
		if (obj instanceof Number) return ((Number) obj).intValue();
		if (obj instanceof Color) return ((Color) obj).getRGB();
		if (obj instanceof IAgent) return ((IAgent) obj).getIndex();
		if (obj instanceof String) {
			String n = obj.toString();
			// removing whitespaces
			n = n.replaceAll("\\p{Zs}", "");
			try {
				// If the string contains an hexadecimal number, parse it with a
				// radix of 16.
				if (n.startsWith("#")) return Integer.parseInt(n.substring(1), 16);
				// Otherwise use by default a "natural" radix of 10 (can be
				// bypassed with the
				// as_int operator, which will put the radix in the param
				// argument)
				int radix = 10;
				if (param instanceof Integer) { radix = (Integer) param; }
				return Integer.parseInt(n, radix);
			} catch (final NumberFormatException e) {
				// for ( Character c : n.toCharArray() ) {
				// System.out.printf("U+%04x ", (int) c);
				// }
				// throw GamaRuntimeException.create(e);
				// Addresses Issue 846 by providing a way to continue the
				// casting into an int
				Double d = 0d;
				try {
					d = Double.parseDouble(n);
				} catch (final NumberFormatException e1) {
					return 0;
				}
				return d.intValue();
			}
		}
		if (obj instanceof Boolean) return (Boolean) obj ? 1 : 0;
		if (obj instanceof GamaFont) return ((GamaFont) obj).getSize();
		if (obj instanceof GamaDate) return ((GamaDate) obj).intValue(scope);
		return 0;
	}

	@Override
	public Integer getDefault() { return 0; }

	@Override
	public boolean isTranslatableInto(final IType<?> type) {
		return type.isNumber() || type == Types.NO_TYPE;
	}

	@Override
	public IType<?> coerce(final IType<?> type, final IDescription context) {
		if (type == this) return null;
		return this;
	}

	/**
	 * Generics information removed here to allow returning IType<Double>
	 */
	@SuppressWarnings ({ "unchecked", "rawtypes" })
	@Override
	public IType findCommonSupertypeWith(final IType<?> type) {
		return type == this ? this : type.id() == IType.FLOAT ? type : Types.NO_TYPE;
	}

	@Override
	public boolean canCastToConst() {
		return true;
	}

	@Override
	public boolean isNumber() { return true; }

}
