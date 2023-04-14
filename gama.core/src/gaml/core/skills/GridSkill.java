/*******************************************************************************************************
 *
 * GridSkill.java, in msi.gama.core, is part of the source code of the
 * GAMA modeling and simulation platform (v.1.9.0).
 *
 * (c) 2007-2022 UMI 209 UMMISCO IRD/SU & Partners (IRIT, MIAT, TLU, CTU)
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package gaml.core.skills;

import gama.annotations.common.interfaces.IKeyword;
import gama.annotations.precompiler.IConcept;
import gama.annotations.precompiler.ITypeProvider;
import gama.annotations.precompiler.GamlAnnotations.doc;
import gama.annotations.precompiler.GamlAnnotations.getter;
import gama.annotations.precompiler.GamlAnnotations.setter;
import gama.annotations.precompiler.GamlAnnotations.skill;
import gama.annotations.precompiler.GamlAnnotations.variable;
import gama.annotations.precompiler.GamlAnnotations.vars;
import gama.core.metamodel.agent.IAgent;
import gama.core.metamodel.topology.grid.IGrid;
import gama.core.runtime.IScope;
import gama.core.util.GamaColor;
import gama.core.util.IList;
import gaml.core.types.IType;

/**
 * Written by drogoul Modified on 24 juin 2010
 *
 * @todo Description
 *
 */
@vars ({ @variable (
		name = IKeyword.COLOR,
		type = IType.COLOR,
		init = "#white",
		doc = { @doc ("Represents the color of the cell, used by default to represent the grid on displays") }),
		@variable (
				name = IKeyword.NEIGHBORS,
				type = IType.LIST,
				of = ITypeProvider.OWNER_TYPE,
				doc = { @doc ("Represents the neighbor at distance 1 of the cell") }),
		@variable (
				name = IKeyword.GRID_VALUE,
				type = IType.FLOAT,
				doc = { @doc ("Represents a floating point value (automatically set when the grid is initialized from a grid file, and used by default to represent the elevation of the cell when displaying it on a display)") }),
		@variable (
				name = IKeyword.BANDS,
				type = IType.LIST,
				of = IType.FLOAT,
				doc = { @doc ("Represents the values of the different bands of the cell (list of floating point value automatically set when the grid is initialized from a grid file)") }),
		@variable (
				name = IKeyword.GRID_X,
				type = IType.INT,
				constant = true,
				doc = { @doc ("Returns the 0-based index of the column of the cell in the grid") }),
		@variable (
				name = IKeyword.GRID_Y,
				type = IType.INT,
				constant = true,
				doc = { @doc ("Returns the 0-based index of the row of the cell in the grid") }) })
@skill (
		name = GridSkill.SKILL_NAME,
		concept = { IConcept.GRID, IConcept.SKILL },
		internal = true)
public class GridSkill extends Skill {

	/**
	 * The Interface IGridAgent.
	 */
	public static interface IGridAgent extends IAgent {

		/**
		 * Gets the color.
		 *
		 * @return the color
		 */
		public GamaColor getColor();

		/**
		 * Sets the color.
		 *
		 * @param color the new color
		 */
		public void setColor(final GamaColor color);

		/**
		 * Gets the x.
		 *
		 * @return the x
		 */
		public int getX();

		/**
		 * Gets the y.
		 *
		 * @return the y
		 */
		public int getY();

		/**
		 * Gets the value.
		 *
		 * @return the value
		 */
		public double getValue();

		/**
		 * Gets the bands.
		 *
		 * @return the bands
		 */
		public IList<Double> getBands();

		/**
		 * Gets the neighbors.
		 *
		 * @param scope the scope
		 * @return the neighbors
		 */
		public IList<IAgent> getNeighbors(IScope scope);

		/**
		 * Sets the value.
		 *
		 * @param d the new value
		 */
		public void setValue(final double d);
	}

	/** The Constant SKILL_NAME. */
	public static final String SKILL_NAME = "grid";

	/**
	 * Gets the grid.
	 *
	 * @param agent the agent
	 * @return the grid
	 */
	protected final IGrid getGrid(final IAgent agent) {
		return (IGrid) agent.getPopulation().getTopology().getPlaces();
	}

	/**
	 * Gets the x.
	 *
	 * @param agent the agent
	 * @return the x
	 */
	@getter ("grid_x")
	public final int getX(final IAgent agent) {
		return ((IGridAgent) agent).getX();
	}

	/**
	 * Gets the value.
	 *
	 * @param agent the agent
	 * @return the value
	 */
	@getter (
			value = "grid_value",
			initializer = true)
	public final double getValue(final IAgent agent) {
		return ((IGridAgent) agent).getValue();
	}

	/**
	 * Gets the bands.
	 *
	 * @param agent the agent
	 * @return the bands
	 */
	@getter (
			value = "bands",
			initializer = true)
	public final IList<Double> getBands(final IAgent agent) {
		return ((IGridAgent) agent).getBands();
	}

	/**
	 * Gets the y.
	 *
	 * @param agent the agent
	 * @return the y
	 */
	@getter ("grid_y")
	public final int getY(final IAgent agent) {
		return ((IGridAgent) agent).getY();
	}

	/**
	 * Sets the X.
	 *
	 * @param agent the agent
	 * @param i the i
	 */
	@setter ("grid_x")
	public final void setX(final IAgent agent, final Integer i) {}

	/**
	 * Sets the value.
	 *
	 * @param agent the agent
	 * @param d the d
	 */
	@setter ("grid_value")
	public final void setValue(final IAgent agent, final Double d) {
		((IGridAgent) agent).setValue(d);
	}

	/**
	 * Sets the Y.
	 *
	 * @param agent the agent
	 * @param i the i
	 */
	@setter ("grid_y")
	public final void setY(final IAgent agent, final Integer i) {}

	/**
	 * Gets the color.
	 *
	 * @param agent the agent
	 * @return the color
	 */
	@getter (
			value = "color",
			initializer = true)
	public GamaColor getColor(final IAgent agent) {
		return ((IGridAgent) agent).getColor();
	}

	/**
	 * Sets the color.
	 *
	 * @param agent the agent
	 * @param color the color
	 */
	@setter ("color")
	public void setColor(final IAgent agent, final GamaColor color) {
		((IGridAgent) agent).setColor(color);
	}

	/**
	 * Gets the neighbors.
	 *
	 * @param scope the scope
	 * @param agent the agent
	 * @return the neighbors
	 */
	@getter (
			value = IKeyword.NEIGHBORS,
			initializer = true)
	public IList<IAgent> getNeighbors(final IScope scope, final IAgent agent) {
		return ((IGridAgent) agent).getNeighbors(scope);
	}

}
