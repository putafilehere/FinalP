import javax.swing.*;
import java.awt.*;
import java.util.*;
public class Screen extends JPanel {

    ArrayList<GameObject> objs = new ArrayList<GameObject>();

    public Screen()
    {
        setBackground(new Color(0, 100 ,100));
    }

    public void add()
    {

    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);




        try
        {
            Thread.sleep(10);
        } catch (InterruptedException e)
        {
            System.out.println("oh no! I accidentally " + e + ". Not again!");
        }
        repaint();
    }
}
