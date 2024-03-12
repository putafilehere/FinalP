public class Vec2 {

    private double x;
    private double y;

    public Vec2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
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
    public double dot(Vec2 other) {
        return this.x * other.x + this.y * other.y;
    }

    // Normal of the vector
    public Vec2 normal() {
        return new Vec2(-y, x);
    }

    // Projection of the vector onto another vector
    public Vec2 project(Vec2 other) {
        double dotProduct = dot(other);
        double lengthSquared = other.dot(other);
        return other.multiply(dotProduct / lengthSquared);
    }

    // Scalar multiplication of the vector
    public Vec2 multiply(double scalar) {
        return new Vec2(this.x * scalar, this.y * scalar);
    }

    public Vec2 unit() {
        double magnitude = Math.sqrt(x * x + y * y);
        if (magnitude == 0) {
            return new Vec2(0, 0); // Prevent division by zero
        }
        return new Vec2(x / magnitude, y / magnitude);
    }

    public Vec2 rotate(double angleInDegrees) {
        double angleInRadians = Math.toRadians(angleInDegrees);
        double cosAngle = Math.cos(angleInRadians);
        double sinAngle = Math.sin(angleInRadians);
        double newX = x * cosAngle - y * sinAngle;
        double newY = x * sinAngle + y * cosAngle;
        return new Vec2(newX, newY);
    }

    public Vec2 rotateAround(double angleInDegrees, Vec2 center) {
        Vec2 relativePos = this.subtract(center);
        return relativePos.rotate(angleInDegrees).add(center);
    }

    @Override
    public String toString()
    {
        return "X: " + x + "Y: " + y;
    }
}
