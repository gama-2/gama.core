/*******************************************************************************************************
 *
 * CreateFromGeometriesDelegate.java, in msi.gama.core, is part of the source code of the GAMA modeling and simulation
 * platform (v.1.9.0).
 *
 * (c) 2007-2022 UMI 209 UMMISCO IRD/SU & Partners (IRIT, MIAT, TLU, CTU)
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gaml.core.statements.create;

import java.util.List;
import java.util.Map;

import gama.annotations.common.interfaces.IKeyword;
import gama.core.common.interfaces.ICreateDelegate;
import gama.core.metamodel.shape.GamaShape;
import gama.core.metamodel.shape.IShape;
import gama.core.runtime.IScope;
import gama.core.util.IAddressableContainer;
import gama.core.util.IList;
import gama.core.util.file.GamaGeometryFile;
import gaml.core.statements.Arguments;
import gaml.core.statements.CreateStatement;
import gaml.core.types.IType;
import gaml.core.types.Types;

/**
 * Class CreateFromDatabaseDelegate.
 *
 * @author drogoul
 * @since 27 mai 2015
 *
 */
@SuppressWarnings ({ "unchecked", "rawtypes" })
public class CreateFromGeometriesDelegate implements ICreateDelegate {

	/**
	 * Method acceptSource()
	 *
	 * @see gama.core.common.interfaces.ICreateDelegate#acceptSource(IScope, java.lang.Object)
	 */
	@Override
	public boolean acceptSource(final IScope scope, final Object source) {
		// THIS CONDITION MUST BE CHECKED : bypass a condition that belong to
		// the case createFromDatabase
		if (source instanceof IList && !((IList) source).isEmpty() && ((IList) source).get(0) instanceof IList)
			return false;
		return source instanceof IList
				&& ((IList) source).getGamlType().getContentType().isAssignableFrom(Types.GEOMETRY)

				|| source instanceof GamaGeometryFile;

	}

	/**
	 * Method createFrom() Method used to read initial values and attributes from a CSV values describing a synthetic
	 * population
	 *
	 * @author Alexis Drogoul
	 * @since 04-09-2012
	 * @see gama.core.common.interfaces.ICreateDelegate#createFrom(gama.core.runtime.IScope, java.util.List, int,
	 *      java.lang.Object)
	 */
	@Override
	public boolean createFrom(final IScope scope, final List<Map<String, Object>> inits, final Integer max,
			final Object input, final Arguments init, final CreateStatement statement) {
		final IAddressableContainer<Integer, GamaShape, Integer, GamaShape> container =
				(IAddressableContainer<Integer, GamaShape, Integer, GamaShape>) input;
		final int num = max == null ? container.length(scope) : Math.min(container.length(scope), max);
		for (int i = 0; i < num; i++) {
			final IShape g = container.get(scope, i);
			final Map map = g.getOrCreateAttributes();
			// The shape is added to the initial values
			g.setAttribute(IKeyword.SHAPE, g);
			// GIS attributes are pushed to the scope in order to be read by read/get statements
			statement.fillWithUserInit(scope, map);
			inits.add(map);
		}
		return true;
	}

	/**
	 * Method fromFacetType()
	 *
	 * @see gama.core.common.interfaces.ICreateDelegate#fromFacetType()
	 */
	@Override
	public IType fromFacetType() {
		return Types.CONTAINER.of(Types.GEOMETRY);
	}

}
