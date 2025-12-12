/*
  ImageApp: Main application for image processing operations
  Includes recoloring, rotation, and image compositing
*/
import java.awt.Color;
import java.util.Scanner;
import java.io.File;

public class ImageApp
{
  private static Scanner scanner = new Scanner(System.in);
  
  /**
   * Main method to run all image processing operations
   * @param args command line arguments (not used)
   */
  public static void main(String[] args)
  {
    System.out.println("=== IMAGE PROCESSING APP ===");
    System.out.println("Current working directory: " + System.getProperty("user.dir"));
    
    boolean running = true;
    while (running) {
      displayMenu();
      int choice = getIntInput("Enter your choice (1-11): ");
      
      switch (choice) {
        case 1:
          testOriginalImage();
          break;
        case 2:
          recolorImage();
          break;
        case 3:
          createNegativeImage();
          break;
        case 4:
          createGrayscaleImage();
          break;
        case 5:
          rotate180();
          break;
        case 6:
          rotate90Counterclockwise();
          break;
        case 7:
          rotate90Clockwise();
          break;
        case 8:
          insertImageInteractive();
          break;
        case 9:
          testVectorMatrixOperations();
          break;
        case 10:
          test2DArrayAlgorithms();
          break;
        case 11:
          running = false;
          System.out.println("Goodbye!");
          break;
        default:
          System.out.println("Invalid choice. Please try again.");
      }
      
      if (choice != 11) {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
      }
    }
    
    scanner.close();
  }
  
  /**
   * Displays the main menu
   */
  private static void displayMenu() {
    System.out.println("\n=== MAIN MENU ===");
    System.out.println("1. View Original Image");
    System.out.println("2. Recolor Image (BRG)");
    System.out.println("3. Create Negative Image");
    System.out.println("4. Create Grayscale Image");
    System.out.println("5. Rotate 180 Degrees");
    System.out.println("6. Rotate 90° Counterclockwise");
    System.out.println("7. Rotate 90° Clockwise");
    System.out.println("8. Insert Small Image onto Large Image");
    System.out.println("9. Test Vector/Matrix Operations");
    System.out.println("10. Test 2D Array Algorithms");
    System.out.println("11. Exit");
    System.out.println("=================");
  }
  
  /**
   * Gets integer input from user
   * @param prompt the prompt to display
   * @return the integer input
   */
  private static int getIntInput(String prompt) {
    System.out.print(prompt);
    while (!scanner.hasNextInt()) {
      System.out.println("Please enter a valid number!");
      scanner.next();
      System.out.print(prompt);
    }
    int input = scanner.nextInt();
    scanner.nextLine(); // Consume newline
    return input;
  }
  
  /**
   * Gets string input from user
   * @param prompt the prompt to display
   * @return the string input
   */
  private static String getStringInput(String prompt) {
    System.out.print(prompt);
    return scanner.nextLine();
  }
  
  /**
   * Loads a picture with error handling
   * @param filename the filename to load
   * @return the Picture object, or null if failed
   */
  private static Picture loadPicture(String filename) {
    try {
      System.out.println("Loading: " + filename);
      File file = new File(filename);
      if (!file.exists()) {
        System.out.println("ERROR: File not found: " + filename);
        System.out.println("Looking in: " + file.getAbsolutePath());
        return null;
      }
      Picture picture = new Picture(filename);
      System.out.println("Successfully loaded: " + filename + " (" + 
                         picture.getWidth() + "x" + picture.getHeight() + ")");
      return picture;
    } catch (Exception e) {
      System.out.println("ERROR loading " + filename + ": " + e.getMessage());
      return null;
    }
  }
  
  /**
   * Test option 1: View original image
   */
  private static void testOriginalImage() {
    System.out.println("\n=== VIEWING ORIGINAL IMAGE ===");
    String pictureFile = getImageFileChoice();
    Picture origImg = loadPicture(pictureFile);
    if (origImg == null) {
      System.out.println("Could not load image. Please check the filename.");
      return;
    }
    Pixel[][] origPixels = origImg.getPixels2D();
    System.out.println("Original pixel color at (0,0): " + origPixels[0][0].getColor());
    origImg.explore();
  }
  
  /**
   * Test option 2: Recolor image
   */
  private static void recolorImage() {
    System.out.println("\n=== RECOLORING IMAGE (BRG) ===");
    String pictureFile = getImageFileChoice();
    Picture recoloredImg = loadPicture(pictureFile);
    if (recoloredImg == null) {
      System.out.println("Could not load image. Please check the filename.");
      return;
    }
    Pixel[][] recoloredPixels = recoloredImg.getPixels2D();
    changeColor(recoloredPixels);
    recoloredImg.explore();
    
    String saveChoice = getStringInput("Save this image? (yes/no): ");
    if (saveChoice.equalsIgnoreCase("yes")) {
      String filename = getStringInput("Enter filename (without extension): ");
      boolean saved = recoloredImg.write(filename + ".jpg");
      if (saved) {
        System.out.println("Image saved as " + filename + ".jpg");
      } else {
        System.out.println("Failed to save image.");
      }
    }
  }
  
  /**
   * Test option 3: Create negative image
   */
  private static void createNegativeImage() {
    System.out.println("\n=== CREATING NEGATIVE IMAGE ===");
    String pictureFile = getImageFileChoice();
    Picture negImg = loadPicture(pictureFile);
    if (negImg == null) {
      System.out.println("Could not load image. Please check the filename.");
      return;
    }
    Pixel[][] negPixels = negImg.getPixels2D();
    negativeColor(negPixels);
    negImg.explore();
    
    String saveChoice = getStringInput("Save this image? (yes/no): ");
    if (saveChoice.equalsIgnoreCase("yes")) {
      String filename = getStringInput("Enter filename (without extension): ");
      boolean saved = negImg.write(filename + ".jpg");
      if (saved) {
        System.out.println("Image saved as " + filename + ".jpg");
      } else {
        System.out.println("Failed to save image.");
      }
    }
  }
  
  /**
   * Test option 4: Create grayscale image
   */
  private static void createGrayscaleImage() {
    System.out.println("\n=== CREATING GRAYSCALE IMAGE ===");
    String pictureFile = getImageFileChoice();
    Picture grayscaleImg = loadPicture(pictureFile);
    if (grayscaleImg == null) {
      System.out.println("Could not load image. Please check the filename.");
      return;
    }
    Pixel[][] grayscalePixels = grayscaleImg.getPixels2D();
    grayscale(grayscalePixels);
    grayscaleImg.explore();
    
    String saveChoice = getStringInput("Save this image? (yes/no): ");
    if (saveChoice.equalsIgnoreCase("yes")) {
      String filename = getStringInput("Enter filename (without extension): ");
      boolean saved = grayscaleImg.write(filename + ".jpg");
      if (saved) {
        System.out.println("Image saved as " + filename + ".jpg");
      } else {
        System.out.println("Failed to save image.");
      }
    }
  }
  
  /**
   * Test option 5: Rotate 180 degrees
   */
  private static void rotate180() {
    System.out.println("\n=== ROTATING 180 DEGREES ===");
    String pictureFile = getImageFileChoice();
    Picture upsidedownImage = loadPicture(pictureFile);
    if (upsidedownImage == null) {
      System.out.println("Could not load image. Please check the filename.");
      return;
    }
    Pixel[][] upsideDownPixels = upsidedownImage.getPixels2D();
    rotate180(upsideDownPixels);
    upsidedownImage.explore();
    
    String saveChoice = getStringInput("Save this image? (yes/no): ");
    if (saveChoice.equalsIgnoreCase("yes")) {
      String filename = getStringInput("Enter filename (without extension): ");
      boolean saved = upsidedownImage.write(filename + ".jpg");
      if (saved) {
        System.out.println("Image saved as " + filename + ".jpg");
      } else {
        System.out.println("Failed to save image.");
      }
    }
  }
  
  /**
   * Test option 6: Rotate 90 degrees counterclockwise
   */
  private static void rotate90Counterclockwise() {
    System.out.println("\n=== ROTATING 90° COUNTERCLOCKWISE ===");
    String pictureFile = getImageFileChoice();
    Picture rotateImg = loadPicture(pictureFile);
    if (rotateImg == null) {
      System.out.println("Could not load image. Please check the filename.");
      return;
    }
    Pixel[][] rotatePixels = rotateImg.getPixels2D();
    Pixel[][] rotated90 = rotate90(rotatePixels);
    Picture rotated90Picture = pixelsToPicture(rotated90);
    rotated90Picture.explore();
    
    String saveChoice = getStringInput("Save this image? (yes/no): ");
    if (saveChoice.equalsIgnoreCase("yes")) {
      String filename = getStringInput("Enter filename (without extension): ");
      boolean saved = rotated90Picture.write(filename + ".jpg");
      if (saved) {
        System.out.println("Image saved as " + filename + ".jpg");
      } else {
        System.out.println("Failed to save image.");
      }
    }
  }
  
  /**
   * Test option 7: Rotate 90 degrees clockwise
   */
  private static void rotate90Clockwise() {
    System.out.println("\n=== ROTATING 90° CLOCKWISE ===");
    String pictureFile = getImageFileChoice();
    Picture rotateImg2 = loadPicture(pictureFile);
    if (rotateImg2 == null) {
      System.out.println("Could not load image. Please check the filename.");
      return;
    }
    Pixel[][] rotatePixels2 = rotateImg2.getPixels2D();
    Pixel[][] rotatedNeg90 = rotateNeg90(rotatePixels2);
    Picture rotatedNeg90Picture = pixelsToPicture(rotatedNeg90);
    rotatedNeg90Picture.explore();
    
    String saveChoice = getStringInput("Save this image? (yes/no): ");
    if (saveChoice.equalsIgnoreCase("yes")) {
      String filename = getStringInput("Enter filename (without extension): ");
      boolean saved = rotatedNeg90Picture.write(filename + ".jpg");
      if (saved) {
        System.out.println("Image saved as " + filename + ".jpg");
      } else {
        System.out.println("Failed to save image.");
      }
    }
  }
  
  /**
   * Test option 8: Insert small image onto large image
   */
  private static void insertImageInteractive() {
    System.out.println("\n=== INSERTING SMALL IMAGE ONTO LARGE IMAGE ===");
    
    System.out.println("Choose large image:");
    String largeFile = getImageFileChoice();
    Picture finalImage = loadPicture(largeFile);
    if (finalImage == null) {
      System.out.println("Could not load large image.");
      return;
    }
    
    System.out.println("Choose small image to insert:");
    String smallFile = getImageFileChoice();
    Picture smallImage = loadPicture(smallFile);
    if (smallImage == null) {
      System.out.println("Could not load small image.");
      return;
    }
    
    int startRow = getIntInput("Enter starting row position: ");
    int startCol = getIntInput("Enter starting column position: ");
    
    Pixel[][] finalPixels = finalImage.getPixels2D();
    Pixel[][] smallPixels = smallImage.getPixels2D();
    
    insertImage(finalPixels, smallPixels, startRow, startCol);
    finalImage.explore();
    
    String saveChoice = getStringInput("Save this composite image? (yes/no): ");
    if (saveChoice.equalsIgnoreCase("yes")) {
      String filename = getStringInput("Enter filename (without extension): ");
      boolean saved = finalImage.write(filename + ".jpg");
      if (saved) {
        System.out.println("Composite image saved as " + filename + ".jpg");
      } else {
        System.out.println("Failed to save image.");
      }
    }
  }
  
  /**
   * Test option 10: Test 2D array algorithms
   */
  private static void test2DArrayAlgorithms() {
    System.out.println("\n=== TESTING 2D ARRAY ALGORITHMS ===");
    
    int[][] test1 = { { 1, 2, 3, 4 },
        { 5, 6, 7, 8 },
        { 9, 10, 11, 12 },
        { 13, 14, 15, 16 } };
    int[][] test2 = new int[4][4];
    
    System.out.println("Original 2D array:");
    print2DArray(test1);
    
    System.out.println("\nCopied 2D array:");
    copy2DArray(test1, test2);
    print2DArray(test2);
    
    System.out.println("\nRotated 2D array (90° counterclockwise):");
    int[][] rotated = rotate2DArray(test1);
    print2DArray(rotated);
  }
  
  /**
   * Gets image file choice from user
   * @return the selected image file path
   */
  private static String getImageFileChoice() {
    System.out.println("\nAvailable images:");
    System.out.println("1. beach.jpg (in lib/ folder)");
    System.out.println("2. robot.jpg (in lib/ folder)");
    System.out.println("3. swan.jpg (in lib/ folder)");
    System.out.println("4. caterpillar.jpg (in lib/ folder)");
    System.out.println("5. flower1.jpg (in lib/ folder)");
    System.out.println("6. flower2.jpg (in lib/ folder)");
    System.out.println("7. temple.jpg (in lib/ folder)");
    System.out.println("8. Enter custom filename");
    System.out.println("9. Use default test image (640x480.jpg)");
    
    int choice = getIntInput("Choose image (1-9): ");
    
    switch (choice) {
      case 1: return checkFileExists("lib/beach.jpg", "images/beach.jpg", "beach.jpg");
      case 2: return checkFileExists("lib/robot.jpg", "images/robot.jpg", "robot.jpg");
      case 3: return checkFileExists("lib/swan.jpg", "images/swan.jpg", "swan.jpg");
      case 4: return checkFileExists("lib/caterpillar.jpg", "images/caterpillar.jpg", "caterpillar.jpg");
      case 5: return checkFileExists("lib/flower1.jpg", "images/flower1.jpg", "flower1.jpg");
      case 6: return checkFileExists("lib/flower2.jpg", "images/flower2.jpg", "flower2.jpg");
      case 7: return checkFileExists("lib/temple.jpg", "images/temple.jpg", "temple.jpg");
      case 8:
        String customFile = getStringInput("Enter filename (e.g., 'myimage.jpg' or 'lib/myimage.jpg'): ");
        return customFile;
      case 9:
        return "640x480.jpg"; // Default test image that should exist
      default:
        System.out.println("Invalid choice, trying beach.jpg");
        return checkFileExists("lib/beach.jpg", "images/beach.jpg", "beach.jpg");
    }
  }
  
  /**
   * Checks if a file exists in multiple possible locations
   * @param paths varargs of possible file paths to check
   * @return the first path that exists, or the first path if none exist
   */
  private static String checkFileExists(String... paths) {
    for (String path : paths) {
      File file = new File(path);
      if (file.exists()) {
        System.out.println("Found: " + path);
        return path;
      }
    }
    System.out.println("Warning: Could not find file. Trying: " + paths[0]);
    return paths[0];
  }
  
  // ====== ALL THE ORIGINAL IMAGE PROCESSING METHODS BELOW ======
  
  /**
   * Changes the color of an image by swapping RGB channels (BRG variation)
   * @param pixels 2D array of pixels to modify
   */
  public static void changeColor(Pixel[][] pixels) {
    System.out.println("Changing colors (BRG variation)...");
    for (int row = 0; row < pixels.length; row++) {
      for (int col = 0; col < pixels[row].length; col++) {
        Pixel p = pixels[row][col];
        Color c = p.getColor();
        // Reorder RGB to BRG
        p.setColor(new Color(c.getBlue(), c.getRed(), c.getGreen()));
      }
    }
  }
  
  /**
   * Creates a photographic negative of an image
   * @param pixels 2D array of pixels to modify
   */
  public static void negativeColor(Pixel[][] pixels) {
    System.out.println("Creating negative image...");
    for (int row = 0; row < pixels.length; row++) {
      for (int col = 0; col < pixels[row].length; col++) {
        Pixel p = pixels[row][col];
        Color c = p.getColor();
        // Subtract each RGB value from 255
        p.setColor(new Color(255 - c.getRed(), 
                             255 - c.getGreen(), 
                             255 - c.getBlue()));
      }
    }
  }
  
  /**
   * Converts an image to grayscale
   * @param pixels 2D array of pixels to modify
   */
  public static void grayscale(Pixel[][] pixels) {
    System.out.println("Converting to grayscale...");
    for (int row = 0; row < pixels.length; row++) {
      for (int col = 0; col < pixels[row].length; col++) {
        Pixel p = pixels[row][col];
        Color c = p.getColor();
        // Calculate average of RGB values
        int avg = (c.getRed() + c.getGreen() + c.getBlue()) / 3;
        p.setColor(new Color(avg, avg, avg));
      }
    }
  }
  
  /**
   * Rotates an image 180 degrees by reversing rows and columns
   * @param pixels 2D array of pixels to rotate
   */
  public static void rotate180(Pixel[][] pixels) {
    System.out.println("Rotating 180 degrees...");
    int height = pixels.length;
    int width = pixels[0].length;
    
    // Create a temporary array for the rotated image
    Pixel[][] temp = new Pixel[height][width];
    
    // Copy pixels in reverse order
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        temp[height - 1 - row][width - 1 - col] = pixels[row][col];
      }
    }
    
    // Copy back to original array
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        pixels[row][col] = temp[row][col];
      }
    }
  }
  
  /**
   * Rotates an image 90 degrees counterclockwise
   * @param pixels 2D array of pixels to rotate
   * @return new 2D array of rotated pixels
   */
  public static Pixel[][] rotate90(Pixel[][] pixels) {
    System.out.println("Rotating 90 degrees counterclockwise...");
    int height = pixels.length;
    int width = pixels[0].length;
    
    // Create new array with swapped dimensions
    Pixel[][] rotated = new Pixel[width][height];
    
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        int targetRow = col;
        int targetCol = height - 1 - row;
        
        if (rotated[targetRow] == null) {
          rotated[targetRow] = new Pixel[height];
        }
        rotated[targetRow][targetCol] = pixels[row][col];
      }
    }
    
    return rotated;
  }
  
  /**
   * Rotates an image -90 degrees (90 clockwise)
   * @param pixels 2D array of pixels to rotate
   * @return new 2D array of rotated pixels
   */
  public static Pixel[][] rotateNeg90(Pixel[][] pixels) {
    System.out.println("Rotating 90 degrees clockwise...");
    int height = pixels.length;
    int width = pixels[0].length;
    
    // Create new array with swapped dimensions
    Pixel[][] rotated = new Pixel[width][height];
    
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        int targetRow = width - 1 - col;
        int targetCol = row;
        
        if (rotated[targetRow] == null) {
          rotated[targetRow] = new Pixel[height];
        }
        rotated[targetRow][targetCol] = pixels[row][col];
      }
    }
    
    return rotated;
  }
  
  /**
   * Inserts a small image into a larger one at specified position
   * @param largePixels 2D array of the large image
   * @param smallPixels 2D array of the small image
   * @param startRow starting row position in large image
   * @param startCol starting column position in large image
   */
  public static void insertImage(Pixel[][] largePixels, Pixel[][] smallPixels, 
                                 int startRow, int startCol) {
    System.out.println("Inserting small image at (" + startRow + ", " + startCol + ")...");
    
    int smallHeight = smallPixels.length;
    int smallWidth = smallPixels[0].length;
    
    // Remove white background from small image and insert
    for (int row = 0; row < smallHeight; row++) {
      for (int col = 0; col < smallWidth; col++) {
        int targetRow = startRow + row;
        int targetCol = startCol + col;
        
        // Check bounds
        if (targetRow < largePixels.length && targetCol < largePixels[0].length) {
          Pixel smallPixel = smallPixels[row][col];
          Color smallColor = smallPixel.getColor();
          
          // Remove white background (skip pixels that are mostly white)
          // White has RGB values close to 255
          if (smallColor.getRed() < 250 || smallColor.getGreen() < 250 || smallColor.getBlue() < 250) {
            largePixels[targetRow][targetCol].setColor(smallColor);
          }
        }
      }
    }
  }
  
  /**
   * Converts a 2D array of Pixels to a Picture object
   * @param pixels 2D array of pixels
   * @return Picture object containing the pixels
   */
  public static Picture pixelsToPicture(Pixel[][] pixels) {
    int height = pixels.length;
    int width = pixels[0].length;
    
    Picture picture = new Picture(height, width);
    Pixel[][] picturePixels = picture.getPixels2D();
    
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        if (pixels[row][col] != null) {
          picturePixels[row][col].setColor(pixels[row][col].getColor());
        }
      }
    }
    
    return picture;
  }
  
  /**
   * Tests vector and matrix operations with sample data
   */
  public static void testVectorMatrixOperations() {
    System.out.println("\n=== TESTING VECTOR AND MATRIX OPERATIONS ===");
    // Test Vector operations
    Vector1by2 v1 = new Vector1by2(3, 4);
    Vector1by2 v2 = new Vector1by2(1, 2);
    
    System.out.println("Vector v1: " + v1);
    System.out.println("Vector v2: " + v2);
    System.out.println("v1 + v2: " + v1.add(v2));
    System.out.println("v1 - v2: " + v1.subtract(v2));
    System.out.println("v1 dot v2: " + v1.dot(v2));
    
    // Test Matrix operations
    Matrix2by2 m1 = new Matrix2by2(1, 2, 3, 4);
    Matrix2by2 m2 = new Matrix2by2(2, 0, 1, 2);
    
    System.out.println("\nMatrix m1:\n" + m1);
    System.out.println("Matrix m2:\n" + m2);
    System.out.println("m1 * m2:\n" + m1.multiply(m2));
    
    // Test rotation matrix
    Matrix2by2 rot90 = new Matrix2by2();
    rot90.setRotationMatrix(90);
    System.out.println("90-degree rotation matrix:\n" + rot90);
    
    Vector1by2 testVec = new Vector1by2(1, 0);
    Vector1by2 rotatedVec = rot90.multiply(testVec);
    System.out.println("Rotating (1,0) by 90 degrees: " + rotatedVec);
    
    // Expected: (0, 1) for 90 degree rotation
    System.out.println("Expected: (0, 1)");
  }
  
  /**
   * Copies a 2D array
   * @param source source array
   * @param destination destination array
   */
  public static void copy2DArray(int[][] source, int[][] destination) {
    for (int i = 0; i < source.length; i++) {
      for (int j = 0; j < source[i].length; j++) {
        destination[i][j] = source[i][j];
      }
    }
  }
  
  /**
   * Rotates a 2D integer array 90 degrees counterclockwise
   * @param array 2D array to rotate
   * @return rotated array
   */
  public static int[][] rotate2DArray(int[][] array) {
    int n = array.length;
    int m = array[0].length;
    int[][] rotated = new int[m][n];
    
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        rotated[m - 1 - j][i] = array[i][j];
      }
    }
    
    return rotated;
  }
  
  /**
   * Prints a 2D integer array
   * @param array array to print
   */
  public static void print2DArray(int[][] array) {
    for (int i = 0; i < array.length; i++) {
      for (int j = 0; j < array[i].length; j++) {
        System.out.printf("%3d ", array[i][j]);
      }
      System.out.println();
    }
  }
}
