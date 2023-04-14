package gama.core.util.serialize;

import gama.core.runtime.IScope;

public interface IStreamConverter {
	 abstract String convertObjectToJSONStream(final IScope scope, final Object o);
}
