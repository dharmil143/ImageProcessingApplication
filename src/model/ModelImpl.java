package model;

import java.util.HashMap;
import java.util.Objects;
import java.util.function.Function;

/**
 * The ModelImpl class is the implementation of the Model Interface used for Image Processing
 * functions.
 */
public class ModelImpl implements Model {
  private HashMap<String, Pixel[][]> images;

  /**
   * The ModelImpl constructor takes in a HashMap of String and Pixel[][] to store images.
   *
   * @param images represents the Hashmap to store all the images.
   */
  public ModelImpl(HashMap<String, Pixel[][]> images) {
    this.images = images;
  }

  /**
   * The levelAdjust method adjusts shadow and highlight levels of the image.
   *
   * @param image is the Image to be worked upon.
   * @param name  is the name of Final Image to be stored.
   * @param b     is the black value.
   * @param m     is the mid value.
   * @param w     is the white value.
   * @throws IllegalArgumentException throws error if b>m>w.
   */

  @Override
  public void levelAdjust(String image, String name, int b, int m, int w)
          throws IllegalArgumentException {
    if (b > m || m > w || b > w) {
      throw new IllegalArgumentException("Invalid Values");
    }
    Pixel[][] i = images.get(image);
    Histogram h = new LevelAdjustment(i, b, m, w);
    images.put(name, h.execute());
  }

  /**
   * The colorCorrect method corrects the color of the image by aligning the peaks of all three
   * channels.
   *
   * @param image is the Image to be worked upon.
   * @param name  is the name of Final Image to be stored.
   */
  @Override
  public void colorCorrect(String image, String name) {
    Pixel[][] i = images.get(image);
    Histogram h = new ColorCorrect(i);
    images.put(name, h.execute());
  }

  /**
   * The histogram function is used to create a histogram of image.
   *
   * @param image is the Image to be worked upon.
   * @param name  is the name of Final Image to be stored.
   */
  @Override
  public void histogram(String image, String name) {
    Pixel[][] i = images.get(image);
    Histogram h = new Histogram(i);
    images.put(name, h.execute());
  }

  /**
   * The compress method compresses a particular image as per threshold value.
   *
   * @param image     is the Image to be worked upon.
   * @param name      is the name of Final Image to be stored.
   * @param threshold is the threshold value or the compression percentage under which the pixels
   *                  are reduced in size.
   * @throws IllegalArgumentException when the threshold values are not in the expected range.
   */
  @Override
  public void compress(String image, String name, float threshold) throws IllegalArgumentException {
    if (threshold < 0 || threshold > 100) {
      throw new IllegalArgumentException("Illegal threshold value");
    }
    Pixel[][] i = images.get(image);
    Compression c = new Compression(i, threshold);
    images.put(name, c.execute());
  }

  /**
   * The splitScreen method splits the image to show the original image as well as the one after
   * performing operations.
   *
   * @param image is the Image to be worked upon.
   * @param name  is the name of Final Image to be stored.
   * @param p     is the percentage of screen after which the image is split.
   */
  @Override
  public void splitScreen(String image, String name, int p) {
    Pixel[][] im = images.get(image);
    Pixel[][] im2 = images.get(name);
    Pixel[][] result = new Pixel[im.length][im[0].length];
    for (int i = 0; i < im.length; i++) {
      for (int j = 0; j < im[0].length; j++) {
        result[i][j] = (i < (im.length * p / 100)) ? im2[i][j] : im[i][j];
      }
    }
    images.remove(image);
    images.put(image, result);
  }

  /**
   * The visualizeComponents method visualizes the color components.
   *
   * @param image is the Image to be worked upon.
   * @param name  is the name of Final Image to be stored.
   * @param color is the color component that needs to be visualized.
   * @throws IllegalArgumentException if the color entered is invalid.
   */
  @Override
  public void visualizeComponents(String image, String name, Color color)
          throws IllegalArgumentException {
    if (color == null) {
      throw new IllegalArgumentException("Illegal Color Entered");
    }
    Pixel[][] im = images.get(image);
    Pixel[][] result = new Pixel[im.length][im[0].length];
    for (int i = 0; i < im.length; i++) {
      for (int j = 0; j < im[0].length; j++) {
        result[i][j] = helperVisualize(im, i, j, color);
      }
    }
    images.put(name, result);
  }

  private Pixel helperVisualize(Pixel[][] image, int i, int j, Color color) {
    if (color == Color.RED) {
      return new Pixel(image[i][j].getRed(), 0, 0);
    }
    if (color == Color.BLUE) {
      return new Pixel(0, 0, image[i][j].getBlue());
    }
    if (color == Color.GREEN) {
      return new Pixel(0, image[i][j].getGreen(), 0);
    }
    return null;
  }

  /**
   * The visualizeVIL method creates images to visualize the Value, Intensity and Luma components.
   *
   * @param image   is the Image to be worked upon.
   * @param name    is the name of Final Image to be stored.
   * @param channel is the component to be given for computation (i.e Value, Intensity or Luma).
   * @throws IllegalArgumentException if the channel entered is invalid.
   */
  @Override
  public void visualizeVIL(String image, String name, Channel channel)
          throws IllegalArgumentException {
    if (channel == null) {
      throw new IllegalArgumentException("Illegal Channel Entered");
    }
    Pixel[][] im = images.get(image);
    Pixel[][] result = new Pixel[im.length][im[0].length];
    Function<Integer, Pixel> function = c -> new Pixel(c, c, c);
    for (int i = 0; i < im.length; i++) {
      for (int j = 0; j < im[0].length; j++) {
        switch (channel) {
          case VALUE:
            result[i][j] = function.apply(im[i][j].getValue());
            break;
          case INTENSITY:
            result[i][j] = function.apply((int) im[i][j].getIntensity());
            break;
          case LUMA:
            result[i][j] = function.apply((int) im[i][j].getLuma());
            break;
          default:
            result[i][j] = null;
        }
      }
    }
    images.put(name, result);
  }

  /**
   * The flipImage method flips the image in a particular direction.
   *
   * @param image     is the Image to be worked upon.
   * @param name      is the name of Final Image to be stored.
   * @param direction is the direction in which the image needs to be flipped.
   * @throws IllegalArgumentException if the direction entered is invalid.
   */
  @Override
  public void flipImage(String image, String name, String direction) throws
          IllegalArgumentException {
    if (!Objects.equals(direction, "horizontal") && !Objects.equals(direction, "vertical")) {
      throw new IllegalArgumentException("Illegal Direction Entered");
    }
    Pixel[][] im = images.get(image);
    Pixel[][] result = new Pixel[im.length][im[0].length];
    if (direction.equals("vertical")) {
      for (int i = 0; i < im.length; i++) {
        for (int j = 0; j < im[0].length; j++) {
          result[i][j] = im[i][im[0].length - j - 1];
        }
      }
    }
    if (direction.equals("horizontal")) {
      for (int i = 0; i < im.length; i++) {
        for (int j = 0; j < im[0].length; j++) {
          result[i][j] = im[im.length - i - 1][j];
        }
      }
    }
    images.put(name, result);
  }

  /**
   * The brightenOrDarken method either brightens or darkens the image as per user requirement.
   *
   * @param image    is the Image to be worked upon.
   * @param name     is the name of Final Image to be stored.
   * @param constant is the constant value by which the image should either brighten or darken.
   */
  @Override
  public void brightenOrDarken(String image, String name, int constant) {
    Pixel[][] im = images.get(image);
    Pixel[][] result = new Pixel[im.length][im[0].length];
    for (int i = 0; i < im.length; i++) {
      for (int j = 0; j < im[0].length; j++) {
        result[i][j] = new Pixel(clamp(im[i][j].getRed() + constant),
                clamp(im[i][j].getGreen() + constant),
                clamp(im[i][j].getBlue() + constant));
      }
    }
    images.put(name, result);
  }

  private int clamp(int value) {
    return Math.max(0, Math.min(value, 255));
  }

  /**
   * The split method splits the image into its R,G,B components.
   *
   * @param image is the Image to be worked upon.
   * @param name1 is the name of Final Image to be stored having the R component.
   * @param name2 is the name of Final Image to be stored having the G component.
   * @param name3 is the name of Final Image to be stored having the B component.
   */

  @Override
  public void split(String image, String name1, String name2, String name3) {
    Pixel[][] im = images.get(image);
    for (int i = 0; i < 3; i++) {
      Pixel[][] result = new Pixel[im.length][im[0].length];
      if (i == 0) {
        for (int k = 0; k < im.length; k++) {
          for (int j = 0; j < im[0].length; j++) {
            result[k][j] = new Pixel(im[k][j].getRed(), 0, 0);
          }
        }
        images.put(name1, result);
      }
      if (i == 1) {
        for (int k = 0; k < im.length; k++) {
          for (int j = 0; j < im[0].length; j++) {
            result[k][j] = new Pixel(0, im[k][j].getGreen(), 0);
          }
        }
        images.put(name2, result);
      }
      if (i == 2) {
        for (int k = 0; k < im.length; k++) {
          for (int j = 0; j < im[0].length; j++) {
            result[k][j] = new Pixel(0, 0, im[k][j].getBlue());
          }
        }
        images.put(name3, result);
      }
    }
  }

  /**
   * The combineGreyscale method combines R,G,B components taken from three images in order to
   * form one color image.
   *
   * @param name   is the name of Final Image that is stored.
   * @param image1 is the image from which the Red component is taken.
   * @param image2 is the image from which the Green component is taken.
   * @param image3 is the image from which the Blue component is taken.
   */
  @Override
  public void combineGreyscale(String name, String image1, String image2, String image3) {
    Pixel[][] im1 = images.get(image1);
    Pixel[][] im2 = images.get(image2);
    Pixel[][] im3 = images.get(image3);

    Pixel[][] result = new Pixel[im1.length][im1[0].length];
    for (int i = 0; i < im1.length; i++) {
      for (int j = 0; j < im1[0].length; j++) {
        result[i][j] = new Pixel(im1[i][j].getRed(),
                im2[i][j].getGreen(),
                im3[i][j].getBlue());
      }
    }
    images.put(name, result);
  }

  /**
   * The blur method blurs the original image.
   *
   * @param image is the Image to be worked upon.
   * @param name  is the name of Final Image to be stored.
   */

  @Override
  public void blur(String image, String name) {
    Pixel[][] im = images.get(image);
    float[][] filter = {{0.0625F, 0.125F, 0.0625F}, {0.125F, 0.25F, 0.125F}, {0.0625F, 0.125F,
            0.0625F}};
    Pixel[][] newImage = new Pixel[im.length][im[0].length];
    for (int i = 0; i < im.length; i++) {
      for (int j = 0; j < im[0].length; j++) {
        newImage[i][j] = new Pixel(0, 0, 0);
        newImage[i][j].setColor(im[i][j].getRed(), Color.RED);
        newImage[i][j].setColor(im[i][j].getGreen(), Color.GREEN);
        newImage[i][j].setColor(im[i][j].getBlue(), Color.BLUE);
      }
    }
    Filtering f = new Filtering(newImage, filter);
    Pixel[][] result = f.filter();
    images.put(name, result);
  }

  /**
   * The sharpen method sharpens the original image.
   *
   * @param image is the Image to be worked upon.
   * @param name  is the name of Final Image to be stored.
   */
  @Override
  public void sharpen(String image, String name) {
    Pixel[][] im = images.get(image);
    float[][] filter = {{-0.125F, -0.125F, -0.125F, -0.125F, -0.125F},
                        {-0.125F, 0.25F, 0.25F, 0.25F, -0.125F},
                        {-0.125F, 0.25F, 1F, 0.25F, -0.125F},
                        {-0.125F, 0.25F, 0.25F, 0.25F, -0.125F},
                        {-0.125F, -0.125F, -0.125F, -0.125F, -0.125F}};
    Pixel[][] newImage = new Pixel[im.length][im[0].length];
    for (int i = 0; i < im.length; i++) {
      for (int j = 0; j < im[0].length; j++) {
        newImage[i][j] = new Pixel(0, 0, 0);
        newImage[i][j].setColor(im[i][j].getRed(), Color.RED);
        newImage[i][j].setColor(im[i][j].getGreen(), Color.GREEN);
        newImage[i][j].setColor(im[i][j].getBlue(), Color.BLUE);
      }
    }
    Filtering f = new Filtering(newImage, filter);
    Pixel[][] result = f.filter();
    images.put(name, result);
  }

  /**
   * The imageToSepia method adds the Sepia tone on the original image.
   *
   * @param image is the Image to be worked upon.
   * @param name  is the name of Final Image to be stored.
   */
  @Override
  public void imageToSepia(String image, String name) {
    Pixel[][] im = images.get(image);
    Pixel[][] result = new Pixel[im.length][im[0].length];
    for (int i = 0; i < im.length; i++) {
      for (int j = 0; j < im[0].length; j++) {
        int r_ = (int) (0.393 * im[i][j].getRed()
                + 0.769 * im[i][j].getGreen()
                + 0.189 * im[i][j].getBlue());
        int g_ = (int) (0.349 * im[i][j].getRed()
                + 0.686 * im[i][j].getGreen()
                + 0.168 * im[i][j].getBlue());
        int b_ = (int) (0.272 * im[i][j].getRed()
                + 0.534 * im[i][j].getGreen()
                + 0.131 * im[i][j].getBlue());
        result[i][j] = new Pixel(clamp(r_), clamp(g_), clamp(b_));
      }
    }
    images.put(name, result);
  }

  /**
   * The getImages retrieves the images stored within the model.
   *
   * @return a HashMap containing image names as keys and their corresponding Pixel[][] as values.
   */
  @Override
  public HashMap<String, Pixel[][]> getImages() {
    return images;
  }

  /**
   * The resetModel resets the model by reconfiguring the image storage.
   * This method removes and rearranges specific images within the model.
   * It removes the "Image" entry, replaces it with the contents of "NewImage", and removes the
   * "Histogram" entry before a new one is set.
   */
  @Override
  public void resetModel() {
    images.remove("Image");
    Pixel[][] a = images.get("NewImage");
    images.remove("NewImage");
    images.put("Image", a);
    images.remove("Histogram");
  }
}
