package thread8;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

public class Processor {
    private static final int DEFAULT_CAPACITY = 10;
    private Random random;
    private Queue<Integer> queue;
    private int capacity;

    public Processor() {
        this(DEFAULT_CAPACITY);
    }

    public Processor(int capacity) {
        queue = new LinkedList<Integer>();
        this.capacity = capacity;
        this.random = new Random();
    }

    public void produce() throws InterruptedException {
        synchronized (this) {
            while (true) {
                // when producer detects that the queue is full
                // producer thread needs wait
                if (queue.size() == this.capacity) {
                    System.out.println("Producer detects the queue is full, capacity=" + capacity);
                    // queue is full wait empty position in queue then produce
                    wait();
                }
                System.out.println("Thread " + Thread.currentThread().getName() + " producer producer item to queue");
                queue.add(random.nextInt(200));
                // add one item notify consumer to consume
                notify();

            }
        }
    }

    public void consume() throws InterruptedException {
        synchronized (this) {
            while (true) {
                if (queue.isEmpty()) {
                    System.out.println("Queue is empty, wait producer produce");
                    // queue is empty wait producer produce
                    wait();
                }
                Integer ret = queue.poll();
                System.out.println("Thread " + Thread.currentThread().getName() + " consume item " + ret);
                // consume 1 item, notify producer to produce another item
                notify();

            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Processor processor = new Processor();

        Thread producer = new Thread(() -> {
            try {
                processor.produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                processor.consume();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();
    }
}
