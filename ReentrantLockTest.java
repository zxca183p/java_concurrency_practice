import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {
    public static void main(String[] args) {

        Counter c = new Counter();

        ExecutorService es = Executors.newFixedThreadPool(50);
        for (int i = 0; i < 1000; i++) {
            es.submit(() -> {
                c.add();
            });
        }
        es.shutdown();
        while (es.isTerminated() == false) {
            System.out.println("running");
            try {
                Thread.sleep(1000);
                System.out.println("count: " + c.count);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("count: " + c.count);
    }

    public static class Counter {
        public int count = 0;
        private Lock lock = new ReentrantLock();

        public void add() {
            try {
                lock.lock();
                count++;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

    }
}
