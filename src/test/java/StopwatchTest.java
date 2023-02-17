import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import samueltm.personalprojects.miscellaneous.Stopwatch;

import java.util.concurrent.ThreadLocalRandom;

public class StopwatchTest {

    private static Stopwatch stopwatch;

    @BeforeAll
    static void setup() {
        stopwatch = new Stopwatch();
    }

    private void waitFor(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testIfStopwatchRemainsStopped() {
        int milliseconds = ThreadLocalRandom.current().nextInt(1000, 2000);

        stopwatch.start();
        waitFor(milliseconds);
        stopwatch.stop();

        String ellapsedTime = String.valueOf(stopwatch.getElapsedTimeNano());
        stopwatch.reset();

        assert (ellapsedTime.startsWith(String.valueOf(milliseconds).substring(0, 3)));
    }

    @Test
    public void testIfStopwatchRemainsReset() {
        int milliseconds = ThreadLocalRandom.current().nextInt(1000, 2000);

        stopwatch.start();
        waitFor(milliseconds);
        stopwatch.stop();
        stopwatch.reset();

        waitFor(milliseconds);

        assert (stopwatch.getElapsedTimeNano() == 0);
    }

    @Test
    public void testStopwatchContinuity() {
        for (int i = 0; i < 5; i++) {
            int firstWaitTime = ThreadLocalRandom.current().nextInt(1000, 2000);
            int secondWaitTime = ThreadLocalRandom.current().nextInt(1000, 2000);

            stopwatch.start();
            waitFor(firstWaitTime);
            stopwatch.reset();

            waitFor(secondWaitTime);
            stopwatch.stop();

            double ellapsedTimeMilliseconds = stopwatch.getElapsedTimeNano() / 1e6;
            stopwatch.reset();

            double currentMinusExpected = Math.abs(ellapsedTimeMilliseconds - (long) secondWaitTime);

            System.out.println("We were expecting " + secondWaitTime + "ms to pass and the actual time ellapsed was "
                    + ellapsedTimeMilliseconds);
            System.out.println("A difference of " + currentMinusExpected + "ms");
            System.out.println("-----------------");

            // 15ms error margin because dealing with a stopwatch with nanosecond precision is hard
            assert (currentMinusExpected <= 15);
        }
    }
}
