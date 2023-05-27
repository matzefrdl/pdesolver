package galerkin.basefunctions;

import maths.Vec3;

public class SBFLineLeft implements BaseFunction {
    private float base;
    private float h;

    public SBFLineLeft(float base, float h) {
        this.base = base;
        this.h = h;
    }

    @Override
    public float eval(Vec3 space) {
        float x = space.x;
        return (x - base) / h;
    }

    @Override
    public float evalDerivativeX(Vec3 space) {
        return 1.0f / h;
    }

    @Override
    public float evalDerivativeY(Vec3 space) {
        return 0;
    }

    @Override
    public float evalDerivativeZ(Vec3 space) {
        return 0;
    }
}
