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
        setBackground(new Color(60, 50, 65));
        objs.add(new Text(new Vec2(0, 0), "import java.awt.*;"));
    }
}
