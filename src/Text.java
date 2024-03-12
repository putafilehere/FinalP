import java.awt.*;
import java.awt.image.BufferedImage; // Import BufferedImage class
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.*;

public class Text extends GameObject {

    private String text;
    private Color color;

    public Text(Vec2 pos, String text, Color color, double angle, boolean isStatic) {
        super(pos, angle, new Vec2(0, 0), isStatic);//i am magic
        this.text = text;
        this.color = color;
        setSize(getSize());
    }

    public String getText() {return text;}

    public void setText(String text) {this.text = text;}

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.drawString(text, (int)(getPos().getX()), (int)(getPos().getY()) + (int)(getSize().getY()));
    }
    @Override
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

    public void timedText(String stringyThing, Runnable after)
    {
        ScheduledExecutorService textThing = Executors.newScheduledThreadPool(1);
        int[] textCount = {0};
        textThing.scheduleAtFixedRate(() -> {
            if (textCount[0] < stringyThing.length()) {
                setText(getText() + stringyThing.charAt(textCount[0]));
                textCount[0]++;
            } else
            {
                textThing.shutdown();
                after.run();
            }
        }, 50, 50, TimeUnit.MILLISECONDS);
    }
}