/*******************************************************************************************************
 *
 * BenchmarkStatement.java, in msi.gama.core, is part of the source code of the
 * GAMA modeling and simulation platform (v.1.9.0).
 *
 * (c) 2007-2022 UMI 209 UMMISCO IRD/SU & Partners (IRIT, MIAT, TLU, CTU)
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package gaml.core.statements;

import gama.annotations.common.interfaces.IKeyword;
import gama.annotations.precompiler.IConcept;
import gama.annotations.precompiler.ISymbolKind;
import gama.annotations.precompiler.GamlAnnotations.doc;
import gama.annotations.precompiler.GamlAnnotations.facet;
import gama.annotations.precompiler.GamlAnnotations.facets;
import gama.annotations.precompiler.GamlAnnotations.inside;
import gama.annotations.precompiler.GamlAnnotations.symbol;
import gama.core.runtime.IScope;
import gama.core.runtime.exceptions.GamaRuntimeException;
import gaml.core.descriptions.IDescription;
import gaml.core.expressions.IExpression;
import gaml.core.operators.Cast;
import gaml.core.types.IType;

/**
 * Class TraceStatement.
 *
 * @author drogoul
 * @since 23 févr. 2014
 *
 */
@symbol (
		name = "benchmark",
		kind = ISymbolKind.SEQUENCE_STATEMENT,
		with_sequence = true,
		concept = { IConcept.TEST })
@facets (
		omissible = IKeyword.MESSAGE,
		value = { @facet (
				name = IKeyword.MESSAGE,
				type = IType.NONE,
				optional = true,
				doc = @doc ("A message to display alongside the results. Should concisely describe the contents of the benchmark")),
				@facet (
						name = IKeyword.REPEAT,
						type = IType.INT,
						optional = true,
						doc = @doc ("An int expression describing how many executions of the block must be handled. The output in this case will return the min, max and average durations")) })
@inside (
		kinds = { ISymbolKind.BEHAVIOR, ISymbolKind.SEQUENCE_STATEMENT, ISymbolKind.LAYER })
@doc (
		value = "Displays in the console the duration in ms of the execution of the statements included in the block. It is possible to indicate, with the 'repeat' facet, how many times the sequence should be run")
public class BenchmarkStatement extends AbstractStatementSequence {

	/** The message. */
	final IExpression repeat, message;

	/**
	 * @param desc
	 */
	public BenchmarkStatement(final IDescription desc) {
		super(desc);
		repeat = getFacet(IKeyword.REPEAT);
		message = getFacet(IKeyword.MESSAGE);
	}

	@Override
	public Object privateExecuteIn(final IScope scope) throws GamaRuntimeException {
		final int repeatTimes = repeat == null ? 1 : Cast.asInt(scope, repeat.value(scope));
		double min = Long.MAX_VALUE;
		int timeOfMin = 0;
		double max = Long.MIN_VALUE;
		int timeOfMax = 0;
		double total = 0;

		for (int i = 0; i < repeatTimes; i++) {
			final long begin = System.nanoTime();
			super.privateExecuteIn(scope);
			final long end = System.nanoTime();
			final double duration = (end - begin) / 1000000d;
			if (min > duration) {
				min = duration;
				timeOfMin = i;
			}
			if (max < duration) {
				max = duration;
				timeOfMax = i;
			}
			total += duration;
		}
		final String title = message == null ? "Execution time " : Cast.asString(scope, message.value(scope));
		final String result = title + " (over " + repeatTimes + " iteration(s)): min = " + min + " ms (iteration #"
				+ timeOfMin + ") | max = " + max + " ms (iteration #" + timeOfMax + ") | average = "
				+ total / repeatTimes + "ms";
		scope.getGui().getConsole().informConsole(result, scope.getRoot(), null);
		return result;
	}

}
