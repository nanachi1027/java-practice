package thread7.cp;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Consumer {
    private BlockingQueue<Integer> queue;
    private Random random;

    public Consumer(BlockingQueue<Integer> queue) {
        this.queue = queue;
        this.random = new Random();
    }

    public void consume() {
        synchronized (this) {
            try {
                Thread.sleep(100);
                if (random.nextInt(10) == 0) {
                    Integer value = queue.take();
                    System.out.println("customer get value " + value + " from QUeue which size = " +
                            queue.size());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Producer {
    private BlockingQueue<Integer> queue;
    private Random random;

    public Producer(BlockingQueue<Integer> queue) {
        this.queue = queue;
        random = new Random();
    }

    public void produce() {
        synchronized (this) {
            queue.offer(random.nextInt(100));
        }
    }
}

public class App {
    public static void main(String[] args) {
        BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(40);
        Producer p = new Producer(blockingQueue);
        Consumer c = new Consumer(blockingQueue);

        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() ->
            p.produce(), 10, 100, TimeUnit.MILLISECONDS);
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() ->
                c.consume(), 10, 20, TimeUnit.MILLISECONDS);
    }
}
