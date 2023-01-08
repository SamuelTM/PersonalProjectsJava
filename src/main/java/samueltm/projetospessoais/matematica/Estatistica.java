package samueltm.projetospessoais.matematica;

public class Estatistica {

    /**
     * Normaliza o valor especificado para um valor compreendido entre 0 e 1
     */
    public static double normalizar(double valor, double limiteSuperior, double limiteInferior) {
        return (valor - limiteInferior) / (limiteSuperior - limiteInferior);
    }

    public static double mediaAritmetica(double[] numeros) {
        double soma = 0;
        for (double n : numeros)
            soma += n;
        return soma / numeros.length;
    }

    public static double mediaGeometrica(double[] numeros) {
        double produto = 1;
        for (double n : numeros) {
            if (n > 0) {
                produto *= n;
            } else {
                throw new IllegalArgumentException("Não dá pra calcular a média geométrica de números não-positivos");
            }
        }
        return MatematicaGeral.enesimaRaiz(produto, numeros.length);
    }

}