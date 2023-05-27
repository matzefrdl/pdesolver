package galerkin.meshes;

import galerkin.PDE;
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

    public Matrix getGlobalLHSMatrix() {
        Matrix ret = new Matrix(numKnots, numKnots);

        for(int i = 0; i < elements.length; i++) {

            Element element = elements[i];
            Matrix K = element.getStiffness(pde.getLhs());
            int[] knots = element.getKnots();
            for(int j = 0; j < knots.length; j++) {
                int k = knots[j];
                Matrix r = K.getRow(j);
                for(int x = 0; x < r.getColumnCount(); x++) {
                    float toAdd = r.get(0, x);
                    ret.set(k, knots[x], ret.get(k, knots[x]) + toAdd);
                }
            }
        }
        return ret;
    }

    public int getNumKnots() {
        return numKnots;
    }

    public Matrix getGlobalRHSMatrix() {
        Matrix ret = new Matrix(numKnots, numKnots);

        for(int i = 0; i < elements.length; i++) {
            Element element = elements[i];
            Matrix K = element.getStiffness(pde.getRhs());
            int[] knots = element.getKnots();
            for(int j = 0; j < knots.length; j++) {
                int k = knots[j];
                Matrix r = K.getRow(j);
                for(int x = 0; x < r.getColumnCount(); x++) {
                    float toAdd = r.get(0, x);
                    ret.set(k, knots[x], ret.get(k, knots[x]) + toAdd);
                }
            }
        }
        return ret;
    }

    public Element[] getElements() {
        return elements;
    }
}
