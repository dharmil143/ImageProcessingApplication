package model;

import java.util.HashMap;

/**
 * The MockModel class implements the Model interface.
 */
public class MockModel implements Model {

  private HashMap<String, Pixel[][]> images;
  private Pixel[][] image;
  public boolean resetModelFunction = false;

  /**
   * Test Constructor for MockModel.
   *
   * @param images is the hashmap of images.
   */
  public MockModel(HashMap<String, Pixel[][]> images) {
    this.images = images;
    image = new Pixel[][]{
            {new Pixel(0, 0, 0), new Pixel(0, 0, 0)},
            {new Pixel(0, 0, 0), new Pixel(0, 0, 0)}
    };
  }

  @Override
  public void levelAdjust(String image, String name, int b, int m, int w) {
    images.put(name, this.image);
  }

  @Override
  public void colorCorrect(String image, String name) {
    images.put(name, this.image);
  }

  @Override
  public void histogram(String image, String name) {
    images.put(name, this.image);
  }

  @Override
  public void compress(String image, String name, float threshold)
          throws IllegalArgumentException {
    images.put(name, this.image);
  }

  @Override
  public void splitScreen(String image, String name, int p) {
    images.put(name, this.image);
  }

  @Override
  public void visualizeComponents(String image, String name, Color color)
          throws IllegalArgumentException {
    images.put(name, this.image);
  }

  @Override
  public void visualizeVIL(String image, String name, Channel channel)
          throws IllegalArgumentException {
    images.put(name, this.image);
  }

  @Override
  public void flipImage(String image, String name, String direction)
          throws IllegalArgumentException {
    images.put(name, this.image);
  }

  @Override
  public void brightenOrDarken(String image, String name, int constant) {
    images.put(name, this.image);
  }

  @Override
  public void split(String image, String name1, String name2, String name3) {
    images.put(name1, this.image);
    images.put(name2, this.image);
    images.put(name3, this.image);
  }

  @Override
  public void combineGreyscale(String name, String image1, String image2, String image3) {
    images.put(name, this.image);
  }

  @Override
  public void blur(String image, String name) {
    images.put(name, this.image);
  }

  @Override
  public void sharpen(String image, String name) {
    images.put(name, this.image);
  }

  @Override
  public void imageToSepia(String image, String name) {
    images.put(name, this.image);
  }

  @Override
  public HashMap<String, Pixel[][]> getImages() {
    return images;
  }

  @Override
  public void resetModel() {
    resetModelFunction = true;
  }
}
