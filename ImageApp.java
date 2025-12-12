/*
  ImageApp: Main application for image processing operations
  Includes recoloring, rotation, and image compositing
*/
import java.awt.Color;
import java.util.ArrayList;

public class ImageApp
{
  /**
   * Main method to run all image processing operations
   * @param args command line arguments (not used)
   */
  public static void main(String[] args)
  {
    // use any file from the lib folder
    String pictureFile = "lib/beach.jpg";

    // Get an image, get 2d array of pixels, show a color of a pixel, and display the image
    Picture origImg = new Picture(pictureFile);
    Pixel[][] origPixels = origImg.getPixels2D();
    System.out.println("Original pixel color at (0,0): " + origPixels[0][0].getColor());
    origImg.explore();

    // Image #1 Using the original image and pixels, recolor an image by changing the RGB color of each Pixel
    Picture recoloredImg = new Picture(pictureFile);
    Pixel[][] recoloredPixels = recoloredImg.getPixels2D();
    changeColor(recoloredPixels);
    recoloredImg.explore();
    
    // Save recolored image
    recoloredImg.write("recolored_beach.jpg");

    // Image #2 Using the original image and pixels, create a photographic negative of the image
    Picture negImg = new Picture(pictureFile);
    Pixel[][] negPixels = negImg.getPixels2D();
    negativeColor(negPixels);
    negImg.explore();
    
    // Save negative image
    negImg.write("negative_beach.jpg");

    // Image #3 Using the original image and pixels, create a grayscale version of the image
    Picture grayscaleImg = new Picture(pictureFile);
    Pixel[][] grayscalePixels = grayscaleImg.getPixels2D();
    grayscale(grayscalePixels);
    grayscaleImg.explore();
    
    // Save grayscale image
    grayscaleImg.write("grayscale_beach.jpg");

    // Image #4 Using the original image and pixels, rotate it 180 degrees
    Picture upsidedownImage = new Picture(pictureFile);
    Pixel[][] upsideDownPixels = upsidedownImage.getPixels2D();
    rotate180(upsideDownPixels);
    upsidedownImage.explore();
    
    // Save rotated image
    upsidedownImage.write("rotated180_beach.jpg");

    // Image #5 Using the original image and pixels, rotate image 90 degrees counterclockwise
    Picture rotateImg = new Picture(pictureFile);
    Pixel[][] rotatePixels = rotateImg.getPixels2D();
    Pixel[][] rotated90 = rotate90(rotatePixels);
    Picture rotated90Picture = pixelsToPicture(rotated90);
    rotated90Picture.explore();
    
    // Save rotated image
    rotated90Picture.write("rotated90_beach.jpg");

    // Image #6 Using the original image and pixels, rotate image -90 degrees (90 clockwise)
    Picture rotateImg2 = new Picture(pictureFile);
    Pixel[][] rotatePixels2 = rotateImg2.getPixels2D();
    Pixel[][] rotatedNeg90 = rotateNeg90(rotatePixels2);
    Picture rotatedNeg90Picture = pixelsToPicture(rotatedNeg90);
    rotatedNeg90Picture.explore();
    
    // Save rotated image
    rotatedNeg90Picture.write("rotatedNeg90_beach.jpg");

    // Final Image: Add a small image to a larger one
    Picture finalImage = new Picture(pictureFile);
    Pixel[][] finalPixels = finalImage.getPixels2D();
    
    // Load a small image to insert
    Picture smallImage = new Picture("lib/robot.jpg");
    Pixel[][] smallPixels = smallImage.getPixels2D();
    
    // Insert small image at position (100, 100)
    insertImage(finalPixels, smallPixels, 100, 100);
    finalImage.explore();
    
    // Save final image
    finalImage.write("final_composite_beach.jpg");
    
    // Test matrix and vector operations
    System.out.println("\n=== Testing Vector and Matrix Operations ===");
    testVectorMatrixOperations();

    // Test 2D array algorithms
    System.out.println("\n=== Testing 2D Array Algorithms ===");
    int[][] test1 = { { 1, 2, 3, 4 },
        { 5, 6, 7, 8 },
        { 9, 10, 11, 12 },
        { 13, 14, 15, 16 } };
    int[][] test2 = new int[4][4];
    
    // Test array copying
    copy2DArray(test1, test2);
    print2DArray(test2);
    
    // Test array rotation using matrix multiplication
    int[][] rotated = rotate2DArray(test1);
    System.out.println("Rotated 2D array:");
    print2DArray(rotated);
  }
  
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
   * Rotates an image 90 degrees counterclockwise using matrix multiplication
   * @param pixels 2D array of pixels to rotate
   * @return new 2D array of rotated pixels
   */
  public static Pixel[][] rotate90(Pixel[][] pixels) {
    System.out.println("Rotating 90 degrees counterclockwise...");
    int height = pixels.length;
    int width = pixels[0].length;
    
    // Create new array with swapped dimensions
    Pixel[][] rotated = new Pixel[width][height];
    
    // Create rotation matrix for -90 degrees (which is 90 counterclockwise)
    Matrix2by2 rotMatrix = new Matrix2by2();
    rotMatrix.setRotationMatrix(-90);
    
    // Center of rotation
    Vector1by2 center = new Vector1by2(height / 2.0, width / 2.0);
    
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        // Convert to vector relative to center
        Vector1by2 v = new Vector1by2(row - center.getRow(), col - center.getCol());
        
        // Apply rotation
        Vector1by2 rotatedV = rotMatrix.multiply(v);
        
        // Convert back to absolute coordinates
        int newRow = (int)Math.round(rotatedV.getRow() + center.getRow());
        int newCol = (int)Math.round(rotatedV.getCol() + center.getCol());
        
        // Adjust for new array dimensions (swap rows and columns)
        int targetRow = col;  // Original column becomes new row
        int targetCol = height - 1 - row;  // Original row becomes new column (reversed)
        
        // Ensure we're within bounds
        if (targetRow >= 0 && targetRow < width && targetCol >= 0 && targetCol < height) {
          if (rotated[targetRow] == null) {
            rotated[targetRow] = new Pixel[height];
          }
          rotated[targetRow][targetCol] = pixels[row][col];
        }
      }
    }
    
    // Fill any null pixels with black
    for (int row = 0; row < width; row++) {
      for (int col = 0; col < height; col++) {
        if (rotated[row][col] == null) {
          // Create a black pixel
          Picture tempPic = new Picture(1, 1);
          Pixel[][] tempPixels = tempPic.getPixels2D();
          tempPixels[0][0].setColor(Color.BLACK);
          rotated[row][col] = tempPixels[0][0];
        }
      }
    }
    
    return rotated;
  }
  
  /**
   * Rotates an image -90 degrees (90 clockwise) using matrix multiplication
   * @param pixels 2D array of pixels to rotate
   * @return new 2D array of rotated pixels
   */
  public static Pixel[][] rotateNeg90(Pixel[][] pixels) {
    System.out.println("Rotating 90 degrees clockwise...");
    int height = pixels.length;
    int width = pixels[0].length;
    
    // Create new array with swapped dimensions
    Pixel[][] rotated = new Pixel[width][height];
    
    // Create rotation matrix for 90 degrees (clockwise)
    Matrix2by2 rotMatrix = new Matrix2by2();
    rotMatrix.setRotationMatrix(90);
    
    // Center of rotation
    Vector1by2 center = new Vector1by2(height / 2.0, width / 2.0);
    
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        // Convert to vector relative to center
        Vector1by2 v = new Vector1by2(row - center.getRow(), col - center.getCol());
        
        // Apply rotation
        Vector1by2 rotatedV = rotMatrix.multiply(v);
        
        // Convert back to absolute coordinates
        int newRow = (int)Math.round(rotatedV.getRow() + center.getRow());
        int newCol = (int)Math.round(rotatedV.getCol() + center.getCol());
        
        // Adjust for new array dimensions (swap rows and columns)
        int targetRow = width - 1 - col;  // Original column becomes new row (reversed)
        int targetCol = row;  // Original row becomes new column
        
        // Ensure we're within bounds
        if (targetRow >= 0 && targetRow < width && targetCol >= 0 && targetCol < height) {
          if (rotated[targetRow] == null) {
            rotated[targetRow] = new Pixel[height];
          }
          rotated[targetRow][targetCol] = pixels[row][col];
        }
      }
    }
    
    // Fill any null pixels with black
    for (int row = 0; row < width; row++) {
      for (int col = 0; col < height; col++) {
        if (rotated[row][col] == null) {
          // Create a black pixel
          Picture tempPic = new Picture(1, 1);
          Pixel[][] tempPixels = tempPic.getPixels2D();
          tempPixels[0][0].setColor(Color.BLACK);
          rotated[row][col] = tempPixels[0][0];
        }
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
