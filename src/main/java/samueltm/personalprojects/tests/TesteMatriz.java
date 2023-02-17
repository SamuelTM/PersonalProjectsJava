package samueltm.personalprojects.tests;

import samueltm.personalprojects.math.Matrix2D;
import samueltm.personalprojects.miscellaneous.Benchmarker;

import java.util.concurrent.ThreadLocalRandom;

public class TesteMatriz {

    public static Matrix2D gerarMatriz(int nLinhas, int nColunas) {
        double[] numeros = new double[nLinhas * nColunas];
        for (int i = 0; i < numeros.length; i++) {
            numeros[i] = ThreadLocalRandom.current().nextInt(0, 11);
        }
        return new Matrix2D(numeros, nLinhas, nColunas);
    }

    private static Benchmarker testarMultiplicacaoNormal(Matrix2D a, Matrix2D b) {
        return new Benchmarker() {
            @Override
            public void execute() {
                a.multiply(b);
            }
        };
    }

    private static Benchmarker testarMultiplicacaoMelhorada(Matrix2D a, Matrix2D b) {
        return new Benchmarker() {
            @Override
            public void execute() {
                a.multiply2(b);
            }
        };
    }

    public static void testarAlgoritmosMultiplicacao() {
        int m1 = ThreadLocalRandom.current().nextInt(1000, 2000);
        int m2 = ThreadLocalRandom.current().nextInt(1000, 2000);
        System.out.println(m1 + "," + m2);
        int amostra = 10;
        Matrix2D a = gerarMatriz(m1, m2);
        Matrix2D b = gerarMatriz(m2, m2);
        testarMultiplicacaoNormal(a, b).getAverageExecTimeNano("Multiplicação normal", amostra);
        testarMultiplicacaoMelhorada(a, b).getAverageExecTimeNano("Multiplicação nova", amostra);
    }
}
