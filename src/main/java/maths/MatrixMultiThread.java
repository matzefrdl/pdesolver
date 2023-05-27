package maths;

import util.Timer;

public class MatrixMultiThread {

    private Matrix A;
    private Matrix b;

    public void setA(Matrix a) {
        A = a;
    }

    public void setB(Matrix b) {
        this.b = b;
    }

    public Matrix multiply() {
        Matrix newMatrix = new Matrix(A.getRowCount(), b.getColumnCount());
        int cores = Runtime.getRuntime().availableProcessors();

        int todos = b.getRowCount() / cores;
        int remaining = b.getRowCount() - todos * cores;
        MatrixThreadAdder[] mtas = new MatrixThreadAdder[cores];
        for(int i = 0; i < mtas.length-1; i++) {
            mtas[i] = new MatrixThreadAdder(A, b, i * todos, (i+1)*todos);
        }
        mtas[mtas.length-1] = new MatrixThreadAdder(A,b, (cores-1) * todos, cores * todos + remaining);
        Thread[] threads = new Thread[mtas.length];

        for(int i = 0; i < mtas.length; i++) {
            threads[i] = new Thread(mtas[i]);
            threads[i].start();
        }
        for(int i = 0; i < mtas.length; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        for(int i = 0; i < mtas.length; i++) {
            float[] r = mtas[i].getTarget();
            for(int x = 0; x < r.length; x++) newMatrix.setArray(x, newMatrix.getArray(x)+r[x]);
        }


        return newMatrix;
    }
}
