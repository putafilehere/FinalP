import java.awt.*;

public class Projectile extends Rect implements Cloneable {

    int health;

    public Projectile(Vec2 pos, Vec2 size, Color color, int health)
    {
        super(pos, size, color, 0, true);

        this.addTag("projectile");
        this.health = health;
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


    @Override
    public Projectile clone() {
            Projectile clone = new Projectile(getPos().clone(), getSize().clone(), getColor(), health);
            return clone;
    }
}
