package linearsolv;

import maths.Matrix;

public interface LinearSolver {

    public void setMatrix(Matrix A);
    public Matrix solve(Matrix b);
}
