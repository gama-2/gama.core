/*******************************************************************************************************
 *
 * GamaGifFile.java, in msi.gama.core, is part of the source code of the GAMA modeling and simulation platform
 * (v.1.9.0).
 *
 * (c) 2007-2022 UMI 209 UMMISCO IRD/SU & Partners (IRIT, MIAT, TLU, CTU)
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gama.core.util.file;

import gama.annotations.precompiler.IConcept;
import gama.annotations.precompiler.GamlAnnotations.doc;
import gama.annotations.precompiler.GamlAnnotations.example;
import gama.annotations.precompiler.GamlAnnotations.file;
import gama.core.common.util.ImageUtils;
import gama.core.runtime.IScope;
import gama.core.runtime.exceptions.GamaRuntimeException;
import gama.core.util.matrix.IMatrix;
import gaml.core.types.IType;

/**
 * The Class GamaGifFile.
 */
@file (
		name = "gif",
		extensions = { "gif" },
		buffer_type = IType.MATRIX,
		buffer_content = IType.INT,
		buffer_index = IType.POINT,
		concept = { IConcept.IMAGE, IConcept.FILE },
		doc = @doc ("GIF files represent a particular type of image files, which can be animated"))
public class GamaGifFile extends GamaImageFile {

	// private int averageDelay;
	/**
	 * Instantiates a new gama gif file.
	 *
	 * @param scope
	 *            the scope
	 * @param pathName
	 *            the path name
	 * @throws GamaRuntimeException
	 *             the gama runtime exception
	 */
	// private int frameCount;
	@doc (
			value = "This file constructor allows to read a gif file",
			examples = { @example (
					value = "gif_file f <- gif_file(\"file.gif\");",
					isExecutable = false) })
	public GamaGifFile(final IScope scope, final String pathName) throws GamaRuntimeException {
		super(scope, pathName);
	}

	/**
	 * Instantiates a new gama gif file.
	 *
	 * @param scope
	 *            the scope
	 * @param pathName
	 *            the path name
	 * @param image
	 *            the image
	 */
	@doc (
			value = "This file constructor allows to store a matrix in a gif file (it does not save it - just store it in memory)",
			examples = { @example (
					value = "gif_file f <- gif_file(\"file.gif\",matrix([10,10],[10,10]));",
					isExecutable = false) })

	public GamaGifFile(final IScope scope, final String pathName, final IMatrix<Integer> image) {
		super(scope, pathName, image);

	}

	@Override
	public boolean isAnimated() { return ImageUtils.getInstance().getFrameCount(localPath) > 0; }

	/**
	 * Gets the average delay.
	 *
	 * @return the average delay
	 */
	// public int getAverageDelay() {
	// return ImageUtils.getInstance().getDuration(localPath) / getFrameCount();
	// }

	// /**
	// * Gets the frame count.
	// *
	// * @return the frame count
	// */
	// public int getFrameCount() { return ImageUtils.getInstance().getFrameCount(localPath); }

	@Override
	protected String getHttpContentType() { return "image/gif"; }

}
