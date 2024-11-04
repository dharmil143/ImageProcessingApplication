package controller;

import java.io.IOException;

import model.Model;
import view.View;

/**
 * The Controller interface is a representation of the controller of this design which will be used
 * by the main class to access the entire system.
 */
public interface Controller {

  /**
   * The Set View method is used to set the View for the implementation of the GUI.
   *
   * @param v View Object.
   */
  public void setView(View v);

  /**
   * The execute method is used to execute the logic of this program.
   */

  public void execute() throws IOException;


  /**
   * The runScript method is used to run a scripts.txt file whose path is passed as a parameter to
   * this method.
   *
   * @param s path of the script file.
   */

  public void runScript(String s);

  /**
   * Getter for getting the model.
   * @return Model.
   */
  Model getModel();
}
