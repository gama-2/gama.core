/*******************************************************************************************************
 *
 * SubTaskMessage.java, in msi.gama.core, is part of the source code of the
 * GAMA modeling and simulation platform (v.1.9.0).
 *
 * (c) 2007-2022 UMI 209 UMMISCO IRD/SU & Partners (IRIT, MIAT, TLU, CTU)
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package gama.core.common;

import gama.annotations.common.interfaces.IStatusMessage;
import gama.core.common.interfaces.IGui;
import gama.core.util.GamaColor;

/**
 * Class SubTaskMessage.
 *
 * @author drogoul
 * @since 5 nov. 2014
 *
 */
public class SubTaskMessage implements IStatusMessage {

	/** The completion. */
	Double completion;
	
	/** The name. */
	String name;
	
	/** The begin or end. */
	Boolean beginOrEnd;

	/**
	 * Instantiates a new sub task message.
	 *
	 * @param name the name
	 * @param begin the begin
	 */
	public SubTaskMessage(final String name, final boolean begin) {
		this.name = name;
		completion = null;
		this.beginOrEnd = begin;
	}

	/**
	 * Instantiates a new sub task message.
	 *
	 * @param completion the completion
	 */
	public SubTaskMessage(final Double completion) {
		this.completion = completion;
		this.beginOrEnd = null;
	}

	/**
	 * Method getText()
	 * 
	 * @see gama.annotations.common.interfaces.IStatusMessage#getText()
	 */
	@Override
	public String getText() {
		return name;
	}

	/**
	 * Method getCode()
	 * 
	 * @see gama.annotations.common.interfaces.IStatusMessage#getCode()
	 */
	@Override
	public int getCode() {
		return IGui.NEUTRAL;
	}

	/**
	 * Gets the completion.
	 *
	 * @return the completion
	 */
	public Double getCompletion() {
		return completion;
	}

	/**
	 * Gets the begin or end.
	 *
	 * @return the begin or end
	 */
	public Boolean getBeginOrEnd() {
		return beginOrEnd;
	}

	/**
	 * Method getColor()
	 * 
	 * @see gama.annotations.common.interfaces.IStatusMessage#getColor()
	 */
	@Override
	public GamaColor getColor() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gama.core.common.IStatusMessage#getIcon()
	 */
	@Override
	public String getIcon() {
		return null;
	}

}
