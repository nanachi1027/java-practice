package thread5;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Processor implements Runnable {
    private int counter;
    public Processor(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println("Begin Processor id " + id);
        try {
            Thread.sleep(3000);
            synchronized (this) {
                counter++;
                // if we do not use synchronized around below line, counter will be different.
                System.out.println("Now processor-" + id + ", counter=" + counter);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("End Processor id " + id);
    }

    private int id;
}

public class AppProcessor {
    public static void main(String[] args) throws InterruptedException {
        // create a thread pool with 2 threads each execute a new processor
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 2; i++) {
            executorService.submit(new Processor(i));
        }


        System.out.println("=============");
        Processor p = new Processor(3);
        // 2 core && 2 task the concurrency is 2
        for (int i = 0; i < 2; i++) {
            executorService.submit(p);
        }

        executorService.shutdown();

        System.out.println("All tasks submitted");
        executorService.awaitTermination(1, TimeUnit.HOURS);
        System.out.println("All tasks completed.");
    }

}
