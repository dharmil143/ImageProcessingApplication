package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Scanner;

import javax.imageio.ImageIO;

import model.Channel;
import model.Color;
import model.Model;
import model.Pixel;
import view.View;

/**
 * The ControllerImpl is an implementation of the controller interface that controls all the
 * functions used for the Text Based Scripting Application.
 */

public class ControllerImpl implements Controller {
  private Model m;
  private Readable in;
  private Appendable out;

  private String[] inputWords;

  /**
   * The ControllerImpl Constructor takes in a model object, a Readable object and an Appendable
   * object.
   *
   * @param m   is the Model Object.
   * @param in  is the Readable object for input.
   * @param out is the Appendable object for output.
   */
  public ControllerImpl(Model m, Readable in, Appendable out) {
    this.m = m;
    this.in = in;
    this.out = out;
  }

  /**
   * The setView method is used to set View in the ControllerGUI implementation but not used in
   * the current class.
   *
   * @param v is the View Object.
   */
  @Override
  public void setView(View v) {
    System.out.println("Wrong method call");
  }

  /**
   * The execute method is used to execute the class and bring the Command Line Interface to the
   * user.
   *
   * @throws IOException if the file is not found.
   */
  @Override
  public void execute() throws IOException {
    Scanner sc = new Scanner(this.in);
    this.out.append("Enter Scripts or press q to exit : ");
    while (sc.hasNextLine()) {
      String input = sc.nextLine();
      if (Objects.equals(input, "q")) {
        break;
      }
      takeInput(input);
    }
  }

  private void takeInput(String input) throws IOException {
    this.inputWords = input.split(" ");
    switch (inputWords[0]) {
      case "load":
        loadScript();
        break;
      case "save":
        saveScript();
        break;
      case "red-component":
        visualizeScripts(inputWords[2], Color.RED);
        break;
      case "blue-component":
        visualizeScripts(inputWords[2], Color.BLUE);
        break;
      case "green-component":
        visualizeScripts(inputWords[2], Color.GREEN);
        break;
      case "value-component":
        if (inputWords.length == 5) {
          visualizeChannelScripts(inputWords[2], Channel.VALUE, 5);
          splitScreenScript(Integer.parseInt(inputWords[4]), 1, 2);
        } else {
          visualizeChannelScripts(inputWords[2], Channel.VALUE, 3);
        }
        break;
      case "intensity-component":
        if (inputWords.length == 5) {
          visualizeChannelScripts(inputWords[2], Channel.INTENSITY, 5);
          splitScreenScript(Integer.parseInt(inputWords[4]), 1, 2);
        } else {
          visualizeChannelScripts(inputWords[2], Channel.INTENSITY, 3);
        }
        break;
      case "luma-component":
        if (inputWords.length == 5) {
          visualizeChannelScripts(inputWords[2], Channel.LUMA, 5);
          splitScreenScript(Integer.parseInt(inputWords[4]), 1, 2);
        } else {
          visualizeChannelScripts(inputWords[2], Channel.LUMA, 3);
        }
        break;
      case "horizontal-flip":
        flipScripts(inputWords[2], "horizontal");
        break;
      case "vertical-flip":
        flipScripts(inputWords[2], "vertical");
        break;
      case "brighten":
        brightenScripts(inputWords[3], Integer.parseInt(inputWords[1]));
        break;
      case "rgb-split":
        rgbSplitScript(inputWords[2], inputWords[3], inputWords[4]);
        break;
      case "rgb-combine":
        rgbCombineScript(inputWords[2], inputWords[3], inputWords[4]);
        break;
      case "blur":
        if (inputWords.length == 5) {
          blurOrSharpen(inputWords[2], "blur", 5);
          splitScreenScript(Integer.parseInt(inputWords[4]), 1, 2);
        } else {
          blurOrSharpen(inputWords[2], "blur", 3);
        }
        break;
      case "sharpen":
        if (inputWords.length == 5) {
          blurOrSharpen(inputWords[2], "sharpen", 5);
          splitScreenScript(Integer.parseInt(inputWords[4]), 1, 2);
        } else {
          blurOrSharpen(inputWords[2], "sharpen", 3);
        }
        break;
      case "sepia":
        if (inputWords.length == 5) {
          sepiaScript(inputWords[2], 5);
          splitScreenScript(Integer.parseInt(inputWords[4]), 1, 2);
        } else {
          sepiaScript(inputWords[2], 3);
        }
        break;
      case "-file":
        System.out.println(inputWords[1]);
        runScript(inputWords[1]);
        break;
      case "run":
        if (inputWords.length != 2) {
          System.out.println("Length of script not as expected");
        } else {
          runScript(inputWords[1]);
        }
        break;
      case "compress":
        compressScript(inputWords[3], Float.parseFloat(inputWords[1]));
        break;
      case "histogram":
        histogramScript(inputWords[2]);
        break;
      case "color-correct":
        if (inputWords.length == 5) {
          colorCorrectScript(inputWords[2], 5);
          splitScreenScript(Integer.parseInt(inputWords[4]), 1, 2);
        } else {
          colorCorrectScript(inputWords[2], 3);
        }
        break;
      case "levels-adjust":
        if (inputWords.length == 8) {
          levelsAdjustScript(inputWords[5], Integer.parseInt(inputWords[1]),
                  Integer.parseInt(inputWords[2]), Integer.parseInt(inputWords[3]), 8);
          splitScreenScript(Integer.parseInt(inputWords[7]), 4, 5);
        } else {
          levelsAdjustScript(inputWords[5], Integer.parseInt(inputWords[1]),
                  Integer.parseInt(inputWords[2]), Integer.parseInt(inputWords[3]), 6);
        }
        break;
      default:
        System.out.println("Wrong Script Written");
    }
  }

  private void loadScript() {
    if (inputWords.length != 3) {
      System.out.println("Length of script not as expected");
    } else {
      loadImage(inputWords[1], inputWords[2]);
    }
  }

  private void saveScript() throws IOException {
    if (inputWords.length != 3) {
      System.out.println("Length of script not as expected");
    } else {
      System.out.println("Image saved at : " + inputWords[1]);
      saveImage(inputWords[1], inputWords[2]);
    }
  }

  private void visualizeChannelScripts(String s, Channel channel, int i) {
    if (inputWords.length != i) {
      System.out.println("Length of script not as expected");
    } else {
      m.visualizeVIL(inputWords[1], s, channel);
    }
  }

  private void brightenScripts(String s, int constant) {
    if (inputWords.length != 4) {
      System.out.println("Length of script not as expected");
    } else {
      m.brightenOrDarken(inputWords[2], s, constant);
    }
  }

  private void visualizeScripts(String s, Color color) {
    if (inputWords.length != 3) {
      System.out.println("Length of script not as expected");
    } else {
      m.visualizeComponents(inputWords[1], s, color);
    }
  }

  private void flipScripts(String s, String direction) {
    if (inputWords.length != 3) {
      System.out.println("Length of script not as expected");
    } else {
      m.flipImage(inputWords[1], s, direction);
    }
  }

  private void rgbSplitScript(String s1, String s2, String s3) {
    if (inputWords.length != 5) {
      System.out.println("Length of script not as expected");
    } else {
      m.split(inputWords[1], s1, s2, s3);
    }
  }

  private void rgbCombineScript(String s1, String s2, String s3) {
    if (inputWords.length != 5) {
      System.out.println("Length of script not as expected");
    } else {
      m.combineGreyscale(inputWords[1], s1, s2, s3);
    }
  }

  private void blurOrSharpen(String s, String action, int i) {
    if (inputWords.length != i) {
      System.out.println("Length of script not as expected");
    } else {
      if (Objects.equals(action, "blur")) {
        m.blur(inputWords[1], s);
      } else if (Objects.equals(action, "sharpen")) {
        m.sharpen(inputWords[1], s);
      }
    }
  }

  private void splitScreenScript(int p, int j, int i) {
    if (p > 100 || p < 0) {
      throw new IllegalArgumentException("Invalid percentage entered");
    }
    m.splitScreen(inputWords[i], inputWords[j], p);
  }

  private void levelsAdjustScript(String s, int b, int m, int w, int i) {
    if (inputWords.length != i) {
      System.out.println("Length of script not as expected");
    } else {
      this.m.levelAdjust(inputWords[4], s, b, m, w);
    }
  }

  private void sepiaScript(String s, int i) {
    if (inputWords.length != i) {
      System.out.println("Length of script not as expected");
    } else {
      m.imageToSepia(inputWords[1], s);
    }
  }

  private void colorCorrectScript(String s, int i) {
    if (inputWords.length != i) {
      System.out.println("Length of script not as expected");
    } else {
      m.colorCorrect(inputWords[1], s);
    }
  }

  private void histogramScript(String s) {
    if (inputWords.length != 3) {
      System.out.println("Length of script not as expected");
    } else {
      m.histogram(inputWords[1], s);
    }
  }

  private void compressScript(String s, float t) {
    if (inputWords.length != 4) {
      System.out.println("Length of script not as expected");
    } else {
      m.compress(inputWords[2], s, t);
    }
  }

  private void readPPM(String filename, String name) {
    Scanner sc = new Scanner(System.in);
    try {
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      System.out.println("File " + filename + " not found!");
    }
    StringBuilder builder = new StringBuilder();
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s).append(System.lineSeparator());
      }
    }
    sc = new Scanner(builder.toString());
    String token;
    token = sc.next();
    if (!token.equals("P3")) {
      System.out.println("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();
    Pixel[][] result = new Pixel[width][height];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        result[j][i] = new Pixel(r, g, b);
      }
    }
    m.getImages().put(name, result);
  }

  private void readOther(String filename, String name) {
    BufferedImage img;
    try {
      img = ImageIO.read(new File(filename));
      int width = img.getWidth();
      int height = img.getHeight();
      Pixel[][] result = new Pixel[width][height];
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          int rgb = img.getRGB(j, i);
          int r = (rgb >> 16) & 0xff;
          int g = (rgb >> 8) & 0xff;
          int b = (rgb) & 0xff;
          result[j][i] = new Pixel(r, g, b);
          m.getImages().put(name, result);
        }
      }
    } catch (IOException e) {
      System.out.println("File " + filename + " not found!");
    }
  }

  private void loadImage(String path, String name) {
    String[] extension = path.split("\\.");
    if (Objects.equals(extension[extension.length - 1], "ppm")) {
      readPPM(path, name);
    } else {
      readOther(path, name);
    }
  }

  private void writeImageToPPM(String name, Pixel[][] image) throws IOException {
    String path = name + ".ppm";
    String builder = "P3" + System.lineSeparator()
            + image.length + " "
            + image[0].length + System.lineSeparator()
            + "225" + System.lineSeparator();
    for (int i = 0; i < image[0].length; i++) {
      for (int j = 0; j < image.length; j++) {
        builder = builder + image[j][i].getRed()
                + System.lineSeparator() + image[j][i].getGreen()
                + System.lineSeparator() + image[j][i].getBlue()
                + System.lineSeparator();
      }
    }
    Files.writeString(Path.of(path), builder,
            StandardCharsets.UTF_8);
  }

  private void writeImage(String name, Pixel[][] image, String extension) {
    String path = name + "." + extension;
    int width = image.length;
    int height = image[0].length;
    BufferedImage i = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int rgb = (image[x][y].getRed() << 16 | image[x][y].getGreen() << 8
                | image[x][y].getBlue());
        i.setRGB(x, y, rgb);
      }
    }
    File imageFile = new File(path);
    try {
      ImageIO.write(i, extension, imageFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void saveImage(String path, String name) throws IllegalArgumentException, IOException {
    Pixel[][] image = m.getImages().get(name);
    String[] array = path.split("\\.");
    String extension = array[array.length - 1];
    if (Objects.equals(extension, "ppm")) {
      writeImageToPPM(array[0], image);
    } else if (Objects.equals(extension, "png") || Objects.equals(extension, "jpg")) {
      writeImage(array[0], image, extension);
    } else {
      throw new IllegalArgumentException("Illegal Image Type");
    }
  }

  /**
   * The runScript method is used to run a script using Command Line.
   *
   * @param s is the path of the script file.
   */
  @Override
  public void runScript(String s) {
    try {
      File f = new File(s);
      Scanner sc = new Scanner(f);
      while (sc.hasNextLine()) {
        String newInput = sc.nextLine();
        if (!newInput.startsWith("#")) {
          takeInput(newInput);
        }
      }
    } catch (FileNotFoundException e) {
      System.out.println("File Not Found");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Getter for Model.
   *
   * @return Model.
   */
  @Override
  public Model getModel() {
    return m;
  }
}
