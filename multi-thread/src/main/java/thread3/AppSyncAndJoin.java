package thread3;

public class AppSyncAndJoin {
    private int count = 0;

    public static void main(String[] args) {
        AppSyncAndJoin syncAndJoin = new AppSyncAndJoin();
        syncAndJoin.doWork();
    }

    public synchronized void increment(String threadName) throws InterruptedException {
        count++;
        System.out.println("Thread " + Thread.currentThread().getName()
        +", count"+ count);
    }

    public void doWork() {
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 10; i++) {
                    try {
                        increment(Thread.currentThread().getName());
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        t1.start();

        Thread t2 = new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 10; i++) {
                    try {
                        increment(Thread.currentThread().getName());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t2.start();

        /**
         * Here we call join to let main thread
         * wait until both of thread1,2 finish their counter computing;
         *
         * And if we comment the codes,
         * the count is not not the result of the two threads, it just he
         * middle result of t1,t2 and main thread.
         */

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Count is " + count);
    }
}
