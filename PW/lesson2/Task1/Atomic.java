package lesson2.Task1;
import java.util.concurrent.atomic.AtomicInteger;


public class Atomic {

    static class Counter {
        AtomicInteger value = new AtomicInteger(0);
        public void increment() {
            value.incrementAndGet();
        }
    }

    static class Worker implements Runnable {
        Counter counter;
        static final int ITER = 1_000_000;

        Worker(Counter c) { counter = c; }

        public void run() {
            for (int i = 0; i < ITER; i++) {
                counter.increment();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int THREADS = 8;
        Counter counter = new Counter();

        Thread[] threads = new Thread[THREADS];

        for (int i = 0; i < THREADS; i++)
            threads[i] = new Thread(new Worker(counter));

        for (Thread t : threads) t.start();
        for (Thread t : threads) t.join();

        System.out.println("Wynik: " + counter.value);
        System.out.println("Oczekiwany: " + (THREADS * Worker.ITER));
    }
}