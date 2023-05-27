package odesolver;

import linearsolv.LinearSolver;
import maths.Matrix;

/**
 * solves equation of the form M * d/dt y = K*y
 */
public interface OdeSolver {

    void setLinearSystemSolver(LinearSolver solver);
    void setMassMatrix(Matrix mass);
    public Matrix solve(Matrix stiffness, Matrix y, float dt);

}
