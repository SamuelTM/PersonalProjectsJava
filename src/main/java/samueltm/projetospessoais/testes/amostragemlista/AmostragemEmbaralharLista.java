package samueltm.projetospessoais.testes.amostragemlista;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class AmostragemEmbaralharLista extends MetodoAmostragem{


    public AmostragemEmbaralharLista(Integer[] elementos, int tamanhoAmostra, int numeroExecucoes) {
        super(elementos, tamanhoAmostra, numeroExecucoes);
    }

    @Override
    public List<Integer> amostrarElementos() {
        List<Integer> indicesDisponiveis = new ArrayList<>(IntStream.range(0, getElementos().length).boxed().toList());
        Collections.shuffle(indicesDisponiveis);
        List<Integer> resultado = new ArrayList<>();
        for (int i = 0; i < getTamanhoAmostra(); i++) {
            resultado.add(getElementos()[indicesDisponiveis.get(i)]);
        }
        return resultado;
    }
}
