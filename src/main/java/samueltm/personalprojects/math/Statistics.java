package samueltm.personalprojects.math;

public class Statistics {

    /**
     * Normaliza o valor especificado para um valor compreendido entre 0 e 1
     */
    public static double normalize(double value, double currentUpperBound, double currentLowerBound) {
        return (value - currentLowerBound) / (currentUpperBound - currentLowerBound);
    }

    public static double arithmeticMean(double[] numbers) {
        double sum = 0;
        for (double n : numbers)
            sum += n;
        return sum / numbers.length;
    }

    public static double geometricMean(double[] numbers) {
        double product = 1;
        for (double n : numbers) {
            if (n > 0) {
                product *= n;
            } else {
                throw new IllegalArgumentException("Cannot calculate the geometric mean of non-positive numbers");
            }
        }
        return GeneralMath.nthRoot(product, numbers.length);
    }

}