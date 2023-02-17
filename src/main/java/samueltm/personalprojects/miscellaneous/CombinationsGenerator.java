package samueltm.personalprojects.miscellaneous;

import samueltm.personalprojects.math.GeneralMath;

import java.math.BigInteger;

public class CombinationsGenerator {

    private final int[] a;
    private final int n;
    private final int r;
    private BigInteger numCombinationsLeft;
    private final BigInteger total;

    public CombinationsGenerator(int n, int r) {
        if (r > n) {
            throw new IllegalArgumentException();
        }
        if (n < 1) {
            throw new IllegalArgumentException();
        }
        this.n = n;
        this.r = r;
        a = new int[r];
        BigInteger nFactorial = GeneralMath.factorial(n);
        BigInteger rFactorial = GeneralMath.factorial(r);
        BigInteger differenceFactorial = GeneralMath.factorial(n - r);
        total = nFactorial.divide(rFactorial.multiply(differenceFactorial));
        reset();
    }

    private void reset() {
        for (int i = 0; i < a.length; i++) {
            a[i] = i;
        }
        numCombinationsLeft = new BigInteger(total.toString());
    }

    public boolean hasNext() {
        return numCombinationsLeft.compareTo(BigInteger.ZERO) > 0;
    }

    public BigInteger getTotal() {
        return total;
    }

    /**
     * For huge number of combinations, it isn't viable to simply return a list with
     * all possible combinations. So we use a (sort of) iterator to return one
     * possible result at a time.
     */
    public int[] getNext() {
        if (numCombinationsLeft.equals(total)) {
            numCombinationsLeft = numCombinationsLeft.subtract(BigInteger.ONE);
            return a;
        }

        int i = r - 1;
        while (a[i] == n - r + i) {
            i--;
        }
        a[i] = a[i] + 1;
        for (int j = i + 1; j < r; j++) {
            a[j] = a[i] + j - i;
        }

        numCombinationsLeft = numCombinationsLeft.subtract(BigInteger.ONE);
        return a;
    }
}