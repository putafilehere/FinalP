import java.util.*;
import java.awt.*;
public abstract class GameObject {


    private Vec2 pos;

    private Vec2 vel;

    private double angle;

    private Vec2 size;

    public GameObject(Vec2 pos, double angle, Vec2 size)
    {
        this.pos = pos;
        this.angle = angle;
        this.size = size;
        this.vel = new Vec2(0, 0);
    }

    public void addVel(Vec2 newVel)
    {
        vel.setX(vel.getX()+newVel.getX());
        vel.setY(vel.getY()+newVel.getY());
    }

    public void setVel(Vec2 newVel)
    {
        vel = newVel;
    }

    public void setPos(Vec2 pos) { this.pos = pos;}
    public Vec2 getPos()
    {
        return pos;
    }

    public Vec2 getVel() { return vel; }

    public double getAngle() { return angle; }
  
    public void setAngle(double angle)
    {
      this.angle = angle;
    }

    public abstract void draw(Graphics g);

    public Vec2 getSize()
    {
      return size;
    }

    public void setSize(Vec2 size)
    {
      this.size = size;
    }

    // Helper method to get corner points of the rectangle


}
