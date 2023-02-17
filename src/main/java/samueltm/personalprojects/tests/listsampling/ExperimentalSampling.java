package samueltm.personalprojects.tests.listsampling;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ExperimentalSampling extends SamplingMethod {

    public ExperimentalSampling(Integer[] elements, int sampleSize, int numberExecutions) {
        super(elements, sampleSize, numberExecutions);
    }

    @Override
    public List<Integer> sampleElements() {
        List<Integer> result = new ArrayList<>();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < getElements().length; i++) {
            int howMuchWeNeed = getSampleSize() - result.size();
            int howMuchWeHave = getElements().length - i;
            double probability = (double) howMuchWeNeed / howMuchWeHave;
            if (random.nextDouble() <= probability) {
                result.add(getElements()[i]);
            }
        }

        return result;
    }
}
