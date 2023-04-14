/*******************************************************************************************************
 *
 * GamaMessage.java, in msi.gama.core, is part of the source code of the
 * GAMA modeling and simulation platform (v.1.9.0).
 *
 * (c) 2007-2022 UMI 209 UMMISCO IRD/SU & Partners (IRIT, MIAT, TLU, CTU)
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package gama.core.extensions.messaging;

import gama.annotations.common.interfaces.IKeyword;
import gama.annotations.precompiler.GamlAnnotations.doc;
import gama.annotations.precompiler.GamlAnnotations.getter;
import gama.annotations.precompiler.GamlAnnotations.setter;
import gama.annotations.precompiler.GamlAnnotations.variable;
import gama.annotations.precompiler.GamlAnnotations.vars;
import gama.core.common.interfaces.IValue;
import gama.core.common.util.StringUtils;
import gama.core.runtime.IScope;
import gama.core.runtime.exceptions.GamaRuntimeException;
import gaml.core.types.IType;
import gaml.core.types.Types;

/**
 * The Class GamaMessageProxy.
 *
 * @author drogoul
 */

@vars({ @variable(name = GamaMessage.SENDER, type = IType.NONE, doc = {
		@doc("Returns the sender that has sent this message") }),
		@variable(name = GamaMessage.CONTENTS, type = IType.NONE, doc = {
				@doc("Returns the contents of this message, as a list of arbitrary objects") }),
		@variable(name = GamaMessage.UNREAD, type = IType.BOOL, init = IKeyword.TRUE, doc = {
				@doc("Returns whether this message is unread or not") }),
		@variable(name = GamaMessage.RECEPTION_TIMESTAMP, type = IType.INT, doc = {
				@doc("Returns the reception time stamp of this message (I.e. at what cycle it has been received)") }),
		@variable(name = GamaMessage.EMISSION_TIMESTAMP, type = IType.INT, doc = {
				@doc("Returns the emission time stamp of this message (I.e. at what cycle it has been emitted)") }) })
public class GamaMessage implements IValue {

	/** The Constant CONTENTS. */
	public final static String CONTENTS = "contents";
	
	/** The Constant UNREAD. */
	public final static String UNREAD = "unread";
	
	/** The Constant EMISSION_TIMESTAMP. */
	public final static String EMISSION_TIMESTAMP = "emission_timestamp";
	
	/** The Constant RECEPTION_TIMESTAMP. */
	public final static String RECEPTION_TIMESTAMP = "recention_timestamp";
	
	/** The Constant SENDER. */
	public final static String SENDER = "sender";
	
	/** The Constant RECEIVERS. */
	public final static String RECEIVERS = "receivers";

	/** The unread. */
	private boolean unread;

	/** The sender. */
	private Object sender;

	/** The receivers. */
	private Object receivers;

	/** The contents. */
	protected Object contents;

	/** The emission time stamp. */
	protected int emissionTimeStamp;

	// private int receptionTimeStamp;

	/**
	 * Instantiates a new gama message.
	 *
	 * @param scope the scope
	 * @param sender the sender
	 * @param receivers the receivers
	 * @param content the content
	 * @throws GamaRuntimeException the gama runtime exception
	 */
	public GamaMessage(final IScope scope, final Object sender, final Object receivers, final Object content)
			throws GamaRuntimeException {
		emissionTimeStamp = scope.getClock().getCycle();
		unread = true;
		setSender(sender);
		setReceivers(receivers);
		setContents(content);
	}

	/**
	 * Gets the sender.
	 *
	 * @return the sender
	 */
	/*
	 * (non-Javadoc)
	 *
	 * @see msi.gama.extensions.fipa.IGamaMessage#getSender()
	 */
	@getter(GamaMessage.SENDER)
	public Object getSender() {
		return sender;
	}

	/**
	 * Sets the sender.
	 *
	 * @param sender
	 *            the sender
	 */
	@setter(GamaMessage.SENDER)
	public void setSender(final Object sender) {
		this.sender = sender;
	}

	/**
	 * Gets the receivers.
	 *
	 * @return the receivers
	 */
	/*
	 * (non-Javadoc)
	 *
	 * @see msi.gama.extensions.fipa.IGamaMessage#getSender()
	 */
	@getter(GamaMessage.RECEIVERS)
	public Object getReceivers() {
		return receivers;
	}

	/**
	 * Sets the receivers.
	 *
	 * @param sender
	 *            the receivers
	 */
	@setter(GamaMessage.RECEIVERS)
	public void setReceivers(final Object receivers) {
		this.receivers = receivers;
	}

	/**
	 * Gets the contents of the message.
	 *
	 * @return the contents
	 */
	@getter(GamaMessage.CONTENTS)
	public Object getContents(final IScope scope) {
		setUnread(false);
		return contents;
	}

	/**
	 * Sets the contents of the message.
	 *
	 * @param content
	 *            the content
	 */
	@setter(GamaMessage.CONTENTS)
	public void setContents(final Object content) {
		contents = content;
	}

	/**
	 * Checks if is unread.
	 *
	 * @return true, if is unread
	 */
	@getter(GamaMessage.UNREAD)
	public boolean isUnread() {
		return unread;
	}

	/**
	 * Sets the unread.
	 *
	 * @param unread
	 *            the new unread
	 */
	@setter(GamaMessage.UNREAD)
	public void setUnread(final boolean unread) {
		this.unread = unread;
	}

	/**
	 * Gets the emission timestamp.
	 *
	 * @return the emission timestamp
	 */
	/*
	 * (non-Javadoc)
	 *
	 * @see msi.gama.extensions.fipa.IGamaMessage#getTimestamp()
	 */
	@getter(GamaMessage.EMISSION_TIMESTAMP)
	public int getEmissionTimestamp() {
		return emissionTimeStamp;
	}

	/**
	 * Gets the reception timestamp.
	 *
	 * @return the reception timestamp
	 */
	@getter(GamaMessage.RECEPTION_TIMESTAMP)
	public int getReceptionTimestamp() {
		return emissionTimeStamp;
	}

	@Override
	public String serialize(final boolean includingBuiltIn) {
		return StringUtils.toGaml(contents, includingBuiltIn);
	}

	@Override
	public String stringValue(final IScope scope) throws GamaRuntimeException {
		return "message[sender: " + getSender() + "; content: " + getContents(scope) + "]";
	}

	@Override
	public GamaMessage copy(final IScope scope) throws GamaRuntimeException {
		return new GamaMessage(scope, getSender(), getReceivers(), getContents(scope));
	}

	/**
	 * Method getType()
	 * 
	 * @see gama.core.common.interfaces.ITyped#getGamlType()
	 */
	@Override
	public IType<?> getGamlType() {
		return Types.get(IType.MESSAGE);
	}

	/**
	 * Checks for been received.
	 *
	 * @param scope the scope
	 */
	public void hasBeenReceived(final IScope scope) {
		// receptionTimeStamp = scope.getClock().getCycle();

	}

}
