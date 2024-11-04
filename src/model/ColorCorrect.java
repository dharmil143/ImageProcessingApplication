package model;

/**
 * The ColorCorrect class extends all the functionalities from the Histogram class. This class
 * performs color correction on the input image. It adjusts R,G,B values of the pixels so that the
 * color histogram are balanced.
 */
public class ColorCorrect extends Histogram {

  private Pixel[][] img;

  /**
   * Constructs the ColorCorrect class that initializes an image in the form of a 2D array made of
   * Pixel objects.
   *
   * @param img is the input image in the form of a 2D array of type Pixel.
   */
  public ColorCorrect(Pixel[][] img) {
    super(img);
    this.img = img;
  }

  private int maxFrequancy(int[] arr) {
    int max = 0;
    for (int i = 1; i < arr.length; i++) {
      if (arr[i] > arr[max]) {
        max = i;
      }
    }
    return max;
  }

  private int check(int i) {
    if (i < 0) {
      return 0;
    } else if (i > 255) {
      return 255;
    } else {
      return i;
    }
  }

  /**
   * The execute method performs color correction as per the calculated Histogram values.
   *
   * @return a 2D array of type Pixel after color correction.
   */
  @Override
  public Pixel[][] execute() {
    Pixel[][] result = new Pixel[img.length][img[0].length];
    calculate();
    int maxR = maxFrequancy(redValues);
    int maxG = maxFrequancy(greenValues);
    int maxB = maxFrequancy(blueValues);
    int average = (maxR + maxB + maxG) / 3;

    for (int i = 0; i < img.length; i++) {
      for (int j = 0; j < img[0].length; j++) {
        int vR = (check(img[i][j].getRed() + (average - maxR)) < 10
                || check(img[i][j].getRed() + (average - maxR)) > 245)
                ? img[i][j].getRed() :
                check(img[i][j].getRed() + (average - maxR));
        int vG = (check(img[i][j].getGreen() + (average - maxG)) < 10
                || check(img[i][j].getGreen() + (average - maxG)) > 245)
                ? img[i][j].getRed()
                : check(img[i][j].getGreen() + (average - maxG));
        int vB = (check(img[i][j].getBlue() + (average - maxB)) < 10
                || check(img[i][j].getBlue() + (average - maxB)) > 245)
                ? img[i][j].getBlue()
                : check(img[i][j].getBlue() + (average - maxB));
        result[i][j] = new Pixel(vR, vG, vB);
      }
    }
    return result;
  }
}
