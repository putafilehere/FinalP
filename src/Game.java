import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Game extends JPanel {

    private int width;

    private ArrayList<GameObject> objs = new ArrayList<>();
  
    private int height;
  
    public Game(int width, int height) {
        this.width = width;
        this.height = height;
        setBackground(new Color(50, 40, 45));

    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (GameObject thing : objs) {
            int yPos = thing.getPos().getY();
            int xPos = thing.getPos().getX();
            thing.setPos(new Vec2(xPos, yPos));
            thing.draw(g);
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            System.out.println("oh no! I accidentally " + e + ". Not again!");
        }
        repaint();
    }
}