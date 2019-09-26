import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;
import java.util.concurrent.locks.ReentrantLock;
import java.text.ParseException;
import java.util.concurrent.TimeUnit;
import java.time.Duration;
import java.time.Instant;

public class LearnReentrantLock {
    public static int count = 0;

    public static void main(String[] args) throws ParseException {
        Instant start = Instant.now();
        methodToTime();
        LearnReentrantLock mutex = new LearnReentrantLock();
        ExecutorService executor = Executors.newFixedThreadPool(2);
        ReentrantLock lock = new ReentrantLock();

        IntStream.range(0, 10).forEach(i -> executor.submit(new Callable<Void>() {
            public Void call() throws Exception {
                mutex.increment(lock);
                return null;
            }
        }));
        executor.shutdown();
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
        System.out.print("Time: ");
        System.out.println(timeElapsed);
        // System.out.println(count);
    }

    public void increment(ReentrantLock lock) {
        lock.lock();
        try {
            count++;
            System.out.println(count);
        } finally {
            lock.unlock();
        }
    }

    private static void methodToTime() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}