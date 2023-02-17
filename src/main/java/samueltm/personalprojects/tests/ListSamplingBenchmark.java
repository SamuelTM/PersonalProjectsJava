package samueltm.personalprojects.tests;

import samueltm.personalprojects.miscellaneous.Benchmarker;
import samueltm.personalprojects.tests.listsampling.*;

import java.util.HashMap;
import java.util.stream.IntStream;

public class ListSamplingBenchmark {

    private final Integer[] elements;
    private final int sampleSize;
    private final int numberExecutions;

    public ListSamplingBenchmark(int numElements, int sampleSize, int numberExecutions) {
        this.elements = IntStream.range(0, numElements).boxed().toArray(Integer[]::new);
        this.sampleSize = sampleSize;
        this.numberExecutions = numberExecutions;
    }

    private Benchmarker benchmarkAlgorithm(SamplingMethod samplingMethod) {
        return new Benchmarker() {
            @Override
            public void execute() {
                HashMap<Integer, Integer> occurrences = samplingMethod.countElementOccurrences();
                double sumOccurrences = (double) occurrences.values().stream().mapToInt(Integer::valueOf).sum();
                double stnDeviation = 0;
                for (int occurrence : occurrences.values()) {
                    // sample size is equal to the average in this case
                    stnDeviation += Math.pow(occurrence - sampleSize, 2);
                }
                stnDeviation = Math.sqrt(stnDeviation);
                System.out.println("Standard deviation of the occurrences is: " + stnDeviation);
            }
        };
    }

    public void benchmarkAlgorithms() {
        SamplingMethod listShuffleSampling = new ListShuffleSampling(elements, sampleSize, numberExecutions);
        SamplingMethod durstenfeldSampling = new DurstenfeldSampling(elements, sampleSize, numberExecutions);
        SamplingMethod reservoirSampling = new ReservoirSampling(elements, sampleSize, numberExecutions);
        SamplingMethod experimentalSampling = new ExperimentalSampling(elements, sampleSize, numberExecutions);

        benchmarkAlgorithm(listShuffleSampling).getSingleExecTimeNano("List shuffle sampling");
        System.out.println();
        benchmarkAlgorithm(durstenfeldSampling).getSingleExecTimeNano("Durstenfeld sampling");
        System.out.println();
        benchmarkAlgorithm(reservoirSampling).getSingleExecTimeNano("Reservoir sampling");
        System.out.println();
        benchmarkAlgorithm(experimentalSampling).getSingleExecTimeNano("Experimental sampling");
    }
}
