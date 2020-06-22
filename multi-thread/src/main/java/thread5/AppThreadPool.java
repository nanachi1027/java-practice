package thread5;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Data
class MyExecutor implements Runnable {
    private Random random = new Random();
    private final Object lock1 = new Object();
    private final Object lock2 = new Object();
    public List<Integer> list1 = new ArrayList<>();
    public List<Integer> list2 = new ArrayList<>();
    ;

    @Override
    public void run() {
        process();
    }

    /**
     * Two threads each one execute process method 1 time.
     */
    public void process() {
        for (int i = 0; i < 1000; i++) {
            step1();
            step2();
        }
    }

    public void step1() {
        synchronized (lock1) {
            list1.add(random.nextInt(100));
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void step2() {
        synchronized (lock2) {
            list2.add(random.nextInt(100));
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class AppThreadPool {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newScheduledThreadPool(2);
        System.out.println("begin ...");

        long start = System.currentTimeMillis();
        MyExecutor myExecutor = new MyExecutor();
        for (int i = 0; i < 2; i++) {
           // we submit the myExecutor this single instance
            // to thread pool twice and execute its process
            // by threads
            executor.submit(myExecutor);
        }

        // thread pool will exit and be destoried when two thread executor
        // finished their task
        executor.shutdown();

        try {
            // if i'm not sure when the two executor's tasks will finish.
            // i will wait for 1 hour then terminate the thread pool
            executor.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        long end = System.currentTimeMillis();

        System.out.println("Total consume " + (end - start) + "ms");
        System.out.println("list1 size" + myExecutor.getList1().size() + "," +
                "list2 size" + myExecutor.getList2().size());
    }
}
