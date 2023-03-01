import org.junit.jupiter.api.Test;
import samueltm.personalprojects.math.Matrix2D;

import java.util.concurrent.ThreadLocalRandom;

public class MatrixTest {

    @Test
    public void testMultiplicationAlgorithm() {
        final int minDimension = 2, maxDimension = 1000;
        final int minElement = 1, maxElement = 10;
        final ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < 10; i++) {
            final int m = random.nextInt(minDimension, maxDimension + 1);
            final int n = random.nextInt(minDimension, maxDimension + 1);
            final int p = random.nextInt(minDimension, maxDimension + 1);

            final int sizeOne = m * n;

            final double[] n1 = new double[sizeOne];
            double[] n2 = new double[sizeOne];

            if (m == n && n == p) {
                for (int j = 0; j < sizeOne; j++) {
                    n1[j] = random.nextInt(minElement, maxElement + 1);
                    n2[j] = random.nextInt(minElement, maxElement + 1);
                }
            } else {
                final int sizeTwo = n * p;
                n2 = new double[sizeTwo];

                for (int j = 0; j < sizeOne; j++) {
                    n1[j] = random.nextInt(minElement, maxElement + 1);
                }

                for (int j = 0; j < sizeTwo; j++) {
                    n2[j] = random.nextInt(minElement, maxElement + 1);
                }
            }

            final Matrix2D a = new Matrix2D(n1, m, n);
            final Matrix2D b = new Matrix2D(n2, n, p);

            assert (a.classicMultiply(b).equals(a.coppersmithWinograd(b)));
        }
    }
}
