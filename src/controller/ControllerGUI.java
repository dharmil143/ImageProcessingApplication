package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

import javax.imageio.ImageIO;

import model.Channel;
import model.Color;
import model.Model;
import model.Pixel;
import view.View;


/**
 * The ControllerGUI is an implementation of the controller interface that controls all the
 * functions used for the GUI based View.
 */

public class ControllerGUI implements Controller {
  private Model m;
  private View v;

  private int c;
  private int l;

  /**
   * Constructor for ControllerGUI class which takes in a Model and initialises c and l,
   * where c stores the number of saved images and l stores the number of loaded images.
   *
   * @param m is the Model input.
   */
  public ControllerGUI(Model m) {
    this.m = m;
    c = 0;
    l = 0;
  }

  /**
   * The setView method helps the user assign a view to the class.
   *
   * @param v is the View input.
   */

  @Override
  public void setView(View v) {
    this.v = v;
    configureButtonListener();
  }

  private BufferedImage toBuffered(Pixel[][] img) {
    int width = img.length;
    int height = img[0].length;
    BufferedImage i = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int rgb = (img[x][y].getRed() << 16 | img[x][y].getGreen() << 8
                | img[x][y].getBlue());
        i.setRGB(x, y, rgb);
      }
    }
    return i;
  }


  private void configureButtonListener() {
    Map<String, Runnable> buttonClickedMap = new HashMap<String, Runnable>();
    ButtonListener buttonListener = new ButtonListener();

    buttonClickedMap.put("Load", () -> {
      if (c == 0 && l != 0) {
        v.loadError();
      } else {
        int x = v.createFileChooser();
        String path = v.getPath(x);
        if (path != null) {
          loadImage(path, "Image");
          v.setImage(m.getImages().get("Image"));
          m.histogram("Image", "Histogram");
          v.setHistogram(m.getImages().get("Histogram"));
          v.resetFocus();
          l++;
        }
      }
    });

    buttonClickedMap.put("Close", () -> {
      v.closeError();
    });

    buttonClickedMap.put("Save", () -> {
      int x = v.createSaveFileChooser();
      String path = v.getPath(x);
      try {
        saveImage(path, "Image");
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      v.resetFocus();
    });

    buttonClickedMap.put("Sepia", () -> {
      m.imageToSepia("Image", "NewImage");
      v.splitPreview();
    });

    buttonClickedMap.put("Preview", () -> {
      int a = v.getSplitPercentage();
      if (a > 100 || a < 0) {
        v.errorPopUp();
      } else {
        Pixel[][] og = m.getImages().get("Image");
        m.splitScreen("Image", "NewImage", a);
        v.changeSplitImage(toBuffered(m.getImages().get("Image")));
        m.getImages().remove("Image");
        m.getImages().put("Image", og);
        v.splitPreview();
      }
    });

    buttonClickedMap.put("Apply", () -> {
      v.disableSplitWindow();
      v.setImage(m.getImages().get("NewImage"));
      m.resetModel();
      m.histogram("Image", "Histogram");
      v.setHistogram(m.getImages().get("Histogram"));
      v.resetFocus();
      c = 0;
    });


    buttonClickedMap.put("Blur", () -> {
      m.blur("Image", "NewImage");
      v.splitPreview();
    });

    buttonClickedMap.put("Sharpen", () -> {
      m.sharpen("Image", "NewImage");
      v.splitPreview();
    });

    buttonClickedMap.put("Red-component", () -> {
      m.visualizeComponents("Image", "NewImage", Color.RED);
      v.setImage(m.getImages().get("NewImage"));
      m.resetModel();
      m.histogram("Image", "Histogram");
      v.setHistogram(m.getImages().get("Histogram"));
      v.resetFocus();
      c = 0;
    });

    buttonClickedMap.put("Blue-component", () -> {
      m.visualizeComponents("Image", "NewImage", Color.BLUE);
      v.setImage(m.getImages().get("NewImage"));
      m.resetModel();
      m.histogram("Image", "Histogram");
      v.setHistogram(m.getImages().get("Histogram"));
      v.resetFocus();
      c = 0;
    });

    buttonClickedMap.put("Green-component", () -> {
      m.visualizeComponents("Image", "NewImage", Color.GREEN);
      v.setImage(m.getImages().get("NewImage"));
      m.resetModel();
      m.histogram("Image", "Histogram");
      v.setHistogram(m.getImages().get("Histogram"));
      v.resetFocus();
      c = 0;
    });

    buttonClickedMap.put("Greyscale Image", () -> {
      m.visualizeVIL("Image", "NewImage", Channel.LUMA);
      v.splitPreview();
    });

    buttonClickedMap.put("Flip", () -> {
      v.setPopMenuVisibleLFlip();
    });

    buttonClickedMap.put("Vertical", () -> {
      m.flipImage("Image", "NewImage", "vertical");
      v.setImage(m.getImages().get("NewImage"));
      m.resetModel();
      m.histogram("Image", "Histogram");
      v.setHistogram(m.getImages().get("Histogram"));
      v.resetFocus();
      v.flipPopUp();
      c = 0;
    });

    buttonClickedMap.put("Horizontal", () -> {
      m.flipImage("Image", "NewImage", "horizontal");
      v.setImage(m.getImages().get("NewImage"));
      m.resetModel();
      m.histogram("Image", "Histogram");
      v.setHistogram(m.getImages().get("Histogram"));
      v.resetFocus();
      v.flipPopUp();
      c = 0;
    });

    buttonClickedMap.put("Color Correct Image", () -> {
      m.colorCorrect("Image", "NewImage");
      v.splitPreview();
    });

    buttonClickedMap.put("Compress Image", () -> {
      v.setPopMenuVisible();
    });

    buttonClickedMap.put("Compress", () -> {
      int a = v.getPercentage();
      if (a < 0 || a > 100) {
        v.errorPopUp();
      } else {
        m.compress("Image", "NewImage", a);
        v.setImage(m.getImages().get("NewImage"));
        m.resetModel();
        m.histogram("Image", "Histogram");
        v.setHistogram(m.getImages().get("Histogram"));
        v.resetFocus();
        c = 0;
      }
    });

    buttonClickedMap.put("Adjust Level of Image", () -> {
      v.setPopMenuVisibleLevelAdjust();
    });

    buttonClickedMap.put("Level Adjust", () -> {
      int[] a = v.getBMW();
      if (a[0] > 0 && a[0] < a[1] && a[1] < a[2] && a[2] < 255) {
        m.levelAdjust("Image", "NewImage", a[0], a[1], a[2]);
        v.splitPreview();
      } else {
        v.errorPopUp();
      }
    });


    buttonListener.setButtonClickedActionMap(buttonClickedMap);
    this.v.addActionListener(buttonListener);
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
    BufferedImage i = toBuffered(image);
    File imageFile = new File(path);
    try {
      ImageIO.write(i, extension, imageFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void saveImage(String path, String name) throws IOException {
    Pixel[][] image = m.getImages().get(name);
    String[] array = path.split("\\.");
    String extension = array[array.length - 1];
    if (Objects.equals(extension, "ppm")) {
      writeImageToPPM(array[0], image);
      c++;
    } else if (Objects.equals(extension, "png") || Objects.equals(extension, "jpg")) {
      writeImage(array[0], image, extension);
      c++;
    } else {
      v.saveErrorPopUp();
    }
  }

  /**
   * The execute method is used to execute the function in a different implementation but is
   * currently not used.
   */
  @Override
  public void execute() {
    System.out.println("Wrong Method Call");
  }

  /**
   * The runScript method is used to run scripts in a different implementation but is currently
   * not used.
   */
  @Override
  public void runScript(String s) {
    System.out.println("Wrong Method Call");
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
