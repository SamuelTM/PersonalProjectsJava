package samueltm.projetospessoais.outros;

import samueltm.projetospessoais.matematica.MatematicaGeral;

import java.math.BigInteger;

public class GeradorCombinacoes {

    private final int[] a;
    private final int n;
    private final int r;
    private BigInteger numRestantes;
    private final BigInteger total;

    public GeradorCombinacoes(int n, int r) {
        if (r > n) {
            throw new IllegalArgumentException();
        }
        if (n < 1) {
            throw new IllegalArgumentException();
        }
        this.n = n;
        this.r = r;
        a = new int[r];
        BigInteger fatorialN = MatematicaGeral.fatorial(n);
        BigInteger fatorialR = MatematicaGeral.fatorial(r);
        BigInteger nMenosFatorial = MatematicaGeral.fatorial(n - r);
        total = fatorialN.divide(fatorialR.multiply(nMenosFatorial));
        redefinir();
    }

    private void redefinir() {
        for (int i = 0; i < a.length; i++) {
            a[i] = i;
        }
        numRestantes = new BigInteger(total.toString());
    }

    public boolean temProximo() {
        return numRestantes.compareTo(BigInteger.ZERO) > 0;
    }

    public BigInteger getTotal() {
        return total;
    }

    /**
     * Por que a gente não faz um método que retorna todas as combinações possíveis?
     * Porque o número de combinações possíveis pode ficar absurdamente grande,
     * então é melhor chamar uma combinação por vez, conforme necessário.
     */
    public int[] getProximo() {
        if (numRestantes.equals(total)) {
            numRestantes = numRestantes.subtract(BigInteger.ONE);
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

        numRestantes = numRestantes.subtract(BigInteger.ONE);
        return a;
    }
}