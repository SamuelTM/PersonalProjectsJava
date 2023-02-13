package samueltm.projetospessoais.outros;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class ClassificadorSubjetivo {

    private final HashMap<Integer, Double> listaItens;
    private final Iterator<Integer[]> disputas;

    public ClassificadorSubjetivo(int numeroItens) {
        this.listaItens = new HashMap<>();

        ArrayList<Integer[]> listaDisputas = new ArrayList<>();
        for (int i = 0; i < numeroItens; i++) {
            if (listaItens.containsKey(i)) throw new RuntimeException("Os itens da lista devem ser únicos");
            listaItens.put(i, 1000.0);

            for (int j = i + 1; j < numeroItens; j++) {
                listaDisputas.add(new Integer[]{i, j});
            }
        }

        this.disputas = listaDisputas.iterator();
    }

    public Integer[] getIndicesProximaDisputa() {
        if (disputas.hasNext()) {
            return disputas.next();
        } else {
            return new Integer[]{};
        }
    }

    public void votar(Integer[] indicesOponentes, boolean primeiraOpcao) {
        if (indicesOponentes.length != 2) throw new IllegalArgumentException("Quantidade de oponentes inválida");
        for(int indiceOponente : indicesOponentes) {
            if (indiceOponente < 0 || indiceOponente >= listaItens.size())
                throw new IllegalArgumentException("Índices dos oponentes inválidos");
        }
        double[] novasPontuacoes = PontuacaoElo.calcularPontuacao(listaItens.get(indicesOponentes[0]),
                listaItens.get(indicesOponentes[1]), 30, primeiraOpcao);

        listaItens.put(indicesOponentes[0], novasPontuacoes[0]);
        listaItens.put(indicesOponentes[1], novasPontuacoes[1]);
    }

    public HashMap<Integer, Double> getListaItens() {
        return listaItens;
    }
}
