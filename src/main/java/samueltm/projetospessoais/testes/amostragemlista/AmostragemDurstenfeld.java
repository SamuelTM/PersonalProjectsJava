package samueltm.projetospessoais.testes.amostragemlista;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AmostragemDurstenfeld extends MetodoAmostragem{

    public AmostragemDurstenfeld(Integer[] elementos, int tamanhoAmostra, int numeroExecucoes) {
        super(elementos, tamanhoAmostra, numeroExecucoes);
    }

    @Override
    public List<Integer> amostrarElementos() {
        int tamanho = getElementos().length;
        for (int i = getElementos().length - 1; i >= getElementos().length - getTamanhoAmostra(); i--) {
            int temp = getElementos()[i];
            int indiceAleatorio = ThreadLocalRandom.current().nextInt(i + 1);
            getElementos()[i] = getElementos()[indiceAleatorio];
            getElementos()[indiceAleatorio] = temp;
        }

        return Arrays.stream(getElementos()).collect(Collectors.toList()).subList(
                getElementos().length - getTamanhoAmostra(), getElementos().length);
    }
}
