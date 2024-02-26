import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Intro extends JPanel {

    private int width;

    private ArrayList<GameObject> objs = new ArrayList<>();
  
    private int height;
  
    public Intro (int width, int height) {
        this.width = width;
        this.height = height;
        setBackground(new Color(50, 40, 45));
        objs.add(new Text(new Vec2(0, 0), "import java.awt.*;", new Color(255, 255, 0)));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (GameObject thing : objs) {
            int yPos = thing.getPos().getY();
            int yVel = thing.getVel().getY();
            int xPos = thing.getPos().getX();
            int xVel = thing.getVel().getX();
            thing.setPos(new Vec2(xPos + xVel, yPos + yVel));
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