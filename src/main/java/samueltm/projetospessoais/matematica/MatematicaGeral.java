package samueltm.projetospessoais.matematica;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class MatematicaGeral {

    public static BigInteger fatorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException();
        }
        BigInteger fatorial = BigInteger.ONE;
        for (int i = n; i > 1; i--) {
            fatorial = fatorial.multiply(new BigInteger(Integer.toString(i)));
        }
        return fatorial;
    }

    public static int arredondarParaMultiploMaisProximo(double numero, int multiplo) {
        return (int) (Math.round(numero / multiplo) * multiplo);
    }

    public static int conjecturaCollatz(int n) {
        int contador = 0;
        while (n > 1) {
            if (n % 2 == 0) {
                n /= 2;
            } else {
                n = (3 * n) + 1;
            }
            contador++;
        }
        return contador;
    }

    public static double enesimaRaiz(double base, double raiz) {
        return Math.pow(base, 1.0 / raiz);
    }

    public static double produtoEscalar(double[] a, double[] b) {
        if (a.length == b.length) {
            double soma = 0;
            for (int i = 0; i < a.length; i++) {
                soma += a[i] * b[i];
            }

            return soma;
        } else {
            throw new IllegalArgumentException("Ambos vetores devem ter o mesmo tamanho");
        }
    }

    public static List<Integer> fatores(int n) {
        final List<Integer> fatores = new ArrayList<>();
        if (n > 0) {
            for (int i = 1; i <= n; i++) {
                if (n % i == 0) {
                    fatores.add(i);
                }
            }
        } else {
            for (int i = n; i <= Math.abs(n); i++) {
                if (i != 0 && n % i == 0) {
                    fatores.add(i);
                }
            }
        }
        return fatores;
    }

    private static int maximoDivisorComum(int a, int b) {
        return b == 0 ? a : maximoDivisorComum(b, a % b);
    }

    private static int minimoMultiploComum(int a, int b) {
        return a * (b / maximoDivisorComum(a,b));
    }

    public static int maximoDivisorComum(int... numeros) {
        int resultado = numeros[0];
        for(int i = 1; i < numeros.length; i++) {
            resultado = maximoDivisorComum(resultado, numeros[i]);
        }
        return resultado;
    }

    public static int minimoMultiploComum(int... numeros) {
        int resultado = numeros[0];
        for(int i = 1; i < numeros.length; i++) {
            resultado = minimoMultiploComum(resultado, numeros[i]);
        }
        return resultado;
    }
}
