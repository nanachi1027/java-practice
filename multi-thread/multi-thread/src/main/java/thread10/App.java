package thread10;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class App {
    public static void main(String[] args) {
        Executor e = new Executor();
        // this time we try 1 thread thread pool
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
            try {
                e.thread1();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        } , 100, 100, TimeUnit.MILLISECONDS);

        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
            try {
                e.thread2();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }, 100, 100, TimeUnit.MILLISECONDS);
    }
}
