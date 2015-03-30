package net.kormos.examples.threading.philosophers;

public class Chopstick {
    private final int id;
    private final String toStringString;

    public Chopstick(int id) {
        this.id = id;
        this.toStringString = String.format("Chopstick %02d", id);
    }

    public String toString() {
        return toStringString;
    }
}
