package samueltm.projetospessoais.outros;

import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;

/**
    Classe usada para retornar um objeto aleatório com base na probabilidade
    especificada de que ele seja escolhido.
 */
public class ColecaoPonderada<E> {

    private final NavigableMap<Double, E> map = new TreeMap<>();
    private double total = 0;

    public ColecaoPonderada<E> adicionar(double peso, E resultado) {
        if (peso <= 0) return this;
        total += peso;
        map.put(total, resultado);
        return this;
    }

    public E proximo() {
        double valor = ThreadLocalRandom.current().nextDouble() * total;
        return map.higherEntry(valor).getValue();
    }
}
