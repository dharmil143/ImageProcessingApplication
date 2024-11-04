package view;

import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.JMenuBar;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.Pixel;

/**
 * Implementation of the View interface that provides a graphical user interface for image
 * manipulation functionalities.
 */
public class ViewImpl extends JFrame implements View {

  private JButton apply;
  private JPopupMenu err;
  private JButton close;
  private JButton save;
  private JButton load;
  private JButton sepia;
  private JButton blur;
  private JButton sharpen;
  private JButton redC;
  private JButton greenC;
  private JButton blueC;
  private JButton flipV;
  private JButton flipH;
  private JButton flip;
  private JButton levelA;
  private JButton compress;
  private JButton compressFinal;
  private JTextField per;
  private JButton colorCorrect;
  private JButton greyscale;
  private BufferedImage image2;
  private JLabel i;
  private JLabel h;
  private JPopupMenu pop;
  private JTextField b;
  private JTextField m;
  private JTextField w;
  private JButton levelAdjustFinal;
  private JPopupMenu popFlip;
  private JPopupMenu popLevel;
  private JPanel sv;
  private JButton pre;
  private JTextPane percentage;
  private JPopupMenu preview;
  private JPopupMenu error;
  private JPopupMenu saveError;
  private JFileChooser fc;

  /**
   * The ViewImpl method constructs a ViewImpl object with the specified name for the JFrame.
   *
   * @param name is the name to set for the JFrame.
   */
  public ViewImpl(String name) {
    super(name);
    setSize(1000, 800);
    setLocation(100, 100);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    createMenuBar(this);
    JPanel p = new JPanel(new GridLayout(4, 4));
    p.setBounds(10, 10, 990, 150);
    addButtons(p);
    BufferedImage image = new BufferedImage(600, 400, BufferedImage.TYPE_INT_RGB);
    BufferedImage histogram = new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB);
    JPanel images = new JPanel();
    ((FlowLayout) images.getLayout()).setHgap(20);
    i = new JLabel(new ImageIcon(image));
    i.setSize(600, 400);
    JScrollPane scrollPane = new JScrollPane(new JPanel().add(i));
    images.add(scrollPane);
    h = new JLabel(new ImageIcon(histogram));
    images.add(h);
    images.setBounds(10, 200, 1000, 800);

    popFlip = new JPopupMenu("Flip");
    popFlip.setPopupSize(300, 60);
    JPanel hv = new JPanel();
    hv.setLayout(new FlowLayout());
    JLabel flip = new JLabel("Flip direction :", SwingConstants.LEFT);
    flipH = new JButton("Horizontal");
    flipV = new JButton("Vertical");
    popFlip.add(flip);
    hv.add(flipH);
    hv.add(flipV);
    popFlip.add(hv);

    pop = new JPopupMenu("Compress");
    pop.setPopupSize(200, 100);
    per = new JTextField();
    JLabel l = new JLabel("Percentage :");
    compressFinal = new JButton("Compress");
    pop.add(l);
    pop.add(per);
    pop.add(compressFinal);

    popLevel = new JPopupMenu("Level Adjust");
    popLevel.setPopupSize(400, 200);
    b = new JTextField();
    m = new JTextField();
    w = new JTextField();
    JLabel l1 = new JLabel("Black :");
    JLabel l2 = new JLabel("Mid :");
    JLabel l3 = new JLabel("White :");
    levelAdjustFinal = new JButton("Level Adjust");
    popLevel.add(l1);
    popLevel.add(b);
    popLevel.add(l2);
    popLevel.add(m);
    popLevel.add(l3);
    popLevel.add(w);
    popLevel.add(levelAdjustFinal);

    err = new JPopupMenu();
    err.setPopupSize(320, 50);
    err.setBorderPainted(true);
    JLabel errorMsg = new JLabel("  Save the Image before Loading Another Image!");
    close = new JButton("Close");
    err.add(errorMsg);
    err.add(close);

    sv = new JPanel();
    JLabel enterPer = new JLabel("Enter Percentage");
    percentage = new JTextPane();
    pre = new JButton("Preview");
    sv.add(enterPer);
    sv.add(percentage);
    sv.add(pre);

    apply = new JButton("Apply");

    error = new JPopupMenu("Error");
    error.setPopupSize(200, 50);
    JLabel e = new JLabel("Invalid Values");
    error.add(e);

    saveError = new JPopupMenu("Error");
    saveError.setPopupSize(200, 50);
    JLabel g = new JLabel("Extension should be ppm,jpg or png only");
    saveError.add(g);

    add(images);
    add(p);
    this.setLayout(null);
    setVisible(true);
    setResizable(false);
  }

  /**
   * The changeSplitImage method changes the split preview.
   *
   * @param i is the BufferedImage to set as the split image.
   */
  @Override
  public void changeSplitImage(BufferedImage i) {
    image2 = i;
  }

  private void addButtons(JPanel p) {
    sepia = new JButton("Sepia");
    blur = new JButton("Blur");
    sharpen = new JButton("Sharpen");
    redC = new JButton("Red-component");
    blueC = new JButton("Blue-component");
    greenC = new JButton("Green-component");
    flip = new JButton("Flip");
    greyscale = new JButton("Greyscale Image");
    compress = new JButton("Compress Image");
    colorCorrect = new JButton("Color Correct Image");
    levelA = new JButton("Adjust Level of Image");
    p.add(sepia);
    p.add(blur);
    p.add(sharpen);
    p.add(redC);
    p.add(greenC);
    p.add(blueC);
    p.add(flip);
    p.add(greyscale);
    p.add(compress);
    p.add(colorCorrect);
    p.add(levelA);
  }

  private void createMenuBar(JFrame f) {
    JMenuBar m = new JMenuBar();
    load = new JButton("Load");
    save = new JButton("Save");
    m.add(load);
    m.add(save);
    f.setJMenuBar(m);
  }

  /**
   * The createFileChooser method creates and displays a file chooser dialog for opening an
   * image file.
   *
   * @return the result of the file chooser dialog.
   */
  @Override
  public int createFileChooser() {
    fc = new JFileChooser(".");
    fc.setFileFilter(new FileNameExtensionFilter("Images", "png", "jpg", "ppm"));
    return fc.showOpenDialog(this);
  }

  /**
   * The createSaveFileChooser method creates and displays a file chooser dialog for saving an
   * image file.
   *
   * @return the result of the save file chooser dialog.
   */
  @Override
  public int createSaveFileChooser() {
    fc = new JFileChooser();
    fc.setApproveButtonText("Save");
    return fc.showSaveDialog(this);
  }

  /**
   * The getPath method retrieves the path of the selected file from the file chooser dialog.
   *
   * @param x is the result of the file chooser dialog.
   * @return the absolute path of the selected file or null if no file was selected.
   */
  @Override
  public String getPath(int x) {
    if (x == JFileChooser.APPROVE_OPTION) {
      return fc.getSelectedFile().getAbsolutePath();
    }
    return null;
  }

  /**
   * The setPopMenuVisible method displays the 'Compress' popup menu at a specific location.
   */
  @Override
  public void setPopMenuVisible() {
    pop.show(this, 300, 300);
  }

  /**
   * The setPopMenuVisibleLevelAdjust method displays the "LevelAdjust" popup menu at a specific
   * location.
   */
  @Override
  public void setPopMenuVisibleLevelAdjust() {
    popLevel.show(this, 300, 300);
  }

  /**
   * The setPopMenuVisibleLFlip method displays the "Flip" popup menu at a specific location.
   */
  @Override
  public void setPopMenuVisibleLFlip() {
    popFlip.show(this, 300, 300);
  }


  private void setViewImage(JLabel i, BufferedImage b) {
    i.setIcon(new ImageIcon(b));
  }

  /**
   * The setHistogram method sets the histogram image based on the provided pixel matrix.
   *
   * @param img is the two-dimensional Pixel array representing the image.
   */
  @Override
  public void setHistogram(Pixel[][] img) {
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
    setViewImage(this.h, i);
  }

  /**
   * The setImage method sets the image using a Pixel matrix and updates the displayed image
   * on the interface.
   *
   * @param img is a 2D array of Pixel representing the image matrix.
   */
  @Override
  public void setImage(Pixel[][] img) {
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
    image2 = i;
    setViewImage(this.i, i);
  }

  /**
   * The addActionListener method add an ActionListener to various buttons in the graphical
   * interface to handle events.The provided ActionListener will be added to multiple buttons,
   * enabling the handling of actions performed on these buttons.
   *
   * @param actionListener is the ActionListener instance to be added to the buttons.
   */
  @Override
  public void addActionListener(ActionListener actionListener) {
    load.addActionListener(actionListener);
    save.addActionListener(actionListener);
    sepia.addActionListener(actionListener);
    blur.addActionListener(actionListener);
    sharpen.addActionListener(actionListener);
    redC.addActionListener(actionListener);
    blueC.addActionListener(actionListener);
    greenC.addActionListener(actionListener);
    greyscale.addActionListener(actionListener);
    flip.addActionListener(actionListener);
    flipV.addActionListener(actionListener);
    flipH.addActionListener(actionListener);
    compress.addActionListener(actionListener);
    colorCorrect.addActionListener(actionListener);
    levelA.addActionListener(actionListener);
    compressFinal.addActionListener(actionListener);
    levelAdjustFinal.addActionListener(actionListener);
    close.addActionListener(actionListener);
    pre.addActionListener(actionListener);
    apply.addActionListener(actionListener);
  }

  /**
   * The resetFocus method resets the focus to this component. This method allows setting the
   * focus back to the current component within the user interface.
   */
  @Override
  public void resetFocus() {
    this.setFocusable(true);
    this.requestFocus();
  }

  /**
   * The getSplitPercentage method retrieves the split percentage from the text field.
   *
   * @return the split percentage as an integer.
   */
  @Override
  public int getSplitPercentage() {
    return Integer.parseInt(percentage.getText());
  }

  /**
   * The getPercentage method retrieves the percentage value from the text field and hides
   * the popup menu.
   *
   * @return the percentage as an integer.
   */
  @Override
  public int getPercentage() {
    int a = Integer.parseInt(per.getText());
    pop.setVisible(false);
    return a;
  }

  /**
   * The flipPopUp hides the pop-up window for the flip button.
   */
  @Override
  public void flipPopUp() {
    popFlip.setVisible(false);
  }

  /**
   * The getBMW method retrieves the BMW values from text fields.
   *
   * @return an array containing B,M,W values in that order.
   */
  @Override
  public int[] getBMW() {
    int[] result = new int[3];
    result[0] = Integer.parseInt(b.getText());
    result[1] = Integer.parseInt(m.getText());
    result[2] = Integer.parseInt(w.getText());
    popLevel.setVisible(false);
    return result;
  }

  /**
   * The closeError method closes the error display by hiding the error message and error panel.
   */
  @Override
  public void closeError() {
    err.setVisible(false);
    error.setVisible(false);
    saveError.setVisible(false);
  }

  /**
   * The splitPreview method displays a split preview of an image within a popup menu. It creates
   * a graphical representation of the image using JLabel and JScrollPane components within a
   * JPopupMenu and shows it at a specified location.
   */
  @Override
  public void splitPreview() {
    preview = new JPopupMenu("Split Preview");
    preview.setSize(1500, 500);
    JLabel i = new JLabel(new ImageIcon(image2));
    i.setSize(600, 400);
    JPanel panel = new JPanel();
    panel.add(i);
    JScrollPane scrollPane = new JScrollPane(panel);
    scrollPane.setPreferredSize(new Dimension(600, 400));
    JPanel img = new JPanel(new BorderLayout());
    img.add(scrollPane, BorderLayout.CENTER);
    img.setBounds(0, 0, 600, 400);
    preview.add(img);

    sv.add(apply);

    preview.add(sv);
    preview.show(this, 100, 100);
  }

  /**
   * The disableSplitWindow method disables the split window by setting the visibility of the
   * preview to false.
   */
  @Override
  public void disableSplitWindow() {
    preview.setVisible(false);
  }

  /**
   * The loadError method displays an error message when clicked on the load button unless the
   * image is saved. This method utilizes the 'err' instance to show an error dialog at the
   * coordinates (300, 300).
   */
  @Override
  public void loadError() {
    err.show(this, 300, 300);
  }

  /**
   * The errorPopUp method displays an error pop-up dialog. This method adds a close button to the
   * error dialog and displays it at a specific location on the screen at the coordinates
   * (300, 300).
   */
  @Override
  public void errorPopUp() {
    error.add(close);
    error.show(this, 300, 300);
  }

  /**
   * The saveErrorPopUp method displays an error pop-up dialog when a wrong extension is put.
   * This method adds a close button to the error dialog and displays it at a specific
   * location on the screen at the coordinates (300, 300).
   */
  @Override
  public void saveErrorPopUp() {
    saveError.add(close);
    saveError.show(this, 300, 300);
  }
}
