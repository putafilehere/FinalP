import javax.swing.*;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.awt.MouseInfo;
import java.awt.PointerInfo;


public class Game extends JPanel implements KeyListener {

    private int width;
    private int height;
    private ArrayList<GameObject> objs = new ArrayList<>();
    private Set<Character> keys = new HashSet<>();

    private GameObject currentItemHeld = null;

    private Sprite player = new Sprite(new Vec2(0, 0), "images/maxwell.jpeg", new Vec2(100, 100), 0, false);

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
        //wrapa
        boolean[] allDone = {false, false};
        int[] textCount = {0};
        String stringyThing = "Yo dude, I got them goods...";

        Text dealerText = new Text(new Vec2(npcHam.getPos().getX()-100, npcHam.getPos().getY()-50), "", new Color(200, 0, 50), 0, false);

        objs.add(dealerText);

        ScheduledExecutorService textThing = Executors.newScheduledThreadPool(1);
        textThing.scheduleAtFixedRate(() -> {
            if (textCount[0] < stringyThing.length()) {
                dealerText.setText(dealerText.getText() + stringyThing.charAt(textCount[0]));
                textCount[0]++;
            } else
            {
                allDone[0] = true;
                textThing.shutdown();
            }
        }, 50, 50, TimeUnit.MILLISECONDS);


        ScheduledExecutorService truckThing = Executors.newScheduledThreadPool(1);
        truckThing.scheduleAtFixedRate(() -> {
            if (allDone[0] && oneTruck.getVel().getX() != -50)
            {
                oneTruck.addVel(new Vec2(-50, 0));
            }
            if (oneTruck.isColliding(npcHam)) {
                //body parts declaration
                Sprite lLeg = new Sprite(new Vec2(width/3-100, height/2), "images/shadder/leftLeg.png", new Vec2(10, 30), 0, true);
                Sprite rLeg = new Sprite(new Vec2(width/3+100, height/2), "images/shadder/rightLeg.png", new Vec2(10, 30), 0, true);
                Sprite lArm = new Sprite(new Vec2(width/3, height/2-100), "images/shadder/leftArm.png", new Vec2(10, 30), 0, true);
                Sprite rArm = new Sprite(new Vec2(width/3, height/2+100), "images/shadder/rightArm.png", new Vec2(10, 30), 0, true);
                Sprite chest = new Sprite(new Vec2(width/3-200, height/2), "images/shadder/chest.png", new Vec2(50, 50), 0, true);
                Sprite head = new Sprite(new Vec2(width/3+200, height/2), "images/shadder/head.png", new Vec2(30, 50), 0, true);
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
                    GameObject randoShmando = objs.get((int)(Math.random() * objs.size()));
                    while (randoShmando == player)
                        randoShmando = objs.get((int)(Math.random() * objs.size()));
                    currentItemHeld = randoShmando;
                    keys.remove('e');
                }
                if (currentItemHeld != null) {
                    PointerInfo pointerInfo = MouseInfo.getPointerInfo();
                    // Get the current mouse location
                    java.awt.Point point = pointerInfo.getLocation();
                    int x = (int) point.getX();
                    int y = (int) point.getY();
                    Vec2 mousePos = new Vec2(x, y);
                    Vec2 difference = mousePos.subtract(player.getPos()).unit();
                    Vec2 otherDiff = difference.normal();
                    // Calculate the new positions for currentItemHeld
                    var extraX = player.getPos().getX() - (difference.getX() * -500);
                    var extraY = player.getPos().getY() - (difference.getY() * -500);
                    // Set the position of currentItemHeld
                    currentItemHeld.getPos().setX(extraX);
                    currentItemHeld.getPos().setY(extraY);
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
                        int overlapX = Math.min(thing.getPos().getX() + thing.getSize().getX() - thing2.getPos().getX(),
                                thing2.getPos().getX() + thing2.getSize().getX() - thing.getPos().getX());
                        int overlapY = Math.min(thing.getPos().getY() + thing.getSize().getY() - thing2.getPos().getY(),
                                thing2.getPos().getY() + thing2.getSize().getY() - thing.getPos().getY());

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
}