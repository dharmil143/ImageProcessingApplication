The structure of the code adheres to an MVC (i.e a model-controller-View) architectural pattern.
The src file contains three packages called the 'controller', 'model' and 'view' which consists of
their own interfaces and classes.

The design is now made such that it supports three functionalities:

-- The Main class by default creates an object of the GUI Controller which has the Model passed as
a parameter. Next the setView method is called to create a GUI view for the user.

-- If the program arguments are passed as "-text", then an object of the CLI Controller is created
and its execute method is called which opens a script-based application on the CLI.

-- If the program arguments are passed as "-file filename", then an object of the CLI Controller is
created and the runScript method is called which takes the filename as a parameter.


## The 'model' interface defines all the classes that perform computations on the code.
All classes implementing this interface handle data manipulation and logic. They interact with all
the data in the program.
The 'model' communicates with the controller to process user input.
The 'ModelImpl' class is an implementation of the model interface.

Earlier the Model represented a single image, however, we changed the design, such that the model
now represents a set of images which are stored in a HashMap.

It contains the functions as listed below:

# public void levelAdjust(String image, String name, int b, int m, int w) throws
 IllegalArgumentException;
# public void colorCorrect(String image, String name);
# public void histogram(String image, String name);
# public void compress(String image, String name, float threshold) throws IllegalArgumentException;
# public void splitScreen(String image, String name, int p);
# public void visualizeComponents(String image, String name, Color color) throws
 IllegalArgumentException;
# public void visualizeVIL(String image, String name, Channel channel) throws
IllegalArgumentException;
# public void flipImage(String image, String name, String direction) throws IllegalArgumentException;
# public void brightenOrDarken(String image, String name, int constant);
# public void split(String image, String name1, String name2, String name3);
# public void combineGreyscale(String name, String image1, String image2, String image3);
# public void blur(String image, String name);
# public void sharpen(String image, String name);
# public void imageToSepia(String image, String name);
# public HashMap<String, Pixel[][]> getImages();
# public void resetModel()-- This is a new method added to reset the model by reconfiguring the
image storage. This method removes and rearranges specific images within the model.

In the Model, we have a private helper method that helps with visualising.


## The controller interface defines all the classes that control the flow of the program and
interact with the model.
The Controller package also contains a class called 'ButtonListener' which acts as an Action
Listener for all the buttons in the View.

There are now two Controller Implementations in the design. One called 'ControllerGUI' and the other
called 'ControllerImpl'. The ControllerGUI is the controller for a GUI based program and
ControllerImpl is the controller for a script-based program.

ControllerGUI contains the functions as listed below:

# public void setView(View v);
# public void execute();
# public void runScript(String s);

ControllerGUI has helper methods to load, save and read images of the accepted file formats.

ControllerImpl contains the functions as listed below:

# public void setView(View v);
# public void execute() throws IOException;
# public void runScript(String s);

ControllerImpl has helper methods to load, save and read images of the accepted file formats.
It also has helper methods to take input from user and to load and save scripts.

The View was added in this design.
The ViewImpl class is an implementation of the View Interface.
This class extends the JFrame class. The constructor of this class creates the entire view with the
help of "helper methods". The image that the user chooses, as well as its histogram depicting RGB
graphs and all buttons to perform operations are displayed here.

ViewImpl contains the functions as listed below:

# public void changeSplitImage(BufferedImage i);
# public int createFileChooser() ;
# public int createSaveFileChooser();
# public String getPath(int x);
# public void setPopMenuVisible();
# public void setPopMenuVisibleLevelAdjust();
# public void setPopMenuVisibleLFlip();
# public void setHistogram(Pixel[][] img);
# public void setImage(Pixel[][] img);
# public void addActionListener(ActionListener actionListener);
# public void resetFocus();
# public int getSplitPercentage();
# public int getPercentage();
# public void flipPopUp();
# public int[] getBMW();
# public void closeError();
# public void splitPreview();
# public void disableSplitWindow();
# public void loadError();
# public void errorPopUp();

All these functions generate the View which displays an image loaded by the user, its histogram
representation and buttons to perform various operations.
Some of the functions namely, blur, sharpen, sepia, levelsAdjust, colorCorrect and greyscale
support split view.
This means a user can preview the image applying the operation to only a certain part of the image
and only then apply it on the entire image.
Lastly, the user is expected to save the image on which he/she has performed operations.