package maths;

public class Vec2 {
    public float x, y;

    public Vec2(float x, float y) {
        this.x = x;
        this.y = y;
    }
    public Vec2(Vec2 other) {
        this.x = other.x;
        this.y = other.y;
    }
    public Vec2() {}

    public Vec2 add(Vec2 other) {
        return new Vec2(x + other.x, y + other.y);
    }
    public Vec2 sub(Vec2 other) {
        return new Vec2(x - other.x, y - other.y);
    }

    public Vec2 mul(float scalar) {
        return new Vec2(scalar * x, scalar * y);
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + "]";
    }
}
