package excercize95;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class SavingsAccount {
    private int balance;
    private int numberOfPrefered;
    private final Lock lock;
    private final Condition condition;

    SavingsAccount() {
        balance = 0;
        numberOfPrefered = 0;
        lock = new ReentrantLock();
        condition = lock.newCondition();
    }

    SavingsAccount(int balance) {
        this.balance = balance;
        numberOfPrefered = 0;
        lock = new ReentrantLock();
        condition = lock.newCondition();
        System.out.println("initial balance: " + balance);
    }

    void deposit(int k) {
        lock.lock();
        try {
            balance += k;
            System.out.println(balance + "(+" + k + ")" );
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    void withdraw(int k) throws InterruptedException {
        lock.lock();
        try {
            while ( balance < k || numberOfPrefered > 0) {
                System.out.println("going to sleep zzzzzzz");
                condition.await();
            }
            balance -= k;
            condition.signalAll();
            System.out.println(balance + "(-" + k + ")" );
        } finally {
            lock.unlock();
        }
    }

    void preferedWithdraw(int k) throws InterruptedException {
        lock.lock();
        try {
            numberOfPrefered++;
            while ( balance < k ) {
                System.out.println("gimme ma moneyyyyyzzzzzzz");
                condition.await();
            }
            balance -= k;
            numberOfPrefered--;
            condition.signalAll();
            System.out.println(balance + "(-" + k + ")" + "pref");
        } finally {
            lock.unlock();
        }
    }

    void transfer(int k, SavingsAccount reserve) throws InterruptedException {
        lock.lock();
        try {
            reserve.withdraw(k);
            deposit(k);
        } finally {
            lock.unlock();
        }
    }
}
