package samueltm.personalprojects.tests;

import samueltm.personalprojects.math.Matrix2D;
import samueltm.personalprojects.miscellaneous.Benchmarker;

import java.util.concurrent.ThreadLocalRandom;

public class MatrixTest {

    public static Matrix2D generateMatrix(int nRows, int nColumns) {
        double[] numbers = new double[nRows * nColumns];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = ThreadLocalRandom.current().nextInt(0, 11);
        }
        return new Matrix2D(numbers, nRows, nColumns);
    }

    private static Benchmarker testVanillaMultiplication(Matrix2D a, Matrix2D b) {
        return new Benchmarker() {
            @Override
            public void execute() {
                a.classicMultiply(b);
            }
        };
    }

    private static Benchmarker testImprovedMultiplication(Matrix2D a, Matrix2D b) {
        return new Benchmarker() {
            @Override
            public void execute() {
                a.multiply(b);
            }
        };
    }
    public static void testMultiplicationAlgorithms() {
        int m = 1000, n = 1000, p = 1000;
        int sampleSize = 1;
        System.out.println("Total elements: " + ((m * n) + (n * p)));
        Matrix2D a = generateMatrix(m, n);
        Matrix2D b = generateMatrix(n, p);
        testVanillaMultiplication(a, b).getAverageExecTimeNano("Normal multiplication", sampleSize);
        testImprovedMultiplication(a, b).getAverageExecTimeNano("Improved multiplication", sampleSize);
    }
}
