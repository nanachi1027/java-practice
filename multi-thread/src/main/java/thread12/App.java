package thread12;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class App {
    public static void main(String[] args) throws InterruptedException {
        // we set concurrency of thread pool is 30
        ExecutorService threadPool = Executors.newFixedThreadPool(30);

        // create 30 Connection instance
        // and we know the connection's conn limitation is 10
        // so the concurrency when multiple comes to doConnect will be shirked to 10
        for (int i = 0; i < 30; i++) {
            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    Connection.getInstance().connect();
                }
            });
        }

        threadPool.shutdown();
        threadPool.awaitTermination(1, TimeUnit.HOURS);
    }
}
