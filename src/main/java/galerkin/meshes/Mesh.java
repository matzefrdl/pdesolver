package galerkin.meshes;

import galerkin.PDE;
import galerkin.WeakForm;
import galerkin.basefunctions.BaseFunction;
import galerkin.elements.Element;
import galerkin.elements.Line;
import maths.Matrix;

import java.util.Arrays;

public abstract class Mesh {

    private PDE pde;

    public Mesh(PDE pde) {
        this.pde = pde;
    }
    protected Element[] elements;

    protected int numKnots = 0;

    private Matrix getGlobalMatrix(WeakForm wf) {
        Matrix global = new Matrix(numKnots, numKnots);

        for(int i = 0; i < elements.length; i++) {

            Element element = elements[i];
            Matrix K = element.getStiffness(wf);
            int[] knots = element.getKnots();
            for(int j = 0; j < knots.length; j++) {
                int k = knots[j];
                Matrix r = K.getRow(j);
                for(int x = 0; x < r.getColumnCount(); x++) {
                    float toAdd = r.get(0, x);
                    global.set(k, knots[x], global.get(k, knots[x]) + toAdd);
                }
            }
        }
        return global;
    }

    public int getNumKnots() {
        return numKnots;
    }


    public Matrix getGlobalLHSMatrix() {
        return getGlobalMatrix(pde.getLhs());
    }

    public Matrix getGlobalRHSMatrix() {
        return getGlobalMatrix(pde.getRhs());
    }

    public Element[] getElements() {
        return elements;
    }
}
