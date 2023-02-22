package samueltm.personalprojects.math;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class GeneralMath {

    public static BigInteger factorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException();
        }
        BigInteger factorial = BigInteger.ONE;
        for (int i = n; i > 1; i--) {
            factorial = factorial.multiply(new BigInteger(Integer.toString(i)));
        }
        return factorial;
    }

    public static int roundToNearestMultiple(double number, int multiple) {
        return (int) (Math.round(number / multiple) * multiple);
    }

    public static int collatzConjecture(int n) {
        int counter = 0;
        while (n > 1) {
            if (n % 2 == 0) {
                n /= 2;
            } else {
                n = (3 * n) + 1;
            }
            counter++;
        }
        return counter;
    }

    public static double nthRoot(double base, double root) {
        return Math.pow(base, 1.0 / root);
    }

    public static double dotProduct(double[] a, double[] b) {
        if (a.length == b.length) {
            double sum = 0;
            for (int i = 0; i < a.length; i++) {
                sum += a[i] * b[i];
            }

            return sum;
        } else {
            throw new IllegalArgumentException("Both vectors must have the same length");
        }
    }

    public static List<Integer> factors(int n) {
        final List<Integer> factors = new ArrayList<>();
        if (n > 0) {
            for (int i = 1; i <= n; i++) {
                if (n % i == 0) {
                    factors.add(i);
                }
            }
        } else {
            for (int i = n; i <= Math.abs(n); i++) {
                if (i != 0 && n % i == 0) {
                    factors.add(i);
                }
            }
        }
        return factors;
    }

    private static int greatestCommonDivisor(int a, int b) {
        return b == 0 ? a : greatestCommonDivisor(b, a % b);
    }

    private static int leastCommonMultiple(int a, int b) {
        return a * (b / greatestCommonDivisor(a, b));
    }

    public static int greatestCommonDivisor(int... numbers) {
        int result = numbers[0];
        for (int i = 1; i < numbers.length; i++) {
            result = greatestCommonDivisor(result, numbers[i]);
        }
        return result;
    }

    public static int leastCommonMultiple(int... numbers) {
        int result = numbers[0];
        for (int i = 1; i < numbers.length; i++) {
            result = leastCommonMultiple(result, numbers[i]);
        }
        return result;
    }

    public static double greatestValue(double... numbers) {
        double greatest = numbers[0];
        for (int i = 1; i < numbers.length; i++) {
            greatest = Math.max(greatest, numbers[i]);
        }
        return greatest;
    }

    public static double lowestValue(double... numbers) {
        double lowest = numbers[0];
        for (int i = 1; i < numbers.length; i++) {
            lowest = Math.min(lowest, numbers[i]);
        }
        return lowest;
    }

    public static int digitalRoot(int number) {
        if (number <= 0) throw new IllegalArgumentException("Number must be positive");

        return number >= 10 ? digitalRoot(String.valueOf(number).chars().mapToObj(i -> (char) i)
                .mapToInt(Character::getNumericValue).sum()) : number;
    }

    public static double round(double number, int decimalPlaces) {
        return BigDecimal.valueOf(number).setScale(decimalPlaces, RoundingMode.HALF_EVEN).doubleValue();
    }
}
