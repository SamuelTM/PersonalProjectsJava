package samueltm.projetospessoais.testes;

import samueltm.projetospessoais.outros.Testador;
import samueltm.projetospessoais.testes.amostragemlista.*;

import java.util.HashMap;
import java.util.stream.IntStream;

public class TesteAmostragemLista {

    private final Integer[] elementos;
    private final int tamanhoAmostra;
    private final int numeroExecucoes;

    public TesteAmostragemLista(int numElementos, int tamanhoAmostra, int numeroExecucoes) {
        this.elementos = IntStream.range(0, numElementos).boxed().toArray(Integer[]::new);
        this.tamanhoAmostra = tamanhoAmostra;
        this.numeroExecucoes = numeroExecucoes;
    }

    private Testador testarAlgoritmo(MetodoAmostragem metodoAmostragem) {
        return new Testador() {
            @Override
            public void executarTeste() {
                HashMap<Integer, Integer> ocorrencias = metodoAmostragem.contarOcorrenciasElementos();
                double somaOcorrencis = (double) ocorrencias.values().stream().mapToInt(Integer::valueOf).sum();
                double desvioPadrao = 0;
                for (int ocorrencia : ocorrencias.values()) {
                    // tamanho da amostra é igual à média neste caso
                    desvioPadrao += Math.pow(ocorrencia - tamanhoAmostra, 2);
                }
                desvioPadrao = Math.sqrt(desvioPadrao);
                System.out.println("Desvio padrão das ocorrências é: " + desvioPadrao);
            }
        };
    }

    public void testarAlgoritmos() {
        MetodoAmostragem amostragemEmbaralharLista = new AmostragemEmbaralharLista(elementos, tamanhoAmostra,
                numeroExecucoes);
        MetodoAmostragem amostragemDurstenfeld = new AmostragemDurstenfeld(elementos, tamanhoAmostra,
                numeroExecucoes);
        MetodoAmostragem amostragemReservatorio = new AmostragemReservatorio(elementos, tamanhoAmostra,
                numeroExecucoes);
        MetodoAmostragem amostragemExperimental = new AmostragemExperimental(
                elementos, tamanhoAmostra, numeroExecucoes);

        testarAlgoritmo(amostragemEmbaralharLista).executarUmaVez("Amostragem de embaralhar lista");
        System.out.println();
        testarAlgoritmo(amostragemDurstenfeld).executarUmaVez("Amostragem de Durstenfeld");
        System.out.println();
        testarAlgoritmo(amostragemReservatorio).executarUmaVez("Amostragem do reservatório");
        System.out.println();
        testarAlgoritmo(amostragemExperimental).executarUmaVez("Amostragem experimental");
    }
}
