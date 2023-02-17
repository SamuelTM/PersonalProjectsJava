package samueltm.personalprojects.tests.listsampling;

import java.util.HashMap;
import java.util.List;

public abstract class SamplingMethod {

    private final Integer[] elements;
    private final int sampleSize;

    private final int numberExecutions;

    public SamplingMethod(Integer[] elements, int sampleSize, int numberExecutions) {
        this.elements = elements;
        this.sampleSize = sampleSize;
        this.numberExecutions = numberExecutions;
    }

    public Integer[] getElements() {
        return elements;
    }

    public int getSampleSize() {
        return sampleSize;
    }

    public abstract List<Integer> sampleElements();

    public HashMap<Integer, Integer> countElementOccurrences() {
        HashMap<Integer, Integer> result = new HashMap<>();
        for (int i = 0; i < numberExecutions; i++) {
            List<Integer> sample = sampleElements();
            for (int element : sample) {
                if (!result.containsKey(element)) {
                    result.put(element, 1);
                } else {
                    result.put(element, result.get(element) + 1);
                }
            }
        }
        return result;
    }
}
