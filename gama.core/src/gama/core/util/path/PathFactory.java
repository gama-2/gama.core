/*******************************************************************************************************
 *
 * PathFactory.java, in msi.gama.core, is part of the source code of the
 * GAMA modeling and simulation platform (v.1.9.0).
 *
 * (c) 2007-2022 UMI 209 UMMISCO IRD/SU & Partners (IRIT, MIAT, TLU, CTU)
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package gama.core.util.path;

import static gama.core.common.geometry.GeometryUtils.getFirstPointOf;
import static gama.core.common.geometry.GeometryUtils.getLastPointOf;

import gama.core.metamodel.shape.GamaPoint;
import gama.core.metamodel.shape.GamaShape;
import gama.core.metamodel.shape.IShape;
import gama.core.metamodel.topology.ITopology;
import gama.core.metamodel.topology.continuous.AmorphousTopology;
import gama.core.metamodel.topology.continuous.ContinuousTopology;
import gama.core.metamodel.topology.graph.GamaSpatialGraph;
import gama.core.metamodel.topology.graph.GraphTopology;
import gama.core.metamodel.topology.grid.GridTopology;
import gama.core.runtime.IScope;
import gama.core.runtime.exceptions.GamaRuntimeException;
import gama.core.util.IList;
import gama.core.util.graph.IGraph;

/**
 * A factory for creating Path objects.
 */
@SuppressWarnings ({ "rawtypes", "unchecked" })
public class PathFactory {

	/**
	 * New instance.
	 *
	 * @param <V> the value type
	 * @param <E> the element type
	 * @param g the g
	 * @param nodes the nodes
	 * @return the gama path
	 */
	public static <V, E> GamaPath<V, E, IGraph<V, E>> newInstance(final IGraph<V, E> g,
			final IList<? extends V> nodes) {
		if (nodes.isEmpty() && g instanceof GamaSpatialGraph || nodes.get(0) instanceof GamaPoint
				|| g instanceof GamaSpatialGraph)
			return (GamaPath) new GamaSpatialPath((GamaSpatialGraph) g, (IList<IShape>) nodes);
		else
			return new GamaPath<>(g, nodes);
	}

	/**
	 * New instance.
	 *
	 * @param <V> the value type
	 * @param <E> the element type
	 * @param g the g
	 * @param start the start
	 * @param target the target
	 * @param edges the edges
	 * @return the gama path
	 */
	public static <V, E> GamaPath<V, E, IGraph<V, E>> newInstance(final IGraph<V, E> g, final V start, final V target,
			final IList<E> edges) {
		if (g instanceof GamaSpatialGraph) {
			edges.removeIf(e -> e == null);
			return (GamaPath) new GamaSpatialPath((GamaSpatialGraph) g, (IShape) start, (IShape) target,
					(IList<IShape>) edges);
		} else
			return new GamaPath<>(g, start, target, edges);
	}

	/**
	 * New instance.
	 *
	 * @param <V> the value type
	 * @param <E> the element type
	 * @param g the g
	 * @param start the start
	 * @param target the target
	 * @param edges the edges
	 * @param modify_edges the modify edges
	 * @return the gama path
	 */
	public static <V, E> GamaPath<V, E, IGraph<V, E>> newInstance(final IGraph<V, E> g, final V start, final V target,
			final IList<E> edges, final boolean modify_edges) {
		if (g instanceof GamaSpatialGraph)
			return (GamaPath) new GamaSpatialPath((GamaSpatialGraph) g, (IShape) start, (IShape) target,
					(IList<IShape>) edges, modify_edges);
		else
			return new GamaPath<>(g, start, target, edges, modify_edges);
	}

	/**
	 * New instance.
	 *
	 * @param scope the scope
	 * @param g the g
	 * @param nodes the nodes
	 * @param weight the weight
	 * @return the gama spatial path
	 */
	// With Topology
	public static GamaSpatialPath newInstance(final IScope scope, final ITopology g,
			final IList<? extends IShape> nodes, final double weight) {
		GamaSpatialPath path;
		if (g instanceof GraphTopology) {
			path = (GamaSpatialPath) newInstance(((GraphTopology) g).getPlaces(), nodes);
		} else if (g instanceof ContinuousTopology || g instanceof AmorphousTopology || g instanceof GridTopology) {
			path = new GamaSpatialPath(null, nodes);
		} else
			throw GamaRuntimeException.error("Topologies that are not Graph are not yet taken into account", scope);
		path.setWeight(weight);
		return path;
	}

	/**
	 * New instance.
	 *
	 * @param scope the scope
	 * @param g the g
	 * @param start the start
	 * @param target the target
	 * @param edges the edges
	 * @return the gama spatial path
	 */
	public static GamaSpatialPath newInstance(final IScope scope, final ITopology g, final IShape start,
			final IShape target, final IList<IShape> edges) {
		if (g instanceof GraphTopology)
			return (GamaSpatialPath) newInstance(((GraphTopology) g).getPlaces(), start, target, edges);
		else
			return new GamaSpatialPath(start, target, edges);
	}

	/**
	 * New instance.
	 *
	 * @param scope the scope
	 * @param g the g
	 * @param start the start
	 * @param target the target
	 * @param edges the edges
	 * @param modify_edges the modify edges
	 * @return the gama spatial path
	 */
	public static GamaSpatialPath newInstance(final IScope scope, final ITopology g, final IShape start,
			final IShape target, final IList<IShape> edges, final boolean modify_edges) {
		if (g instanceof GraphTopology)
			return (GamaSpatialPath) newInstance(((GraphTopology) g).getPlaces(), start, target, edges, modify_edges);
		else
			// AmorphousTopology ) {
			return new GamaSpatialPath(null, start, target, edges, modify_edges);
	}

	/**
	 * New instance.
	 *
	 * @param scope the scope
	 * @param edgesNodes the edges nodes
	 * @param isEdges the is edges
	 * @return the i path
	 */
	public static IPath newInstance(final IScope scope, final IList<? extends IShape> edgesNodes,
			final boolean isEdges) {
		if (isEdges) {
			final GamaShape shapeS = (GamaShape) edgesNodes.get(0).getGeometry();
			final GamaShape shapeT = (GamaShape) edgesNodes.get(edgesNodes.size() - 1).getGeometry();
			return new GamaSpatialPath(null, getFirstPointOf(shapeS), getLastPointOf(shapeT), edgesNodes, false);
		}
		return new GamaSpatialPath(edgesNodes);
	}

}
