package samueltm.projetospessoais.testes.amostragemlista;

import samueltm.projetospessoais.outros.Aleatoriedade;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class AmostragemExperimental extends MetodoAmostragem{

    public AmostragemExperimental(Integer[] elementos, int tamanhoAmostra, int numeroExecucoes) {
        super(elementos, tamanhoAmostra, numeroExecucoes);
    }

    @Override
    public List<Integer> amostrarElementos() {
        List<Integer> resultado = new ArrayList<>();
        for (int i = 0; i < getElementos().length; i++) {
            int quantosPrecisamos = getTamanhoAmostra() - resultado.size();
            int quantoAindaTemos = getElementos().length - i;
            double probabilidade = (double) quantosPrecisamos / quantoAindaTemos;
            if (ThreadLocalRandom.current().nextDouble() <= probabilidade) {
                resultado.add(getElementos()[i]);
            }
        }

        return resultado;
    }
}
