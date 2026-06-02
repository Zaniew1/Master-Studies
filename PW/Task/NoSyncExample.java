class Counter {
    int value = 0;

    void inc() {
        ++value;
    }
}

public class NoSyncExample {

    public static void runTest(int threads, int increments) throws InterruptedException {
        Counter counter = new Counter();
        Thread[] t = new Thread[threads];

        long start = System.nanoTime();

        for (int i = 0; i < threads; i++) {
            t[i] = new Thread(() -> {
                for (int j = 0; j < increments; j++) {
                    counter.inc();
                }
            });
            t[i].start();
        }

        for (Thread thread : t) {
            thread.join();
        }

        long end = System.nanoTime();

        System.out.println("Wątki: " + threads +
                ", Inkrementacje: " + increments +
                ", Wynik: " + counter.value +
                ", Czas: " + (end - start) / 1_000 + " microsec");
    }

    public static void main(String[] args) throws InterruptedException {
        int[] threadOptions = {2, 8};
        int[] incrementOptions = {10, 100, 1000, 10000, 1_000_000};

        for (int t : threadOptions) {
            for (int inc : incrementOptions) {
                for (int i = 0; i < 5; i++) {
                    runTest(t, inc);
                }
                System.out.println("-----");
            }
        }
    }
}