import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;
import java.util.concurrent.locks.ReentrantLock;
// import java.util.concurrent.locks.StampedLock;
import java.text.ParseException;
import java.util.concurrent.TimeUnit;
import java.time.Duration;
import java.time.Instant;

public class LearnMutex {
    public static int count = 0;

    public static void main(String[] args) throws ParseException {
        Instant start = Instant.now();
        methodToTime();
        LearnMutex mutex = new LearnMutex();
        ExecutorService executor = Executors.newFixedThreadPool(2);
        // StampedLock lock = new StampedLock();
        ReentrantLock lock = new ReentrantLock();

        IntStream.range(0, 10).forEach(i -> executor.submit(new Callable<Void>() {
            public Void call() throws Exception {
                mutex.increment(lock);
                // mutex.increment();
                return null;
            }
        }));
        // IntStream.range(0, 4).forEach(i -> executor.submit(mutex.increment()));
        executor.shutdown();
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
        System.out.print("Time: ");
        System.out.println(timeElapsed);
        // System.out.println(count);
    }

    // public void increment(StampedLock lock) {
        public void increment(ReentrantLock lock) {
        // public void increment() {
        // count = count + 1;
        // System.out.println(count);
        // long stamp = lock.writeLock();
        lock.lock();
        try {
            count++;
            System.out.println(count);
        } finally {
            lock.unlock();
            // lock.unlockWrite(stamp);
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