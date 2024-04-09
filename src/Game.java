    import javax.swing.*;
    import javax.swing.Timer;
    import java.awt.*;
    import java.awt.event.*;
    import java.util.*;
    import java.util.concurrent.Executors;
    import java.util.concurrent.ScheduledExecutorService;
    import java.util.concurrent.TimeUnit;
    import java.awt.MouseInfo;
    import java.awt.PointerInfo;


    public class Game extends JPanel implements KeyListener, MouseMotionListener, MouseListener {

        private int width;
        private int height;
        private ArrayList<GameObject> objs = new ArrayList<>();
        private Set<Character> keys = new HashSet<>();

        private Vec2 mouse = null;

        private Sprite player = new Sprite(new Vec2(0, 0), "images/maxwell.jpeg", new Vec2(100, 100), 0, false);
        public Game(int width, int height) {
            this.width = width;
            this.height = height;
            setBackground(new Color(50, 40, 45));

            //cutscene
            player.setPos(new Vec2(width / 2-player.getSize().getX(), height / 2-player.getSize().getY()));
            objs.add(player);
            //wrappa
            setFocusable(true); // Allow panel to get focus for key events
            addKeyListener(this); // Add key listener to the panel
            addMouseMotionListener(this); // Add mouse motion listener to the panel
            addMouseListener(this); //add moose mistener
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            ArrayList<GameObject> objectsToPaint = new ArrayList<>(objs);

            for (GameObject thing : objectsToPaint) painting: {
                if (thing.isStatic()) {
                    for (GameObject thing2 : objs) {
                        if ((thing.hasTag("enemy") && thing2.hasTag("enemy")) || (thing.hasTag("projectile") && thing2.hasTag("enemy"))) {
                            if (thing.isColliding(thing2)) {
                                objs.remove(thing);
                                objs.remove(thing2);
                                System.out.println("DIE!");
                                break painting;
                                //skip a frame to avoid errors, maybe bad coding practice
                            }
                        }


                        if (thing2.isStatic() && thing != thing2 && thing.isColliding(thing2)) {
                            //collision resolving

                            final int errorMargin = 5; // Adjust this value according to your needs

                            // Calculate the overlap along each axis
                            int overlapX = (int)(Math.min(thing.getPos().getX() + thing.getSize().getX() - thing2.getPos().getX(),
                                    thing2.getPos().getX() + thing2.getSize().getX() - thing.getPos().getX()));
                            int overlapY = (int)(Math.min(thing.getPos().getY() + thing.getSize().getY() - thing2.getPos().getY(),
                                    thing2.getPos().getY() + thing2.getSize().getY() - thing.getPos().getY()));

                            if (overlapX < overlapY) {
                                // Adjust horizontally
                                if (thing.getPos().getX() < thing2.getPos().getX()) {
                                    thing.setPos(new Vec2(thing.getPos().getX() - overlapX - errorMargin, thing.getPos().getY()));
                                } else {
                                    thing.setPos(new Vec2(thing.getPos().getX() + overlapX + errorMargin, thing.getPos().getY()));
                                }
                            } else {
                                // Adjust vertically
                                if (thing.getPos().getY() < thing2.getPos().getY()) {
                                    thing.setPos(new Vec2(thing.getPos().getX(), thing.getPos().getY() - overlapY - errorMargin));
                                } else {
                                    thing.setPos(new Vec2(thing.getPos().getX(), thing.getPos().getY() + overlapY + errorMargin));
                                }
                            }
                        }
                    }
                }
                int yPos = (int)(thing.getPos().getY());
                int yVel = (int)(thing.getVel().getY());
                int xPos = (int)(thing.getPos().getX());
                int xVel = (int)(thing.getVel().getX());
                thing.setPos(new Vec2(xPos + xVel, yPos + yVel));
                thing.draw(g);
                if (thing.isOffscreen(width, height))
                    objs.remove(thing);
                else
                    thing.draw(g);


            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println("oh no! I accidentally " + e + ". Not again!");
            }

            repaint();
        }

        public int randInt(int min, int max)
        {
            return (int) (Math.random() * (max - min + 1)) + min;
        }


        public void spawnEnemy() {
            // Debugging statement to ensure the method is called
            System.out.println("Spawning enemy...");

            // Randomly determine which edge the enemy should spawn on
            int edge = randInt(0, 3); // 0: top, 1: right, 2: bottom, 3: left

            int x = 0, y = 0;

            // Set initial position based on the chosen edge
            switch (edge) {
                case 0: // Top edge
                    x = randInt(0, width);
                    y = 0;
                    break;
                case 1: // Right edge
                    x = width;
                    y = randInt(0, height);
                    break;
                case 2: // Bottom edge
                    x = randInt(0, width);
                    y = height;
                    break;
                case 3: // Left edge
                    x = 0;
                    y = randInt(0, height);
                    break;
            }

            Vec2 posVec = new Vec2(x, y);
            Vec2 center = new Vec2(width / 2.0, height / 2.0);
            Sprite enemy = new Sprite(posVec, "images/shaddy.png", new Vec2(100, 100), 0, false);
            Vec2 directionToCenter = center.subtract(enemy.middlePos()).unit();
            enemy.addVel(directionToCenter.multiply(5));
            objs.add(enemy);

        }




        @Override
        public void keyPressed(KeyEvent e) {
            // Add the pressed key to the keys set
            keys.add(e.getKeyChar());
            // Handle any actions related to the pressed key
            // For example, move a player object based on key presses
            if (e.getKeyChar() == 'e')
                spawnEnemy();
        }

        @Override
        public void keyReleased(KeyEvent e) {
            // Remove the released key from the keys set
            keys.remove(e.getKeyChar());
            // Handle any actions related to the released key
        }

        @Override
        public void keyTyped(KeyEvent e) {
            // This method is not used in this example
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            // Handle mouse movement here
            // e.getX() and e.getY() gives the current mouse coordinates
            mouse = new Vec2(e.getX(), e.getY());
            // For example, you can update some object's position based on mouse movement
        }

        @Override
        public void mouseDragged(MouseEvent e) {

            mouse = new Vec2(e.getX(), e.getY());
            // This method is not used in this example
        }

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            Vec2 mousePos = new Vec2(e.getX(), e.getY());
            Vec2 playerToMouse = mousePos.subtract(player.middlePos()).unit();
            Rect newProj = new Rect(player.middlePos(), new Vec2(20, 20), new Color(100, 100, 100), 0, true);
            newProj.addVel(playerToMouse.multiply(10.0));
            objs.add(newProj);
            System.out.println("hi");
        }


        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

    }
