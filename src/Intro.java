import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class Intro extends JPanel {

    private int width;

    private ArrayList<GameObject> objs = new ArrayList<>();
  
    private int height;
  
    public Intro (int width, int height) {
        this.width = width;
        this.height = height;
        setBackground(new Color(50, 40, 45));
      //these wrappers are necessary to make them "effectively" final
        int[] yCounterWrapper = {0};
        int[] startCountWrapper = {0};
      String[] codeLines = {
          "import java.awt.*;",
          "import javax.swing.*;",
          "class Main {",
          "  public static void main(String[] args) {",
          "    JFrame frame = new JFrame();",
          "    Dimension size = Toolkit.getDefaultToolkit().getScreenSize();",
          "    int width = (int)size.getWidth();",
          "    int height = (int)size.getHeight();",
          "    frame.setSize(width, height);",
          "    JPanel panel = new JPanel();",
          "    frame.add(panel);",
          "    frame.setVisible(true);",
          "  }",
          "}"
      };
        Timer startCode = new Timer(500, (ActionEvent e) -> {

          objs.add(new Text(new Vec2(0, yCounterWrapper[0]), codeLines[startCountWrapper[0]], new Color(255, 255, 0)));
          yCounterWrapper[0] += objs.get(objs.size()-1).getSize().getY();
          startCountWrapper[0]++;
          if (startCountWrapper[0] >= codeLines.length)
          {
            startCode.stop();
          }
        });
        startCode.start();

      
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