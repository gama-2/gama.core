/*******************************************************************************************************
 *
 * GamaAttributesType.java, in msi.gama.core, is part of the source code of the
 * GAMA modeling and simulation platform (v.1.9.0).
 *
 * (c) 2007-2022 UMI 209 UMMISCO IRD/SU & Partners (IRIT, MIAT, TLU, CTU)
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package gaml.core.types;

import java.util.Map;

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
import gama.core.util.IMap;
import gaml.core.expressions.IExpression;

/**
 * The Class GamaAttributesType.
 */
@type (
		name = IKeyword.ATTRIBUTES,
		id = IType.ATTRIBUTES,
		wraps = { SavedAgent.class },
		kind = ISymbolKind.Variable.CONTAINER,
		concept = { IConcept.TYPE, IConcept.CONTAINER, IConcept.MAP },
		doc = @doc ("Represents the attributes of an agent, a special kind of map<string, unknown> with additional information such as its index or its sub-populations"))

public class GamaAttributesType extends GamaMapType {

	@SuppressWarnings ("unchecked")
	@Override
	public SavedAgent cast(final IScope scope, final Object obj, final Object param, final IType keyType,
			final IType contentType, final boolean copy) throws GamaRuntimeException {
		if (obj instanceof IAgent) { return new SavedAgent(scope, (IAgent) obj); }
		if (obj instanceof SavedAgent) {
			if (copy) {
				return ((SavedAgent) obj).clone();
			} else {
				return (SavedAgent) obj;
			}
		}
		if (obj instanceof Map) {
			final IMap<String, Object> map =
					GamaMapFactory.createWithoutCasting(Types.STRING, Types.NO_TYPE, (Map<String, Object>) obj);
			return new SavedAgent(map);
		}
		return null;
	}

	@Override
	public IType keyTypeIfCasting(final IExpression exp) {
		return Types.get(STRING);
	}

}
