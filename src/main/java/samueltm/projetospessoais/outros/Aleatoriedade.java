package samueltm.projetospessoais.outros;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class Aleatoriedade {

    public static <T> List<T> amostraPonderada(ColecaoAleatoria<T> colecao, int n) {
        List<T> resultado = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            resultado.add(colecao.proximo());
        }

        return resultado;
    }

    public static <T> List<T> amostraSimples(T[] elementos, int n) {
        if (elementos.length < n)
            throw new IllegalArgumentException("Número de elementos menor que amostra desejada");
        List<T> resultado = new ArrayList<>();

        List<Integer> indicesDisponiveis = new ArrayList<>(IntStream.range(0, elementos.length).boxed().toList());
        Collections.shuffle(indicesDisponiveis);
        for (int i = 0; i < n; i++) {
            resultado.add(elementos[indicesDisponiveis.get(i)]);
        }

        return resultado;
    }

    public static <T> T elementoAleatorio(List<T> lista) {
        return lista.get(ThreadLocalRandom.current().nextInt(lista.size()));
    }

}
