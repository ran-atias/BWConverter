import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MainFrame extends JFrame {
    // Members
    private final ArrayList<EventListener> listeners = new ArrayList<>();
    private final JLabel m_imageLabel;
    // Methods
    public void addEventListener(EventListener listener) { listeners.add(listener);}
    //public void removeEventListener(EventListener listener) {listeners.remove(listener);}
    public void raiseEvent(EventObject.Type type, Object obj) {
        EventObject event = new EventObject(type, obj);
        for (EventListener listener : listeners) {
            listener.handleEvent(event);
        }
    }
    // Constructor
    public MainFrame() {
        // Set the title of the JFrame, and some other parameters
        setTitle("JPEG Defect Finder");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(650, 700));

        // Create a JPanel to hold the image
        JPanel imagePanel = new JPanel();
        m_imageLabel = new JLabel();
        imagePanel.add(m_imageLabel);
        try {
            String dir = System.getProperty("user.dir") + "\\src\\pics\\no_image.jpg";
            BufferedImage buffImg = ImageIO.read(new File(dir));
            m_imageLabel.setIcon(new ImageIcon(buffImg));
            raiseEvent(EventObject.Type.IMG_UPDATED, buffImg);
        } catch (IOException e) {
            System.out.println("Could not open and load no_image.jpg");
            System.out.println(e.getMessage());
        }
        // Create a JPanel to hold the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        // Create buttons and add them to the button panel
        JButton openButton = new JButton("Open Image");
        buttonPanel.add(openButton);

        JButton runButton = new JButton("Run");
        runButton.addActionListener(_ -> raiseEvent(EventObject.Type.RUN_CMD, null));
        buttonPanel.add(runButton);

        // Create the button listener to open the file chooser
        openButton.addActionListener(_ -> {
            JFileChooser fileChooser = new JFileChooser();
            // if "D:\\Pictures" doesn't exist, not exception will be thrown
            // but rather, currentDirectory will be chosen
            fileChooser.setCurrentDirectory(new File("D:\\Pictures"));
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    BufferedImage origImg = ImageIO.read(selectedFile);
                    if (origImg != null) { // if file is a readable img
                        BufferedImage scaledBuffImg = scaleBufferedImage(origImg);
                        render(scaledBuffImg);
                        raiseEvent(EventObject.Type.IMG_UPDATED, scaledBuffImg);
                    }
                    else {
                        JOptionPane.showMessageDialog(null,
                                "File format is not supported", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException e) {
                    System.err.println("Could not open and load required file");
                    System.out.println(e.getMessage());
                }
            }
        });

        // Add the panels to the JFrame
        add(imagePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack(); // Pack the JFrame to fit all it's components together with good look-and-feel
        setVisible(true); // Set the JFrame to be visible
    }

    public void render(BufferedImage buffImg) {
        m_imageLabel.setIcon(new ImageIcon(buffImg));
        // Refresh the JFrame
        revalidate();
        repaint();
    }

    private BufferedImage scaleBufferedImage(BufferedImage buffImg) {
        int newWidth = 500, newHeight = 500; // Define the new dimensions
        // Create a new BufferedImage for the scaled image
        BufferedImage scaledImage = new BufferedImage(newWidth, newHeight, buffImg.getType());
        Graphics2D g2d = scaledImage.createGraphics(); // Get the Graphics2D object

        // Draw the original image scaled to the new dimensions
        g2d.drawImage(buffImg, 0, 0, newWidth, newHeight, null);
        g2d.dispose();

        return scaledImage; // save scaled image reference to the m_BuffImage
    }
}
