package samueltm.projetospessoais.matematica;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Objects;

public class Matriz2D {

    private final double[] matrizAchatada;
    private final int nLinhas, nColunas;
    private final int[] maiorNumeroDeCasasDecimaisEmCadaColuna;

    public Matriz2D(double[] matrizAchatada, int nLinhas, int nColunas) {
        boolean dentroDosLimites = nLinhas > 0 && nColunas > 0 && nLinhas <= matrizAchatada.length
                && nColunas <= matrizAchatada.length;
        boolean distribuivel = nLinhas * nColunas == matrizAchatada.length;
        if (dentroDosLimites && distribuivel) {
            this.matrizAchatada = matrizAchatada;
            this.nLinhas = nLinhas;
            this.nColunas = nColunas;
            maiorNumeroDeCasasDecimaisEmCadaColuna = new int[nColunas];
            for (int i = 0; i < matrizAchatada.length; i++) {
                int indiceColunaAtual = i % nColunas;
                int maiorAtual = maiorNumeroDeCasasDecimaisEmCadaColuna[indiceColunaAtual];
                int potencialMaior = String.valueOf(matrizAchatada[i]).length();
                maiorNumeroDeCasasDecimaisEmCadaColuna[indiceColunaAtual] = Math.max(potencialMaior,
                        maiorAtual);
            }
        } else {
            throw new IllegalArgumentException("Não dá pra montar uma matriz com os parâmetros fornecidos");
        }
    }

    public double getElemento(int indiceLinha, int indiceColuna) {
        return matrizAchatada[indiceLinha * nColunas + indiceColuna];
    }

    public Matriz2D somar(Matriz2D b) {
        if (nLinhas == b.nLinhas && nColunas == b.nColunas) {
            double[] numeros = new double[matrizAchatada.length];
            for (int i = 0; i < numeros.length; i++) {
                numeros[i] = matrizAchatada[i] + b.matrizAchatada[i];
            }
            return new Matriz2D(numeros, nLinhas, nColunas);
        } else {
            throw new IllegalArgumentException("Ambas matrizes devem ter o mesmo tamanho");
        }
    }

    public Matriz2D subtrair(Matriz2D b) {
        if (nLinhas == b.nLinhas && nColunas == b.nColunas) {
            double[] numeros = new double[matrizAchatada.length];
            for (int i = 0; i < numeros.length; i++) {
                numeros[i] = matrizAchatada[i] - b.matrizAchatada[i];
            }
            return new Matriz2D(numeros, nLinhas, nColunas);
        } else {
            throw new IllegalArgumentException("Ambas matrizes devem ter o mesmo tamanho");
        }
    }

    public Matriz2D hadamard(Matriz2D b) {
        if (nLinhas == b.nLinhas && nColunas == b.nColunas) {
            double[] numeros = new double[matrizAchatada.length];
            for (int i = 0; i < numeros.length; i++) {
                numeros[i] = matrizAchatada[i] * b.matrizAchatada[i];
            }
            return new Matriz2D(numeros, nLinhas, nColunas);
        } else {
            throw new IllegalArgumentException("Ambas matrizes devem ter o mesmo tamanho");
        }
    }

    public Matriz2D multiplicar(Matriz2D b) {
        int acessosMatrizUm = 0;
        int acessosMatrizDois = 0;
        int acessosMatrizTres = 0;
        BigInteger total = BigInteger.ZERO;
        if (nColunas == b.nLinhas) {
            double[] numeros = new double[nLinhas * b.nColunas];

            for (int i = 0; i < nLinhas; i++) {
                for (int j = 0; j < b.nColunas; j++) {
                    for (int k = 0; k < nColunas; k++) {
                        numeros[i * b.nColunas + j] += getElemento(i, k) * b.getElemento(k, j);
                        acessosMatrizUm++;
                        acessosMatrizDois++;
                        acessosMatrizTres++;
                    }
                }
            }
            total = total.add(BigInteger.valueOf(acessosMatrizUm)).add(BigInteger.valueOf(acessosMatrizUm))
                    .add(BigInteger.valueOf(acessosMatrizTres));
            System.out.println(acessosMatrizUm + "," + acessosMatrizDois + "," + acessosMatrizTres + "=" + total);
            return new Matriz2D(numeros, nLinhas, b.nColunas);
        } else {
            throw new IllegalArgumentException("Número de colunas de A deve ser igual ao número de linhas de B");
        }
    }

    public Matriz2D multiplicar2(Matriz2D b) {
        int acessosMatrizUm = 0;
        int acessosMatrizDois = 0;
        int acessosMatrizTres = 0;
        BigInteger total = BigInteger.ZERO;
        if (nColunas == b.nLinhas) {
            double[] numeros = new double[nLinhas * b.nColunas];
            for (int i = 0; i < numeros.length; i++) {
                int linha = i / b.nColunas;
                int coluna = i % b.nColunas;
                double soma = 0;
                for (int produtoVetorialAtual = 0; produtoVetorialAtual < b.nLinhas; produtoVetorialAtual++) {
                    acessosMatrizUm++;
                    acessosMatrizDois++;
                    soma += getElemento(linha, produtoVetorialAtual) * b.getElemento(produtoVetorialAtual, coluna);
                }
                numeros[i] = soma;
                acessosMatrizTres++;
            }
            total = total.add(BigInteger.valueOf(acessosMatrizUm)).add(BigInteger.valueOf(acessosMatrizUm))
                    .add(BigInteger.valueOf(acessosMatrizTres));
            System.out.println(acessosMatrizUm + "," + acessosMatrizDois + "," + acessosMatrizTres + "=" + total);

            return new Matriz2D(numeros, nLinhas, b.nColunas);
        } else {
            throw new IllegalArgumentException("Número de colunas de A deve ser igual ao número de linhas de B");
        }
    }

    public Matriz2D multiplicar(double escalar) {
        double[] numeros = new double[matrizAchatada.length];
        for (int i = 0; i < numeros.length; i++) {
            numeros[i] = matrizAchatada[i] * escalar;
        }
        return new Matriz2D(numeros, nLinhas, nColunas);
    }

    public Matriz2D transpor() {
        double[] numeros = new double[matrizAchatada.length];
        for (int i = 0; i < numeros.length; i++) {
            int linha = i / nLinhas;
            int coluna = i % nLinhas;
            numeros[i] = getElemento(coluna, linha);
        }

        return new Matriz2D(numeros, nColunas, nLinhas);
    }

    public static Matriz2D identidade(int n) {
        if (n > 0) {
            double[] numeros = new double[n * n];
            for (int i = 0; i < n; i++) {
                numeros[i * n + i] = 1;
            }

            return new Matriz2D(numeros, n, n);
        } else {
            throw new IllegalArgumentException("Não dá pra criar uma matriz identidade com o valor fornecido");
        }
    }

    public Matriz2D envolverComZeros(int nCamadas) {
        if (nCamadas > 0) {
            int novoNumeroDeLinhas = nLinhas + (2 * nCamadas);
            int novoNumeroDeColunas = nColunas + (2 * nCamadas);
            double[] numeros = new double[novoNumeroDeLinhas * novoNumeroDeColunas];
            for (int indiceLinha = nCamadas; indiceLinha < nLinhas + nCamadas; indiceLinha++) {
                for (int indiceColuna = nCamadas; indiceColuna < nColunas + nCamadas; indiceColuna++) {
                    int indiceLinhaAntigo = indiceLinha - nCamadas;
                    int indiceColunaAntigo = indiceColuna - nCamadas;
                    numeros[indiceLinha * novoNumeroDeColunas + indiceColuna] =
                            matrizAchatada[indiceLinhaAntigo * nColunas + indiceColunaAntigo];
                }
            }

            return new Matriz2D(numeros, novoNumeroDeLinhas, novoNumeroDeColunas);
        } else {
            throw new IllegalArgumentException("Número de camadas deve ser maior ou igual a 1");
        }
    }

    public Matriz2D getLinha(int indiceLinha) {
        if (indiceLinha >= 0 && indiceLinha < nLinhas) {
            double[] numeros = new double[nColunas];
            int indiceInicial = indiceLinha * nColunas;
            int contador = 0;
            for (int i = indiceInicial; i < indiceInicial + nColunas; i++) {
                numeros[contador] = matrizAchatada[i];
                contador++;
            }
            return new Matriz2D(numeros, 1, nColunas);
        } else {
            throw new IndexOutOfBoundsException("Índice da linha fora dos limites");
        }
    }

    public Matriz2D getColuna(int indiceColuna) {
        if (indiceColuna >= 0 && indiceColuna < nColunas) {
            double[] numeros = new double[nColunas];
            int indiceFinal = (indiceColuna + (nColunas * (nLinhas - 1))) + 1;
            int contador = 0;
            for (int i = indiceColuna; i < indiceFinal; i += nColunas) {
                numeros[contador] = matrizAchatada[i];
                contador++;
            }
            return new Matriz2D(numeros, nLinhas, 1);
        } else {
            throw new IndexOutOfBoundsException("Índice da coluna fora dos limites");
        }
    }

    public int[] getForma() {
        return new int[]{nLinhas, nColunas};
    }

    public Matriz2D achatar() {
        return new Matriz2D(matrizAchatada, 1, nLinhas * nColunas);
    }

    public Matriz2D reformar(int nLinhas, int nColunas) {
        return new Matriz2D(matrizAchatada, nLinhas, nColunas);
    }

    public Matriz2D copiar() {
        return new Matriz2D(matrizAchatada, nLinhas, nColunas);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Matriz2D m = (Matriz2D) o;
        return nLinhas == m.nLinhas && nColunas == m.nColunas
                && Arrays.equals(matrizAchatada, m.matrizAchatada)
                && Arrays.equals(maiorNumeroDeCasasDecimaisEmCadaColuna, m.maiorNumeroDeCasasDecimaisEmCadaColuna);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(nLinhas, nColunas);
        result = 31 * result + Arrays.hashCode(matrizAchatada);
        result = 31 * result + Arrays.hashCode(maiorNumeroDeCasasDecimaisEmCadaColuna);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < matrizAchatada.length; i++) {
            final int indiceLinhaAtual = i / nColunas;
            final int indiceColunaAtual = i % nColunas;
            final boolean deveImprimirColuna = nColunas < 20 || (indiceColunaAtual < 3
                    || indiceColunaAtual >= nColunas - 3);
            final boolean deveImprimirLinha = nLinhas < 20 || (indiceLinhaAtual < 3 || indiceLinhaAtual >= nLinhas - 3);
            if (deveImprimirLinha) {
                if (indiceColunaAtual == 0) {
                    if (indiceLinhaAtual > 0) {
                        sb.append(" ");
                    }
                    sb.append("[");
                }
                final double numero = matrizAchatada[i];

                if (deveImprimirColuna) {
                    final int numeroParaFormatar = maiorNumeroDeCasasDecimaisEmCadaColuna[indiceColunaAtual] + 1;
                    sb.append(String.format("%" + numeroParaFormatar + "s", numero));
                    if (indiceColunaAtual < nColunas - 1) {
                        sb.append(" ");
                    }
                } else if (indiceColunaAtual == 7) {
                    sb.append(" ... ");
                }

                if (indiceColunaAtual == nColunas - 1) {
                    sb.append("]");

                    if (indiceLinhaAtual < nLinhas - 1) {
                        sb.append("\n");
                    }
                }
            } else if (indiceLinhaAtual == 7 && indiceColunaAtual == 0) {
                sb.append(" ...\n");
            }
        }
        sb.append("]");

        return sb.toString();
    }


}