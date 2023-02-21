package samueltm.personalprojects.tests.listsampling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class ListShuffleSampling extends SamplingMethod {


    public ListShuffleSampling(Integer[] elements, int sampleSize) {
        super(elements, sampleSize);
    }

    @Override
    public List<Integer> sampleElements() {
        List<Integer> availableIndices = new ArrayList<>(IntStream.range(0, getElements().length).boxed().toList());
        Collections.shuffle(availableIndices);
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < getSampleSize(); i++) {
            result.add(getElements()[availableIndices.get(i)]);
        }
        return result;
    }
}
