package samueltm.projetospessoais.outros;

import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;

/**
    Classe usada para retornar um objeto aleatório com base na probabilidade
    especificada de que ele seja escolhido.
 */
public class ColecaoPonderada<E> {

    private final NavigableMap<Double, E> elementos = new TreeMap<>();
    private double pesoTotal = 0;

    public ColecaoPonderada<E> adicionar(double peso, E elemento) {
        if (peso <= 0) return this;
        pesoTotal += peso;
        elementos.put(pesoTotal, elemento);
        return this;
    }

    public E proximo() {
        double valor = ThreadLocalRandom.current().nextDouble() * pesoTotal;
        return elementos.higherEntry(valor).getValue();
    }

    public int getNumElementos() {
        return elementos.size();
    }

}
