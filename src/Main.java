import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Objects;


import controller.Controller;
import controller.ControllerGUI;
import controller.ControllerImpl;
import model.ModelImpl;
import model.Pixel;
import view.ViewImpl;

/**
 * The Main class is the starting point of the program that initialises the model and the
 * controller.
 */
public class Main {
  /**
   * Constructs the controller to take the model and start implementation.
   *
   * @param args is the argument given to the main method.
   * @throws IOException if there is an error in the input/output files.
   */
  public static void main(String[] args) throws IOException {
    HashMap<String, Pixel[][]> s = new HashMap<>();
    if (args.length == 0) {
      Controller c = new ControllerGUI(new ModelImpl(s));
      c.setView(new ViewImpl("Image Processing"));
    } else if (Objects.equals(args[0], "-file") || args.length == 2) {
      Controller c = new ControllerImpl(new ModelImpl(s),
              new InputStreamReader(System.in), System.out);
      c.runScript(args[1]);
    } else if (Objects.equals(args[0], "-text")) {
      Controller c = new ControllerImpl(new ModelImpl(s),
              new InputStreamReader(System.in), System.out);
      c.execute();
    }
  }
}
