package linearsolv;

import maths.Matrix;

public class GaussElimination implements LinearSolver {

    private Matrix currentSystem;

    private Matrix getRowEcholonForm(Matrix A) {
        int h = 0;
        int k = 0;

        while(h < A.getRowCount() && k < A.getColumnCount()) {

            int i_max = h;
            float max = A.get(h, k);
            for(int i = h+1; i < A.getRowCount(); i++) {
                float val = A.get(i, k);
                if(val >= max) {
                    i_max = i;
                    max = val;
                }
            }

            if (!(Math.abs(max) <= 0.0000000000001f)) {
                Matrix r = A.getRow(h);

                for(int i = 0; i < A.getColumnCount(); i++) {
                    A.set(h, i, A.get(i_max, i));
                    A.set(i_max, i, r.get(0, i));
                }
                for(int i = h+1; i < A.getRowCount(); i++) {
                    float f = A.get(i, k) / A.get(h, k);
                    A.set(i, k, 0);
                    for(int j = k+1; j < A.getColumnCount(); j++) {
                        A.set(i, j, A.get(i,j) - A.get(h,j) *f);
                    }
                }

                h++;
            }
            k++;
        }
        return A;
    }

    @Override
    public void setMatrix(Matrix A) {
        this.currentSystem = getRowEcholonForm(A);
    }

    public Matrix solve(Matrix b) {
        Matrix x = new Matrix(b.getRowCount(), 1);

        for(int i = b.getRowCount()-1; i >= 0; i--) {
            float bn = b.get(i, 0);
            float sum = 0;

            for(int j = i+1; j < this.currentSystem.getRowCount(); j++) {
                sum += x.get(j, 0) * this.currentSystem.get(i, j);
            }
            x.set(i, 0, (bn - sum) / this.currentSystem.get(i, i));

        }
        return x;
    }

}
