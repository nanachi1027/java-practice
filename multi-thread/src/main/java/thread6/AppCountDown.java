package thread6;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Processor implements Runnable {
    private CountDownLatch latch;

    public Processor (CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " Started, countDown:" +
                latch.getCount());
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        latch.countDown();
        System.out.println("Thread: " + Thread.currentThread().getName() + ", Count:" + latch.getCount());
        System.out.println("Thread: " + Thread.currentThread().getName() + " Complete.");

    }
}

/**
 * In this case, we use the latch like the join.
 * A little from join, we can control the num of the join,
 * which in the following demo, we set the thread pool with size of 3, which means we set the thread concurrency = 3;
 * And we submit 3 threads based on 3 Processor instance.
 *
 * However, we set the latch's number only to 2 which means
 * two of the thread completed and execute the CountDownLatch#countDown to let the 2 -> 0
 * and that is what the CountDownLatch#wait waiting for.
 * Main thread waits for CountDownLatch's inner counter comes to 0 via the CountDownLatch#await method.
 *
 * When counter comes to 0, main thread continue with its logic, it output "Completed." without caring about
 * the third thread is finished or not.
 *
 * Then we get the result like:
 * pool-1-thread-1 Started, countDown:2
 * pool-1-thread-2 Started, countDown:2
 * pool-1-thread-3 Started, countDown:2
 * Thread: pool-1-thread-2, Count:0
 * Thread: pool-1-thread-2 Complete.
 * Thread: pool-1-thread-1, Count:0
 * Thread: pool-1-thread-1 Complete.
 * Thread: pool-1-thread-3, Count:0
 * Completed.
 * Thread: pool-1-thread-3 Complete.
 *
 * Which means thread 1,2,3 in Processor is initialized and submited by the thread-pool with concurrency=3.
 *
 * Then thread1,2 finish CountDownLatch#counter == 0, latch.await() invoke main thread continue.
 * Output "Complete".
 *
 * Then at this moment third-3 complete, it output "Thread: pool-1-thread-3 Complete." and
 * it is after the main thread.
 *
*/
public class AppCountDown {
    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(2);
        ExecutorService executor = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 3; i++) {
            executor.submit(new Processor(latch));
        }
        executor.shutdown();

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Completed.");
    }
}
