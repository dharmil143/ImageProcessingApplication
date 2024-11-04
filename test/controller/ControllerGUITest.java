package controller;

import org.junit.Test;

import java.util.HashMap;

import model.MockModel;
import view.MockView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test class for ControllerGUI.
 */
public class ControllerGUITest {
  @Test
  public void testControllerGUI1() {
    MockModel m = new MockModel(new HashMap<>());
    Controller c = new ControllerGUI(m);
    MockView v = new MockView();
    c.setView(v);
    Runnable a = v.b.getButtonClickedActions().get("Load");
    a.run();
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        assertEquals(100, c.getModel().getImages().get("Image")[0][0].getRed());
        assertEquals(150, c.getModel().getImages().get("Image")[0][0].getGreen());
        assertEquals(200, c.getModel().getImages().get("Image")[0][0].getBlue());
      }
    }
    assertTrue(v.setImageFunction);
    assertTrue(v.resetFocusFunction);
    assertTrue(v.setHistogramFunction);
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(0, c.getModel().getImages().get("Histogram")[0][0].getRed());
        assertEquals(0, c.getModel().getImages().get("Histogram")[0][0].getGreen());
        assertEquals(0, c.getModel().getImages().get("Histogram")[0][0].getBlue());
      }
    }
    a = v.b.getButtonClickedActions().get("Close");
    a.run();
    assertTrue(v.closeErrorFunction);

    a = v.b.getButtonClickedActions().get("Save");
    a.run();
    assertEquals(0, v.createSaveFileChooser());
    assertEquals("res/TestImage.png", v.getPath(0));

    a = v.b.getButtonClickedActions().get("Sepia");
    a.run();
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(0, c.getModel().getImages().get("NewImage")[0][0].getRed());
        assertEquals(0, c.getModel().getImages().get("NewImage")[0][0].getGreen());
        assertEquals(0, c.getModel().getImages().get("NewImage")[0][0].getBlue());
      }
    }
    assertTrue(v.splitPreviewFunction);

    a = v.b.getButtonClickedActions().get("Preview");
    a.run();
    assertEquals(50, v.getSplitPercentage());
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(0, c.getModel().getImages().get("NewImage")[0][0].getRed());
        assertEquals(0, c.getModel().getImages().get("NewImage")[0][0].getGreen());
        assertEquals(0, c.getModel().getImages().get("NewImage")[0][0].getBlue());
      }
    }
    assertTrue(v.changeSplitImageFunction);
    assertTrue(v.splitPreviewFunction);

    a = v.b.getButtonClickedActions().get("Apply");
    a.run();
    assertTrue(v.disableSplitWindowFunction);
    assertTrue(v.setImageFunction);
    assertTrue(m.resetModelFunction);
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(0, c.getModel().getImages().get("Histogram")[0][0].getRed());
        assertEquals(0, c.getModel().getImages().get("Histogram")[0][0].getGreen());
        assertEquals(0, c.getModel().getImages().get("Histogram")[0][0].getBlue());
      }
    }
    assertTrue(v.setHistogramFunction);
    assertTrue(v.resetFocusFunction);
  }

  @Test
  public void testControllerGUIBlur() {
    MockModel m = new MockModel(new HashMap<>());
    Controller c = new ControllerGUI(m);
    MockView v = new MockView();
    c.setView(v);
    Runnable a = v.b.getButtonClickedActions().get("Blur");
    a.run();
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(0, c.getModel().getImages().get("NewImage")[0][0].getRed());
        assertEquals(0, c.getModel().getImages().get("NewImage")[0][0].getGreen());
        assertEquals(0, c.getModel().getImages().get("NewImage")[0][0].getBlue());
      }
    }
    assertTrue(v.splitPreviewFunction);
  }

  @Test
  public void testControllerGUISharpen() {
    MockModel m = new MockModel(new HashMap<>());
    Controller c = new ControllerGUI(m);
    MockView v = new MockView();
    c.setView(v);
    Runnable a = v.b.getButtonClickedActions().get("Sharpen");
    a.run();
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(0, c.getModel().getImages().get("NewImage")[0][0].getRed());
        assertEquals(0, c.getModel().getImages().get("NewImage")[0][0].getGreen());
        assertEquals(0, c.getModel().getImages().get("NewImage")[0][0].getBlue());
      }
    }
    assertTrue(v.splitPreviewFunction);
  }

  @Test
  public void testControllerGUIRed() {
    MockModel m = new MockModel(new HashMap<>());
    Controller c = new ControllerGUI(m);
    MockView v = new MockView();
    c.setView(v);
    Runnable a = v.b.getButtonClickedActions().get("Red-component");
    a.run();
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(0, c.getModel().getImages().get("NewImage")[0][0].getRed());
        assertEquals(0, c.getModel().getImages().get("NewImage")[0][0].getGreen());
        assertEquals(0, c.getModel().getImages().get("NewImage")[0][0].getBlue());
      }
    }
    assertTrue(v.setImageFunction);
    assertTrue(m.resetModelFunction);
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(0, c.getModel().getImages().get("Histogram")[0][0].getRed());
        assertEquals(0, c.getModel().getImages().get("Histogram")[0][0].getGreen());
        assertEquals(0, c.getModel().getImages().get("Histogram")[0][0].getBlue());
      }
    }
    assertTrue(v.setHistogramFunction);
    assertTrue(v.resetFocusFunction);
  }

  @Test
  public void testControllerGUIBlue() {
    MockModel m = new MockModel(new HashMap<>());
    Controller c = new ControllerGUI(m);
    MockView v = new MockView();
    c.setView(v);
    Runnable a = v.b.getButtonClickedActions().get("Blue-component");
    a.run();
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(0, c.getModel().getImages().get("NewImage")[0][0].getRed());
        assertEquals(0, c.getModel().getImages().get("NewImage")[0][0].getGreen());
        assertEquals(0, c.getModel().getImages().get("NewImage")[0][0].getBlue());
      }
    }
    assertTrue(v.setImageFunction);
    assertTrue(m.resetModelFunction);
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(0, c.getModel().getImages().get("Histogram")[0][0].getRed());
        assertEquals(0, c.getModel().getImages().get("Histogram")[0][0].getGreen());
        assertEquals(0, c.getModel().getImages().get("Histogram")[0][0].getBlue());
      }
    }
    assertTrue(v.setHistogramFunction);
    assertTrue(v.resetFocusFunction);
  }

  @Test
  public void testControllerGUIGreen() {
    MockModel m = new MockModel(new HashMap<>());
    Controller c = new ControllerGUI(m);
    MockView v = new MockView();
    c.setView(v);
    Runnable a = v.b.getButtonClickedActions().get("Green-component");
    a.run();
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(0, c.getModel().getImages().get("NewImage")[0][0].getRed());
        assertEquals(0, c.getModel().getImages().get("NewImage")[0][0].getGreen());
        assertEquals(0, c.getModel().getImages().get("NewImage")[0][0].getBlue());
      }
    }
    assertTrue(v.setImageFunction);
    assertTrue(m.resetModelFunction);
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(0, c.getModel().getImages().get("Histogram")[0][0].getRed());
        assertEquals(0, c.getModel().getImages().get("Histogram")[0][0].getGreen());
        assertEquals(0, c.getModel().getImages().get("Histogram")[0][0].getBlue());
      }
    }
    assertTrue(v.setHistogramFunction);
    assertTrue(v.resetFocusFunction);
  }

  @Test
  public void testControllerGUIGreyscale() {
    MockModel m = new MockModel(new HashMap<>());
    Controller c = new ControllerGUI(m);
    MockView v = new MockView();
    c.setView(v);
    Runnable a = v.b.getButtonClickedActions().get("Greyscale Image");
    a.run();
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(0, c.getModel().getImages().get("NewImage")[0][0].getRed());
        assertEquals(0, c.getModel().getImages().get("NewImage")[0][0].getGreen());
        assertEquals(0, c.getModel().getImages().get("NewImage")[0][0].getBlue());
      }
    }
    assertTrue(v.splitPreviewFunction);
  }

  @Test
  public void testControllerGUIFlip() {
    MockModel m = new MockModel(new HashMap<>());
    Controller c = new ControllerGUI(m);
    MockView v = new MockView();
    c.setView(v);
    Runnable a = v.b.getButtonClickedActions().get("Flip");
    a.run();
    assertTrue(v.setPopMenuVisibleLFlipFunction);
  }

  @Test
  public void testControllerGUIVertical() {
    MockModel m = new MockModel(new HashMap<>());
    Controller c = new ControllerGUI(m);
    MockView v = new MockView();
    c.setView(v);
    Runnable a = v.b.getButtonClickedActions().get("Vertical");
    a.run();
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(0, c.getModel().getImages().get("NewImage")[0][0].getRed());
        assertEquals(0, c.getModel().getImages().get("NewImage")[0][0].getGreen());
        assertEquals(0, c.getModel().getImages().get("NewImage")[0][0].getBlue());
      }
    }
    assertTrue(v.setImageFunction);
    assertTrue(m.resetModelFunction);
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(0, c.getModel().getImages().get("Histogram")[0][0].getRed());
        assertEquals(0, c.getModel().getImages().get("Histogram")[0][0].getGreen());
        assertEquals(0, c.getModel().getImages().get("Histogram")[0][0].getBlue());
      }
    }
    assertTrue(v.setHistogramFunction);
    assertTrue(v.resetFocusFunction);
  }

  @Test
  public void testControllerGUIHorizontal() {
    MockModel m = new MockModel(new HashMap<>());
    Controller c = new ControllerGUI(m);
    MockView v = new MockView();
    c.setView(v);
    Runnable a = v.b.getButtonClickedActions().get("Horizontal");
    a.run();
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(0, c.getModel().getImages().get("NewImage")[0][0].getRed());
        assertEquals(0, c.getModel().getImages().get("NewImage")[0][0].getGreen());
        assertEquals(0, c.getModel().getImages().get("NewImage")[0][0].getBlue());
      }
    }
    assertTrue(v.setImageFunction);
    assertTrue(m.resetModelFunction);
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(0, c.getModel().getImages().get("Histogram")[0][0].getRed());
        assertEquals(0, c.getModel().getImages().get("Histogram")[0][0].getGreen());
        assertEquals(0, c.getModel().getImages().get("Histogram")[0][0].getBlue());
      }
    }
    assertTrue(v.setHistogramFunction);
    assertTrue(v.resetFocusFunction);
  }

  @Test
  public void testControllerGUIColorCorrect() {
    MockModel m = new MockModel(new HashMap<>());
    Controller c = new ControllerGUI(m);
    MockView v = new MockView();
    c.setView(v);
    Runnable a = v.b.getButtonClickedActions().get("Color Correct Image");
    a.run();
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(0, c.getModel().getImages().get("NewImage")[0][0].getRed());
        assertEquals(0, c.getModel().getImages().get("NewImage")[0][0].getGreen());
        assertEquals(0, c.getModel().getImages().get("NewImage")[0][0].getBlue());
      }
    }
    assertTrue(v.splitPreviewFunction);
  }

  @Test
  public void testControllerGUICompressImage() {
    MockModel m = new MockModel(new HashMap<>());
    Controller c = new ControllerGUI(m);
    MockView v = new MockView();
    c.setView(v);
    Runnable a = v.b.getButtonClickedActions().get("Compress Image");
    a.run();
    assertTrue(v.setPopMenuVisibleFunction);
  }

  @Test
  public void testControllerGUICompress() {
    MockModel m = new MockModel(new HashMap<>());
    Controller c = new ControllerGUI(m);
    MockView v = new MockView();
    c.setView(v);
    Runnable a = v.b.getButtonClickedActions().get("Compress");
    a.run();
    assertEquals(50, v.getPercentage());
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(0, c.getModel().getImages().get("NewImage")[0][0].getRed());
        assertEquals(0, c.getModel().getImages().get("NewImage")[0][0].getGreen());
        assertEquals(0, c.getModel().getImages().get("NewImage")[0][0].getBlue());
      }
    }
    assertTrue(v.setImageFunction);
    assertTrue(m.resetModelFunction);
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(0, c.getModel().getImages().get("Histogram")[0][0].getRed());
        assertEquals(0, c.getModel().getImages().get("Histogram")[0][0].getGreen());
        assertEquals(0, c.getModel().getImages().get("Histogram")[0][0].getBlue());
      }
    }
    assertTrue(v.setHistogramFunction);
    assertTrue(v.resetFocusFunction);
  }

  @Test
  public void testControllerGUIAdjustLevels() {
    MockModel m = new MockModel(new HashMap<>());
    Controller c = new ControllerGUI(m);
    MockView v = new MockView();
    c.setView(v);
    Runnable a = v.b.getButtonClickedActions().get("Adjust Level of Image");
    a.run();
    assertTrue(v.setPopMenuVisibleLevelAdjustFunction);
  }

  @Test
  public void testControllerGUILevelAdjust() {
    MockModel m = new MockModel(new HashMap<>());
    Controller c = new ControllerGUI(m);
    MockView v = new MockView();
    c.setView(v);
    Runnable a = v.b.getButtonClickedActions().get("Level Adjust");
    a.run();
    assertEquals(50, v.getBMW()[0]);
    assertEquals(100, v.getBMW()[1]);
    assertEquals(150, v.getBMW()[2]);
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(0, c.getModel().getImages().get("NewImage")[0][0].getRed());
        assertEquals(0, c.getModel().getImages().get("NewImage")[0][0].getGreen());
        assertEquals(0, c.getModel().getImages().get("NewImage")[0][0].getBlue());
      }
    }
    assertTrue(v.splitPreviewFunction);
  }
}