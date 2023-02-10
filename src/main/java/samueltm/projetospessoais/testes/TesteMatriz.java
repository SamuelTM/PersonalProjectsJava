package samueltm.projetospessoais.testes;

import samueltm.projetospessoais.matematica.Matriz2D;
import samueltm.projetospessoais.outros.Testador;

import java.util.concurrent.ThreadLocalRandom;

public class TesteMatriz {

    public static Matriz2D gerarMatriz(int nLinhas, int nColunas) {
        double[] numeros = new double[nLinhas * nColunas];
        for (int i = 0; i < numeros.length; i++) {
            numeros[i] = ThreadLocalRandom.current().nextInt(0, 11);
        }
        return new Matriz2D(numeros, nLinhas, nColunas);
    }

    private static Testador testarMultiplicacaoNormal(Matriz2D a, Matriz2D b) {
        return new Testador() {
            @Override
            public void executarTeste() {
                a.multiplicar(b);
            }
        };
    }

    private static Testador testarMultiplicacaoMelhorada(Matriz2D a, Matriz2D b) {
        return new Testador() {
            @Override
            public void executarTeste() {
                a.multiplicar2(b);
            }
        };
    }

    public static void testarAlgoritmosMultiplicacao() {
        int m1 = ThreadLocalRandom.current().nextInt(1000, 2000);
        int m2 = ThreadLocalRandom.current().nextInt(1000, 2000);
        System.out.println(m1 + "," + m2);
        int amostra = 10;
        Matriz2D a = gerarMatriz(m1, m2);
        Matriz2D b = gerarMatriz(m2, m2);
        testarMultiplicacaoNormal(a, b).executarMedia("Multiplicação normal", amostra);
        testarMultiplicacaoMelhorada(a, b).executarMedia("Multiplicação nova", amostra);
    }
}
