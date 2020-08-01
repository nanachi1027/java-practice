package thread12;

import java.util.concurrent.Semaphore;

/**
 * In this case, we limit built connect's num <= 10 by Semaphore.
 *
 * TODO: fix block problem.
 */
public class Connection {
    // param1: semaphore counter
    // param2: fair which mean FIFO that if multiple threads need only one permits
    // will the semaphore give the permit to the first arrived thread

    // it just like we have 1 thread queue, and 10 lock for n threads (n > 10) to capture
    // or you can take the Semaphore with 10 permits like a thread-pool
    // which fixed size is 10, or the concurrency of the thread pool is 10.
    // the 11-th coming thread has to wait former threads finishes their job and release the thread.
    private Semaphore sem = new Semaphore(10, true);
    private int connCounter = 0;
    private int connCapacity = 10;
    private static Connection instance = new Connection();

    private Connection() {
    }

    public static Connection getInstance() {
        return instance;
    }

    public void connect() {
        try {
            // get permit decrease if the permit comes to 0
            // then current thread have to wait other thread release
            sem.acquire();

            System.out.println("[After acuqire] how many permits available: " + sem.availablePermits() +
                    ", how many permits used " + sem.drainPermits());
            doConnect();
        } catch (InterruptedException ex) {
            // ignore
        } finally {
            sem.release();
            System.out.println("[After release ]how many permits available: " + sem.availablePermits() +
                    " , how many permits used: " + sem.drainPermits());
        }
    }

    public void doConnect() {
        System.out.println("Connection is built successfully via Thread " + Thread.currentThread().getName());
        synchronized (this) {
            connCounter++;
            System.out.println("counterr=" + connCounter + ", conn capacity is " + connCapacity);
        }

        try {
            System.out.println("Thread " + Thread.currentThread().getName() + " do logic based on this connection");
            System.out.println("Now permit is " + sem.availablePermits());
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        synchronized (this) {
            connCounter--;
            System.out.println("Thread " + Thread.currentThread().getName() + " finished connection and return. counter=" + connCounter);
        }
    }
}
