package samueltm.personalprojects.miscellaneous;

/**
 * Class used for performing small function benchmarks
 */
public abstract class Benchmarker {

    public abstract void execute();

    /**
     * Executes the test for the specified number of times to get
     * an average run time
     * @param testName a convenience parameter to give a name to the current test
     * @param numberExecs the number of times the test is going to be executed
     * @return the average run time in nanoseconds
     */
    public final long getAverageExecTimeNano(String testName, int numberExecs) {
        if (numberExecs < 1)
            throw new IllegalArgumentException("Number of executions must be greater than zero");
        System.out.println("Executing test: " + testName);
        long totalRunTimesNano = 0;
        Stopwatch stopwatch = new Stopwatch();
        for (int i = 0; i < numberExecs; i++) {
            stopwatch.start();
            execute();
            stopwatch.stop();
            totalRunTimesNano += stopwatch.getElapsedTimeNano();
            stopwatch.reset();
        }
        totalRunTimesNano = totalRunTimesNano / numberExecs;
        System.out.println("Function \"" + testName + "\" took in average "
                + Formatter.formatNanoseconds(totalRunTimesNano));
        return totalRunTimesNano;
    }

    public final long getSingleExecTimeNano(String nomeTeste) {
        return getAverageExecTimeNano(nomeTeste, 1);
    }
}
