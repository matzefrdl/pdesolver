package maths;

import java.text.DecimalFormat;
import java.util.Arrays;

public class Matrix {

    private int N, M; /*N = rows, M = columns*/
    private float[] values;

    public Matrix(int N, int M) {
        this.N = N;
        this.M = M;
        this.values = new float[N * M];
        Arrays.fill(values, 0);
    }

    public float get(int n, int m) {
        return this.values[n * M + m];
    }

    public Matrix transpose() {
        Matrix t = new Matrix(M, N);

        for(int i = 0; i < N; i++) {
            for(int j = 0; j < M; j++) {
                t.set(j, i, this.get(i, j));
            }
        }
        return t;
    }

    public Matrix mul(float other) {
        Matrix m = new Matrix(N, M);
        for(int i = 0; i < M *N;i++) m.setArray(i, this.getArray(i) * other);
        return m;
    }

    public void setArray(int index, float value) {
        this.values[index] = value;
    }
    public float getArray(int index) {
        return this.values[index];
    }

    public void set(int n, int m, float value) {
        this.values[n * M + m] = value;
    }

    public Matrix add(Matrix m) {
        Matrix newM = new Matrix(N, M);
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < M; j++) {
                newM.set(i, j, m.get(i,j)+get(i,j));
            }
        }
        return newM;
    }

    public Matrix cpy() {
        Matrix m = new Matrix(N, M);
        for(int i = 0; i < N * M;i++)m.setArray(i, this.getArray(i));
        return m;
    }

    public Matrix getRow(int r) {
        Matrix m = new Matrix(1, M);
        for(int i = 0; i < M; i++) m.set(0, i, this.get(r, i));
        return m;
    }
    public Matrix getColumn(int c) {
        Matrix m = new Matrix(N, 1);
        for(int i = 0; i < N; i++) m.set(i, 0, this.get(i, c));
        return m;
    }
    public Matrix add(int mi, int mj, Matrix m) {
        Matrix newM = this.cpy();
        for(int i = mi; i < mi + m.getRowCount(); i++) {
            for(int j = mj; j < mj + m.getColumnCount() ; j++) {
                newM.set(i, j, m.get(i-mi,j-mj)+get(i,j));
            }
        }
        return newM;
    }

    public float error(Matrix other) {
        Matrix e = this.sub(other);
        float error = 0;

        for(int i = 0; i < this.values.length; i++) {
            float ei = e.getArray(i);
            error += ei * ei;
        }
        return error;
    }
    public Matrix sub(Matrix m) {
        Matrix newM = new Matrix(N, M);
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < M; j++) {
                newM.set(i, j, m.get(i,j)-get(i,j));
            }
        }
        return newM;
    }

    public float min() {
        float min = this.values[0];
        for(int i = 0; i < this.values.length; i++) {
            if(this.values[i] < min) {
                min = this.values[i];
            }
        }
        return min;
    }
    public float max() {
        float max = this.values[0];
        for(int i = 0; i < this.values.length; i++) {
            if(this.values[i] > max) {
                max = this.values[i];
            }
        }
        return max;
    }

    public Matrix mul(Matrix other) {
        Matrix ret = new Matrix(N, other.M);
        for(int i = 0; i < this.N; i++) {
            for(int j = 0; j < other.M; j++) {
                float value = 0.0f;
                for(int k = 0; k < other.N; k++) {
                    value += this.get(i, k) * other.get(k, j);
                }
                ret.set(i, j, value);
            }
        }
        return ret;
    }

    public Matrix mulThreaded(Matrix other) {
        MatrixMultiThread mmt = new MatrixMultiThread();
        mmt.setA(this);
        mmt.setB(other);
        return mmt.multiply();
    }

    public static Matrix lin(int N, Mapper mapper) {
        Matrix y = new Matrix(N, 1);
        for(int i = 0; i < N; i++) {
            y.set(i, 0,  mapper.map((float) i / (float) (N-1)));
        }
        return y;
    }
    public static Matrix lin2D(int N, Mapper2D mapper) {
        Matrix y = new Matrix(N*N, 1);
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                y.set(i * N + j, 0, mapper.map((float) i / (float) (N - 1), (float) j / (float) (N-1)));
            }
        }
        return y;
    }

    public int getRowCount() {return N;}
    public int getColumnCount() {return M;}

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("0.0000");
        StringBuilder buff = new StringBuilder();
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < M-1; j++) {
                buff.append(df.format(get(i, j)));
                buff.append(" ");
            }
            buff.append(df.format(get(i, M-1)));
            buff.append("\n");
        }
        return buff.toString();
    }
}
