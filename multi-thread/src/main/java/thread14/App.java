package thread14;


import java.sql.Time;
import java.util.concurrent.*;

/**
 * In this class we gonna try to interrupt running threads
 * via java native thread interruption mechanism:
 *
 * We know that there are multiple ways to create thread
 * 1. extends Thread method and over-write run method;
 * 2. implement Runnable and override & implement run method;
 * 3. implement Callable<T> interface and implement T call(void) method defined inside the interface.
 *
 * In this case we will use Callable which supports callback mechanism.
 * Every time we crate a thread via implementing Callable,
 * we will get a callback handler.
 *
 * We can receive the return result via the handler after the thread finished.
 * And we can also interrupt the thread via the handler by calling cancel method.
 */
public class App {
    public static void main(String[] args) throws InterruptedException {
        // build a thread-pool
        ExecutorService threadPool = Executors.newCachedThreadPool();
        Future<?> future = threadPool.submit(new Callable<Void>() {

            @Override
            public Void call() throws Exception {
                while (true) {
                    if (Thread.currentThread().isInterrupted()) {
                        System.out.println("Thread " + Thread.currentThread().getName() + " is interrupted!");
                        break;
                    }
                }
                return null;
            }
        });

        threadPool.shutdown();
        Thread.sleep(400);

        // this call will send an interrupted signal to inner thread which
        // is executing call() {...} logic inner thread pool
        // if we comment the under line, this progress will keep running for at least
        // 1 minute and the thread inner thread pool is terminated by exit of thread pool
       // future.cancel(true);

        // the thread pool will wait 1 hour
        // then no matter inner thread is still alive or not the thread pool will exit.
        threadPool.awaitTermination(30, TimeUnit.SECONDS);

        System.out.println("Main thread exit!");
    }
}
