package net.kormos.examples.threading.philosophers;

import java.util.stream.Stream;

import static java.util.stream.IntStream.range;

public class Table {

    private final int seats;
    private final Chopstick[] chopsticks;
    private final Diner[] diners;

    public Table(int seats, long diningTime) {
        this.seats = seats;
        this.chopsticks = range(0, seats)
                .mapToObj(Chopstick::new)
                .toArray(Chopstick[]::new);

        int lastSeat = seats - 1;
        diners = new Diner[seats];
        for (int seat = 0; seat < lastSeat; seat++) {
            diners[seat] = Diner.builder()
                    .withId(seat)
                    .withDiningTime(diningTime)
                    .withLeft(this.chopsticks[seat])
                    .withRight(this.chopsticks[seat + 1])
                    .build();
        }

        diners[lastSeat] = Diner.builder()
                .withId(lastSeat)
                .withDiningTime(diningTime)
                .withLeft(this.chopsticks[lastSeat])
                .withRight(this.chopsticks[0])
                .build();
    }

    public void serve() throws Exception {
        Thread[] dinerMonitor = new Thread[seats];
        for (Diner diner : diners) {
            dinerMonitor[diner.getId()] = new Thread(diner::eat);
        }
        Stream.of(dinerMonitor).forEach(Thread::start);
    }

    public static void main(String... args) throws Throwable {
        Table table = new Table(5, 5000);
        table.serve();
    }
}
