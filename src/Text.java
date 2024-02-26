import java.awt.*;
import java.awt.image.BufferedImage; // Import BufferedImage class
import javax.swing.*;

public class Text extends GameObject {

    private String text;
    private Color color;

    public Text(Vec2 pos, String text, Color color) {
        super(pos);//i am magic
        this.text = text;
        this.color = color;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.drawString(text, getPos().getX(), getPos().getY());
    }

    public Vec2 getTextSize() {
        Graphics2D g2d = (Graphics2D) new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB).getGraphics();

        // Get the font metrics (uses default font)
        FontMetrics fontMetrics = g2d.getFontMetrics();

        // Get the height of the text
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