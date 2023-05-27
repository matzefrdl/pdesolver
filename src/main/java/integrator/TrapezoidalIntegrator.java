package integrator;

import galerkin.basefunctions.BaseFunction;
import galerkin.WeakForm;
import maths.Vec3;

public class TrapezoidalIntegrator {
    public static float integrate(float a, float b, WeakForm wf, BaseFunction test, BaseFunction basis) {
        int N = 20;
        float dx = (b-a) / N;
        float eps = 0.0000001f;
        float total = wf.eval(test, basis, new Vec3(a+eps, 0, 0)) + wf.eval(test, basis, new Vec3(b-eps, 0, 0));
        total /= 2.0f;
        for(int i = 1; i < N; i++) {
            total += wf.eval(test, basis, new Vec3(a + dx * i, 0, 0));
        }
        return total * dx;
    }

}
