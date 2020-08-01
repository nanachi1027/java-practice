package thread11;

public class DeadLockDemo {
    public static final Object lock1 = new Object();
    public static final Object lock2 = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread1 t1 = new Thread1("Thread-1");
        Thread2 t2 = new Thread2("Thread-2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }

    private static class Thread1 extends Thread {
        public Thread1(String name) {
            super(name);
        }

        @Override
        public void run() {
            synchronized (lock1) {
                System.out.println("Thread " + Thread.currentThread().getName() + " holds lock1");
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    // ignore
                }
                System.out.println("Thread " + Thread.currentThread().getName() + " wait lock2 release and hold lock2");
                synchronized (lock2) {
                    // this will never happen
                    System.out.println("Thread " + Thread.currentThread().getName() + " hold lock1 and lock2");
                }
            }
        }
    }

    private static class Thread2 extends Thread {

        public Thread2(String name) {
            super(name);
        }

        @Override
        public void run() {
            synchronized (lock2) {
                System.out.println("Thread " + Thread.currentThread().getName() + " occupy lock2");
                try {
                    Thread.sleep(300);
                } catch (InterruptedException ex) {
                    // ignore
                }
                System.out.println("Thread " + Thread.currentThread().getName() + " try to hold lock1");
                synchronized (lock1) {
                    // this will never happen
                    System.out.println("Thread " + Thread.currentThread().getName() + " now holds lock 2 & lock2");
                }
            }
        }
    }

}
