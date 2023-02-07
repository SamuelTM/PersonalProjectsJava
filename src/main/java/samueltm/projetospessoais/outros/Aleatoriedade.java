package samueltm.projetospessoais.outros;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Aleatoriedade {

    public static <T> List<T> amostraPonderada(ColecaoPonderada<T> colecao, int n) {
        List<T> resultado = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            resultado.add(colecao.proximo());
        }

        return resultado;
    }

    public static <T> List<T> amostraSimples(List<T> elementos, int n) {
        if (elementos.size() < n)
            throw new IllegalArgumentException("Número de elementos menor que amostra desejada");
        if (n < 1) throw new IllegalArgumentException("Tamanho da amostra deve ser positivo");

        List<T> resultado = new ArrayList<>();

        for (int i = 0; i < elementos.size(); i++) {
            int quantosPrecisamos = n - resultado.size();
            int quantoAindaTemos = elementos.size() - i;
            double probabilidade = (double) quantosPrecisamos / quantoAindaTemos;
            if (ThreadLocalRandom.current().nextDouble() <= probabilidade) {
                resultado.add(elementos.get(i));
            }
        }

        return resultado;
    }

    public static <T> T elementoAleatorio(List<T> lista) {
        return lista.get(ThreadLocalRandom.current().nextInt(lista.size()));
    }

    public static <T> T elementoAleatorio(T[] lista) {
        return lista[ThreadLocalRandom.current().nextInt(lista.length)];
    }

}
