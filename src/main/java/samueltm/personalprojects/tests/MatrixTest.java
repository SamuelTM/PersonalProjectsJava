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
                a.multiply(b);
            }
        };
    }

    private static Benchmarker testImprovedMultiplication(Matrix2D a, Matrix2D b) {
        return new Benchmarker() {
            @Override
            public void execute() {
                a.multiply2(b);
            }
        };
    }

    public static void testMultiplicationAlgorithms() {
        int m1 = ThreadLocalRandom.current().nextInt(1000, 2000);
        int m2 = ThreadLocalRandom.current().nextInt(1000, 2000);
        System.out.println(m1 + "," + m2);
        int sampleSize = 100;
        Matrix2D a = generateMatrix(m1, m2);
        Matrix2D b = generateMatrix(m2, m2);
        testVanillaMultiplication(a, b).getAverageExecTimeNano("Normal multiplication", sampleSize);
        testImprovedMultiplication(a, b).getAverageExecTimeNano("Improved multiplication", sampleSize);
    }
}
