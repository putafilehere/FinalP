public class Enemy extends Sprite implements Cloneable {

    private int health;

    private int width;

    private int height;

    private double speed;

    Runnable onDeath;
    public Enemy(String sprite, Vec2 size, int health, int width, int height, double speed, Runnable onDeath)
    {
        super(new Vec2(0, 0), sprite, size, 0, false);
        this.width = width;
        this.height = height;
        this.speed = speed;
        int edge = randInt(0, 4); // 0: top, 1: right, 2: bottom, 3: left
        this.health = health;
        this.onDeath = onDeath;
        int x = 0, y = 0;
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

        // Set position vector
        setPos(new Vec2(x, y));
        setPos(myOtherMagic());

        // Compute direction towards the center
        Vec2 center = new Vec2(width / 2.0, height / 2.0);
        Vec2 directionToCenter = center.subtract(getPos()).unit();

        // Set velocity vector towards the center
        Vec2 velocity = directionToCenter.multiply(speed);
        this.addTag("enemy");
        this.addVel(velocity);
        System.out.println(velocity);
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

    public void death()
    {
        System.out.println("IIIIIII AM DEADDDDD");
        if (onDeath != null)
            onDeath.run();
    }

    @Override
    public Enemy clone() {
        Enemy clone = new Enemy(getSprite() + "", getSize().clone(), health, width, height, speed, onDeath);
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
    }
}
