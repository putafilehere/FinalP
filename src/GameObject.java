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

    private Vec2[] getCorners() {
        int halfWidth = size.getX() / 2;
        int halfHeight = size.getY() / 2;
        Vec2[] corners = {
            new Vec2(-halfWidth, -halfHeight),
            new Vec2(-halfWidth, halfHeight),
            new Vec2(halfWidth, halfHeight),
            new Vec2(halfWidth, -halfHeight)
        };
        for (int i = 0; i < corners.length; i++) {
            corners[i] = corners[i].add(pos);
        }
        return corners;
    }

    public boolean isColliding(GameObject other) {
        // create corner points for both rectangles
        Vec2[] corners1 = getCorners();
        Vec2[] corners2 = other.getCorners();
        // check for intersection of edges of both rectangles
        for (int i = 0; i < 4; i++) {
            Vec2 currentEdge = corners1[(i + 1) % 4].subtract(corners1[i]);
            // find the axis perpendicular to the edge
            Vec2 axis = currentEdge.normal();
            // rotate the axis by the angle of the first rectangle
            axis = axis.rotate(angle);
            // project both rectangles onto the axis
            double min1 = Double.MAX_VALUE, max1 = Double.MIN_VALUE;
            double min2 = Double.MAX_VALUE, max2 = Double.MIN_VALUE;
            for (Vec2 corner : corners1) {
                double projection = corner.rotate(angle).project(axis).dot(axis);
                min1 = Math.min(min1, projection);
                max1 = Math.max(max1, projection);
            }
            for (Vec2 corner : corners2) {
                double projection = corner.rotate(other.angle).project(axis).dot(axis);
                min2 = Math.min(min2, projection);
                max2 = Math.max(max2, projection);
            }
            // check for overlap
            if (max1 < min2 || max2 < min1) {
                return false; // no overlap found, rectangles are not colliding
            }
        }
        return true; // overlap found on all axes, rectangles are colliding
    }
}
