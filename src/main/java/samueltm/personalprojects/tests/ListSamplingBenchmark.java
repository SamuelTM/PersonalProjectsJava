package samueltm.personalprojects.tests;

import samueltm.personalprojects.miscellaneous.Benchmarker;
import samueltm.personalprojects.tests.listsampling.*;

import java.util.HashMap;
import java.util.List;
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

    private HashMap<Integer, Integer> countOccurrences(SamplingMethod samplingMethod) {
        HashMap<Integer, Integer> occurrences = new HashMap<>();

        for (int i = 0; i < numberExecutions; i++) {
            List<Integer> sample = samplingMethod.sampleElements();
            for (int element : sample) {
                if (!occurrences.containsKey(element)) {
                    occurrences.put(element, 1);
                } else {
                    occurrences.put(element, occurrences.get(element) + 1);
                }
            }
        }

        return occurrences;
    }

    private Benchmarker benchmarkAlgorithm(SamplingMethod samplingMethod) {
        return new Benchmarker() {
            @Override
            public void execute() {
                HashMap<Integer, Integer> occurrences = countOccurrences(samplingMethod);
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
        SamplingMethod listShuffleSampling = new ListShuffleSampling(elements, sampleSize);
        SamplingMethod durstenfeldSampling = new DurstenfeldSampling(elements, sampleSize);
        SamplingMethod reservoirSampling = new ReservoirSampling(elements, sampleSize);
        SamplingMethod experimentalSampling = new ExperimentalSampling(elements, sampleSize);

        benchmarkAlgorithm(listShuffleSampling).getSingleExecTimeNano("List shuffle sampling");
        System.out.println();
        benchmarkAlgorithm(durstenfeldSampling).getSingleExecTimeNano("Durstenfeld sampling");
        System.out.println();
        benchmarkAlgorithm(reservoirSampling).getSingleExecTimeNano("Reservoir sampling");
        System.out.println();
        benchmarkAlgorithm(experimentalSampling).getSingleExecTimeNano("Experimental sampling");
    }
}
