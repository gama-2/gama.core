/*******************************************************************************************************
 *
 * IExpressionDescription.java, in msi.gama.core, is part of the source code of the GAMA modeling and simulation
 * platform (v.1.9.0).
 *
 * (c) 2007-2022 UMI 209 UMMISCO IRD/SU & Partners (IRIT, MIAT, TLU, CTU)
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gaml.core.descriptions;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;

import gama.annotations.common.interfaces.IDisposable;
import gama.annotations.common.interfaces.IGamlable;
import gaml.core.expressions.IExpression;
import gaml.core.types.IType;

/**
 * The class IExpressionDescription.
 *
 * @author drogoul
 * @since 31 mars 2012
 *
 */
public interface IExpressionDescription extends IGamlable, IDisposable {

	/**
	 * Sets the expression.
	 *
	 * @param expr
	 *            the new expression
	 */
	void setExpression(final IExpression expr);

	/**
	 * Compile.
	 *
	 * @param context
	 *            the context
	 * @return the i expression
	 */
	IExpression compile(final IDescription context);

	/**
	 * Gets the expression.
	 *
	 * @return the expression
	 */
	IExpression getExpression();

	/**
	 * Compile as label.
	 *
	 * @return the i expression description
	 */
	IExpressionDescription compileAsLabel();

	/**
	 * Equals string.
	 *
	 * @param o
	 *            the o
	 * @return true, if successful
	 */
	boolean equalsString(String o);

	/**
	 * Gets the target.
	 *
	 * @return the target
	 */
	EObject getTarget();

	/**
	 * Sets the target.
	 *
	 * @param target
	 *            the new target
	 */
	void setTarget(EObject target);

	/**
	 * Checks if is const.
	 *
	 * @return true, if is const
	 */
	boolean isConst();

	/**
	 * Gets the strings.
	 *
	 * @param context
	 *            the context
	 * @param skills
	 *            the skills
	 * @return the strings
	 */
	Collection<String> getStrings(IDescription context, boolean skills);

	/**
	 * Clean copy.
	 *
	 * @return the i expression description
	 */
	IExpressionDescription cleanCopy();

	/**
	 * Gets the denoted type.
	 *
	 * @param context
	 *            the context
	 * @return the denoted type
	 */
	IType<?> getDenotedType(IDescription context);

	// void collectMetaInformation(GamlProperties meta);

}