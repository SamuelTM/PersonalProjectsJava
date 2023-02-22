package samueltm.personalprojects.miscellaneous;

import samueltm.personalprojects.math.GeneralMath;

import java.math.BigInteger;
import java.util.Iterator;

public class CombinationsGenerator implements Iterator<Integer[]> {

    private final Integer[] a;
    private final int n;
    private final int r;
    private BigInteger nCombinationsLeft;
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
        a = new Integer[r];
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
        nCombinationsLeft = new BigInteger(total.toString());
    }

    public boolean hasNext() {
        return nCombinationsLeft.compareTo(BigInteger.ZERO) > 0;
    }

    /**
     * For huge number of possible combinations, it isn't viable to simply return
     * a list of all possible combinations. So we use an iterator to return one
     * possible result at a time.
     */
    @Override
    public Integer[] next() {
        if (nCombinationsLeft.equals(total)) {
            nCombinationsLeft = nCombinationsLeft.subtract(BigInteger.ONE);
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

        nCombinationsLeft = nCombinationsLeft.subtract(BigInteger.ONE);
        return a;
    }

    public BigInteger getTotalPossibleCombinations() {
        return total;
    }
}