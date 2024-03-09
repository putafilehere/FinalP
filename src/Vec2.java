public class Vec2 {

    private int x;

    private int y;


    public Vec2(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    // Addition of vectors
    public Vec2 add(Vec2 other) {
        return new Vec2(this.x + other.x, this.y + other.y);
    }

    // Subtraction of vectors
    public Vec2 subtract(Vec2 other) {
        return new Vec2(this.x - other.x, this.y - other.y);
    }

    // Dot product of vectors
    public int dot(Vec2 other) {
        return this.x * other.x + this.y * other.y;
    }

    // Normal of the vector
    public Vec2 normal() {
        return new Vec2(-y, x);
    }

    // Projection of the vector onto another vector
    public Vec2 project(Vec2 other) {
        int dotProduct = dot(other);
        int lengthSquared = other.dot(other);
        return other.multiply(dotProduct / (double) lengthSquared);
    }

    // Scalar multiplication of the vector
    public Vec2 multiply(double scalar) {
        return new Vec2((int) (this.x * scalar), (int) (this.y * scalar));
    }

    public Vec2 unit() {
        double magnitude = Math.sqrt(x * x + y * y);
        if (magnitude == 0) {
            return new Vec2(0, 0); // Prevent division by zero
        }
        return new Vec2((int) (x / magnitude), (int) (y / magnitude));
    }



    public Vec2 rotate(double angleInDegrees) {
        double angleInRadians = Math.toRadians(angleInDegrees);
        double cosAngle = Math.cos(angleInRadians);
        double sinAngle = Math.sin(angleInRadians);
        int newX = (int) (x * cosAngle - y * sinAngle);
        int newY = (int) (x * sinAngle + y * cosAngle);
        return new Vec2(newX, newY);
    }

    public Vec2 rotateAround(double angleInDegrees, Vec2 center) {
        Vec2 relativePos = this.subtract(center);
        return relativePos.rotate(angleInDegrees).add(center);
    }

}
