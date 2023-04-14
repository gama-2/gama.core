/*******************************************************************************************************
 *
 * GamaMapType.java, in msi.gama.core, is part of the source code of the GAMA modeling and simulation platform
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
import gama.core.metamodel.agent.IAgent;
import gama.core.metamodel.agent.SavedAgent;
import gama.core.runtime.IScope;
import gama.core.runtime.exceptions.GamaRuntimeException;
import gama.core.util.GamaMapFactory;
import gama.core.util.IContainer;
import gama.core.util.IMap;
import gama.core.util.file.json.DeserializationException;
import gama.core.util.file.json.Jsoner;
import gaml.core.expressions.IExpression;

/**
 * The Class GamaMapType.
 */
@type (
		name = IKeyword.MAP,
		id = IType.MAP,
		wraps = { IMap.class },
		kind = ISymbolKind.Variable.CONTAINER,
		concept = { IConcept.TYPE, IConcept.CONTAINER, IConcept.MAP },
		doc = @doc ("Represents lists of pairs key::value, where each key is unique in the map. Maps are ordered by the insertion order of elements"))
@SuppressWarnings ({ "unchecked", "rawtypes" })
public class GamaMapType extends GamaContainerType<IMap> {

	@Override
	@doc ("Casts the operand into a map. In case of an agent, returns its attributes. In case of a string, tries to parse JSON contents and returns a corresponding map.")
	public IMap cast(final IScope scope, final Object obj, final Object param, final IType keyType,
			final IType contentType, final boolean copy) throws GamaRuntimeException {
		return staticCast(scope, obj, keyType, contentType, copy);
	}

	/**
	 * Static cast.
	 *
	 * @param scope
	 *            the scope
	 * @param obj
	 *            the obj
	 * @param keyType
	 *            the key type
	 * @param contentsType
	 *            the contents type
	 * @param copy
	 *            the copy
	 * @return the i map
	 */
	public static IMap staticCast(final IScope scope, final Object obj, final IType keyType, final IType contentsType,
			final boolean copy) {
		if (obj instanceof IAgent ia) return new SavedAgent(scope, ia);
		if (obj instanceof IContainer ic) return ic.mapValue(scope, keyType, contentsType, copy);
		if (obj instanceof String s) {
			final IMap<String, Object> map;
			try {
				Object o = Jsoner.deserialize(s);
				if (o instanceof IMap m) return m;
				map = GamaMapFactory.create();
				map.put(IKeyword.CONTENTS, o);
				return map;
			} catch (DeserializationException e) {
				throw GamaRuntimeException.create(e, scope);
			}
		}
		final IMap result = GamaMapFactory.create(keyType, contentsType);
		if (obj != null) { result.setValueAtIndex(scope, obj, obj); }
		return result;
	}

	@Override
	public int getNumberOfParameters() { return 2; }

	@Override
	public IType keyTypeIfCasting(final IExpression exp) {
		final IType itemType = exp.getGamlType();
		if (itemType.isAgentType()) return Types.STRING;
		switch (itemType.id()) {
			case STRING:
				return Types.STRING;
			case PAIR:
			case MAP:
				return itemType.getKeyType();
			case MATRIX:
				return itemType.getContentType();
			case GRAPH:
				return Types.PAIR;
			case LIST:
				if (itemType.getContentType().id() == IType.PAIR) return itemType.getContentType().getKeyType();
				return itemType.getContentType();
		}
		return itemType;
	}

	@Override
	public IType contentsTypeIfCasting(final IExpression exp) {
		final IType itemType = exp.getGamlType();
		if (itemType.isAgentType()) return Types.NO_TYPE;
		switch (itemType.id()) {
			case STRING:
				return Types.NO_TYPE;
			case LIST:
				if (itemType.getContentType().id() == IType.PAIR)
					return itemType.getContentType().getContentType();
				else
					return itemType.getContentType();
			case PAIR:
			case GRAPH:
			case MAP:
			case MATRIX:
				return itemType.getContentType();

		}
		return itemType;
	}

	@Override
	public boolean canCastToConst() {
		return true;
	}
}
