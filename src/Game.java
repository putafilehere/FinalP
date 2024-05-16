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

    private int  waveNum = 0;

    private Sprite nextButton;

    private Sprite shopButton;

    private JFrame shopFrame;

    private JLabel moneyLabel;
    private static final int LAST_WAVE = 15;

    public boolean waveOver = false;
    private boolean firstOver = false;

    public boolean lastEnSpawned = false;

    public int money = 0;

    private int sharpness = 1;

    private int chonk = 20;

    private boolean auto = false;

    private JFrame gamey;


    private Sprite player = new Sprite(new Vec2(0, 0), "images/guy.png", new Vec2(90, 150), 0, false);
    public Game(int width, int height, JFrame gamey)
    {
        this.gamey = gamey;
        this.width = width;
        this.height = height;
        player.addTag("player");
        setBackground(new Color(50, 40, 45));
        enemyPrefabs = new Enemy[]{
                /*Index 0*/ new Enemy("images/slime.png", new Vec2(50, 40), 1, width, height, 1, 1, null),
                /*Index 1*/ new Enemy("images/skelebones.png", new Vec2(30, 80), 2, width, height, 2, 1, null),
                /*Index 2*/ new Enemy("images/motherslime.png", new Vec2(80, 60), 2, width, height, 1, 2, () -> spawnEnemy(0, 3)),
                /*Index 3*/ new Enemy("images/scary_ball.png", new Vec2(100, 100), 4, width, height, 1, 2, null),
                /*Index 4*/ new Enemy("images/snail.png", new Vec2(50, 20), 1, width, height, 0.5, 0, () -> spawnEnemy(4, 1)),
                /*Index 5*/ new Enemy("images/fdd.png", new Vec2(40, 75), 1, width, height, 3, 3, null),
                /*Index 6*/ new Enemy("images/spider.png", new Vec2(70, 40), 4, width, height, 1.5, 3, () -> spawnEnemy(7, 4)),
                /*Index 7*/ new Enemy("images/spider.png", new Vec2(35, 20), 1, width, height, 0.75, 1, null),
                /*Index 8*/ new Enemy("images/wall.jpeg", new Vec2(200, 200), 30, width, height, 0.5, 15, () -> spawnEnemy(9, 10)),
                /*Index 9*/ new Enemy("images/brick.jpeg", new Vec2(20, 20), 1, width, height, 1, 3, null),
        };
        waves = new Enemy[][][]{
                // Wave 1
                {
                        // Rush 1
                        {enemyPrefabs[0], enemyPrefabs[0], enemyPrefabs[0], enemyPrefabs[0]},
                        // Rush 2
                        {enemyPrefabs[0], enemyPrefabs[0], enemyPrefabs[0], enemyPrefabs[0]}
                },
                // Wave 2
                {
                        // Rush 1
                        {enemyPrefabs[0], enemyPrefabs[0], enemyPrefabs[1], enemyPrefabs[0]},
                        // Rush 2
                        {enemyPrefabs[1], enemyPrefabs[1], enemyPrefabs[0], enemyPrefabs[0]}
                },
                // Wave 3
                {
                        // Rush 1
                        {enemyPrefabs[1], enemyPrefabs[1], enemyPrefabs[2], enemyPrefabs[1]},
                        // Rush 2
                        {enemyPrefabs[2], enemyPrefabs[1], enemyPrefabs[1], enemyPrefabs[2]}
                },
                // Wave 4
                {
                        // Rush 1
                        {enemyPrefabs[2], enemyPrefabs[3], enemyPrefabs[3], enemyPrefabs[2]},
                        // Rush 2
                        {enemyPrefabs[2], enemyPrefabs[3], enemyPrefabs[3], enemyPrefabs[2]}
                },
                // Wave 5
                {
                        // Rush 1
                        {enemyPrefabs[4], enemyPrefabs[4], enemyPrefabs[1], enemyPrefabs[4]},
                        // Rush 2
                        {enemyPrefabs[1], enemyPrefabs[4], enemyPrefabs[1], enemyPrefabs[4]},
                        // Rush 3
                        {enemyPrefabs[4], enemyPrefabs[1], enemyPrefabs[4], enemyPrefabs[1]}
                },
                // Wave 6
                {
                        // Rush 1
                        {enemyPrefabs[5], enemyPrefabs[0], enemyPrefabs[5], enemyPrefabs[0]},
                        // Rush 2
                        {enemyPrefabs[0], enemyPrefabs[5], enemyPrefabs[0], enemyPrefabs[5]}
                },
                // Wave 7
                {
                        // Rush 1
                        {enemyPrefabs[6], enemyPrefabs[6], enemyPrefabs[7], enemyPrefabs[7]},
                        // Rush 2
                        {enemyPrefabs[7], enemyPrefabs[7], enemyPrefabs[6], enemyPrefabs[6]}
                },
                // Wave 8
                {
                        // Rush 1
                        {enemyPrefabs[9], enemyPrefabs[9], enemyPrefabs[9], enemyPrefabs[9]},
                        // Rush 2
                        {enemyPrefabs[9], enemyPrefabs[9], enemyPrefabs[9], enemyPrefabs[9]},
                        // Rush 3
                        {enemyPrefabs[9], enemyPrefabs[9], enemyPrefabs[9], enemyPrefabs[9]}
                },
                // Wave 9
                {
                        // Rush 1
                        {enemyPrefabs[0], enemyPrefabs[1], enemyPrefabs[2], enemyPrefabs[3]},
                        // Rush 2
                        {enemyPrefabs[4], enemyPrefabs[5], enemyPrefabs[6], enemyPrefabs[7]}
                },
                // Wave 10
                {
                        // Rush 1
                        {enemyPrefabs[3], enemyPrefabs[3], enemyPrefabs[3], enemyPrefabs[3]},
                        // Rush 2
                        {enemyPrefabs[6], enemyPrefabs[6], enemyPrefabs[6], enemyPrefabs[6]},
                        // Rush 3
                        {enemyPrefabs[7], enemyPrefabs[7], enemyPrefabs[7], enemyPrefabs[7]}
                },
                // Wave 11
                {
                        // Rush 1
                        {enemyPrefabs[2], enemyPrefabs[2], enemyPrefabs[5], enemyPrefabs[5]},
                        // Rush 2
                        {enemyPrefabs[1], enemyPrefabs[1], enemyPrefabs[4], enemyPrefabs[4]},
                        // Rush 3
                        {enemyPrefabs[0], enemyPrefabs[0], enemyPrefabs[3], enemyPrefabs[3]}
                },
                // Wave 12
                {
                        // Rush 1
                        {enemyPrefabs[9], enemyPrefabs[9], enemyPrefabs[9], enemyPrefabs[9]},
                        // Rush 2
                        {enemyPrefabs[6], enemyPrefabs[6], enemyPrefabs[6], enemyPrefabs[6]},
                        // Rush 3
                        {enemyPrefabs[7], enemyPrefabs[7], enemyPrefabs[7], enemyPrefabs[7]},
                        // Rush 4
                        {enemyPrefabs[3], enemyPrefabs[3], enemyPrefabs[3], enemyPrefabs[3]}
                },
                // Wave 13
                {
                        // Rush 1
                        {enemyPrefabs[1], enemyPrefabs[2], enemyPrefabs[4], enemyPrefabs[5]},
                        // Rush 2
                        {enemyPrefabs[6], enemyPrefabs[7], enemyPrefabs[3], enemyPrefabs[0]}
                },
                // Wave 14
                {
                        // Rush 1
                        {enemyPrefabs[9], enemyPrefabs[9], enemyPrefabs[9], enemyPrefabs[9]},
                        // Rush 2
                        {enemyPrefabs[6], enemyPrefabs[6], enemyPrefabs[6], enemyPrefabs[6]},
                        // Rush 3
                        {enemyPrefabs[3], enemyPrefabs[3], enemyPrefabs[3], enemyPrefabs[3]},
                        // Rush 4
                        {enemyPrefabs[7], enemyPrefabs[7], enemyPrefabs[7], enemyPrefabs[7]}
                },
                // Wave 15 (Final Boss Wave)
                {
                        // Rush 1
                        {enemyPrefabs[8]} // The "wall" boss enemy
                }
        };

        game = new Wave[]{
                new Wave(waves[0], new int[]{500, 500}, new int[]{0, 1500}),
                new Wave(waves[1], new int[]{750, 250}, new int[]{0, 2000}),
                new Wave(waves[2], new int[]{500, 750}, new int[]{0, 2500}),
                new Wave(waves[3], new int[]{1000, 500}, new int[]{0, 3000}),
                new Wave(waves[4], new int[]{750, 500, 750}, new int[]{0, 1000, 3000}),
                new Wave(waves[5], new int[]{1000, 750}, new int[]{0, 2000}),
                new Wave(waves[6], new int[]{750, 500}, new int[]{0, 2500}),
                new Wave(waves[7], new int[]{500, 500, 500}, new int[]{0, 1000, 3000}),
                new Wave(waves[8], new int[]{1000, 750, 500}, new int[]{0, 2000, 3500}),
                new Wave(waves[9], new int[]{1250, 1000}, new int[]{0, 3000}),
                new Wave(waves[10], new int[]{750, 1000, 500}, new int[]{0, 2000, 4000}),
                new Wave(waves[11], new int[]{1000, 750, 1250, 1000}, new int[]{0, 1500, 3000, 5000}),
                new Wave(waves[12], new int[]{500, 1000, 750, 1250}, new int[]{0, 1500, 3000, 4500}),
                new Wave(waves[13], new int[]{1500, 1250}, new int[]{0, 3500}),
                new Wave(waves[14], new int[]{750, 1000, 1250, 500}, new int[]{0, 1500, 3000, 4250}),
        };



        nextButton = new Sprite(new Vec2(width-400, height - 200), "images/button.png", new Vec2(400, 200), 0, false);

        shopButton = new Sprite(new Vec2(0, height - 200), "images/shopButton.png", new Vec2(400, 200), 0, false);
        objs.add(shopButton);


        player.setPos(new Vec2(width / 2.0, height / 2.0));
        player.setPos(player.myOtherMagic());
        objs.add(player);
        setFocusable(true);
        addKeyListener(this);
        addMouseMotionListener(this);
        addMouseListener(this); //add moose mistener
        game[0].start(objs, this);





        // wave bullcrap

        ScheduledExecutorService secretaryGeorge = Executors.newScheduledThreadPool(1);

        secretaryGeorge.scheduleAtFixedRate(() -> {
            if (waveOver)
            {
                if (firstOver)
                {
                    objs.add(nextButton);
                    firstOver = false;
                }
            }
        }, 0, 50, TimeUnit.MILLISECONDS);



        //shop bullcrap omg im so smart


        shopFrame = new JFrame("SHOP!");

        JPanel shopPanel = new JPanel();

        shopPanel.setLayout(new GridLayout(4, 3));

        // setup

        shopPanel.add(new JLabel("Thanks to ryan stark for the awesome shopkeep image"));

        shopPanel.add(new JLabel(new ImageIcon(new ImageIcon("images/shopkeep.png").getImage().getScaledInstance(300, 300, Image.SCALE_DEFAULT))));

        moneyLabel = new JLabel("Money: " + money);
        shopPanel.add(moneyLabel);
        final int[][] PRICES = {{25, 50, 150}, {16, 48, 128}, {250}};

        int[] owned = {0, 0, 0};

        boolean[] soldOut = {false, false, false};

        JLabel sharpLabel = new JLabel("Extra sharp projectiles\nCost: 25");
        shopPanel.add(sharpLabel);

        JLabel largeLabel = new JLabel("Extra large projectiles\nCost: 16");
        shopPanel.add(largeLabel);

        JLabel fastLabel = new JLabel("SUPA FAST FIRING\nCost: 100");
        shopPanel.add(fastLabel);

        JButton sharp = new JButton("Buy!");
        sharp.addActionListener(e -> {
            try {
                if (money > PRICES[0][owned[0]] && !soldOut[0])
                {
                    money -= PRICES[0][owned[0]];
                    owned[0]++;

                    sharpLabel.setText("Extra sharp projectiles\nCost: " + PRICES[0][owned[0]]);
                    moneyLabel.setText("Money: " + money);

                    sharpness += 2;
                }
            } catch (Exception eaSports)
                {
                    sharpLabel.setText("Extra sharp projectiles\nSOLD OUT!");
                    soldOut[0] = true;
                }
        });

        shopPanel.add(sharp);

        JButton large = new JButton("Buy!");
        large.addActionListener(e -> {
            try {
                if (money > PRICES[1][owned[1]] && !soldOut[1]) {
                    money -= PRICES[1][owned[1]];
                    owned[1]++;

                    largeLabel.setText("Extra large projectiles\nCost: " + PRICES[1][owned[1]]);
                    moneyLabel.setText("Money: " + money);

                    chonk += 10;
                }
            } catch (Exception eaSports)
                {
                    largeLabel.setText("SUPA FAST FIRING\nSOLD OUT!");
                    soldOut[1] = true;
                    auto = true;
                }
        });

        shopPanel.add(large);

        JButton fast = new JButton("Buy!");
        fast.addActionListener(e -> {
            if (money > PRICES[2][owned[2]] && !soldOut[2])
            {
                money -= PRICES[2][owned[2]];
                moneyLabel.setText("Money: " + money);
                fastLabel.setText("Extra large projectiles\nSOLD OUT!");
                soldOut[2] = true;
            }
        });

        shopPanel.add(fast);

        shopFrame.add(shopPanel);

        shopFrame.setSize(new Dimension(width/2, height/2));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        ArrayList<GameObject> objectsToPaint = new ArrayList<>(objs);
        if (lastEnSpawned)
        {
            boolean test = true;
            for (GameObject thing : objectsToPaint)
            {
                if (thing.hasTag("enemy") && !thing.hasTag("snail"))
                    test = false;
            }
            if (test) {
                firstOver = true;
                waveOver = true;
                lastEnSpawned = false;
            }
        }

        for (GameObject thing : objectsToPaint) painting: {
                for (GameObject thing2 : objs) {
                    if (thing.hasTag("projectile") && thing.isOffscreen(width, height)) {
                        objs.remove(thing);
                        break painting;
                    }
                    else if (thing.hasTag("enemy") && thing2.hasTag("player") || thing2.hasTag("enemy") && thing.hasTag("player"))
                    {
                        System.out.println("ALERT ALERT ALERT");
                        if (thing.isColliding(thing2))
                        {
                            System.out.println("ALERT ALERT ALERT");
                            gamey.dispose();
                        }
                    } else if ((thing.hasTag("enemy") && thing2.hasTag("projectile")) || (thing.hasTag("projectile") && thing2.hasTag("enemy"))) {
                        if (thing.isColliding(thing2)) {
                            if (thing instanceof Enemy)
                            {
                                ((Enemy)thing).setHealth(((Enemy)thing).getHealth()-1);
                                ((Projectile)thing2).setHealth(((Projectile)thing2).getHealth()-1);
                                if (((Enemy)thing).getHealth() == 0) {

                                    ((Enemy)thing).death(this, moneyLabel, money);
                                    objs.remove(thing);
                                }
                                if (((Projectile)thing2).getHealth() == 0)
                                    objs.remove(thing2);
                            } else {
                                ((Enemy)thing2).setHealth(((Enemy)thing2).getHealth()-1);
                                ((Projectile)thing).setHealth(((Projectile)thing).getHealth()-1);
                                if (((Enemy)thing2).getHealth() == 0) {
                                    ((Enemy)thing2).death(this, moneyLabel, money);
                                    objs.remove(thing2);
                                }
                                if (((Projectile)thing).getHealth() == 0)
                                    objs.remove(thing);
                            }
                            break painting;
                            //skip a frame to avoid errors, maybe bad coding practice i dont care LMAOOO
                        }
                    }
                }
            int yPos = (int)(thing.getPos().getY());
            double yVel = thing.getVel().getY();
            int xPos = (int)(thing.getPos().getX());
            double xVel = thing.getVel().getX();
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
        if (auto)
        {
            mouse = new Vec2(e.getX(), e.getY());
            Vec2 mousePos = new Vec2(e.getX(), e.getY());
            Vec2 playerToMouse = mousePos.subtract(player.middlePos()).unit();
            Projectile newProj = new Projectile(player.middlePos(), new Vec2(chonk, chonk), new Color(100, 100, 100), sharpness);
            newProj.addVel(playerToMouse.multiply(10.0));
            objs.add(newProj);
        }
    }


    @Override
    public void mouseReleased(MouseEvent e) 
    {
        if (waveOver)
        {
            if (nextButton.isHovered(new Vec2(e.getX(), e.getY())))
            {
                waveNum++;
                if (waveNum <= LAST_WAVE)
                {
                    objs.remove(nextButton);
                    System.out.println(game.length);
                    objs.removeIf(obj -> obj.hasTag("snail"));
                    game[waveNum].start(objs, this);
                    waveOver = false;
                } else
                    gamey.dispose();
            }
        }
        if (shopButton.isHovered(new Vec2(e.getX(), e.getY())))
        {
            shopFrame.setVisible(true);
        }
        if (!auto)
        {
            mouse = new Vec2(e.getX(), e.getY());
            Vec2 mousePos = new Vec2(e.getX(), e.getY());
            Vec2 playerToMouse = mousePos.subtract(player.middlePos()).unit();
            Projectile newProj = new Projectile(player.middlePos(), new Vec2(chonk, chonk), new Color(100, 100, 100), sharpness);
            newProj.addVel(playerToMouse.multiply(10.0));
            objs.add(newProj);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}
