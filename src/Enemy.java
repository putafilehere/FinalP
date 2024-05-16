import javax.swing.*;

public class Enemy extends Sprite implements Cloneable {

    private int health;

    private int width;

    private int height;

  //no way this has to be illegal

    private double speed;

    private int moneyOnDeath;

    Runnable onDeath;
    public Enemy(String sprite, Vec2 size, int health, int width, int height, double speed, int moneyOnDeath, Runnable onDeath)
    {
        super(new Vec2(0, 0), sprite, size, 0, false);
        this.width = width;
        this.height = height;
        this.speed = speed;
        int edge = randInt(0, 3); // 0: top, 1: right, 2: bottom, 3: left
        this.health = health;
        this.moneyOnDeath = moneyOnDeath;
        this.onDeath = onDeath;
        int x = 0, y = 0;

        Vec2 directionToCenter = new Vec2(0, 0);

        // Set initial position based on the chosen edge
        switch (edge) {
            case 0: // Top edge
                x = width/2;
                y = 0;

                directionToCenter = new Vec2(0, 1);
                break;
            case 1: // Right edge
                x = width;
                y = height/2;
                directionToCenter = new Vec2(-1, 0);
                break;
            case 2: // Bottom edge
                x = width/2;
                y = height;
                directionToCenter = new Vec2(0, -1);
                break;
            case 3: // Left edge
                x = 0;
                y = height/2;
                directionToCenter = new Vec2(1, 0);
                break;
        }

        Vec2 posVec = new Vec2(x, y);
        System.out.println(directionToCenter);
        this.setPos(posVec);
        this.addTag("enemy");
        this.addVel(directionToCenter.multiply(speed));
        if (sprite.equals("images/snail.png"))
        {
            this.addTag("snail");
        }
    }

    public int randInt(int min, int max)
    {
        return (int) (Math.random() * (max - min + 1)) + min;
    }

    public int getHealth()
    {
        return health;
    }

    public void setHealth(int health)
    {
        this.health = health;
    }

    public void subHealth(int health)
    {
        this.health -= health;
    }

    public void death(Game game, JLabel moneyLabel, int money)
    {
        game.money += moneyOnDeath;
        if (onDeath != null)
            onDeath.run();
        moneyLabel.setText("Money: " + money);
    }

    @Override
    public Enemy clone() {
        Enemy clone = new Enemy(getSprite() + "", getSize().clone(), health, width, height, speed, moneyOnDeath, onDeath);
            return clone;
    }
}
