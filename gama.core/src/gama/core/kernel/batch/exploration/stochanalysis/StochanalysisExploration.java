/*******************************************************************************************************
 *
 * StochanalysisExploration.java, in msi.gama.core, is part of the source code of the GAMA modeling and simulation
 * platform (v.1.9.0).
 *
 * (c) 2007-2023 UMI 209 UMMISCO IRD/SU & Partners (IRIT, MIAT, TLU, CTU)
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gama.core.kernel.batch.exploration.stochanalysis;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import gama.annotations.common.interfaces.IKeyword;
import gama.annotations.precompiler.IConcept;
import gama.annotations.precompiler.ISymbolKind;
import gama.annotations.precompiler.GamlAnnotations.doc;
import gama.annotations.precompiler.GamlAnnotations.example;
import gama.annotations.precompiler.GamlAnnotations.facet;
import gama.annotations.precompiler.GamlAnnotations.facets;
import gama.annotations.precompiler.GamlAnnotations.inside;
import gama.annotations.precompiler.GamlAnnotations.symbol;
import gama.annotations.precompiler.GamlAnnotations.usage;
import gama.core.common.util.FileUtils;
import gama.core.kernel.batch.exploration.AExplorationAlgorithm;
import gama.core.kernel.batch.exploration.Exploration;
import gama.core.kernel.batch.exploration.sampling.LatinhypercubeSampling;
import gama.core.kernel.batch.exploration.sampling.OrthogonalSampling;
import gama.core.kernel.batch.exploration.sampling.RandomSampling;
import gama.core.kernel.experiment.ParametersSet;
import gama.core.kernel.experiment.IParameter.Batch;
import gama.core.runtime.IScope;
import gama.core.runtime.concurrent.GamaExecutorService;
import gama.core.runtime.exceptions.GamaRuntimeException;
import gama.core.util.GamaMapFactory;
import gama.core.util.IList;
import gama.core.util.IMap;
import gaml.core.compilation.ISymbol;
import gaml.core.descriptions.IDescription;
import gaml.core.expressions.IExpression;
import gaml.core.operators.Cast;
import gaml.core.types.IType;

/**
 * The Class StochanalysisExploration.
 */
@symbol (
		name = { IKeyword.STO },
		kind = ISymbolKind.BATCH_METHOD,
		with_sequence = false,
		concept = { IConcept.BATCH, IConcept.ALGORITHM })
@inside (
		kinds = { ISymbolKind.EXPERIMENT })
@facets (
		value = { @facet (
				name = IKeyword.NAME,
				type = IType.ID,
				optional = false,
				internal = true,
				doc = @doc ("The name of the method. For internal use only")),
				@facet (
						name = Exploration.METHODS,
						type = IType.ID,
						optional = true,
						doc = @doc ("The sampling method to build parameters sets. Available methods are: "
								+ IKeyword.LHS + ", " + IKeyword.ORTHOGONAL + ", " 
								+ IKeyword.FACTORIAL + ", "+ IKeyword.UNIFORM + ", " 
								+ IKeyword.SALTELLI + ", "+ IKeyword.MORRIS)),
				@facet (
						name = IKeyword.BATCH_VAR_OUTPUTS,
						type = IType.LIST,
						optional = false,
						doc = @doc ("The list of output variables to analyse")),
				@facet (
						name = Exploration.SAMPLE_SIZE,
						type = IType.INT,
						optional = true,
						doc = @doc ("The number of sample required , 10 by default")),
				@facet (
						name = IKeyword.BATCH_REPORT,
						type = IType.STRING,
						optional = false,
						doc = @doc ("The path to the file where the Sobol report will be written")),
				@facet (
						name = IKeyword.BATCH_OUTPUT,
						type = IType.STRING,
						optional = true,
						doc = @doc ("The path to the file where the automatic batch report will be written"))

		},
		omissible = IKeyword.NAME)
@doc (
		value = "This algorithm runs an exploration with a given sampling to compute a Stochasticity Analysis",
		usages = { @usage (
				value = "For example: ",
				examples = { @example (
						value = "method stochanalyse sampling:'latinhypercube' outputs:['my_var'] replicat:10 results:'../path/to/report/file.txt'; ",
						isExecutable = false) }) })
public class StochanalysisExploration extends AExplorationAlgorithm {

	/** The method. */
	public String method = "";

	/** The sample size. */
	public int sample_size = 10;

	/** The threshold. */
	public double threshold = -1;
	/** Theoretical inputs */
	private List<Batch> parameters;
	/** Theoretical outputs */
	private IList<Object> outputs;
	/** Actual input / output map */
	protected IMap<ParametersSet, Map<String, List<Object>>> res_outputs;

	/**
	 * Instantiates a new stochanalysis exploration.
	 *
	 * @param desc
	 *            the desc
	 */
	public StochanalysisExploration(final IDescription desc) {
		super(desc);
	}

	@Override
	public void setChildren(final Iterable<? extends ISymbol> children) {}

	@Override
	public void explore(final IScope scope) throws GamaRuntimeException {

		List<Batch> params = new ArrayList<>(currentExperiment.getParametersToExplore());

		parameters = parameters == null ? params : parameters;

		List<ParametersSet> sets;

		if (hasFacet(Exploration.SAMPLE_SIZE)) {
			this.sample_size = Cast.asInt(scope, getFacet(Exploration.SAMPLE_SIZE).value(scope));
		}
		if (hasFacet(Exploration.METHODS)) {
			method = Cast.asString(scope, getFacet(Exploration.METHODS).value(scope));
		}
		sets = switch (method) {
			case IKeyword.LHS -> LatinhypercubeSampling.LatinHypercubeSamples(sample_size, parameters,
					scope.getRandom().getGenerator(), scope);
			case IKeyword.ORTHOGONAL -> {
				int iterations = hasFacet(Exploration.ITERATIONS)
						? Cast.asInt(scope, getFacet(Exploration.ITERATIONS).value(scope)) : 5;
				yield OrthogonalSampling.OrthogonalSamples(sample_size, iterations, parameters,
						scope.getRandom().getGenerator(), scope);
			}
			case IKeyword.FACTORIAL -> {
				List<ParametersSet> ps = null;
				if (hasFacet(Exploration.SAMPLE_FACTORIAL)) {
					@SuppressWarnings ("unchecked") int[] factors =
							Cast.asList(scope, getFacet(Exploration.SAMPLE_FACTORIAL).value(scope)).stream()
									.mapToInt(o -> Integer.parseInt(o.toString())).toArray();
					ps = RandomSampling.FactorialUniformSampling(scope, factors, params);
				} else {
					ps = RandomSampling.FactorialUniformSampling(scope, sample_size, params);
				}

				yield ps;
			}
			default -> RandomSampling.UniformSampling(scope, sample_size, params);
		};

		if (GamaExecutorService.shouldRunAllSimulationsInParallel(currentExperiment)) {
			res_outputs = currentExperiment.launchSimulationsWithSolution(sets);
		} else {
			res_outputs = GamaMapFactory.create();
			for (ParametersSet sol : sets) {
				res_outputs.put(sol, currentExperiment.launchSimulationsWithSolution(sol));
			}
		}

		IExpression outputFacet = getFacet(IKeyword.BATCH_VAR_OUTPUTS);
		outputs = Cast.asList(scope, scope.evaluate(outputFacet, currentExperiment).getValue());
		int res = 0;
		Map<String, Map<Double, List<Object>>> MapOutput = new LinkedHashMap<>();
		for (Object out : outputs) {
			Map<Double, List<Object>> res_val = new HashMap<>(Map.of(0.05, Collections.emptyList(), 0.01,
					Collections.emptyList(), 0.001, Collections.emptyList(), 90.0, Collections.emptyList(), 95.0,
					Collections.emptyList(), 99.0, Collections.emptyList(), -1.0, Collections.emptyList()));
			IMap<ParametersSet, List<Object>> sp = GamaMapFactory.create();
			for (ParametersSet ps : res_outputs.keySet()) { sp.put(ps, res_outputs.get(ps).get(out.toString())); }
			List<Double> keys = res_val.keySet().stream().toList();
			for (Double thresh : keys) {
				if (res_val.get(thresh).isEmpty()) {
					res_val.replace(thresh, Stochanalysis.StochasticityAnalysis(sp, thresh, scope));
				}
			}
			MapOutput.put(out.toString(), res_val);
		}
		
		// Build report
		String path = Cast.asString(scope, getFacet(IKeyword.BATCH_REPORT).value(scope));
		final File f = new File(FileUtils.constructAbsoluteFilePath(scope, path, false));
		final File parent = f.getParentFile();
		if (!parent.exists()) { parent.mkdirs(); }
		if (f.exists()) { f.delete(); }
		Stochanalysis.WriteAndTellReport(f, MapOutput, scope);
		
		/* Save the simulation values in the provided .csv file (input and corresponding output) */
		if (hasFacet(IKeyword.BATCH_OUTPUT)) {
			String path_to = Cast.asString(scope, getFacet(IKeyword.BATCH_OUTPUT).value(scope));
			final File fo = new File(FileUtils.constructAbsoluteFilePath(scope, path_to, false));
			final File parento = fo.getParentFile();
			if (!parento.exists()) { parento.mkdirs(); }
			if (fo.exists()) { fo.delete(); }
			Stochanalysis.WriteAndTellResult(fo, res_outputs, scope);
		}
	}

	@Override
	public List<ParametersSet> buildParameterSets(final IScope scope, final List<ParametersSet> sets, final int index) {

		return null;
	}
}
