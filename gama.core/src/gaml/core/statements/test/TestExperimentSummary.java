/*******************************************************************************************************
 *
 * TestExperimentSummary.java, in msi.gama.core, is part of the source code of the
 * GAMA modeling and simulation platform (v.1.9.0).
 *
 * (c) 2007-2022 UMI 209 UMMISCO IRD/SU & Partners (IRIT, MIAT, TLU, CTU)
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package gaml.core.statements.test;

import gama.core.kernel.experiment.TestAgent;
import gaml.core.operators.Strings;

/**
 * A summary of a whole test experiment
 * 
 * @author drogoul
 *
 */
public class TestExperimentSummary extends CompoundSummary<IndividualTestSummary, TestAgent> {

	/**
	 * Instantiates a new test experiment summary.
	 *
	 * @param testAgent the test agent
	 */
	public TestExperimentSummary(final TestAgent testAgent) {
		super(testAgent);
	}

	@Override
	protected void printFooter(final StringBuilder sb) {
		sb.append(Strings.LN);
		sb.append("----------------------------------------------------------------");
	}

	@Override
	protected void printHeader(final StringBuilder sb) {
		sb.append("----------------------------------------------------------------");
		sb.append(Strings.LN);
	}
}
