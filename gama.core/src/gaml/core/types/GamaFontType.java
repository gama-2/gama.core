/*******************************************************************************************************
 *
 * GamaFontType.java, in msi.gama.core, is part of the source code of the
 * GAMA modeling and simulation platform (v.1.9.0).
 *
 * (c) 2007-2022 UMI 209 UMMISCO IRD/SU & Partners (IRIT, MIAT, TLU, CTU)
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package gaml.core.types;

import java.awt.Font;

import gama.annotations.common.interfaces.IKeyword;
import gama.annotations.precompiler.IConcept;
import gama.annotations.precompiler.ISymbolKind;
import gama.annotations.precompiler.GamlAnnotations.doc;
import gama.annotations.precompiler.GamlAnnotations.example;
import gama.annotations.precompiler.GamlAnnotations.type;
import gama.annotations.precompiler.GamlAnnotations.usage;
import gama.core.common.preferences.GamaPreferences;
import gama.core.common.preferences.Pref;
import gama.core.runtime.IScope;
import gama.core.runtime.exceptions.GamaRuntimeException;
import gama.core.util.GamaFont;

/**
 * Written by drogoul Modified on 1 ao�t 2010
 *
 * @todo Description
 *
 */
@SuppressWarnings ("unchecked")
@type (
		name = IKeyword.FONT,
		id = IType.FONT,
		wraps = { GamaFont.class },
		kind = ISymbolKind.Variable.REGULAR,
		doc = { @doc ("Represents font objects that can be passed directly as arguments to draw statements and text layers. A font is identified by its face name (e.g. 'Helvetica'), its size in points (e.g. 12) and its style (i.e., #bold, #italic, or an addition of the 2") },
		concept = { IConcept.TYPE, IConcept.TEXT, IConcept.DISPLAY })
public class GamaFontType extends GamaType<GamaFont> {

	/** The Constant DEFAULT_DISPLAY_FONT. */
	public static final Pref<GamaFont> DEFAULT_DISPLAY_FONT = GamaPreferences
			.create("pref_display_default_font", "Default font to use in 'draw'",
					() -> new GamaFont("Helvetica", Font.PLAIN, 12), IType.FONT, true)
			.in(GamaPreferences.Displays.NAME, GamaPreferences.Displays.DRAWING);

	@doc (
			value = "Cast any object as a font",
			usages = { @usage (
					value = "if the operand is a number, returns with the operand value as font size and the default display font style",
					examples = { @example ("font f <- font(12);") }),
					@usage (
							value = "if the operand is a string, returns a font with this font name",
							examples = { @example ("font f <- font(12);") }), })
	@Override
	public GamaFont cast(final IScope scope, final Object obj, final Object param, final boolean copy)
			throws GamaRuntimeException {
		return staticCast(scope, obj, copy);
	}

	/**
	 * Static cast.
	 *
	 * @param scope the scope
	 * @param obj the obj
	 * @param copy the copy
	 * @return the gama font
	 * @throws GamaRuntimeException the gama runtime exception
	 */
	public static GamaFont staticCast(final IScope scope, final Object obj, final boolean copy)
			throws GamaRuntimeException {
		if (obj instanceof Number) {
			final Number size = (Number) obj;
			final GamaFont font = DEFAULT_DISPLAY_FONT.getValue();
			return new GamaFont(font.getName(), font.getStyle(), size.intValue());
		}
		if (obj instanceof GamaFont) {
			if (copy) return new GamaFont((Font) obj);
			return (GamaFont) obj;
		}
		if (obj instanceof String) return new GamaFont(Font.decode((String) obj));
		return DEFAULT_DISPLAY_FONT.getValue();
	}

	@Override
	public GamaFont getDefault() { return DEFAULT_DISPLAY_FONT.getValue(); }

	@Override
	public boolean canCastToConst() {
		return true;
	}

}
