package samueltm.personalprojects.tests.listsampling;

import samueltm.personalprojects.miscellaneous.Randomness;

import java.util.List;

public class ExperimentalSampling extends SamplingMethod {

    public ExperimentalSampling(Integer[] elements, int sampleSize, int numberExecutions) {
        super(elements, sampleSize, numberExecutions);
    }

    @Override
    public List<Integer> sampleElements() {
        return Randomness.simpleSampling(getElements(), getSampleSize());
    }
}
