import java.util.*;
import java.awt.*;
public abstract class GameObject {


    private Vec2 pos;

    private Vec2 vel;

    private boolean grav;

    public GameObject(Vec2 pos, boolean grav)
    {
        this.pos = pos;
        this.grav = grav;
    }

    public void addVel(Vec2 newVel)
    {
        vel.setX(vel.getX()+newVel.getX());
        vel.setY(vel.getY()+newVel.getY());
    }

    public Vec2 getPos()
    {
        return pos;
    }

    public abstract void draw(Graphics g);


}
