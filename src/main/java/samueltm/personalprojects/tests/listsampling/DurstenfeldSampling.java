package samueltm.personalprojects.tests.listsampling;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class DurstenfeldSampling extends SamplingMethod {

    public DurstenfeldSampling(Integer[] elements, int sampleSize) {
        super(elements, sampleSize);
    }

    @Override
    public List<Integer> sampleElements() {
        int totalElements = getElements().length;
        for (int i = getElements().length - 1; i >= getElements().length - getSampleSize(); i--) {
            int temp = getElements()[i];
            int randomIndex = ThreadLocalRandom.current().nextInt(i + 1);
            getElements()[i] = getElements()[randomIndex];
            getElements()[randomIndex] = temp;
        }

        return Arrays.stream(getElements()).collect(Collectors.toList()).subList(
                getElements().length - getSampleSize(), getElements().length);
    }
}
