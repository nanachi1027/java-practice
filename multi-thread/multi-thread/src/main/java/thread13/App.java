package thread13;

import com.sun.tools.javac.util.Assert;
import lombok.Data;
import thread10.Executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Data
class JavaBean {
    String name;
    String id;

    public JavaBean(String name, String id) {
        this.name = name;
        this.id = id;
    }
}

class CallableProcessor implements Callable<List<JavaBean>> {
    private int totalNum;

    public CallableProcessor(int totalNum) {
        this.totalNum = totalNum;
    }

    @Override
    public List<JavaBean> call() throws Exception {
        final List<JavaBean> list = new ArrayList<>();
        for (int i = 0; i < totalNum; i++) {
            list.add(new JavaBean("Beanname-" + i, (i + 1) + ""));
            Thread.sleep(100);
        }
        return list;
    }
}

public class App {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Callable<List<JavaBean>> callable = new CallableProcessor(300);
        ExecutorService threadPool = new ScheduledThreadPoolExecutor(1);

        Future<List<JavaBean>> futureRes = threadPool.submit(callable);

        while (!futureRes.isDone()) {
            System.out.println("List is still adding, wait 10 ms");
            // we could call futureRes#get()#size() this method
            // future#get() method will block current main thread.
            // the block will be consisted until thread-pool's inner thread complete.
            // so ... if we un-comment the under code, the main thread will just blocked instead of
            // keeping cycle printing currrent list's size;
            // System.out.println("List is still adding .. list size is " + futureRes.get().size() +" , wait 10 ms");
            Thread.sleep(10);
        }

        System.out.println("Thread is finished " + futureRes.isDone());
        Assert.check(futureRes.get().size() > 0);

        // shut down thread pool
        threadPool.shutdown();
        // in case of the thread inner thread pool is kept alive
        // wait 1 minute and then shutdown and destory threadpool
        threadPool.awaitTermination(1, TimeUnit.MINUTES);
    }
}
