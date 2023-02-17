import org.junit.jupiter.api.Test;
import samueltm.personalprojects.math.Matrix2D;

import java.util.concurrent.ThreadLocalRandom;

public class MatrixTest {

    @Test
    public void testarMultiplicacao2() {
        final int dimensaoMinima = 2, dimensaoMaxima = 100;
        final int elementoMinimo = 1, elementoMaximo = 11;
        for (int i = 0; i < 100; i++) {
            final int m = ThreadLocalRandom.current().nextInt(dimensaoMinima, dimensaoMaxima + 1);
            final int n = ThreadLocalRandom.current().nextInt(dimensaoMinima, dimensaoMaxima + 1);
            final int p = ThreadLocalRandom.current().nextInt(dimensaoMinima, dimensaoMaxima + 1);

            final int tamanhoUm = m * n;

            final double[] n1 = new double[tamanhoUm];
            double[] n2 = new double[tamanhoUm];

            if (m == n && n == p) {
                for (int j = 0; j < tamanhoUm; j++) {
                    n1[j] = ThreadLocalRandom.current().nextInt(elementoMinimo, elementoMaximo + 1);
                    n2[j] = ThreadLocalRandom.current().nextInt(elementoMinimo, elementoMaximo + 1);
                }
            } else {
                final int tamanhoDois = n * p;
                n2 = new double[tamanhoDois];

                for (int j = 0; j < tamanhoUm; j++) {
                    n1[j] = ThreadLocalRandom.current().nextInt(elementoMinimo, elementoMaximo + 1);
                }

                for (int j = 0; j < tamanhoDois; j++) {
                    n2[j] = ThreadLocalRandom.current().nextInt(elementoMinimo, elementoMaximo + 1);
                }
            }

            final Matrix2D a = new Matrix2D(n1, m, n);
            final Matrix2D b = new Matrix2D(n2, n, p);

            assert (a.multiply(b).equals(a.multiply2(b)));
        }
    }
}
