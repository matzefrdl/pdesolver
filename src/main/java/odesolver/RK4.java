package odesolver;

import linearsolv.GaussElimination;
import linearsolv.LinearSolver;
import maths.Matrix;
import util.Timer;

public class RK4 implements OdeSolver {
    private LinearSolver solver;

    @Override
    public void setLinearSystemSolver(LinearSolver solver) {
        this.solver = solver;
    }

    public void setMassMatrix(Matrix mass) {
        solver.setMatrix(mass);
    }
    @Override
    public Matrix solve(Matrix stiffness, Matrix starting, float dt) {
        return next(stiffness, starting, dt);
    }
    private Matrix step(Matrix stiffness, Matrix y) {
        Matrix b = stiffness.mulThreaded(y);
        return solver.solve(b);
    }

    private Matrix next(Matrix K, Matrix y, float h) {
        Matrix k1 = step(K, y);
        Matrix k2 = step(K, y.add(k1.mul(h/2f)));
        Matrix k3 = step(K, y.add(k2.mul(h/2f)));
        Matrix k4 = step(K, y.add(k3.mul(h)));
        k1 = k1.add(k2.mul(2.0f));
        k1 = k1.add(k3.mul(2.0f));
        k1 = k1.add(k4);
        return y.add(k1.mul(h/6.0f));

    }

}
