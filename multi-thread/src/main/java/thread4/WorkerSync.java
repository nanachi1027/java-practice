package thread4;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Data
public class WorkerSync {
    private Random random = new Random();
    private List<Integer> list1 = new ArrayList<Integer>();
    private List<Integer> list2 = new ArrayList<Integer>();

    public void step1() {
        // synchronized (list1)
        {
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            list1.add(random.nextInt(100));
        }
    }

    public void step2() {
        // synchronized (list2)
        {
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            list2.add(random.nextInt(100));
        }
    }

    /**
     * we define two lock here one is on list1 and another one is on list2.
     * multiple locks can speed up the step1 and step2 in process.
     * when thread1 is executing step1 and the thread2 is executing step2 concurrently.
     */
    public synchronized void process() {
        for (int i = 0; i < 1000; i++) {
            step1();
            step2();
        }
    }

    public static void main(String[] args) {
        System.out.println("begin...");
        long start = System.currentTimeMillis();

        WorkerSync worker = new WorkerSync();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                worker.process();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                worker.process();
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        LocalDateTime end = LocalDateTime.now();

        // 552 000 000 ns
        // 355 000 000 ns
        System.out.println(System.currentTimeMillis() - start);
        System.out.println("List1: " + worker.getList1().size() + "; List2:" +
                worker.getList2().size());
    }
}
