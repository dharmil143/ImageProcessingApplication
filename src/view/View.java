package view;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import model.Pixel;

/**
 * The View interface defines methods for manipulating and interacting with the graphical user
 * interface.
 */
public interface View {
  /**
   * The createSaveFileChooser method creates and displays a file chooser dialog for saving an
   * image file.
   *
   * @return the result of the save file chooser dialog.
   */
  public int createSaveFileChooser();

  /**
   * The changeSplitImage method changes the secondary image to the provided BufferedImage.
   *
   * @param i is the BufferedImage to set as the secondary image.
   */
  public void changeSplitImage(BufferedImage i);

  /**
   * The createFileChooser method creates and displays a file chooser dialog for opening an
   * image file.
   *
   * @return the result of the file chooser dialog.
   */
  public int createFileChooser();

  /**
   * The getPath method retrieves the path of the selected file from the file chooser dialog.
   *
   * @param x is the result of the file chooser dialog.
   * @return the absolute path of the selected file or null if no file was selected.
   */
  public String getPath(int x);

  /**
   * The setPopMenuVisible method displays the 'Compress' popup menu at a specific location.
   */
  public void setPopMenuVisible();

  /**
   * The setPopMenuVisibleLevelAdjust method displays the "LevelAdjust" popup menu at a specific
   * location.
   */
  public void setPopMenuVisibleLevelAdjust();

  /**
   * The setPopMenuVisibleLFlip method displays the "Flip" popup menu at a specific location.
   */
  public void setPopMenuVisibleLFlip();

  /**
   * The setHistogram method sets the histogram image based on the provided pixel matrix.
   *
   * @param img is the two-dimensional Pixel array representing the image.
   */
  public void setHistogram(Pixel[][] img);

  /**
   * The setImage method sets the image using a Pixel matrix and updates the displayed image
   * on the interface.
   *
   * @param img is a 2D array of Pixel representing the image matrix.
   */
  public void setImage(Pixel[][] img);

  /**
   * The addActionListener method add an ActionListener to various buttons in the graphical
   * interface to handle events.The provided ActionListener will be added to multiple buttons,
   * enabling the handling of actions performed on these buttons.
   *
   * @param actionListener is the ActionListener instance to be added to the buttons.
   */
  public void addActionListener(ActionListener actionListener);

  /**
   * The resetFocus method resets the focus to this component. This method allows setting the
   * focus back to the current component within the user interface.
   */
  public void resetFocus();

  /**
   * The getSplitPercentage method retrieves the split percentage from the text field.
   *
   * @return the split percentage as an integer.
   */
  public int getSplitPercentage();

  /**
   * The getPercentage method retrieves the percentage value from the text field and hides
   * the popup menu.
   *
   * @return the percentage as an integer.
   */
  public int getPercentage();

  /**
   * The flipPopUp hides the pop-up window for the flip button.
   */
  public void flipPopUp();

  /**
   * The getBMW method retrieves the BMW values from text fields.
   *
   * @return an array containing B,M,W values in that order.
   */
  public int[] getBMW();

  /**
   * The closeError method closes the error display by hiding the error message and error panel.
   */
  public void closeError();

  /**
   * The splitPreview method displays a split preview of an image within a popup menu. It creates
   * a graphical representation of the image using JLabel and JScrollPane components within a
   * JPopupMenu and shows it at a specified location.
   */
  public void splitPreview();

  /**
   * The disableSplitWindow method disables the split window by setting the visibility of the
   * preview to false.
   */
  public void disableSplitWindow();

  /**
   * The loadError method displays an error message when clicked on the load button unless the
   * image is saved. This method utilizes the 'err' instance to show an error dialog at the
   * coordinates (300, 300).
   */
  public void loadError();

  /**
   * The errorPopUp method displays an error pop-up dialog. This method adds a close button to the
   * error dialog and displays it at a specific location on the screen at the coordinates
   * (300, 300).
   */
  public void errorPopUp();

  /**
   * The saveErrorPopUp method displays an error pop-up dialog when a wrong extension is put.
   * This method adds a close button to the error dialog and displays it at a specific
   * location on the screen at the coordinates (300, 300).
   */
  public void saveErrorPopUp();
}
