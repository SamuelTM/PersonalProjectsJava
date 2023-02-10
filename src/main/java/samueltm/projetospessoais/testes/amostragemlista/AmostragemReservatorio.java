package samueltm.projetospessoais.testes.amostragemlista;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class AmostragemReservatorio extends MetodoAmostragem {

    public AmostragemReservatorio(Integer[] elementos, int tamanhoAmostra, int numeroExecucoes) {
        super(elementos, tamanhoAmostra, numeroExecucoes);
    }

    @Override
    public List<Integer> amostrarElementos() {
        List<Integer> resultado = new ArrayList<>();
        for (int i = 0; i < getElementos().length; i++) {
            if (resultado.size() < getTamanhoAmostra()) {
                resultado.add(getElementos()[i]);
            } else {
                int indiceAleatorio = ThreadLocalRandom.current().nextInt(i + 1);
                if (indiceAleatorio < getTamanhoAmostra()) {
                    resultado.set(indiceAleatorio, getElementos()[i]);
                }
            }
        }
        return resultado;
    }
}
