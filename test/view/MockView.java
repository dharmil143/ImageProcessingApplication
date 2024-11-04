package view;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JFileChooser;

import controller.ButtonListener;
import model.Pixel;

/**
 * MockView for testing.
 */
public class MockView implements View {
  public ButtonListener b;
  public boolean setImageFunction = false;
  public boolean setHistogramFunction = false;
  public boolean resetFocusFunction = false;
  public boolean closeErrorFunction = false;
  public boolean splitPreviewFunction = false;
  public boolean disableSplitWindowFunction = false;
  public boolean changeSplitImageFunction = false;
  public boolean setPopMenuVisibleFunction = false;
  public boolean setPopMenuVisibleLFlipFunction = false;
  public boolean setPopMenuVisibleLevelAdjustFunction = false;

  @Override
  public int createSaveFileChooser() {
    return JFileChooser.APPROVE_OPTION;
  }

  @Override
  public void changeSplitImage(BufferedImage i) {
    changeSplitImageFunction = true;
  }

  @Override
  public int createFileChooser() {
    return JFileChooser.APPROVE_OPTION;
  }

  @Override
  public String getPath(int x) {
    return "res/TestImage.png";
  }

  @Override
  public void setPopMenuVisible() {
    setPopMenuVisibleFunction = true;
  }

  @Override
  public void setPopMenuVisibleLevelAdjust() {
    setPopMenuVisibleLevelAdjustFunction = true;
  }

  @Override
  public void setPopMenuVisibleLFlip() {
    setPopMenuVisibleLFlipFunction = true;
  }

  @Override
  public void setHistogram(Pixel[][] img) {
    setHistogramFunction = true;
  }

  @Override
  public void setImage(Pixel[][] img) {
    setImageFunction = true;
  }

  @Override
  public void addActionListener(ActionListener actionListenerr) {
    b = (ButtonListener) actionListenerr;
  }

  @Override
  public void resetFocus() {
    resetFocusFunction = true;
  }

  @Override
  public int getSplitPercentage() {
    return 50;
  }

  @Override
  public int getPercentage() {
    return 50;
  }

  @Override
  public void flipPopUp() {
    //The popup displayed when clicked on the flip button.
  }

  @Override
  public int[] getBMW() {
    return new int[]{50, 100, 150};
  }

  @Override
  public void closeError() {
    closeErrorFunction = true;
  }

  @Override
  public void splitPreview() {
    splitPreviewFunction = true;
  }

  @Override
  public void disableSplitWindow() {
    disableSplitWindowFunction = true;
  }

  @Override
  public void loadError() {
    //The popup displayed when user loads image before saving.
  }

  @Override
  public void errorPopUp() {
    //The popup displayed when there is an error in the values entered.
  }

  @Override
  public void saveErrorPopUp() {
    //The popup displayed when user puts an incorrect extension.
  }
}
