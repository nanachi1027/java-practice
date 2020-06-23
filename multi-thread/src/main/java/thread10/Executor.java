package thread10;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Executor {
    private int count = 0;
    private Lock lock = new ReentrantLock();
    private Condition cond = lock.newCondition();

    // 1000 plu per unit
    private void inc() {
        for (int i = 0; i < 1000; i++) {
            count++;
        }
    }

    public void thread1() throws InterruptedException {
        lock.lock();
        System.out.println("waiting ");
        // thread release lock and append to lock's thread queue
        cond.await();
        try {
            inc();
            System.out.println("counter = " + count + " in Thread " + Thread.currentThread().getName());
        } finally {
            lock.unlock();
        }
    }

    public void thread2() throws InterruptedException {
        lock.lock();
        System.out.println("Generated random value");
        Random r = new Random();
        int value = r.nextInt(200);
        // wake up one thread from lock's thread queue
        cond.signal();

        try {
            inc();
            System.out.println("counter = " + count + " in Thread " + Thread.currentThread().getName());
        } finally {
            lock.unlock();
        }
    }

    public void finished() {
        System.out.println("Count is " + count);
    }
}
