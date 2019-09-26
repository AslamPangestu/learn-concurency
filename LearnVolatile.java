import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;
import java.text.ParseException;
import java.util.concurrent.TimeUnit;
import java.time.Duration;
import java.time.Instant;

public class LearnVolatile {
    public static volatile int count = 0;

    public static void main(String[] args) throws ParseException {
        Instant start = Instant.now();
        methodToTime();
        LearnVolatile mutex = new LearnVolatile();
        ExecutorService executor = Executors.newFixedThreadPool(2);
        IntStream.range(0, 10).forEach(i -> executor.submit(new Callable<Void>() {
            public Void call() throws Exception {
                mutex.increment();
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

    public void increment() {
        count = count + 1;
        System.out.println(count);
    }

    private static void methodToTime() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}