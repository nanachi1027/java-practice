package thread2;

import javax.annotation.processing.Processor;

class Processor1 extends Thread {
    private boolean running = true;
    @Override
    public void run() {
        while (running) {
            System.out.println("Running");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void shutdown() {
        running = false;
    }
}


class Processor2 extends Thread {
    private volatile boolean running = true;

    @Override
    public void run() {
        while (running) {
            System.out.println("Running");

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void shutdown() {
        this.running = false;
    }
}


public class AppVolatile {
    public static void main(String[] args) {
        Processor1 pro1 = new Processor1();
        // we noted it cause it won't stop
        // pro1.start();
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // while this may not really stop the thread of Processor1
        // cause false -> running is invisible to while (running) this variable
        pro1.shutdown();

        System.out.println("Now process-2");
        Processor2 pro2 = new Processor2();
        pro2.start();
        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // this will shutdown the thread, cause it is decorated by volatile
        pro2.shutdown();
    }
}
