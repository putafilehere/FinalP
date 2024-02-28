import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
public class RotateHelper
  {
    public static Image getRotatedInstance(Image image, double angleDegrees) {
        // Convert angle from degrees to radians
        double angleRadians = Math.toRadians(angleDegrees);

        // Create a MediaTracker to wait for image loading
        MediaTracker tracker = new MediaTracker(new Component() {});

        // Add the image to the tracker
        tracker.addImage(image, 0);

        try {
            // Wait until the image is fully loaded
            tracker.waitForID(0);
        } catch (InterruptedException e) {
            // Handle interruption if needed
            e.printStackTrace();
        }

        // Check if the image is fully loaded
        if (tracker.isErrorID(0)) {
            // Image loading error
            return null;
        }

        // Get the dimensions of the original image
        int originalWidth = image.getWidth(null);
        int originalHeight = image.getHeight(null);

        // Calculate the bounding box of the rotated image
        Rectangle bounds = calculateBoundingBox(originalWidth, originalHeight, angleRadians);

        // Create a BufferedImage to hold the rotated image
        BufferedImage rotatedImage = new BufferedImage(bounds.width, bounds.height, BufferedImage.TYPE_INT_ARGB);

        // Get the Graphics2D object to draw on the rotated image
        Graphics2D g2d = rotatedImage.createGraphics();

        // Set the background color (optional)
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, bounds.width, bounds.height);

        // Set the rotation transformation
        AffineTransform rotationTransform = new AffineTransform();
        rotationTransform.rotate(angleRadians, bounds.width / 2.0, bounds.height / 2.0);

        // Apply transformation
        g2d.setTransform(rotationTransform);

        // Draw the original image onto the rotated image using the transformed coordinates
        int x = (bounds.width - originalWidth) / 2;
        int y = (bounds.height - originalHeight) / 2;
        g2d.drawImage(image, x, y, null);

        // Dispose the Graphics2D object
        g2d.dispose();

        // Return the rotated image
        return rotatedImage;
    }

    private static Rectangle calculateBoundingBox(int width, int height, double angleRadians) {
        // Calculate the rotated bounding box
        AffineTransform rotationTransform = AffineTransform.getRotateInstance(angleRadians, width / 2.0, height / 2.0);
        Rectangle bounds = rotationTransform.createTransformedShape(new Rectangle(width, height)).getBounds();
        return bounds;
    }
  }