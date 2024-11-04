package model;

import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * The Histogram class generates a graphical representation of RGB color histograms based on an
 * input image. It provides methods and functionalities that can calculate values, draw lines,
 * grids and help visualize various operations.
 */

public class Histogram {
  private int[][] redImg;
  private int[][] greenImg;
  private int[][] blueImg;

  protected int[] redValues;
  protected int[] greenValues;
  protected int[] blueValues;

  private static BufferedImage i;

  /**
   * Constructs the Histogram class which initialises an input image shown by a 2D array of Pixel
   * objects.
   *
   * @param img is the input image represented by a 2D array of type Pixel.
   */
  public Histogram(Pixel[][] img) {
    redImg = new int[img.length][img[0].length];
    blueImg = new int[img.length][img[0].length];
    greenImg = new int[img.length][img[0].length];
    for (int i = 0; i < img.length; i++) {
      for (int j = 0; j < img[0].length; j++) {
        redImg[i][j] = img[i][j].getRed();
        blueImg[i][j] = img[i][j].getBlue();
        greenImg[i][j] = img[i][j].getGreen();
      }
    }

    this.i = new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB);
  }

  private void drawGrid() {
    Graphics2D g = i.createGraphics();
    g.setBackground(Color.WHITE);
    g.fillRect(0, 0, 255, 255);
    g.setColor(Color.LIGHT_GRAY);
    g.setStroke(new BasicStroke(1));

    g.drawLine(0, 0, 0, 255);
    g.drawLine(255, 0, 255, 255);
    g.drawLine(0, 0, 255, 0);
    g.drawLine(0, 255, 2255, 255);
    for (int i = 15; i < 240; i += 16) {
      g.drawLine(i, 0, i, 255);
      g.drawLine(0, i, 255, i);
    }
  }

  private int[] calculateValues(int[][] arr) {
    int[] values = new int[256];
    for (int i = 0; i < arr.length; i++) {
      for (int j = 0; j < arr[0].length; j++) {
        values[arr[i][j]]++;
      }
    }
    return values;
  }

  private int maxValue(int[] arr) {
    int max = arr[0];
    for (int i = 1; i < arr.length; i++) {
      if (arr[i] > max) {
        max = arr[i];
      }
    }
    return max;
  }

  private int minValue(int[] arr) {
    int min = arr[0];
    for (int i = 1; i < arr.length; i++) {
      if (arr[i] < min) {
        min = arr[i];
      }
    }
    return min;
  }

  /**
   * The calculate method calculates the count of value in each pixel of the image and sets the
   * red, blue and green values in their respective arrays.
   */
  protected void calculate() {
    redValues = calculateValues(redImg);
    greenValues = calculateValues(greenImg);
    blueValues = calculateValues(blueImg);
  }


  private void drawLines() {
    Graphics2D g = i.createGraphics();
    int max = Math.max(maxValue(redValues), Math.max(maxValue(greenValues), maxValue(blueValues)));
    int min = Math.min(minValue(redValues), Math.min(minValue(greenValues), minValue(blueValues)));

    g.setColor(Color.RED);
    g.setStroke(new BasicStroke());
    for (int i = 1; i < 256; i++) {
      g.drawLine(i - 1, 255 - 255 * (redValues[i - 1] - min) / (max - min),
              i, 255 - 255 * (redValues[i] - min) / (max - min));
    }

    g.setColor(Color.GREEN);
    g.setStroke(new BasicStroke());
    for (int i = 1; i < 256; i++) {
      g.drawLine(i - 1, 255 - 255 * (greenValues[i - 1] - min) / (max - min),
              i, 255 - 255 * (greenValues[i] - min) / (max - min));
    }

    g.setColor(Color.BLUE);
    g.setStroke(new BasicStroke());
    for (int i = 1; i < 256; i++) {
      g.drawLine(i - 1, 255 - 255 * (blueValues[i - 1] - min) / (max - min),
              i, 255 - 255 * (blueValues[i] - min) / (max - min));
    }
  }

  /**
   * The execute method creates a 2D array of type Pixel to generate a histogram image.
   *
   * @return the image of the histogram in the form of a 2D array of Pixel objects.
   */
  public Pixel[][] execute() {
    Pixel[][] result = new Pixel[256][256];
    drawGrid();
    calculate();
    drawLines();
    for (int i = 0; i < 256; i++) {
      for (int j = 0; j < 256; j++) {
        int rgb = Histogram.i.getRGB(j, i);
        int r = (rgb >> 16) & 0xff;
        int g = (rgb >> 8) & 0xff;
        int b = (rgb) & 0xff;
        result[j][i] = new Pixel(r, g, b);
      }
    }
    return result;
  }

  /**
   * The getImage method is a helper function that helps retrieve the image in the form of a
   * BufferedImage.
   *
   * @return the image showing the generated histogram.
   */
  public BufferedImage getImage() {
    return i;
  }
}
