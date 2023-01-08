package samueltm.projetospessoais.outros;

import samueltm.projetospessoais.outros.Cronometro;
import samueltm.projetospessoais.outros.Formatador;

/**
 * Classe para fazer pequenos benchmarks de funções
 */
public abstract class Testador {

    public abstract void executarTeste();

    public final void executarMedia(String nomeTeste, int numVezes) {
        if (numVezes < 1)
            throw new IllegalArgumentException("Número de vezes deve ser maior que zero");
        System.out.println("Executando função: " + nomeTeste);
        long soma = 0;
        Cronometro c = new Cronometro();
        for (int i = 0; i < numVezes; i++) {
            c.iniciar();
            executarTeste();
            c.parar();
            soma += c.getTempoDecorrido();
            c.zerar();
        }
        System.out.println("Função \"" + nomeTeste + "\" levou em média "
                + Formatador.formatarNanosegundos(soma / numVezes));
    }

    public final void executarUmaVez(String nomeTeste) {
        executarMedia(nomeTeste, 1);
    }
}
