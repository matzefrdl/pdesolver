package galerkin;

import galerkin.basefunctions.BaseFunction;
import maths.Vec3;

public interface WeakForm {
    public float eval(BaseFunction test, BaseFunction basis, Vec3 space);
}
