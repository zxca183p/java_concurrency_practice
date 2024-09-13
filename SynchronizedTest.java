import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SynchronizedTest {
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName());

        TestBo bo = new TestBo();

        ExecutorService es = Executors.newFixedThreadPool(50);
        for (int j = 0; j < 1000; j++) {
            es.submit(() -> {
                bo.addI();
            });
            es.submit(() -> {
                bo.addJ();
            });
        }

        es.shutdown();
        while (es.isTerminated() == false) {
        }

        System.out.println("i:" + bo.i);
        System.out.println("j:" + bo.j);
    }

    public static class TestBo {
        int i;
        int j;

        public synchronized void addI() {
            i++;
        }

        public void addJ() {
            j++;
        }
    }
}
