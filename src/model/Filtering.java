package model;

/**
 * Filtering is a Helper class used to perform the blur and sharpen operations on an image.
 */
public class Filtering {
  private Pixel[][] image;
  private float[][] filter;

  /**
   * The Constructor initialises an image and a filter as set by the user.
   *
   * @param image  is the image represented as a 2D array of type Pixel.
   * @param filter is the matrix of values used to filter the image which is represented by a 2D
   *               array of type float.
   * @throws IllegalArgumentException when the filter entered is invalid.
   */
  public Filtering(Pixel[][] image, float[][] filter) throws IllegalArgumentException {
    if (image.length < filter.length) {
      throw new IllegalArgumentException("Illegal Filter Entered");
    }
    this.image = image;
    this.filter = filter;
  }

  /**
   * The filter method calculates the final image after computations of the filter on the initial
   * image.
   *
   * @return the final image after applying the filter.
   */
  public Pixel[][] filter() {
    for (int i = 0; i < image.length; i++) {
      for (int j = 0; j < image[0].length; j++) {
        for (Color c : Color.values()) {
          float[][] small = findSmallerArray(i, j, c);
          int value = computeForOnePixel(image[i][j], small);
          image[i][j].setColor(value, c);
        }
      }
    }
    return image;
  }

  private int computeForOnePixel(Pixel p, float[][] smallArray) {
    float result = 0;
    for (int i = 0; i < smallArray.length; i++) {
      for (int j = 0; j < smallArray[0].length; j++) {
        if (smallArray[i][j] != 0) {
          result += (smallArray[i][j] * filter[i][j]);
        }
      }
    }
    return clamp((int) result);
  }

  private int clamp(int value) {
    return Math.max(0, Math.min(value, 255));
  }

  private float[][] findSmallerArray(int i, int j, Color color) {
    int x = (int) filter.length / 2;
    float[][] result = new float[filter.length][filter[0].length];
    for (int a = -x; a < x + 1; a++) {
      for (int b = -x; b < x + 1; b++) {
        if (i + a >= 0 && i + a < image.length && j + b >= 0 && j + b < image[0].length) {
          if (color == Color.BLUE) {
            result[a + x][b + x] = image[i + a][j + b].getBlue();
          } else if (color == Color.GREEN) {
            result[a + x][b + x] = image[i + a][j + b].getGreen();
          } else if (color == Color.RED) {
            result[a + x][b + x] = image[i + a][j + b].getRed();
          }
        }
      }
    }
    return result;
  }
}
