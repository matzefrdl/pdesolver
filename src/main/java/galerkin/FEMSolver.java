package galerkin;

import galerkin.meshes.Mesh;
import linearsolv.LinearSolver;
import maths.Matrix;
import odesolver.OdeSolver;

public class FEMSolver {

    private Mesh mesh;
    private OdeSolver odeSolver;

    private LinearSolver linearSystemSolver;

    public void setLinearSystemSolver(LinearSolver linearSystemSolver) {
        this.linearSystemSolver = linearSystemSolver;
    }

    public void setOdeSolver(OdeSolver odeSolver) {
        this.odeSolver = odeSolver;
    }

    public void setMesh(Mesh mesh) {
        this.mesh = mesh;
    }

    private Matrix stiffness;
    private Matrix mass;

    private Matrix lastStep;

    public void setup(Matrix initialSystem) {
        if(mesh == null) {
            throw new RuntimeException("Mesh must be provided");
        }
        if(odeSolver == null) {
            throw new RuntimeException("ODE solver must be provided");
        }
        if(linearSystemSolver == null) {
            throw new RuntimeException("Linear system solver must be provided");
        }
        stiffness = mesh.getGlobalLHSMatrix();
        mass = mesh.getGlobalRHSMatrix();

        odeSolver.setLinearSystemSolver(linearSystemSolver);
        odeSolver.setMassMatrix(mass);
        lastStep = initialSystem;
    }

    public Matrix timestep(float dt) {
        Matrix current = odeSolver.solve(this.stiffness, lastStep, dt);
        lastStep = current;
        return current;
    }
}
