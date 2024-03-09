import java.util.*;
import java.awt.*;
public abstract class GameObject {


    private Vec2 pos;

    private Vec2 vel;

    private double angle;

    private Vec2 size;

    private boolean isStatic;

    public GameObject(Vec2 pos, double angle, Vec2 size, boolean isStatic)
    {
        this.pos = pos;
        this.angle = angle;
        this.size = size;
        this.isStatic = isStatic;
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

    public boolean isStatic() { return isStatic; }

    public void setStatic(boolean isStatic) {this.isStatic = isStatic;}
    // Helper method to get corner points of the rectangle

    public boolean isColliding(GameObject other) {
        int x1 = this.pos.getX();
        int y1 = this.pos.getY();
        int width1 = this.size.getX();
        int height1 = this.size.getY();
        int x2 = other.pos.getX();
        int y2 = other.pos.getY();
        int width2 = other.size.getX();
        int height2 = other.size.getY();
        return x1 < x2 + width2 && x1 + width1 > x2 && y1 < y2 + height2 && y1 + height1 > y2;
    }

    public boolean isLeft(GameObject other, int tolerance) {
        return other.getPos().getX() + other.getSize().getX() + tolerance < this.getPos().getX(); // Check if pos2 is directly to the left of pos1
    }

    public boolean isRight(GameObject other, int tolerance) {
        return this.getPos().getX() + this.getSize().getX() + tolerance < other.getPos().getX(); // Check if pos2 is directly to the right of pos1
    }

    public boolean isUp(GameObject other, int tolerance) {
        return other.getPos().getY() + other.getSize().getY() + tolerance < this.getPos().getY(); // Check if pos2 is directly above pos1
    }

    public boolean isDown(GameObject other, int tolerance) {
        return this.getPos().getY() + this.getSize().getY() + tolerance < other.getPos().getY(); // Check if pos2 is directly below pos1
    }

}
