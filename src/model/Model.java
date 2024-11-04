package model;

import java.util.HashMap;

/**
 * Model of the System which does all the computation.
 */
public interface Model {

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
  public void levelAdjust(String image, String name, int b, int m, int w);

  /**
   * The colorCorrect method corrects the color of the image by aligning the peaks of all three
   * channels.
   *
   * @param image is the Image to be worked upon.
   * @param name  is the name of Final Image to be stored.
   */
  public void colorCorrect(String image, String name);

  /**
   * The histogram function is used to create a histogram of image.
   *
   * @param image is the Image to be worked upon.
   * @param name  is the name of Final Image to be stored.
   */
  public void histogram(String image, String name);

  /**
   * The compress method compresses a particular image as per threshold value.
   *
   * @param image     is the Image to be worked upon.
   * @param name      is the name of Final Image to be stored.
   * @param threshold is the threshold value or the compression percentage under which the pixels
   *                  are reduced in size.
   * @throws IllegalArgumentException when the threshold values are not in the expected range.
   */
  public void compress(String image, String name, float threshold) throws IllegalArgumentException;

  /**
   * The splitScreen method splits the image to show the original image as well as the one after
   * performing operations.
   *
   * @param image is the Image to be worked upon.
   * @param name  is the name of Final Image to be stored.
   * @param p     is the percentage of screen after which the image is split.
   */

  public void splitScreen(String image, String name, int p);

  /**
   * The visualizeComponents method visualizes the color components.
   *
   * @param image is the Image to be worked upon.
   * @param name  is the name of Final Image to be stored.
   * @param color is the color component that needs to be visualized.
   * @throws IllegalArgumentException if the color entered is invalid.
   */
  public void visualizeComponents(String image, String name, Color color)
          throws IllegalArgumentException;

  /**
   * The visualizeVIL method creates images to visualize the Value, Intensity and Luma components.
   *
   * @param image   is the Image to be worked upon.
   * @param name    is the name of Final Image to be stored.
   * @param channel is the component to be given for computation (i.e Value, Intensity or Luma).
   * @throws IllegalArgumentException if the channel entered is invalid.
   */
  public void visualizeVIL(String image, String name, Channel channel)
          throws IllegalArgumentException;

  /**
   * The flipImage method flips the image in a particular direction.
   *
   * @param image     is the Image to be worked upon.
   * @param name      is the name of Final Image to be stored.
   * @param direction is the direction in which the image needs to be flipped.
   * @throws IllegalArgumentException if the direction entered is invalid.
   */
  public void flipImage(String image, String name, String direction) throws
          IllegalArgumentException;

  /**
   * The brightenOrDarken method either brightens or darkens the image as per user requirement.
   *
   * @param image    is the Image to be worked upon.
   * @param name     is the name of Final Image to be stored.
   * @param constant is the constant value by which the image should either brighten or darken.
   */
  public void brightenOrDarken(String image, String name, int constant);

  /**
   * The split method splits the image into its R,G,B components.
   *
   * @param image is the Image to be worked upon.
   * @param name1 is the name of Final Image to be stored having the R component.
   * @param name2 is the name of Final Image to be stored having the G component.
   * @param name3 is the name of Final Image to be stored having the B component.
   */
  public void split(String image, String name1, String name2, String name3);

  /**
   * The combineGreyscale method combines R,G,B components taken from three images in order to
   * form one color image.
   *
   * @param name   is the name of Final Image that is stored.
   * @param image1 is the image from which the Red component is taken.
   * @param image2 is the image from which the Green component is taken.
   * @param image3 is the image from which the Blue component is taken.
   */
  public void combineGreyscale(String name, String image1, String image2, String image3);

  /**
   * The blur method blurs the original image.
   *
   * @param image is the Image to be worked upon.
   * @param name  is the name of Final Image to be stored.
   */

  public void blur(String image, String name);

  /**
   * The sharpen method sharpens the original image.
   *
   * @param image is the Image to be worked upon.
   * @param name  is the name of Final Image to be stored.
   */

  public void sharpen(String image, String name);

  /**
   * The imageToSepia method adds the Sepia tone on the original image.
   *
   * @param image is the Image to be worked upon.
   * @param name  is the name of Final Image to be stored.
   */
  public void imageToSepia(String image, String name);

  /**
   * The getImages retrieves the images stored within the model.
   *
   * @return a HashMap containing image names as keys and their corresponding Pixel[][] as values.
   */

  public HashMap<String,Pixel[][]> getImages();

  /**
   * The resetModel resets the model by reconfiguring the image storage.
   * This method removes and rearranges specific images within the model.
   * It removes the "Image" entry, replaces it with the contents of "NewImage", and removes the
   * "Histogram" entry before a new one is set.
   */
  public void resetModel();
}
