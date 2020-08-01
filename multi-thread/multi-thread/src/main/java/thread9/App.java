package thread9;

import java.util.LinkedList;
import java.util.Random;

public class App {
    public static void main(String[] args) throws InterruptedException {

        Processor p = new Processor();

        Thread t1 = new Thread(() -> {
            try {
                p.produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                p.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }
}


/**
 * In this case, we create a lock and synchronzied (lock)
 * to merge producer & consumer's synchronized regions together.
 */
class Processor {
    private LinkedList<Integer> list = new LinkedList<>();
    private final int capacity = 10;
    private final Object lock = new Object();

    public void produce() throws InterruptedException {
        int value = 0;
        while (true) {
            synchronized (lock) {
                while (list.size() == capacity) {
                    lock.wait();
                }
                list.add(value);
                System.out.println("Thread " + Thread.currentThread().getName() + " Producer add item " + value + "to queue, queue size =" + list.size());
                value++;

                // notify all threads which in lock's thread queue
                lock.notify();
            }
        }
    }

    public void consume() throws InterruptedException {
        Random random = new Random();
        while (true) {
            synchronized (lock) {
                while (list.size() == 0) {
                    // if queue is empty, then let the thread release current lock
                    // and add this thread to lock's thread queue.
                    lock.wait();
                }

                // remove the head element from queue
                int value = list.removeFirst();
                System.out.println("Thread " + Thread.currentThread().getName() + " Consumer get first element=" + value
                        + " from queue which size = " + list.size());
                // this will wake up 1 thread from lock's thread queue
                lock.notify();
            }

            Thread.sleep(random.nextInt(3000));
        }
    }
}
