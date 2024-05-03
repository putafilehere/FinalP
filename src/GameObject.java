import java.util.*;
import java.awt.*;
public abstract class GameObject implements Cloneable {


    private Vec2 pos;

    private Vec2 vel;

    private double angle;

    private Vec2 size;

    private boolean hasBody;

    private boolean friction;

    private int index;

    private ArrayList<String> tags = new ArrayList<>();

    public GameObject(Vec2 pos, double angle, Vec2 size, boolean hasBody)
    {
        this.pos = pos;
        this.angle = angle;
        this.size = size;
        this.hasBody = hasBody;
        this.vel = new Vec2(0, 0);
    }

    public boolean hasTag(String target)
    {
        for (String tag : tags)
        {
            if (tag.equals(target))
                return true;
        }
        return false;
    }

    public void addVel(Vec2 newVel)
    {
        vel.setX(vel.getX()+newVel.getX());
        vel.setY(vel.getY()+newVel.getY());
    }

    public void addTag(String tag)
    {
        tags.add(tag);
    }

    public void setIndex(int index){this.index = index;}

    public int getIndex(){return index;}

    public void setVel(Vec2 newVel)
    {
        vel = newVel;
    }

    public void setPos(Vec2 pos) { this.pos = pos;}
    public Vec2 getPos()
    {
        return pos;
    }

    public Vec2 middlePos() {
        // Calculate the middle position using the object's position and size
        int middleX = (int) (pos.getX() + size.getX() / 2);
        int middleY = (int) (pos.getY() + size.getY() / 2);

        return new Vec2(middleX, middleY);
    }

    public Vec2 myOtherMagic() {
        int middleX = (int) (pos.getX() - size.getX() / 2);
        int middleY = (int) (pos.getY() - size.getY() / 2);

        return new Vec2(middleX, middleY);
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

    public boolean hasFriction() { return friction; }

    public boolean isStatic() { return hasBody; }

    public void setStatic(boolean hasBody) {this.hasBody = hasBody;}
    // Helper method to get corner points of the rectangle
    public void setFriction(boolean friction) { this.friction = friction; }

    public boolean isColliding(GameObject other) {
        int x1 = (int)(this.pos.getX());
        int y1 = (int)(this.pos.getY());

        int width1 = (int)(this.size.getX());
        int height1 = (int)(this.size.getY());

        int x2 = (int)(other.pos.getX());
        int y2 = (int)(other.pos.getY());

        int width2 = (int)(other.size.getX());
        int height2 = (int)(other.size.getY());

        return x1 < x2 + width2 && x1 + width1 > x2 && y1 < y2 + height2 && y1 + height1 > y2;
    }

    public boolean isOffscreen(int width, int height)
    {
        return pos.getX() > width || pos.getX() < 0 - size.getX() || pos.getY() > height || pos.getY() < 0 - size.getY();
    }

    public boolean isHovered(Vec2 mouse)
    {
        return (mouse.getX() >= pos.getX() && mouse.getX() <= pos.getX() + size.getX() &&
                mouse.getY() >= pos.getY() && mouse.getY() <= pos.getY() + size.getY());
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
