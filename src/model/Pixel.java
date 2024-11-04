package model;

/**
 * This class represents a single pixel in the image.
 */
public class Pixel {
  private int r;
  private int g;
  private int b;

  /**
   * The constructor initialises the red, green and blue components of the pixel.
   *
   * @param r is the red component of the pixel.
   * @param g is the green component of the pixel.
   * @param b is the blue component of the pixel.
   * @throws IllegalArgumentException when the R,G,B values entered are invalid.
   */
  public Pixel(int r, int g, int b) throws IllegalArgumentException {
    if (r < 0 || g < 0 || b < 0) {
      throw new IllegalArgumentException("Invalid R,G,B values");
    }
    if (r > 255 || g > 255 || b > 255) {
      throw new IllegalArgumentException("Invalid R,G,B values");
    }
    this.r = r;
    this.g = g;
    this.b = b;
  }

  /**
   * The getRed method retrieves the red component of the Pixel.
   *
   * @return An integer representing the red component value.
   */

  public int getRed() {
    return r;
  }

  /**
   * The getBlue method retrieves the blue component of the Pixel.
   *
   * @return An integer representing the blue component value.
   */
  public int getBlue() {
    return b;
  }

  /**
   * The getGreen method retrieves the green component of the Pixel.
   *
   * @return An integer representing the green component value.
   */
  public int getGreen() {
    return g;
  }

  /**
   * The getValue method retrieves the value property of the Pixel.
   *
   * @return An integer representing the value property.
   */
  public int getValue() {
    return Math.max(Math.max(r, g), b);
  }

  /**
   * The getIntensity method retrieves the intensity property of the Pixel.
   *
   * @return A float representing the intensity property.
   */
  public float getIntensity() {
    return (float) (r + g + b) / 3;
  }

  /**
   * The getLuma method retrieves the luma property of the Pixel.
   *
   * @return A double representing the luma property.
   */
  public double getLuma() {
    return 0.2126 * r + 0.7152 * g + 0.0722 * b;
  }

  /**
   * The setColor method sets the components of the pixel with respect to the color.
   *
   * @param v is the value of the pixel.
   * @param c is the color of the component.
   */
  public void setColor(int v, Color c) {
    if (c == Color.RED) {
      this.r = clamp(v);
    } else if (c == Color.GREEN) {
      this.g = clamp(v);
    } else if (c == Color.BLUE) {
      this.b = clamp(v);
    }
  }

  private int clamp(int num) {
    return Math.max(0, Math.min(num, 255));
  }

}
