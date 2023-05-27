package maths;

public class MatrixThreadAdder implements Runnable {

    private Matrix A, b;

    private int startK, endK;

    private float[] target;

    public MatrixThreadAdder(Matrix a, Matrix b, int startK, int endK) {
        this.A = a;
        this.b = b;
        this.startK = startK;
        this.endK = endK;
        this.target = new float[A.getRowCount() * b.getColumnCount()];
    }

    @Override
    public void run() {

        for(int i = 0; i < A.getRowCount(); i++) {
            for (int j = 0; j < b.getColumnCount(); j++) {
                float value = 0.0f;
                for(int k = startK; k < endK; k++) {
                    value += A.get(i, k) * b.get(k, j);
                }
                target[i * b.getColumnCount() + j] = value;
            }
        }
    }

    public float[] getTarget() {
        return target;
    }
}
