package samueltm.personalprojects.tests.listsampling;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ReservoirSampling extends SamplingMethod {

    public ReservoirSampling(Integer[] elements, int sampleSize, int numberExecutions) {
        super(elements, sampleSize, numberExecutions);
    }

    @Override
    public List<Integer> sampleElements() {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < getElements().length; i++) {
            if (result.size() < getSampleSize()) {
                result.add(getElements()[i]);
            } else {
                int randomIndex = ThreadLocalRandom.current().nextInt(i + 1);
                if (randomIndex < getSampleSize()) {
                    result.set(randomIndex, getElements()[i]);
                }
            }
        }
        return result;
    }
}
