/*******************************************************************************************************
 *
 * GamaListType.java, in msi.gama.core, is part of the source code of the
 * GAMA modeling and simulation platform (v.1.9.0).
 *
 * (c) 2007-2022 UMI 209 UMMISCO IRD/SU & Partners (IRIT, MIAT, TLU, CTU)
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package gaml.core.types;

import java.awt.Color;
import java.util.Collection;

import gama.annotations.common.interfaces.IKeyword;
import gama.annotations.precompiler.IConcept;
import gama.annotations.precompiler.ISymbolKind;
import gama.annotations.precompiler.GamlAnnotations.doc;
import gama.annotations.precompiler.GamlAnnotations.type;
import gama.core.common.util.StringUtils;
import gama.core.metamodel.population.IPopulation;
import gama.core.metamodel.shape.GamaPoint;
import gama.core.runtime.IScope;
import gama.core.runtime.exceptions.GamaRuntimeException;
import gama.core.util.GamaDate;
import gama.core.util.GamaListFactory;
import gama.core.util.IContainer;
import gama.core.util.IList;
import gaml.core.expressions.IExpression;

/**
 * The Class GamaListType.
 */
@type (
		name = IKeyword.LIST,
		id = IType.LIST,
		wraps = { IList.class },
		kind = ISymbolKind.Variable.CONTAINER,
		concept = { IConcept.TYPE, IConcept.CONTAINER, IConcept.LIST },
		doc = @doc ("Ordered collection of values or agents"))
@SuppressWarnings ({ "unchecked", "rawtypes" })
public class GamaListType extends GamaContainerType<IList> {

	@Override
	public IList cast(final IScope scope, final Object obj, final Object param, final IType keyType,
			final IType contentsType, final boolean copy) throws GamaRuntimeException {
		return staticCast(scope, obj, contentsType, copy);
	}

	/**
	 * Static cast.
	 *
	 * @param scope the scope
	 * @param obj the obj
	 * @param ct the ct
	 * @param copy the copy
	 * @return the i list
	 * @throws GamaRuntimeException the gama runtime exception
	 */
	public static IList staticCast(final IScope scope, final Object obj, final IType ct, final boolean copy)
			throws GamaRuntimeException {
		final IType contentsType = ct == null ? Types.NO_TYPE : ct;
		// BG fix issue ##2338
		// if (obj == null) { return GamaListFactory.create(Types.NO_TYPE, 0); }
		if (obj == null) { return GamaListFactory.create(ct, 0); }

		if (obj instanceof GamaDate) { return ((GamaDate) obj).listValue(scope, ct); }
		if (obj instanceof IContainer) {
			if (obj instanceof IPopulation) {
				// Explicitly set copy to true if we deal with a population
				return ((IPopulation) obj).listValue(scope, contentsType, true);
			}
			return ((IContainer) obj).listValue(scope, contentsType, copy);
		}
		// Dont copy twice the collection
		if (obj instanceof Collection) { return GamaListFactory.create(scope, contentsType, (Collection) obj); }
		if (obj instanceof Color) {
			final Color c = (Color) obj;
			return GamaListFactory.create(scope, contentsType, new int[] { c.getRed(), c.getGreen(), c.getBlue() });
		}
		if (obj instanceof GamaPoint) {
			final GamaPoint point = (GamaPoint) obj;
			return GamaListFactory.create(scope, contentsType, new double[] { point.x, point.y, point.z });
		}
		if (obj instanceof String) {
			return GamaListFactory.create(scope, contentsType, StringUtils.tokenize((String) obj));
		}
		return GamaListFactory.create(scope, contentsType, new Object[] { obj });
	}

	@Override
	public IType getKeyType() {
		return Types.get(INT);
	}

	@Override
	public IType contentsTypeIfCasting(final IExpression expr) {
		switch (expr.getGamlType().id()) {
			case COLOR:
			case DATE:
				return Types.get(INT);
			case POINT:
				return Types.get(FLOAT);
		}
		return super.contentsTypeIfCasting(expr);
	}

	@Override
	public boolean canCastToConst() {
		return true;
	}
}
