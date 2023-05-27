package galerkin.elements;

import galerkin.WeakForm;
import galerkin.basefunctions.BaseFunction;
import integrator.TrapezoidalIntegrator;
import maths.Matrix;

public abstract class Element {

    protected int[] knots;

    abstract float integrate(BaseFunction test, BaseFunction basis, WeakForm wf);
    public abstract Matrix getStiffness(WeakForm wf);
    public int[] getKnots() {
        return knots;
    }
}
