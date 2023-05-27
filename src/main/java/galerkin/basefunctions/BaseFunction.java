package galerkin.basefunctions;

import maths.Vec3;

public interface BaseFunction {

    public float eval(Vec3 space);
    public float evalDerivativeX(Vec3 space);
    public float evalDerivativeY(Vec3 space);
    public float evalDerivativeZ(Vec3 space);
}
