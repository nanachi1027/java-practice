package thread8;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * In this case we try to under stand the "wait" & "notify" in multi-thread.
 * One thing needs to make sure is that : {@code wait() and notify()} should
 * be called inside a synchronized region which can be decorated by 'synchronized'
 * or 'lock & unlock';
 */
class BlockingQueue<T> {
    private Queue<T> _queue = new LinkedList<>();
    private int capacity;
    private Lock lock;

    // create two queue condition variables
    private Condition queueNotEmpty ;
    private Condition queueNotFull;

    public BlockingQueue(int capacity) {
        this.capacity = capacity;
        lock = new ReentrantLock();
        queueNotFull = lock.newCondition();
        queueNotEmpty = lock.newCondition();
    }

    public void put(T elem) throws InterruptedException {
        lock.lock();
        try {
            while (_queue.size() == capacity) {
                System.out.println("Queue is full");

                // thread will come to wait state
                // until the same condition variable calls signal to wake up one of the wait threads.
                queueNotFull.await();
            }
            _queue.add(elem);
            System.out.println("Add to queue elem=" + elem);

            // let one of the wait threads waits wake up
            queueNotEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public T take() throws InterruptedException {
        lock.lock();
        try {
            while (_queue.isEmpty()) {
                System.out.println("Queue is empty, can not take any elements.");
                // when thread meet await, this thread will release the lock
                // and added to the thread-queue of queueNotEmpty
                queueNotEmpty.await();
            }

            T item = _queue.poll();
            System.out.println("Poll one item from queue: " + item);
            // when thread meet signal, system will wake up a thread
            // from the thread-queue of queueNotFull
            queueNotFull.signal();
            return item;
        } finally {
            lock.unlock();
        }
    }
}

public class QueueApp {
    public static void main(String[] args) throws InterruptedException {
        final BlockingQueue<Integer> blockingQueue = new BlockingQueue<>(10);
        final Random random = new Random();

        // thread1 t1 add random elem to blocking-queue
        Thread t1 = new Thread(() -> {
            try {
                while (true) {
                    int value = random.nextInt(300);
                    blockingQueue.put(value);
                    System.out.println("Thread " + Thread.currentThread().getName() + " get " + value + " from Queue");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // thread2 t2 take elem from blocking-queue
        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                while (true) {
                    Integer ret = blockingQueue.take();
                    System.out.println("Thread:" + Thread.currentThread().getName() + " take " + ret + " from Queue");
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });

        // start t1 and t2
        t1.start();
        t2.start();

        // join t1 and t2 let main thread continue until  t1 & t2 finish their tasks.
        t1.join();
        t2.join();
    }
}
