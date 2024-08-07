import java.awt.image.BufferedImage;

public class ImageManipulation {
    public static void invertColor(BufferedImage image) {
            // Loop through each pixel
            for (int y = 0; y < image.getHeight(); ++y) {
                for (int x = 0; x < image.getWidth(); ++x) {
                    // Get the RGB value of the pixel
                    int rgb = image.getRGB(x, y);

                    // Extract the color components
                    int alpha = (rgb >> 24) & 0xff;
                    int red = (rgb >> 16) & 0xff;
                    int green = (rgb >> 8) & 0xff;
                    int blue = rgb & 0xff;

                    // Manipulate the color components (example: invert colors)
                    red = 255 - red;
                    green = 255 - green;
                    blue = 255 - blue;

                    // Reassemble the pixel
                    rgb = (alpha << 24) | (red << 16) | (green << 8) | blue;

                    // Set the new RGB value
                    image.setRGB(x, y, rgb);
                }
            }
    }
}
