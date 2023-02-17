package samueltm.personalprojects.miscellaneous;

public class Stopwatch {

    private long startTime;
    private long pausedTime = -1;

    private boolean isPaused() {
        return pausedTime > -1;
    }

    public void start() {
        // if paused, reset the paused time
        if (isPaused()) {
            pausedTime = -1;
        } else if (startTime <= 0) {
            // else, the stopwatch must be reset in order to be started
            startTime = System.nanoTime();
        }
    }

    public void stop() {
        long stopTime = System.nanoTime();
        // if the stopwatch isn't stopped already
        if (startTime > 0 && !isPaused())
            pausedTime = stopTime;
    }

    public void reset() {
        // if the stopwatch is reset while it's paused, we reset the paused time
        // and the start time. If it's reset while running, we reset the paused
        // time but the start time becomes the current time
        startTime = isPaused() ? 0 : System.nanoTime();
        pausedTime = -1;
    }

    public long getElapsedTimeNano() {
        if (startTime > 0) {
            return isPaused() ? pausedTime - startTime : System.nanoTime() - startTime;
        } else {
            return 0;
        }
    }

    public String getFormattedElapsedTime() {
        return Formatter.formatNanoseconds(getElapsedTimeNano());
    }
}