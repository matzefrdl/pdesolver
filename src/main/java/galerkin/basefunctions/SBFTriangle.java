package galerkin.basefunctions;

import maths.Vec2;
import maths.Vec3;

public class SBFTriangle implements BaseFunction {

    private Vec2 a;
    private Vec2 b;
    private Vec2 c;

    private float det;

    public SBFTriangle(Vec2 v0,Vec2 v1,Vec2 v2) {
        this.a = v0;
        this.b = v1;
        this.c = v2;
        this.det = (b.y - c.y) * (a.x-c.x) + (c.x - b.x) * (a.y - c.y);
    }

    @Override
    public float eval(Vec3 space) {
        float over = (b.y - c.y) * (space.x - c.x) +(c.x - b.x) * (space.y - c.y);
        return over / det;
    }

    @Override
    public float evalDerivativeX(Vec3 space) {
        return (b.y - c.y) / det;
    }

    @Override
    public float evalDerivativeY(Vec3 space) {
        return (c.x - b.x) / det;
    }

    @Override
    public float evalDerivativeZ(Vec3 space) {
        return 0;
    }
}
