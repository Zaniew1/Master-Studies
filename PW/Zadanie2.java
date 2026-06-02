
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.*;

class StockMarket {
    int[] shares;
    private Lock[] locks;

    public static final int M = 10;

    public StockMarket() {
        shares = new int[M];
        locks = new Lock[M];

        for (int i = 0; i < M; i++) {
            shares[i] = 1000;
            locks[i] = new ReentrantLock();
        }
    }

    public boolean buyShares(int company, int numShares) {
        Lock lock = locks[company];
        lock.lock();
        try {
            if (shares[company] >= numShares) {
                shares[company] -= numShares;
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    public void sellShares(int company, int numShares) {
        Lock lock = locks[company];
        lock.lock();
        try {
            shares[company] += numShares;
        } finally {
            lock.unlock();
        }
    }
}

class Trader extends Thread {
    StockMarket market;
    int[] myShares;
    int buyCount = 0;
    int sellCount = 0;

    public Trader(StockMarket market) {
        this.market = market;
        myShares = new int[StockMarket.M];
    }

    int getShares(int company) {
        return myShares[company];
    }

    @Override
    public void run() {
        Random rng = ThreadLocalRandom.current();

        for (int i = 0; i < 100; ++i) {
            int company = rng.nextInt(StockMarket.M);
            int action = rng.nextInt(2);

            if (action == 0) {
                int shares = rng.nextInt(50) + 1;

                if (market.buyShares(company, shares)) {
                    myShares[company] += shares;
                    buyCount += shares;
                }

            } else {
                int shares = Math.min(rng.nextInt(50) + 1, myShares[company]);

                market.sellShares(company, shares);
                myShares[company] -= shares;
                sellCount += shares;
            }
        }
    }
}

public class Zadanie2 {
    public static void main(String[] args) throws InterruptedException {
        StockMarket market = new StockMarket();

        Trader[] traders = new Trader[10];

        for (int i = 0; i < traders.length; ++i) {
            traders[i] = new Trader(market);
        }

        for (Thread t : traders)
            t.start();
        for (Thread t : traders)
            t.join();

        for (int i = 0; i < StockMarket.M; ++i) {
            int total_shares = market.shares[i];

            for (var trader : traders) {
                total_shares += trader.getShares(i);
            }

            System.out.printf(
                    "Company [%d] - total shares: %d\tExpected: %d\n",
                    i, total_shares, 1000);
        }

        for (int i = 0; i < traders.length; ++i) {
            System.out.printf(
                    "Trader [%d]: shares bought: %d\tshares sold: %d\n",
                    i, traders[i].buyCount, traders[i].sellCount);
        }
    }
}