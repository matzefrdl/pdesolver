package galerkin.elements;

import galerkin.WeakForm;
import galerkin.basefunctions.BaseFunction;
import galerkin.basefunctions.SBFTriangle;
import integrator.GaussianQuadrature;
import maths.Matrix;
import maths.Vec2;

public class Triangle extends Element {

    private Vec2 v0;
    private Vec2 v1;
    private Vec2 v2;

    public Triangle(Vec2 v0, Vec2 v1, Vec2 v2, int k1, int k2, int k3) {
        this.v0 = v0;
        this.v1 = v1;
        this.v2 = v2;
        this.knots = new int[]{k1, k2, k3};
    }

    @Override
    float integrate(BaseFunction test, BaseFunction basis, WeakForm wf) {
        return GaussianQuadrature.integrate(v0, v1, v2, wf, test, basis);
    }

    @Override
    public Matrix getStiffness(WeakForm wf) {
        SBFTriangle t0 = new SBFTriangle(v0,v1,v2);
        SBFTriangle t1 = new SBFTriangle(v1,v2,v0);
        SBFTriangle t2 = new SBFTriangle(v2,v0,v1);
        float m00 = integrate(t0, t0, wf);
        float m01 = integrate(t0, t1, wf);
        float m02 = integrate(t0, t2, wf);

        float m10 = integrate(t1, t0, wf);
        float m11 = integrate(t1, t1, wf);
        float m12 = integrate(t1, t2, wf);

        float m20 = integrate(t2, t0, wf);
        float m21 = integrate(t2, t1, wf);
        float m22 = integrate(t2, t2, wf);
        Matrix m = new Matrix(3, 3);
        m.set(0, 0, m00);
        m.set(0, 1, m01);
        m.set(0, 2, m02);

        m.set(1, 0, m10);
        m.set(1, 1, m11);
        m.set(1, 2, m12);

        m.set(2, 0, m20);
        m.set(2, 1, m21);
        m.set(2, 2, m22);
        return m;
    }

    public Vec2 getV0() {
        return v0;
    }

    public Vec2 getV1() {
        return v1;
    }

    public Vec2 getV2() {
        return v2;
    }
}
