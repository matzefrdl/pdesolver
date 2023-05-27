package galerkin.elements;

import galerkin.PDE;
import galerkin.WeakForm;
import galerkin.basefunctions.BaseFunction;
import galerkin.basefunctions.SBFLineLeft;
import galerkin.basefunctions.SBFLineRight;
import integrator.TrapezoidalIntegrator;
import maths.Matrix;

public class Line extends Element {
    private float a;
    private float b;

    public Line(float a, float b, int left, int right) {
        this.a = a;
        this.b = b;

        this.knots = new int[2];
        this.knots[0] = left;
        this.knots[1] = right;
    }

    @Override
    public float integrate(BaseFunction test, BaseFunction basis, WeakForm wf) {
        return TrapezoidalIntegrator.integrate(a, b, wf, test, basis);
    }

    @Override
    public Matrix getStiffness(WeakForm wf) {
        SBFLineRight right = new SBFLineRight(a, b-a);
        SBFLineLeft left = new SBFLineLeft(b, b-a);

        Matrix m = new Matrix(2, 2);
        m.set(0, 0, integrate(right, right, wf));
        m.set(1, 1, integrate(left, left, wf));
        m.set(0, 1, integrate(right, left, wf));
        m.set(1, 0, integrate(left, right, wf));
        return m;
    }
}
