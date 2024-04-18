public class Enemy extends Sprite {

    int health;
    public Enemy(String sprite, Vec2 size, boolean isStatic, int health, int width, int height)
    {
        super(new Vec2(0, 0), sprite, size, 0, isStatic);
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
        this.setPos(posVec);
        Vec2 center = new Vec2(width / 2.0, height / 2.0);
        this.addTag("enemy");
        Vec2 directionToCenter = center.subtract(this.middlePos()).unit();
        this.addVel(directionToCenter.multiply(5));
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
}
