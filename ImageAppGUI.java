import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Simple GUI menu for image processing operations
 */
public class ImageAppGUI extends JFrame {
    private Picture currentPicture;
    private JLabel imageLabel;
    private ImageIcon imageIcon;
    
    public ImageAppGUI() {
        setTitle("Image Processing App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Create menu bar
        JMenuBar menuBar = new JMenuBar();
        
        // File menu
        JMenu fileMenu = new JMenu("File");
        JMenuItem openItem = new JMenuItem("Open Image");
        JMenuItem saveItem = new JMenuItem("Save Image");
        JMenuItem exitItem = new JMenuItem("Exit");
        
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        
        // Edit menu
        JMenu editMenu = new JMenu("Edit");
        JMenuItem recolorItem = new JMenuItem("Recolor Image");
        JMenuItem negativeItem = new JMenuItem("Negative Image");
        JMenuItem grayscaleItem = new JMenuItem("Grayscale Image");
        
        editMenu.add(recolorItem);
        editMenu.add(negativeItem);
        editMenu.add(grayscaleItem);
        
        // Transform menu
        JMenu transformMenu = new JMenu("Transform");
        JMenuItem rotate180Item = new JMenuItem("Rotate 180°");
        JMenuItem rotate90Item = new JMenuItem("Rotate 90° CCW");
        JMenuItem rotateNeg90Item = new JMenuItem("Rotate 90° CW");
        
        transformMenu.add(rotate180Item);
        transformMenu.add(rotate90Item);
        transformMenu.add(rotateNeg90Item);
        
        // Add menus to menu bar
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(transformMenu);
        
        setJMenuBar(menuBar);
        
        // Image display area
        imageLabel = new JLabel("No image loaded");
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        add(imageLabel, BorderLayout.CENTER);
        
        // Status bar
        JLabel statusBar = new JLabel("Ready");
        add(statusBar, BorderLayout.SOUTH);
        
        // Add action listeners
        openItem.addActionListener(e -> openImage());
        saveItem.addActionListener(e -> saveImage());
        exitItem.addActionListener(e -> System.exit(0));
        
        recolorItem.addActionListener(e -> {
            if (currentPicture != null) {
                Pixel[][] pixels = currentPicture.getPixels2D();
                ImageApp.changeColor(pixels);
                updateImage();
                statusBar.setText("Image recolored");
            }
        });
        
        negativeItem.addActionListener(e -> {
            if (currentPicture != null) {
                Pixel[][] pixels = currentPicture.getPixels2D();
                ImageApp.negativeColor(pixels);
                updateImage();
                statusBar.setText("Negative image created");
            }
        });
        
        grayscaleItem.addActionListener(e -> {
            if (currentPicture != null) {
                Pixel[][] pixels = currentPicture.getPixels2D();
                ImageApp.grayscale(pixels);
                updateImage();
                statusBar.setText("Grayscale image created");
            }
        });
        
        rotate180Item.addActionListener(e -> {
            if (currentPicture != null) {
                Pixel[][] pixels = currentPicture.getPixels2D();
                ImageApp.rotate180(pixels);
                updateImage();
                statusBar.setText("Image rotated 180°");
            }
        });
        
        // Set window size and make visible
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void openImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new java.io.File("."));
        int result = fileChooser.showOpenDialog(this);
        
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                currentPicture = new Picture(fileChooser.getSelectedFile().getPath());
                updateImage();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error loading image: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void saveImage() {
        if (currentPicture == null) {
            JOptionPane.showMessageDialog(this, "No image to save", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new java.io.File("."));
        int result = fileChooser.showSaveDialog(this);
        
        if (result == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getPath();
            if (!filePath.toLowerCase().endsWith(".jpg")) {
                filePath += ".jpg";
            }
            currentPicture.write(filePath);
        }
    }
    
    private void updateImage() {
        if (currentPicture != null) {
            Image image = currentPicture.getImage();
            Image scaledImage = image.getScaledInstance(600, 400, Image.SCALE_SMOOTH);
            imageIcon = new ImageIcon(scaledImage);
            imageLabel.setIcon(imageIcon);
            imageLabel.setText("");
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ImageAppGUI());
    }
}
