package thread13;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

class Processor implements Callable<Integer> {
    private int value;

    public Processor(int value) {
        this.value = value;
    }

    @Override
    public Integer call() throws Exception {
        int sum = 0;
        for (int i = 0; i <= value; i++) {
            sum += i;
        }
        return sum;
    }
}

public class App2 {
    public static void main(String[] args) throws InterruptedException {
        List<Integer> resList = new ArrayList<>();
        ExecutorService threadPool = Executors.newFixedThreadPool(2);
        Future<Integer> futureHandler = null;


        for (int i = 1; i <= 10; i++) {
            // here we create 1.. 10 , 10 new processor
            // and the 10 processor will send to 2 threads = thread pool size
            // the concurrency inside the thread pool is 2.
            // all these threads use the same future handler
            futureHandler = threadPool.submit(new Processor(i));
            try {
                // i-th, value = i, resList add sum of 0 + 1 + ... + value
                resList.add(futureHandler.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        threadPool.shutdown();

        // in case of the 2 threads keeps aliving and not release the resources
        // we set the thread pool will shutdown in 2 minutes
        threadPool.awaitTermination(2, TimeUnit.MINUTES);

        // traverse the results inside resList
        for (int i = 1; i <= resList.size(); i++) {
            int res = resList.get(i - 1);
            System.out.println(String.format("[0 + ... + %d] sum is %d",
                    i, res));
        }
    }

}
