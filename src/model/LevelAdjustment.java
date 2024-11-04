package model;

/**
 * The LevelAdjustment class extends all the functionalities of the Histogram class. It adjusts
 * levels as per black, white and mid-values.
 */
public class LevelAdjustment extends Histogram {
  private Pixel[][] img;
  private int w;
  private int m;
  private int b;

  /**
   * Constructs the LevelAdjustment class which takes an input image and specific parameters.
   *
   * @param img is the input image represented by a 2D array of Pixel objects.
   * @param b   is the black value given for level adjustment.
   * @param m   is the mid-value given for level adjustment.
   * @param w   is the white value given for level adjustment.
   */
  public LevelAdjustment(Pixel[][] img, int b, int m, int w) {
    super(img);
    if (b < 0 || m < 0 || w < 0 || b > 255 || m > 255 || w > 255) {
      throw new IllegalArgumentException("Illegal values entered");
    }
    this.img = img;
    this.w = w;
    this.m = m;
    this.b = b;
  }

  private int clamp(int value) {
    return Math.max(0, Math.min(value, 255));
  }

  /**
   * The execute method implements level adjustment with respect to black, mid and white values.
   *
   * @return the 2D array of Pixel objects representing the adjusted image after level adjustment.
   */
  public Pixel[][] execute() {
    Pixel[][] result = new Pixel[img.length][img[0].length];
    calculate();
    float x = b * b * (m - w) - b * (m * m - w * w) + w * m * m - m * w * w;
    float xa = -b * (128 - 255) + 128 * w - 255 * m;
    float xb = b * b * (128 - 255) + 255 * m * m - 128 * w * w;
    float xc = b * b * (255 * m - 128 * w) - b * (255 * m * m - 128 * w * w);
    float a = xa / x;
    float b = xb / x;
    float c = xc / x;

    for (int i = 0; i < img.length; i++) {
      for (int j = 0; j < img[0].length; j++) {
        int red = clamp((int) (a * img[i][j].getRed() * img[i][j].getRed()
                + b * img[i][j].getRed() + c));
        int green = clamp((int) (a * img[i][j].getGreen() * img[i][j].getGreen()
                + b * img[i][j].getGreen() + c));
        int blue = clamp((int) (a * img[i][j].getBlue() * img[i][j].getBlue()
                + b * img[i][j].getBlue() + c));
        result[i][j] = new Pixel(red, green, blue);
      }
    }

    return result;
  }
}
