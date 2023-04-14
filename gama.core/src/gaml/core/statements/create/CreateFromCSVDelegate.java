/*******************************************************************************************************
 *
 * CreateFromCSVDelegate.java, in msi.gama.core, is part of the source code of the
 * GAMA modeling and simulation platform (v.1.9.0).
 *
 * (c) 2007-2022 UMI 209 UMMISCO IRD/SU & Partners (IRIT, MIAT, TLU, CTU)
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package gaml.core.statements.create;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import gama.core.common.interfaces.ICreateDelegate;
import gama.core.runtime.IScope;
import gama.core.util.GamaMapFactory;
import gama.core.util.IList;
import gama.core.util.file.GamaCSVFile;
import gama.core.util.matrix.IMatrix;
import gaml.core.expressions.IExpression;
import gaml.core.operators.Cast;
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
public class CreateFromCSVDelegate implements ICreateDelegate {

	/**
	 * Method acceptSource()
	 *
	 * @see gama.core.common.interfaces.ICreateDelegate#acceptSource(IScope, java.lang.Object)
	 */
	@Override
	public boolean acceptSource(final IScope scope, final Object source) {
		return source instanceof GamaCSVFile;
	}

	/**
	 * Method createFrom() Method used to read initial values and attributes from a CSV values descring a synthetic
	 * population
	 *
	 * @author Alexis Drogoul
	 * @since 04-09-2012
	 * @see gama.core.common.interfaces.ICreateDelegate#createFrom(gama.core.runtime.IScope, java.util.List, int,
	 *      java.lang.Object)
	 */
	@SuppressWarnings ("rawtypes")
	@Override
	public boolean createFrom(final IScope scope, final List<Map<String, Object>> inits, final Integer max,
			final Object input, final Arguments init, final CreateStatement statement) {
		final GamaCSVFile source = (GamaCSVFile) input;
		final IExpression header = statement.getHeader();
		if (header != null) { source.forceHeader(Cast.asBool(scope, header.value(scope))); }
		final boolean hasHeader = source.hasHeader();
		final IMatrix<?> mat = source.getContents(scope);
		if (mat == null || mat.isEmpty(scope)) return false;
		int rows = mat.getRows(scope);
		final int cols = mat.getCols(scope);
		rows = max == null ? rows : Math.min(rows, max);

		List<String> headers;
		if (hasHeader) {
			headers = source.getAttributes(scope);
		} else {
			headers = new ArrayList<>();
			for (int j = 0; j < cols; j++) {
				headers.add(String.valueOf(j));
			}
		}
		for (int i = 0; i < rows; i++) {
			final Map<String, Object> map = GamaMapFactory.create(hasHeader ? Types.STRING : Types.INT, Types.NO_TYPE);
			final IList vals = mat.getRow(i);
			for (int j = 0; j < cols; j++) {
				map.put(headers.get(j), vals.get(j));
			}
			// CSV attributes are mixed with the attributes of agents
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
	public IType<?> fromFacetType() {
		return Types.FILE;
	}

}
