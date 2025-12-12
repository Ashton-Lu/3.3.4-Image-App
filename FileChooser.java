import javax.swing.JFileChooser;
import javax.swing.JFrame;
import java.io.*;
import java.net.*;
  
/**
 * A class to make working with a file chooser easier
 * for students.  It uses a JFileChooser to let the user
 * pick a file and returns the chosen file name.
 * 
 * @author Barb Ericson ericson@cc.gatech.edu
 */
public class FileChooser 
{
  
  /////////////////////// methods /////////////////////////////
  
  /**
   * Method to get the full path for the passed file name
   * @param fileName the name of a file
   * @return the full path for the file
   */
  public static String getMediaPath(String fileName) 
  {
    String path = null;
    String directory = getMediaDirectory();
    
    // get the full path
    path = directory + fileName;
    return path;
  }
  
  /**
   * Method to pick an item using the file chooser
   * @param fileChooser the file Chooser to use
   * @return the path name
   */
  public static String pickPath(JFileChooser fileChooser)
  {
    String path = null;
    
    /* create a JFrame to be the parent of the file 
     * chooser open dialog if you don't do this then 
     * you may not see the dialog.
     */
    JFrame frame = new JFrame();
    frame.setAlwaysOnTop(true);
    
    // get the return value from choosing a file
    int returnVal = fileChooser.showOpenDialog(frame);
    
    // if the return value says the user picked a file 
    if (returnVal == JFileChooser.APPROVE_OPTION)
      path = fileChooser.getSelectedFile().getPath();
    
    frame.dispose(); // Clean up the frame
    return path;
  }
  
  /**
   * Method to let the user pick a file and return
   * the full file name as a string.  If the user didn't 
   * pick a file then the file name will be null.
   * @return the full file name of the picked file or null
   */
  public static String pickAFile()
  {
    JFileChooser fileChooser = null;
    
    // start off the file name as null
    String fileName = null;
    
    // get the current media directory
    String mediaDir = getMediaDirectory();
    
    /* create a file for this and check that the directory exists
     * and if it does set the file chooser to use it
     */
    try {
      File file = new File(mediaDir);
      if (file.exists())
        fileChooser = new JFileChooser(file);
    } catch (Exception ex) {
    }
    
    // if no file chooser yet create one
    if (fileChooser == null)
      fileChooser = new JFileChooser();
    
    // pick the file
    fileName = pickPath(fileChooser);
    
    return fileName;
  }
  
  /**
   * Method to get the directory for the media
   * @return the media directory
   */
  public static String getMediaDirectory() 
  {
    String directory = null;
    File dirFile = null;
    
    // First, try the current directory
    try {
      directory = System.getProperty("user.dir");
      dirFile = new File(directory);
      if (dirFile.exists()) {
        return directory + File.separator;
      }
    } catch (Exception ex) {
    }
    
    // try to find the images directory
    try {
      // get the URL for where we loaded this class 
      URL classURL = FileChooser.class.getResource("FileChooser.class");
      if (classURL != null) {
        // Use URI to avoid deprecated URL constructor
        URI uri = classURL.toURI();
        File classFile = new File(uri.getPath());
        File parentDir = classFile.getParentFile();
        File imagesDir = new File(parentDir, "images");
        
        if (imagesDir.exists()) {
          return imagesDir.getPath() + File.separator;
        }
      }
    } catch (Exception ex) {
      // Fall through to default directory
    }
    
    // Last resort: try current directory
    return System.getProperty("user.dir") + File.separator;
  }
  
  /**
   * Main method for testing
   * @param args command line arguments
   */
  public static void main(String[] args) {
    System.out.println("Media directory: " + getMediaDirectory());
    String file = pickAFile();
    System.out.println("Selected file: " + file);
  }
}
