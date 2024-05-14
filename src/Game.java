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

    private Enemy[] enemyPrefabs;

    private Enemy[][][] waves;

    private Wave[] game;

    private int time = 0;

    private int timeThingy = 0;

    private Sprite player = new Sprite(new Vec2(0, 0), "images/guy.png", new Vec2(90, 150), 0, false);
    public Game(int width, int height) 
    {
        this.width = width;
        this.height = height;
        setBackground(new Color(50, 40, 45));
        enemyPrefabs = new Enemy[]{
                /*Index 0*/ new Enemy("images/slime.png", new Vec2(50, 40), 1, width, height, 2, null),
                /*Index 1*/ new Enemy("images/skelebones.png", new Vec2(30, 80), 2, width, height, 4, null),
                /*Index 2*/ new Enemy("images/motherslime.png", new Vec2(80, 60), 2, width, height, 2, () -> spawnEnemy(0, 3)),
                /*Index 3*/ new Enemy("images/scary_ball.png", new Vec2(100, 100), 4, width, height, 1, null),
                /*Index 4*/ new Enemy("images/snail.png", new Vec2(50, 20), 1, width, height, 1, () -> spawnEnemy(4, 1)),
                /*Index 5*/ new Enemy("images/fdd.png", new Vec2(40, 75), 1, width, height, 7.5, null),
                /*Index 6*/ new Enemy("images/spider.png", new Vec2(70, 40), 4, width, height, 3, () -> spawnEnemy(7, 4)),
                /*Index 7*/ new Enemy("images/spider.png", new Vec2(35, 20), 1, width, height, 1.5, null),
                /*Index 8*/ new Enemy("images/wall.jpeg", new Vec2(200, 200), 30, width, height, 1, () -> spawnEnemy(9, 10)),
                /*Index 9*/ new Enemy("images/brick.jpeg", new Vec2(20, 20), 1, width, height, 1, null),
        };
        waves = new Enemy[][][]{
                // Wave 1
                {
                    // Rush 1
                        {
                             enemyPrefabs[0],
                             enemyPrefabs[0],
                             enemyPrefabs[0],
                             enemyPrefabs[0],
                        },
                    // Rush 2
                        {
                            enemyPrefabs[0],
                            enemyPrefabs[0],
                            enemyPrefabs[0],
                            enemyPrefabs[0],
                        }
                },
                // Wave 2
                {
                    // Rush 1
                        {
                            enemyPrefabs[0],
                            enemyPrefabs[0],
                            enemyPrefabs[1],
                            enemyPrefabs[0],
                        },
                    // Rush 2
                        {
                            enemyPrefabs[1],
                            enemyPrefabs[1],
                            enemyPrefabs[0],
                            enemyPrefabs[0],
                        }
                }
        };


        game = new Wave[]{
               new Wave(waves[0], new int[]{1000, 1000}, new int[]{0, 2500}),
               new Wave(waves[1], new int[]{1500, 500}, new int[]{0, 3000}),
        };


        player.setPos(new Vec2(width / 2.0, height / 2.0));
        player.setPos(player.myOtherMagic());
        objs.add(player);
        //wrappa
        setFocusable(true); // Allow panel to get focus for key events
        addKeyListener(this); // Add key listener to the panel
        addMouseMotionListener(this); // Add mouse motion listener to the panel
        addMouseListener(this); //add moose mistener
        game[1].start(objs);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        ArrayList<GameObject> objectsToPaint = new ArrayList<>(objs);

        for (GameObject thing : objectsToPaint) painting: {
            if (thing.isStatic()) {
                for (GameObject thing2 : objs) {
                    if ((thing.hasTag("enemy") && thing2.hasTag("projectile")) || (thing.hasTag("projectile") && thing2.hasTag("enemy"))) {
                        if (thing.isColliding(thing2)) {
                            if (thing instanceof Enemy)
                            {
                                ((Enemy)thing).setHealth(((Enemy)thing).getHealth()-1);
                                ((Projectile)thing2).setHealth(((Projectile)thing2).getHealth()-1);
                                if (((Enemy)thing).getHealth() == 0) {

                                    ((Enemy)thing).death();
                                    objs.remove(thing);
                                }
                                if (((Projectile)thing2).getHealth() == 0)
                                    objs.remove(thing2);
                            } else {
                                ((Enemy)thing2).setHealth(((Enemy)thing2).getHealth()-1);
                                ((Projectile)thing).setHealth(((Projectile)thing).getHealth()-1);
                                if (((Enemy)thing2).getHealth() == 0) {
                                    ((Enemy)thing2).death();
                                    objs.remove(thing2);
                                }
                                if (((Projectile)thing).getHealth() == 0)
                                    objs.remove(thing);
                            }
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
          timeThingy++;
          if (timeThingy == 100)
          {
            time++;
            timeThingy = 0;
            System.out.println("Seconds elapsed " + time);
          }
        } catch (InterruptedException e) {
            System.out.println("oh no! I accidentally " + e + ". Not again!");
        }

        repaint();
    }

    public int randInt(int min, int max)
    {
        return (int) (Math.random() * (max - min + 1)) + min;
    }


    public void spawnEnemy(int index, int count) {
        for (int i = 0; i < count; i++) {
            Enemy enemy = enemyPrefabs[index].clone();
            enemy.addTag("enemy");
            objs.add(enemy);
        }
    }




    @Override
    public void keyPressed(KeyEvent e) {
        // Add the pressed key to the keys set
        keys.add(e.getKeyChar());
        // Handle any actions related to the pressed key
        // For example, move a player object based on key presses
        if (e.getKeyChar() == 'e')
            spawnEnemy(8, 1);
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
    public void mouseDragged(MouseEvent e)
    {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouse = new Vec2(e.getX(), e.getY());
        Vec2 mousePos = new Vec2(e.getX(), e.getY());
        Vec2 playerToMouse = mousePos.subtract(player.middlePos()).unit();
        Projectile newProj = new Projectile(player.middlePos(), new Vec2(20, 20), new Color(100, 100, 100), 3);
        newProj.addVel(playerToMouse.multiply(10.0));
        objs.add(newProj);
    }


    @Override
    public void mouseReleased(MouseEvent e) 
    {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}
