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

    private double funny = 0;

    // initialization of everything
    public Phase1(int width, int height) {
        this.width = width;
        this.height = height;
        setBackground(new Color(60, 50, 65));
        player = new Sprite(new Vec2(0, 0), "images/hands/fist.png", new Vec2(100, 100), 45);
        objs.add(player);
        addKeyListener(new MyKeyListener());
        setFocusable(true); // Ensure the panel can receive key events
    }

    @Override
    public void paintComponent(Graphics g) {
        funny += 1;
        super.paintComponent(g);
        for (GameObject thing : objs) {
            if (pressedKeys.contains('w')) {
                if (player.getVel().getY() == 0)
                    player.addVel(new Vec2(0, -5));
            } else if (player.getVel().getY() == -5) {
                player.addVel(new Vec2(0, 5));
            }
            if (pressedKeys.contains('a')) {
                if (player.getVel().getX() == 0)
                    player.addVel(new Vec2(-5, 0));
            } else if (player.getVel().getX() == -5) {
                player.addVel(new Vec2(5, 0));
            }
            if (pressedKeys.contains('s')) {
                if (player.getVel().getY() == 0)
                    player.setVel(new Vec2(0, 5));
            } else if (player.getVel().getY() == 5) {
                player.addVel(new Vec2(0, -5));
            }
            if (pressedKeys.contains('d')) {
                if (player.getVel().getX() == 0)
                    player.addVel(new Vec2(5, 0));
            } else if (player.getVel().getX() == 5) {
                player.addVel(new Vec2(-5, 0));
            }
            int yPos = thing.getPos().getY();
            int yVel = thing.getVel().getY();
            int xPos = thing.getPos().getX();
            int xVel = thing.getVel().getX();
            thing.setPos(new Vec2(xPos + xVel, yPos + yVel));
            thing.draw(g);
        }
        player.setAngle(funny);
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
