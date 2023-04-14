/*******************************************************************************************************
 *
 * Activator.java, in msi.gama.core, is part of the source code of the
 * GAMA modeling and simulation platform (v.1.9.0).
 *
 * (c) 2007-2022 UMI 209 UMMISCO IRD/SU & Partners (IRIT, MIAT, TLU, CTU)
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package gama.core.common;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import gama.core.runtime.concurrent.GamaExecutorService;
import gaml.core.compilation.kernel.GamaBundleLoader;
import gaml.core.operators.Dates;

/**
 * The Class Activator.
 */
public class Activator implements BundleActivator {

	@Override
	public void start(final BundleContext context) throws Exception {
		/* Early build of the contributions made by plugins to GAMA */
		GamaBundleLoader.preBuildContributions();
		GamaExecutorService.reset();
		Dates.initialize();

	}

	@Override
	public void stop(final BundleContext context) throws Exception {}

}
