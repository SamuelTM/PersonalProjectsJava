package samueltm.projetospessoais.testes.amostragemlista;

import java.util.HashMap;
import java.util.List;

public abstract class MetodoAmostragem {

    private final Integer[] elementos;
    private final int tamanhoAmostra;

    private final int numeroExecucoes;

    public MetodoAmostragem(Integer[] elementos, int tamanhoAmostra, int numeroExecucoes) {
        this.elementos = elementos;
        this.tamanhoAmostra = tamanhoAmostra;
        this.numeroExecucoes = numeroExecucoes;
    }

    public Integer[] getElementos() {
        return elementos;
    }

    public int getTamanhoAmostra() {
        return tamanhoAmostra;
    }

    public abstract List<Integer> amostrarElementos();

    public HashMap<Integer, Integer> contarOcorrenciasElementos() {
        HashMap<Integer, Integer> resultado = new HashMap<>();
        for (int i = 0; i < numeroExecucoes; i++) {
            List<Integer> amostra = amostrarElementos();
            for (int numero : amostra) {
                if (!resultado.containsKey(numero)) {
                    resultado.put(numero, 1);
                } else {
                    resultado.put(numero, resultado.get(numero) + 1);
                }
            }
        }
        return resultado;
    }
}
