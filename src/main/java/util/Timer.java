package util;

public class Timer {

    private long last;

    public void start() {
        last = System.nanoTime();
    }

    public double stop() {
        long current = System.nanoTime();
        return (current - last) / 1_000_000.0f;
    }

}
