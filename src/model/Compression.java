package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;

/**
 * The Compression class applies compression techniques on an input image using Haar Wavelet
 * Transform. The class performs transformation, thresholding and padding unpadding in order to
 * compress an image.
 */
public class Compression {

  private float[][] redImg;

  private float[][] greenImg;

  private float[][] blueImg;
  private final float threshold;

  private final Pixel[][] originalImage;

  /**
   * Constructs the compression class where the image and the threshold are passed as parameters.
   *
   * @param img       is the input image in the form of a 2D array with Pixel objects.
   * @param threshold is the value under which the pixels are reduced in size.
   */
  public Compression(Pixel[][] img, float threshold) {
    this.originalImage = img;
    img = padding(img);
    redImg = new float[img.length][img[0].length];
    blueImg = new float[img.length][img[0].length];
    greenImg = new float[img.length][img[0].length];
    for (int i = 0; i < img.length; i++) {
      for (int j = 0; j < img[0].length; j++) {
        redImg[i][j] = img[i][j].getRed();
        blueImg[i][j] = img[i][j].getBlue();
        greenImg[i][j] = img[i][j].getGreen();
      }
    }
    this.threshold = threshold;
  }

  /**
   * The execute method implements compression using Haar Wavelet Transform and thresholding.
   *
   * @return the image in a 2D array format.
   */
  public Pixel[][] execute() {
    redImg = haarTransform2D(redImg);
    greenImg = haarTransform2D(greenImg);
    blueImg = haarTransform2D(blueImg);

    Pixel[][] result = new Pixel[redImg.length][redImg[0].length];
    redImg = haarInverse2D(redImg);
    greenImg = haarInverse2D(greenImg);
    blueImg = haarInverse2D(blueImg);

    for (int i = 0; i < redImg.length; i++) {
      for (int j = 0; j < redImg[0].length; j++) {
        result[i][j] = new Pixel(0, 0, 0);
        result[i][j].setColor((int) redImg[i][j], Color.RED);
        result[i][j].setColor((int) greenImg[i][j], Color.GREEN);
        result[i][j].setColor((int) blueImg[i][j], Color.BLUE);
      }
    }
    return unpad(result);
  }

  private float[][] haarInverse2D(float[][] img) {
    int c = 2;
    while (c <= img.length) {
      for (int i = 0; i < c; i++) {
        float[] x = new float[c];
        for (int j = 0; j < c; j++) {
          x[j] = img[j][i];
        }
        x = invert(x);
        for (int j = 0; j < c; j++) {
          img[j][i] = x[j];
        }
      }
      for (int i = 0; i < c; i++) {
        float[] x = new float[c];
        for (int j = 0; j < c; j++) {
          x[j] = img[i][j];
        }
        x = invert(x);
        for (int j = 0; j < c; j++) {
          img[i][j] = x[j];
        }
      }
      c = c * 2;
    }
    return img;
  }

  private float[][] haarTransform2D(float[][] img) {
    int c = img.length;
    while (c > 1) {
      for (int i = 0; i < c; i++) {
        float[] x = new float[c];
        for (int j = 0; j < c; j++) {
          x[j] = img[i][j];
        }
        x = transform(x);
        for (int j = 0; j < c; j++) {
          img[i][j] = x[j];
        }
      }
      for (int i = 0; i < c; i++) {
        float[] x = new float[c];
        for (int j = 0; j < c; j++) {
          x[j] = img[j][i];
        }
        x = transform(x);
        for (int j = 0; j < c; j++) {
          img[j][i] = x[j];
        }
      }
      c = c / 2;
    }
    return thresholdImage(img);
  }

  private Pixel[][] unpad(Pixel[][] img) {
    Pixel[][] result = new Pixel[originalImage.length][originalImage[0].length];
    for (int i = 0; i < originalImage.length; i++) {
      for (int j = 0; j < originalImage[0].length; j++) {
        result[i][j] = img[i][j];
      }
    }
    return result;
  }

  private Pixel[][] padding(Pixel[][] img) {
    int n = Math.max(img.length, img[0].length);
    Pixel[][] img2 = new Pixel[n][n];
    if (!isPowerOfTwo(n)) {
      int d = 1;
      while (!isPowerOfTwo(n + d)) {
        d++;
      }
      img2 = new Pixel[n + d][n + d];
    }
    for (int i = 0; i < img2.length; i++) {
      for (int j = 0; j < img2[0].length; j++) {
        img2[i][j] = new Pixel(0, 0, 0);
      }
    }
    for (int i = 0; i < img.length; i++) {
      for (int j = 0; j < img[0].length; j++) {
        img2[i][j] = img[i][j];
      }
    }
    return img2;
  }

  private boolean isPowerOfTwo(int n) {
    if (n == 0) {
      return false;
    }
    double v = Math.log(n) / Math.log(2);
    return (int) (Math.ceil(v)) == (int) (Math.floor(v));
  }

  private float[] transform(float[] list) {
    ArrayList<Float> avg = new ArrayList<>();
    ArrayList<Float> diff = new ArrayList<>();
    for (int i = 0; i < list.length; i += 2) {
      double av = (list[i] + list[i + 1]) / Math.sqrt(2);
      double di = (list[i] - list[i + 1]) / Math.sqrt(2);
      avg.add((float) av);
      diff.add((float) di);
    }
    avg.addAll(diff);
    float[] total = new float[avg.size()];
    int i = 0;
    for (Float f : avg) {
      total[i++] = f;
    }
    return total;
  }

  private float[] invert(float[] list) {
    ArrayList<Float> avg = new ArrayList<>();
    ArrayList<Float> diff = new ArrayList<>();
    int j = list.length / 2;
    for (int i = 0; i < list.length / 2; i++) {
      float a = list[i];
      float b = list[j];
      float av = (float) ((a + b) / Math.sqrt(2));
      float di = (float) ((a - b) / Math.sqrt(2));
      avg.add(av);
      diff.add(di);
      j++;
    }
    float[] total = new float[avg.size() + diff.size()];
    for (int i = 0; i < total.length; i += 2) {
      total[i] = avg.remove(0);
      total[i + 1] = diff.remove(0);
    }
    return total;
  }

  private float[] removeDuplicates(float[] a) {
    LinkedHashSet<Float> x = new LinkedHashSet<>();
    for (int i = 0; i < a.length; i++) {
      x.add(a[i]);
    }
    float[] result = new float[x.size()];
    int c = 0;
    for (float f : x) {
      result[c++] = f;
    }
    return result;
  }

  private float[][] thresholdImage(float[][] img) {
    float[] l = new float[img.length * img[0].length];
    int c = 0;
    for (int i = 0; i < img[0].length; i++) {
      for (int j = 0; j < img.length; j++) {
        l[j + c] = Math.abs(img[i][j]);
      }
      c = c + img.length;
    }
    Arrays.sort(l);
    float[] x = removeDuplicates(l);
    float num = 0;
    if ((int) (x.length * threshold / 100) - 1 > 0) {
      num = x[(int) (x.length * threshold / 100) - 1];
    } else {
      num = 0;
    }

    for (int i = 0; i < img[0].length; i++) {
      for (int j = 0; j < img.length; j++) {
        if (Math.abs(img[i][j]) <= num) {
          img[i][j] = 0;
        }
      }
    }
    return img;
  }
}
