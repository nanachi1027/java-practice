package thread1;

public class AppRunnable implements Runnable {
    private String id;

    public AppRunnable(String id) {
        this.id = id;
    }

    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println( id + "-thread-" + Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(new AppRunnable("th1"));
        Thread t2 = new Thread(new AppRunnable("th2"));

        t1.start();;
        t2.start();
    }
}
