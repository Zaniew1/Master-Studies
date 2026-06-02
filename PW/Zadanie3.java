import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.*;

class Bank {
    public static final int N = 10;
    private int[] balances = new int[N];
    private Lock[] locks = new Lock[N];

    public Bank() {
        for (int i = 0; i < locks.length; ++i) {
            locks[i] = new ReentrantLock();
        }
    }

    public void deposit(int accountId, int amount) {
        locks[accountId].lock();
        try {
            balances[accountId] += amount;
        } finally {
            locks[accountId].unlock();
        }
    }

    public int getBalance(int accountId) {
        locks[accountId].lock();
        try {
            return balances[accountId];
        } finally {
            locks[accountId].unlock();
        }
    }

    public boolean transfer(int fromAccount, int toAccount, int amount) {
        // Zapobieganie zakleszczeniom (deadlock) poprzez wymuszenie stałej kolejności
        // blokowania.
        // Zawsze blokujemy najpierw konto o niższym numerze ID.
        int first = Math.min(fromAccount, toAccount);
        int second = Math.max(fromAccount, toAccount);

        locks[first].lock();
        try {
            locks[second].lock();
            try {
                if (balances[fromAccount] >= amount) {
                    balances[fromAccount] -= amount;
                    balances[toAccount] += amount;
                    return true;
                }
                return false;
            } finally {
                locks[second].unlock();
            }
        } finally {
            locks[first].unlock();
        }
    }

    public void equalize(int accountA, int accountB) {
        while (true) {
            boolean lockedA = locks[accountA].tryLock();
            boolean lockedB = locks[accountB].tryLock();

            try {
                if (lockedA && lockedB) {
                    int total = balances[accountA] + balances[accountB];
                    balances[accountA] = total / 2 + total % 2;
                    balances[accountB] = total / 2;
                    return;
                }
            } finally {
                if (lockedA)
                    locks[accountA].unlock();
                if (lockedB)
                    locks[accountB].unlock();
            }

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}

class Accountant extends Thread {
    Bank bank;

    public Accountant(Bank bank) {
        this.bank = bank;
    }

    @Override
    public void run() {
        Random rng = ThreadLocalRandom.current();
        for (int i = 0; i < 1000; ++i) {
            int fromAccount = rng.nextInt(Bank.N);
            int toAccount = rng.nextInt(Bank.N);
            while (toAccount == fromAccount) {
                toAccount = rng.nextInt(Bank.N);
            }
            if (rng.nextBoolean()) {
                bank.transfer(fromAccount, toAccount, rng.nextInt(100));
            } else {
                bank.equalize(fromAccount, toAccount);
            }
        }
    }
}

public class Zadanie3 {
    public static void main(String[] args) throws InterruptedException {
        Bank bank = new Bank();
        for (int i = 0; i < Bank.N; ++i) {
            bank.deposit(i, 100);
        }
        Thread[] threads = new Thread[10];
        for (int i = 0; i < threads.length; ++i) {
            threads[i] = new Accountant(bank);
        }
        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            t.join();
        }
        int total = 0;
        for (int i = 0; i < Bank.N; ++i) {
            int b = bank.getBalance(i);
            total += b;
            System.out.printf("Account [%d] balance: %d\n", i, b);
        }
        System.out.printf("Total balance equals %d.\n", total);
    }
}