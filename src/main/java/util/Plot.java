package util;

import maths.Matrix;

public class Plot {

    private Matrix matrix;
    private float a;
    private float b;

    public void updateBounds() {
        a = matrix.min();
        b = matrix.max();
    }

    public void setA(float a) {
        this.a = a;
    }

    public void setB(float b) {
        this.b = b;
    }

    public void setData(Matrix m) {
        this.matrix = m;
    }

    public Matrix getMatrix() {
        return matrix;
    }

    public float getA() {
        return a;
    }

    public float getB() {
        return b;
    }
}
