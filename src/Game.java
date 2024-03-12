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

        private GameObject currentItemHeld = null;

        private Vec2 mouse = null;

        private Sprite player = new Sprite(new Vec2(0, 0), "images/maxwell.jpeg", new Vec2(100, 100), 0, false);

        private Rect pogressBar;

        public Game(int width, int height) {
            this.width = width;
            this.height = height;
            setBackground(new Color(50, 40, 45));

            //cutscene
            player.setPos(new Vec2(width/3, height/4));
            objs.add(player);
            Sprite npcHam = new Sprite(new Vec2(width/3, height/2), "images/shaddy.png", new Vec2(70, 100), 0, true);
            objs.add(npcHam);

            Sprite oneTruck = new Sprite(new Vec2(width, height/2), "images/truck.png", new Vec2(1000, 300), 0, false);

            objs.add(oneTruck);
            //wrappa
            boolean[] allDone = {false, false};
            String stringyThing = "Yo dude, I got them goods...";

            Text dealerText = new Text(new Vec2(npcHam.getPos().getX()-100, npcHam.getPos().getY()-50), "", new Color(200, 0, 50), 0, false);

            objs.add(dealerText);

            dealerText.timedText(stringyThing, () -> {
                allDone[0] = true;
            });


            ScheduledExecutorService truckThing = Executors.newScheduledThreadPool(1);
            truckThing.scheduleAtFixedRate(() -> {
                if (allDone[0] && oneTruck.getVel().getX() != -50)
                {
                    oneTruck.addVel(new Vec2(-50, 0));
                }
                if (oneTruck.isColliding(npcHam)) {
                    //body parts declaration
                    Sprite lLeg = new Sprite(new Vec2(width/3-100, height/2), "images/shadder/leftLeg.png", new Vec2(10, 30), 0, true);
                    lLeg.setFriction(true);
                    Sprite rLeg = new Sprite(new Vec2(width/3+100, height/2), "images/shadder/rightLeg.png", new Vec2(10, 30), 0, true);
                    rLeg.setFriction(true);
                    Sprite lArm = new Sprite(new Vec2(width/3, height/2-100), "images/shadder/leftArm.png", new Vec2(10, 30), 0, true);
                    lArm.setFriction(true);
                    Sprite rArm = new Sprite(new Vec2(width/3, height/2+100), "images/shadder/rightArm.png", new Vec2(10, 30), 0, true);
                    rArm.setFriction(true);
                    Sprite chest = new Sprite(new Vec2(width/3-200, height/2), "images/shadder/chest.png", new Vec2(50, 50), 0, true);
                    chest.setFriction(true);
                    Sprite head = new Sprite(new Vec2(width/3+200, height/2), "images/shadder/head.png", new Vec2(30, 50), 0, true);
                    head.setFriction(true);
                    pogressBar = new Rect(new Vec2(0, height - 200), new Vec2(5, 200), new Color(0, 0, 0), 0, false);
                    objs.remove(npcHam);
                    objs.add(lLeg);
                    objs.add(rLeg);
                    objs.add(lArm);
                    objs.add(rArm);
                    objs.add(chest);
                    objs.add(head);
                    allDone[1] = true;
                    truckThing.shutdown();
                }
            }, 100, 100, TimeUnit.MILLISECONDS);


            setFocusable(true); // Allow panel to get focus for key events
            addKeyListener(this); // Add key listener to the panel
            addMouseMotionListener(this); // Add mouse motion listener to the panel
            addMouseListener(this); //add moose mistener

            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
            final int speed = 10;
            scheduler.scheduleAtFixedRate(() -> {
                //player movement
                if (allDone[1]) {
                    player.setStatic(true);
                    if (keys.contains('w')) {
                        player.setVel(new Vec2(player.getVel().getX(), -speed));
                    } else if (player.getVel().getY() == -speed) {
                        player.setVel(new Vec2(player.getVel().getX(), 0));
                    }
                    if (keys.contains('a')) {
                        player.setVel(new Vec2(-speed, player.getVel().getY()));
                    } else if (player.getVel().getX() == -speed) {
                        player.setVel(new Vec2(0, player.getVel().getY()));
                    }
                    if (keys.contains('s')) {
                        player.setVel(new Vec2(player.getVel().getX(), speed));
                    } else if (player.getVel().getY() == speed) {
                        player.setVel(new Vec2(player.getVel().getX(), 0));
                    }
                    if (keys.contains('d')) {
                        player.setVel(new Vec2(speed, player.getVel().getY()));
                    } else if (player.getVel().getX() == speed) {
                        player.setVel(new Vec2(0, player.getVel().getY()));
                    }
                    if (keys.contains('e'))
                    {
                        System.out.println("thing go brrr");
                        GameObject randoShmando = null;
                        for (GameObject object : objs)
                            if (object.isHovered(mouse))
                                randoShmando = object;
                        if (randoShmando == null || randoShmando == currentItemHeld)
                            currentItemHeld = null;
                        else
                            currentItemHeld = randoShmando;
                        keys.remove('e');
                    }
                    if (currentItemHeld != null && mouse != null) {
                        // Calculate the direction vector from the player's middle position to the mouse
                        Vec2 playerToMouse = mouse.subtract(player.middlePos()).unit();

                        // Set the distance you want the held item to be from the player
                        double distance = 150; // Adjust this value as needed

                        // Calculate the position of the held item based on the player's middle position and the direction vector
                        int newX = (int) (player.middlePos().getX() + playerToMouse.getX() * distance);
                        int newY = (int) (player.middlePos().getY() + playerToMouse.getY() * distance);

                        // Set the position of currentItemHeld
                        currentItemHeld.getPos().setX(newX);
                        currentItemHeld.getPos().setY(newY);
                    }



                }
            }, 10, 10, TimeUnit.MILLISECONDS);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            ArrayList<GameObject> objectsToPaint = new ArrayList<>(objs);

            for (GameObject thing : objectsToPaint) {
                if (thing.isStatic()) {
                    for (GameObject thing2 : objs) {
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
                if (thing.hasFriction()) {
                    double frictionValue = 0.05;
                    double newVelX = thing.getVel().getX() > 0 ? thing.getVel().getX() - frictionValue : thing.getVel().getX() + frictionValue;
                    double newVelY = thing.getVel().getY() > 0 ? thing.getVel().getY() - frictionValue : thing.getVel().getY() + frictionValue;
                    thing.setVel(new Vec2(newVelX, newVelY));
                }
                int yPos = (int)(thing.getPos().getY());
                int yVel = (int)(thing.getVel().getY());
                int xPos = (int)(thing.getPos().getX());
                int xVel = (int)(thing.getVel().getX());
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



        @Override
        public void keyPressed(KeyEvent e) {
            // Add the pressed key to the keys set
            keys.add(e.getKeyChar());
            // Handle any actions related to the pressed key
            // For example, move a player object based on key presses
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
            // Start a Swing Timer to continuously increase the progress bar size
            Timer timer = new Timer(50, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    // Update the progress bar size
                    pogressBar.setSize(pogressBar.getSize().add(new Vec2(5, 0)));
                    // Repaint the panel to reflect the changes
                    repaint();
                }
            });
            // Start the timer
            timer.start();

            // Add a mouse listener to detect when the left mouse button is released
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    // Stop the timer when the left mouse button is released
                    timer.stop();
                    // Remove the temporary mouse listener
                    removeMouseListener(this);
                }
            });
        }


        @Override
        public void mouseReleased(MouseEvent e) {
            if (currentItemHeld != null) {
                Vec2 mousePos = new Vec2(e.getX(), e.getY());
                Vec2 playerToMouse = mousePos.subtract(player.middlePos()).unit();
                currentItemHeld.addVel(playerToMouse.multiply(5));
                currentItemHeld = null;
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

    }
