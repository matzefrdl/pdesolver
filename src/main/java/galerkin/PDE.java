package galerkin;

public class PDE {

    private WeakForm lhs;
    private WeakForm rhs;

    public PDE(WeakForm lhs, WeakForm rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    public WeakForm getLhs() {
        return lhs;
    }

    public WeakForm getRhs() {
        return rhs;
    }
}
