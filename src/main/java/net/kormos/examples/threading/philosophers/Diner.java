package net.kormos.examples.threading.philosophers;

public class Diner {
    private static final String THINKING_LEFT = "pondering on left chopstick";
    private static final String THINKING_RIGHT = "pondering on right chopstick";
    private static final String EATING = "eating";
    private static final String DONE = "full for now";
    private static final String FINISHED = "given the check";

    private final int id;
    private final long diningTime;
    private final Chopstick left;
    private final Chopstick right;

    protected volatile boolean stopped = false;

    private Diner(Builder builder) {
        this.id = builder.id;
        this.diningTime = builder.diningTime;
        this.left = builder.left;
        this.right = builder.right;

        System.out.println(String.format("Diner %02d has left %s and right %s", this.id, this.left, this.right));

    }

    public static Builder builder() {
        return new Builder();
    }

    public void logAction(String action) {
        System.out.println(String.format("[%s] Diner %02d is %s.", System.currentTimeMillis(), this.id, action));
    }

    public void eat() {
        while (!stopped) {
            try {
                logAction(THINKING_LEFT);
                synchronized (this.left) {
                    logAction(THINKING_RIGHT);
                    synchronized (this.right) {
                        logAction(EATING);
                        Thread.sleep(diningTime);
                        logAction(DONE);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int getId() {
        return id;
    }

    public void stop() {
        this.stopped = true;
        logAction(FINISHED);
    }

    public static class Builder {
        private int id;
        private long diningTime;
        private Chopstick left;
        private Chopstick right;

        public Builder withId(int id) {
            this.id = id;
            return this;
        }

        public Builder withDiningTime(long diningTime) {
            this.diningTime = diningTime;
            return this;
        }

        public Builder withLeft(Chopstick left) {
            this.left = left;
            return this;
        }

        public Builder withRight(Chopstick right) {
            this.right = right;
            return this;
        }

        public Diner build() {
            return new Diner(this);
        }
    }
}
