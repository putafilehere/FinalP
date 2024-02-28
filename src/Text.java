import java.awt.*;
import java.awt.image.BufferedImage; // Import BufferedImage class
import javax.swing.*;

public class Text extends GameObject {

    private String text;
    private Color color;

    public Text(Vec2 pos, String text, Color color, double angle) {
        super(pos, angle);//i am magic
        this.text = text;
        this.color = color;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.drawString(text, getPos().getX(), getPos().getY() + getSize().getY());
    }

    public Vec2 getSize() {
        Graphics2D g2d = (Graphics2D) new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB).getGraphics();

        // Get the default font metrics by for some reason creating a default buffered image and 
        FontMetrics fontMetrics = g2d.getFontMetrics();
        // Get height of text
        int textHeight = fontMetrics.getHeight();
        int totalWidth = 0;
        for (int i = 0; i < text.length(); i++) {
            char character = text.charAt(i);
            int charWidth = fontMetrics.charWidth(character);
            totalWidth += charWidth;
        }

        return new Vec2(totalWidth, fontMetrics.getHeight());
    }
}