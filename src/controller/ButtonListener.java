package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

/**
 * ButtonListener class implements ActionListener interface which acts as a listener in the
 * system for buttons.
 */
public class ButtonListener implements ActionListener {
  private Map<String, Runnable> buttonClickedActions;

  /**
   * The setButtonClickedActionMap method sets the map for key typed events.
   * Key typed events in Java Swing are characters.
   */
  public void setButtonClickedActionMap(Map<String, Runnable> map) {
    buttonClickedActions = map;
  }

  /**
   * The actionPerformed method here overrides the actionPerformed method in the interface
   * when an action is called.
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    if (buttonClickedActions.containsKey(e.getActionCommand())) {

      buttonClickedActions.get(e.getActionCommand()).run();
    }
  }

  /**
   * Getter for getButtonClickedActions.
   *
   * @return buttonClickedActions.
   */
  public Map<String, Runnable> getButtonClickedActions() {
    return buttonClickedActions;
  }
}
