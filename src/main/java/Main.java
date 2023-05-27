import galerkin.*;
import galerkin.meshes.Mesh2D;
import linearsolv.GaussElimination;
import maths.Matrix;
import odesolver.RK4;
import util.Plot;
import util.Plot2DRenderer;
import util.Timer;

public class Main {

    public static void main(String[] args)
    {
        WeakForm lhs = (test, basis, space) -> (-(test.evalDerivativeX(space) * basis.evalDerivativeX(space) + basis.evalDerivativeY(space) * test.evalDerivativeY(space)));
        WeakForm rhs = (test, basis, space) -> (test.eval(space) * basis.eval(space));

        PDE pde = new PDE(lhs, rhs);

        int N = 40;
        Mesh2D m2d = new Mesh2D(pde, N, 0, 1);

        Matrix y = Matrix.lin2D(N, (xa,ya) -> ((xa-0.5f)*(xa-0.5f)+(ya-0.5f)*(ya-0.5f)) <= Math.pow(0.29894228040143267793994605993438f, 2) ? 1 : 0);//

        FEMSolver solver = new FEMSolver();
        solver.setLinearSystemSolver(new GaussElimination());
        solver.setOdeSolver(new RK4());
        solver.setMesh(m2d);
        solver.setup(y);

        Plot p = new Plot();
        p.setData(y);
        p.updateBounds();
        Plot2DRenderer renderer = new Plot2DRenderer(p, m2d, "Heat");
        float dt = 0.000015f;
        while(true) {
            Matrix res = solver.timestep(dt);
            p.setData(res);
            renderer.repaint();
        }
    }

}
