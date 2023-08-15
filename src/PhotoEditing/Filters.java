package PhotoEditing;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Filters {
	 /**
     * Applies a blur filter to the given image using a specified kernel size.
     *
     * @param img     The BufferedImage to apply the blur filter to.
     * @param kernel  The size of the blur filter kernel.
     * @return        The resulting blurred image as a BufferedImage.
     */
	public static BufferedImage blurFilter(BufferedImage img, int kernel) {
		ImageMatrix image = new ImageMatrix(img);
		ImageMatrix writer = new ImageMatrix(img.getWidth(), img.getHeight());

		for (int i = kernel; i < image.getWidth() - kernel; i++) {
			for (int j = kernel; j < image.getHeight() - kernel; j++) {
				try {
					int red = 0;
					int green = 0;
					int blue = 0;

					for (int i2 = i - kernel; i2 < i + kernel + 1; i2++) {
						for (int j2 = j - kernel; j2 < j + kernel + 1; j2++) {
							red += image.getRed(i2, j2);
							green += image.getGreen(i2, j2);
							blue += image.getBlue(i2, j2);
						}
					}
					red /= ((2 * kernel) + 1) * ((2 * kernel) + 1);
					green /= ((2 * kernel) + 1) * ((2 * kernel) + 1);
					blue /= ((2 * kernel) + 1) * ((2 * kernel) + 1);
					writer.setRGB(i, j, ImageMatrix.convertRGB(red, green, blue));
				} catch (IndexOutOfBoundsException e) {
					e.printStackTrace();
				}
			}
		}

		BufferedImage output = writer.getBufferedImage();
		return output;
	}

	/**
	 * Applies a sharpen filter to the given image using a specified kernel size.
	 *
	 * @param img     The BufferedImage to apply the sharpen filter to.
	 * @param kernel  The size of the sharpen filter kernel.
	 * @return        The resulting sharpened image as a BufferedImage.
	 */
	public static BufferedImage sharpenFilter(BufferedImage img, int kernel) {
		ImageMatrix image = new ImageMatrix(img);
		ImageMatrix write = new ImageMatrix(img.getWidth(), img.getHeight());

		for (int i = kernel; i < image.getWidth() - kernel; i++) {
			for (int j = kernel; j < image.getHeight() - kernel; j++) {
				try {
					int red = 0;
					int green = 0;
					int blue = 0;

					for (int i2 = i - kernel; i2 < i + kernel + 1; i2++) {
						for (int j2 = j - kernel; j2 < j + kernel + 1; j2++) {
							red += image.getRed(i2, j2);
							green += image.getGreen(i2, j2);
							blue += image.getBlue(i2, j2);
						}
					}
					red /= ((2 * kernel) + 1) * ((2 * kernel) + 1);
					green /= ((2 * kernel) + 1) * ((2 * kernel) + 1);
					blue /= ((2 * kernel) + 1) * ((2 * kernel) + 1);
					write.setRGB(i, j,
							ImageMatrix.convertRGB(isBetween0and255(Math.abs(2 * image.getRed(i, j) - red)),
									isBetween0and255(Math.abs(2 * image.getGreen(i, j) - green)),
									isBetween0and255(Math.abs(2 * image.getBlue(i, j) - blue))));
				} catch (IndexOutOfBoundsException e) {
					write.setRGB(i, j, image.getRGB(i, j));
				}
			}
		}

		BufferedImage output = write.getBufferedImage();
		return output;
	}

	/**
	 * Applies a grayscale filter to the given image with a specified change factor.
	 *
	 * @param img     The BufferedImage to apply the grayscale filter to.
	 * @param change  The change factor to adjust the grayscale intensity. Values between 0.0 and 1.0 are recommended.
	 * @return        The resulting grayscale image as a BufferedImage.
	 */
	public static BufferedImage grayscaleFilter(BufferedImage img, double change) {
		ImageMatrix image = new ImageMatrix(img);
		ImageMatrix write = new ImageMatrix(img.getWidth(), img.getHeight());

		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {
				double average = (image.getRed(i, j) + image.getGreen(i, j) + image.getBlue(i, j)) / 3;
				write.setRGB(i, j, ImageMatrix.convertRGB((int) Math.floor(average * change),
						(int) Math.floor(average * change), (int) Math.floor(average * change)));
			}
		}

		BufferedImage output = write.getBufferedImage();
		return output;
	}

	/**
	 * Applies an edge detection filter to the given image using the specified kernel size.
	 *
	 * @param img     The BufferedImage to apply the edge detection filter to.
	 * @param kernel  The size of the kernel for the edge detection. Must be an odd integer.
	 * @return        The resulting image with edge detection applied as a BufferedImage.
	 */
	public static BufferedImage edgeDetectionFilter(BufferedImage img, int kernel) {
		ImageMatrix image = new ImageMatrix(img);
		ImageMatrix write = new ImageMatrix(image.getWidth(), image.getHeight());

		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {
				try {
					int red = 0;
					int green = 0;
					int blue = 0;

					for (int i2 = i - kernel; i2 < i + kernel + 1; i2++) {
						red -= image.getRed(i2, j);
						green -= image.getGreen(i2, j);
						blue -= image.getBlue(i2, j);
					}

					for (int j2 = j - kernel; j2 < j + kernel + 1; j2++) {
						red -= image.getRed(i, j2);
						green -= image.getGreen(i, j2);
						blue -= image.getBlue(i, j2);
					}

					red = red + (4 * kernel + 2) * image.getRed(i, j);
					green = green + (4 * kernel + 2) * image.getGreen(i, j);
					blue = blue + (4 * kernel + 2) * image.getBlue(i, j);
					write.setRGB(i, j, ImageMatrix.convertRGB(isBetween0and255(red), isBetween0and255(green),
							isBetween0and255(blue)));
				} catch (IndexOutOfBoundsException e) {
					write.setRGB(i, j, image.getRGB(i, j));
				}
			}
		}

		BufferedImage output = write.getBufferedImage();
		return output;
	}

	/**
	 * Adjusts the brightness of the given image by applying the specified change value to each pixel.
	 *
	 * @param img     The BufferedImage to adjust the brightness of.
	 * @param change  The value to change the brightness. Positive values increase brightness, negative values decrease brightness.
	 * @return        The resulting image with adjusted brightness as a BufferedImage.
	 */
	public static BufferedImage brightnessFilter(BufferedImage img, int change) {
		ImageMatrix image = new ImageMatrix(img);
		ImageMatrix write = new ImageMatrix(img.getWidth(), img.getHeight());

		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {
				write.setRGB(i, j,
						ImageMatrix.convertRGB(isBetween0and255(image.getRed(i, j) + change),
								isBetween0and255(image.getGreen(i, j) + change),
								isBetween0and255(image.getBlue(i, j) + change)));
			}
		}

		BufferedImage output = write.getBufferedImage();
		return output;
	}

	/**
	 * Adjusts the contrast of the given image by applying the specified change value to each pixel.
	 *
	 * @param img     The BufferedImage to adjust the contrast of.
	 * @param change  The value to change the contrast. Values less than 1 decrease contrast, values greater than 1 increase contrast.
	 * @return        The resulting image with adjusted contrast as a BufferedImage.
	 */
	public static BufferedImage contrastFilter(BufferedImage img, double change) {
		int width = img.getWidth();
		int height = img.getHeight();

		BufferedImage filteredImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		// Apply contrast filter to each pixel
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				Color originalColor = new Color(img.getRGB(x, y));
				int red = originalColor.getRed();
				int green = originalColor.getGreen();
				int blue = originalColor.getBlue();

				// Apply contrast change
				red = applyContrast(red, change);
				green = applyContrast(green, change);
				blue = applyContrast(blue, change);

				// Create new color with adjusted contrast
				Color newColor = new Color(red, green, blue);
				filteredImage.setRGB(x, y, newColor.getRGB());
			}
		}

		return filteredImage;
	}
	
	/**
	 * Applies contrast adjustment to the given value based on the specified change.
	 *
	 * @param value   The original value to apply contrast adjustment to.
	 * @param change  The contrast change to apply. Positive values increase contrast, negative values decrease contrast.
	 * @return        The adjusted value after applying contrast.
	 */
	private static int applyContrast(int value, double change) {
		// Apply contrast formula
		double newValue = value / 255.0;
		newValue -= 0.5;
		newValue *= change;
		newValue += 0.5;
		newValue *= 255;

		// Ensure the value is within valid range
		newValue = Math.max(0, Math.min(255, newValue));

		return (int) newValue;
	}
	
	/**
	 * Checks if the given value is between 0 and 255 (inclusive).
	 * If the value is greater than 255, it returns 255.
	 * If the value is less than 0, it returns 0.
	 * Otherwise, it returns the original value.
	 *
	 * @param a  The value to check.
	 * @return   The value clamped between 0 and 255.
	 */
	public static int isBetween0and255(int a) {
		if (a >= 255) {
			return 255;
		} else if (a <= 0) {
			return 0;
		} else {
			return a;
		}
	}
}
