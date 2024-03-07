import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

public class Phase1 extends JPanel {

    private ArrayList<GameObject> objs = new ArrayList<>();
    private int width, height;

    private Set<Character> pressedKeys = new HashSet<>();

    private Sprite player;

    private Sprite bob;

    // initialization of everything
    public Phase1(int width, int height) {
        this.width = width;
        this.height = height;
        setBackground(new Color(60, 50, 65));
        player = new Sprite(new Vec2(0, 0), "images/hands/fist.png", new Vec2(100, 100), 45);
        addKeyListener(new MyKeyListener());
        setFocusable(true); // Ensure the panel can receive key events
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (GameObject thing : objs) {
            thing.draw(g);
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            System.out.println("oh no! I accidentally " + e + ". Not again!");
        }
        repaint();
    }

    private class MyKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {
            // Not used in this case
        }

        @Override
        public void keyPressed(KeyEvent e) {
            char key = e.getKeyChar();
            switch (key) {
                case 'w':
                case 'a':
                case 's':
                case 'd':
                    pressedKeys.add(key);
                    System.out.println(key);
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            char key = e.getKeyChar();
            switch (key) {
                case 'w':
                case 'a':
                case 's':
                case 'd':
                    pressedKeys.remove(key);
                    System.out.println(key);
                    break;
            }
        }
    }
}
