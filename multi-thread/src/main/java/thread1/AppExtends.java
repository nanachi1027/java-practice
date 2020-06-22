package thread1;

public class AppExtends extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println("Thread-" + i + ", name:" + Thread.currentThread().getName());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        AppExtends app = new AppExtends();
        app.start();

        AppExtends app2 = new AppExtends();
        app2.start();
    }
}
