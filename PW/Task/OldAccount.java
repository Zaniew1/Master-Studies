// class Account {
// private int balance_ = 0;

// public Account(int balance) {
// balance_ = balance;
// }

// public synchronized void withdraw(int amount) {
// simulateDelay();
// balance_ -= amount;
// }

// public synchronized void deposit(int amount) {
// balance_ += amount;
// }

// public synchronized int getBalance() {
// return balance_;
// }

// private void simulateDelay() {
// try {
// Thread.sleep(ThreadLocalRandom.current().nextInt(10, 20));
// } catch (InterruptedException e) {
// }
// }
// }