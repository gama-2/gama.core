/*******************************************************************************************************
 *
 * ParameterAdapter.java, in msi.gama.core, is part of the source code of the GAMA modeling and simulation platform
 * (v.1.9.0).
 *
 * (c) 2007-2022 UMI 209 UMMISCO IRD/SU & Partners (IRIT, MIAT, TLU, CTU)
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gama.core.kernel.experiment;

import java.util.List;
import java.util.Set;

import gama.core.common.util.StringUtils;
import gama.core.runtime.IScope;
import gama.core.runtime.exceptions.GamaRuntimeException;
import gama.core.util.GamaColor;
import gama.dev.COUNTER;
import gaml.core.types.IType;
import gaml.core.types.Types;

/**
 * The Class ParameterAdapter.
 */
@SuppressWarnings ({ "rawtypes" })
public abstract class ParameterAdapter implements IParameter.Batch {

	/** The order. */
	private final int order = COUNTER.COUNT();

	/** The title. */
	protected String title;

	/** The type. */
	protected final IType type;

	/** The category. */
	protected String category;

	/** The unit label. */
	protected String unitLabel;

	/**
	 * Instantiates a new parameter adapter.
	 *
	 * @param title
	 *            the title
	 * @param type
	 *            the type
	 */
	public ParameterAdapter(final String title, final int type) {
		this.title = title;
		this.type = Types.get(type);
	}

	/**
	 * Instantiates a new parameter adapter.
	 *
	 * @param title
	 *            the title
	 * @param category
	 *            the category
	 * @param type
	 *            the type
	 */
	public ParameterAdapter(final String title, final String category, final int type) {
		this(title, type);
		this.category = category;
	}

	/**
	 * Instantiates a new parameter adapter.
	 *
	 * @param title
	 *            the title
	 * @param category
	 *            the category
	 * @param unit
	 *            the unit
	 * @param type
	 *            the type
	 */
	public ParameterAdapter(final String title, final String category, final String unit, final int type) {
		this(title, category, type);
		this.unitLabel = unit;
	}

	@Override
	public int getOrder() { return order; }

	@Override
	public String getTitle() { return title; }

	@Override
	public String getName() { return getTitle(); }

	@Override
	public boolean isEditable() { return false; }

	@Override
	public boolean canBeExplored() {
		return false;
	}

	@Override
	public void setEditable(final boolean editable) {}

	@Override
	public String getCategory() { return category; }

	@Override
	public String getUnitLabel(final IScope scope) {
		return unitLabel;
	}

	@Override
	public void setValue(final IScope scope, final Object value) {}

	@Override
	public Object value(final IScope iScope) throws GamaRuntimeException {
		return value();
	}

	@Override
	public IType getType() { return type; }

	// @Override
	// public IType getContentType() {
	// return Types.NO_TYPE;
	// }

	@Override
	public String serialize(final boolean includingBuiltIn) {
		return StringUtils.toGaml(value(), includingBuiltIn);
	}

	@Override
	public Object getInitialValue(final IScope scope) {
		return value(scope);
	}

	@Override
	public Comparable getMinValue(final IScope scope) {
		return null;
	}

	@Override
	public Comparable getMaxValue(final IScope scope) {
		return null;
	}

	@Override
	public List getAmongValue(final IScope scope) {
		return null;
	}

	@Override
	public void reinitRandomly(final IScope scope) {}

	//
	// @Override
	// public void tryToInit(IScope scope) {}

	@Override
	public Set<Object> neighborValues(final IScope scope) throws GamaRuntimeException {
		return null;
	}

	@Override
	public Comparable getStepValue(final IScope scope) {
		return null;
	}

	@Override
	public void setCategory(final String name) { category = name; }

	/**
	 * Method setUnitLabel()
	 *
	 * @see gama.core.kernel.experiment.IParameter#setUnitLabel(java.lang.String)
	 */
	@Override
	public void setUnitLabel(final String label) { unitLabel = label; }

	@Override
	public boolean isDefined() { return true; }

	@Override
	public void setDefined(final boolean defined) {}

	@Override
	public boolean isDefinedInExperiment() { return false; }

	@Override
	public boolean acceptsSlider(final IScope scope) {
		return false;
	}

	@Override
	public List<GamaColor> getColors(final IScope scope) {
		return null;
	}

	@Override
	public GamaColor getColor(final IScope scope) {
		return null;
	}

}
