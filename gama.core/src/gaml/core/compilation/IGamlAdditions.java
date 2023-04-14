/*******************************************************************************************************
 *
 * IGamlAdditions.java, in msi.gama.core, is part of the source code of the GAMA modeling and simulation platform
 * (v.1.9.0).
 *
 * (c) 2007-2022 UMI 209 UMMISCO IRD/SU & Partners (IRIT, MIAT, TLU, CTU)
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gaml.core.compilation;

import java.util.Arrays;
import java.util.Collections;

import gama.annotations.common.interfaces.IKeyword;
import gama.core.kernel.experiment.ExperimentAgent;
import gama.core.kernel.root.PlatformAgent;
import gama.core.kernel.simulation.SimulationAgent;
import gama.core.metamodel.agent.GamlAgent;
import gama.core.metamodel.agent.IAgent;
import gama.core.metamodel.shape.GamaPoint;
import gama.core.metamodel.shape.GamaShape;
import gama.core.metamodel.shape.IShape;
import gama.core.metamodel.topology.ITopology;
import gama.core.runtime.IScope;
import gama.core.util.GamaColor;
import gama.core.util.GamaDate;
import gama.core.util.GamaPair;
import gama.core.util.IContainer;
import gama.core.util.IList;
import gama.core.util.IMap;
import gama.core.util.file.IGamaFile;
import gama.core.util.graph.IGraph;
import gama.core.util.matrix.IMatrix;
import gama.core.util.path.IPath;
import gaml.core.descriptions.FacetProto;
import gaml.core.descriptions.IDescription;
import gaml.core.expressions.IExpression;
import gaml.core.factories.DescriptionFactory;
import gaml.core.skills.GridSkill;
import gaml.core.skills.MovingSkill;
import gaml.core.species.ISpecies;
import gaml.core.types.IType;
import gaml.core.types.Types;

/**
 * The Interface IGamlAdditions. Holds a number of default utility methods that allow to write compact declarations of
 * operators, actions, skills, etc. in the GamlAdditions files. Also provides the unique method to redefine
 * ({@link #initialize()}.
 */
public interface IGamlAdditions {

	/**
	 * The Class Children.
	 */
	public static class Children {

		/** The children. */
		private final Iterable<IDescription> children;

		/**
		 * Instantiates a new children.
		 *
		 * @param descs
		 *            the descs
		 */
		public Children(final IDescription... descs) {
			if (descs == null || descs.length == 0) {
				children = Collections.emptyList();
			} else {
				children = Arrays.asList(descs);
			}
		}

		/**
		 * Gets the children.
		 *
		 * @return the children
		 */
		public Iterable<IDescription> getChildren() { return children; }

	}

	/**
	 * Desc.
	 *
	 * @param keyword
	 *            the keyword
	 * @param children
	 *            the children
	 * @param facets
	 *            the facets
	 * @return the i description
	 */
	default IDescription desc(final String keyword, final Children children, final String... facets) {
		return DescriptionFactory.create(keyword, null, children.getChildren(), facets);
	}

	/**
	 * Desc.
	 *
	 * @param keyword
	 *            the keyword
	 * @param facets
	 *            the facets
	 * @return the i description
	 */
	default IDescription desc(final String keyword, final String... facets) {
		return DescriptionFactory.create(keyword, facets);
	}

	/**
	 * Creates a VariableDescription
	 *
	 * @param keyword
	 * @param facets
	 * @return
	 */
	default IDescription desc(final int keyword, final String... facets) {
		final IType t = Types.get(keyword);
		if (t == null) throw new RuntimeException("Types not defined");
		return desc(t.toString(), facets);
	}

	/**
	 * S.
	 *
	 * @param strings
	 *            the strings
	 * @return the string[]
	 */
	default String[] S(final String... strings) {
		return strings;
	}

	/**
	 * I.
	 *
	 * @param integers
	 *            the integers
	 * @return the int[]
	 */
	default int[] I(final int... integers) {
		return integers;
	}

	/**
	 * P.
	 *
	 * @param protos
	 *            the protos
	 * @return the facet proto[]
	 */
	default FacetProto[] P(final FacetProto... protos) {
		return protos;
	}

	/**
	 * C.
	 *
	 * @param classes
	 *            the classes
	 * @return the class[]
	 */
	default Class[] C(final Class... classes) {
		return classes;
	}

	/**
	 * T.
	 *
	 * @param c
	 *            the c
	 * @return the i type
	 */
	default IType<?> T(final Class<?> c) {
		return Types.get(c);
	}

	/**
	 * Ti.
	 *
	 * @param c
	 *            the c
	 * @return the string
	 */
	default String Ti(final Class<?> c) {
		return String.valueOf(Types.get(c).id());
	}

	/**
	 * Ts.
	 *
	 * @param c
	 *            the c
	 * @return the string
	 */
	default String Ts(final Class<?> c) {
		return Types.get(c).toString();
	}

	/**
	 * T.
	 *
	 * @param c
	 *            the c
	 * @return the i type
	 */
	default IType T(final String c) {
		return Types.get(c);
	}

	/**
	 * T.
	 *
	 * @param c
	 *            the c
	 * @return the i type
	 */
	default IType T(final int c) {
		return Types.get(c);
	}

	/** The ai. */
	int[] AI = {};

	/** The as. */
	String[] AS = {};

	/** The f. */
	boolean F = false;

	/** The t. */
	boolean T = true;

	/** The prim. */
	String PRIM = IKeyword.PRIMITIVE;

	/** The ia. */
	Class<?> IA = IAgent.class;

	/** The it. */
	Class<?> IT = ITopology.class;

	/** The sp. */
	Class<?> SP = ISpecies.class;

	/** The ga. */
	Class<?> GA = GamlAgent.class;

	/** The gc. */
	Class<?> GC = GamaColor.class;

	/** The gp. */
	Class<?> GP = GamaPair.class;

	/** The gs. */
	Class<?> GS = GamaShape.class;

	/** The o. */
	Class<?> O = Object.class;

	/** The b. */
	Class<?> B = Boolean.class;

	/** The i. */
	Class<?> I = Integer.class;

	/** The d. */
	Class<?> D = Double.class;

	/** The s. */
	Class<?> S = String.class;

	/** The ie. */
	Class<?> IE = IExpression.class;

	/** The is. */
	Class<?> IS = IShape.class;

	/** The gm. */
	Class<?> GM = IMap.class;

	/** The p. */
	// public final static Class<?> GL = GamaList.class;
	Class<?> P = GamaPoint.class;

	/** The ic. */
	Class<?> IC = IContainer.class;

	/** The il. */
	Class<?> IL = GamaPoint.class;

	/** The li. */
	Class<?> LI = IList.class;

	/** The im. */
	Class<?> IM = IMatrix.class;

	/** The gr. */
	Class<?> GR = IGraph.class;

	/** The ip. */
	Class<?> IP = IPath.class;

	/** The gf. */
	Class<?> GF = IGamaFile.class;

	/** The msk. */
	Class<?> MSK = MovingSkill.class;

	/** The gsk. */
	Class<?> GSK = GridSkill.class;

	/** The sc. */
	Class<?> SC = IScope.class;

	/** The gd. */
	Class<?> GD = GamaDate.class;

	/** The sa. */
	Class<?> SA = SimulationAgent.class;

	/** The ea. */
	Class<?> EA = ExperimentAgent.class;

	/** The pa. */
	Class<?> PA = PlatformAgent.class;

	/** The i. */
	Class<?> i = int.class;

	/** The d. */
	Class<?> d = double.class;

	/** The b. */
	Class<?> b = boolean.class;

	/**
	 * Initialize.
	 *
	 * @throws SecurityException
	 *             the security exception
	 * @throws NoSuchMethodException
	 *             the no such method exception
	 */
	void initialize() throws SecurityException, NoSuchMethodException;

}
