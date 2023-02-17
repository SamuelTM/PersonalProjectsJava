package samueltm.personalprojects.miscellaneous;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Randomness {

    public static <T> List<T> weightedSampling(WeightedCollection<T> collection, int sampleSize) {
        if (collection.getNumElements() < sampleSize)
            throw new IllegalArgumentException("Number of elements smaller than sample size");
        if (sampleSize < 1) throw new IllegalArgumentException("Sample size must be positive");

        List<T> result = new ArrayList<>();
        for (int i = 0; i < sampleSize; i++) {
            result.add(collection.next());
        }

        return result;
    }


    public static <T> List<T> simpleSampling(List<T> elements, int sampleSize) {
        if (elements.size() < sampleSize)
            throw new IllegalArgumentException("Number of elements smaller than sample size");
        if (sampleSize < 1) throw new IllegalArgumentException("Sample size must be positive");

        List<T> result = new ArrayList<>();
        ThreadLocalRandom random = ThreadLocalRandom.current();

        for (int i = 0; i < elements.size(); i++) {
            int howMuchWeNeed = sampleSize - result.size();
            int howMuchWeHaveLeft = elements.size() - i;
            double probability = (double) howMuchWeNeed / howMuchWeHaveLeft;
            if (random.nextDouble() <= probability) {
                result.add(elements.get(i));
            }
        }
        return result;
    }

    public static <T> List<T> simpleSampling(T[] elements, int sampleSize) {
        return simpleSampling(Arrays.asList(elements), sampleSize);
    }

    public static <T> T randomElement(List<T> list) {
        return list.get(ThreadLocalRandom.current().nextInt(list.size()));
    }

    public static <T> T randomElement(T[] list) {
        return list[ThreadLocalRandom.current().nextInt(list.length)];
    }

}
