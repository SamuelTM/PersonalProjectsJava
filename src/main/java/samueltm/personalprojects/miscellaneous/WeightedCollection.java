package samueltm.personalprojects.miscellaneous;

import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Class used to return a random element based on the specified probability
 * that it'll be chosen.
 */
public class WeightedCollection<E> {

    private final NavigableMap<Double, E> elements = new TreeMap<>();
    private double totalWeight = 0;

    public WeightedCollection<E> add(double weight, E element) {
        if (weight <= 0) return this;
        totalWeight += weight;
        elements.put(totalWeight, element);
        return this;
    }

    public E next() {
        double value = ThreadLocalRandom.current().nextDouble() * totalWeight;
        return elements.higherEntry(value).getValue();
    }

    public int getNumElements() {
        return elements.size();
    }

}
