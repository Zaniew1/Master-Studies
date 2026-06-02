package lesson2.Task1;
class Counter {
    int value = 0;

    public void increment() {
        ++value; // NIEBEZPIECZNE
    }
}

class Worker implements Runnable {
    private final Counter counter;
    static final int ITER = 1_000_000;

    public Worker(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        for (int i = 0; i < ITER; i++) {
            counter.increment();
        }
    }
}

public class Wrong {
    public static void main(String[] args) throws InterruptedException {
        final int THREADS = 8;
        Counter counter = new Counter();

        Thread[] threads = new Thread[THREADS];

        for (int i = 0; i < THREADS; i++) {
            threads[i] = new Thread(new Worker(counter));
        }

        for (Thread t : threads) t.start();
        for (Thread t : threads) t.join();

        System.out.println("Wynik: " + counter.value);
        System.out.println("Oczekiwany: " + (THREADS * Worker.ITER));
    }
}