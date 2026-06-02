import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

class Account {
    private AtomicInteger balance_ = new AtomicInteger(0);

    public Account(int balance) {
        balance_.set(balance);
    }

    public void withdraw(int amount) {
        simulateDelay();
        balance_.addAndGet(-amount);
    }

    public void deposit(int amount) {
        balance_.addAndGet(amount);
    }

    public int getBalance() {
        return balance_.get();
    }

    private void simulateDelay() {
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(10, 20));
        } catch (InterruptedException e) {
        }
    }
}

class Operator extends Thread {
    public static final int N = 20; // l. transakcji
    private final Account account_;
    public int deposited_ = 0;
    public int withdrawn_ = 0;

    public Operator(Account account) {
        account_ = account;
    }

    @Override
    public void run() {
        for (int i = 0; i < N; ++i) {
            // Wybierz losowo typ operacji...
            if (ThreadLocalRandom.current().nextFloat() < 0.9) { // ...pobranie 90% czasu
                if (account_.getBalance() >= 5) {
                    account_.withdraw(5);
                    withdrawn_ += 5;
                }
            } else { // ...czasem wpłata
                account_.deposit(10);
                deposited_ += 10;
            }
        }
    }
}

public class Zadanie1 {
    public static void main(String[] args) throws InterruptedException {
        Account acc = new Account(0);
        Operator[] operators = new Operator[50];
        for (int i = 0; i < operators.length; ++i) {
            operators[i] = new Operator(acc);
        }
        for (Thread t : operators) {
            t.start();
        }
        int totalWithdrawn = 0;
        int totalDeposited = 0;
        for (Operator o : operators) {
            o.join();
            totalDeposited += o.deposited_;
            totalWithdrawn += o.withdrawn_;
        }
        System.out.println("Całk. kwota wpłacona: " + totalDeposited);
        System.out.println("Stan konta: " + acc.getBalance());
        System.out.println("Całk. kwota wypłacona: " + totalWithdrawn);
        if (acc.getBalance() < 0) {
            System.out.println("Debet na koncie!");
        } else if (acc.getBalance() + totalWithdrawn > totalDeposited) {
            System.out.println("Za dużo pieniędzy w systemie!");
        } else if (acc.getBalance() + totalWithdrawn < totalDeposited) {
            System.out.println("Za mało pieniędzy w systemie!");
        } else {
            System.out.println("OK");
        }
    }
}