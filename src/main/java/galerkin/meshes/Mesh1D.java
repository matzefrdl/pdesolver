package galerkin.meshes;

import galerkin.PDE;
import galerkin.basefunctions.BaseFunction;
import galerkin.elements.Line;

public class Mesh1D extends Mesh {

    public Mesh1D(PDE pde, float a, float b, int numPoints) {
        super(pde);
        this.elements = new Line[numPoints-1];
        float h = (b-a)/(float) numPoints;
        this.numKnots = numPoints;
        for(int i = 0; i < numPoints-1; i++) {
            this.elements[i] = new Line( i*h, (i+1)*h, i, i+1);
        }
    }

}
