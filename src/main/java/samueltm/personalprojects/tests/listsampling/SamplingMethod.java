package samueltm.personalprojects.tests.listsampling;

import java.util.List;

public abstract class SamplingMethod {

    private final Integer[] elements;
    private final int sampleSize;

    public SamplingMethod(Integer[] elements, int sampleSize) {
        this.elements = elements;
        this.sampleSize = sampleSize;
    }

    public Integer[] getElements() {
        return elements;
    }

    public int getSampleSize() {
        return sampleSize;
    }

    public abstract List<Integer> sampleElements();
}
