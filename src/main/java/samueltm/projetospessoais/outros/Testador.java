package samueltm.projetospessoais.outros;

/**
 * Classe para fazer pequenos benchmarks de funções
 */
public abstract class Testador {

    public abstract void executarTeste();

    /**
     * Executa o teste o número especificado de vezes pra conseguir
     * o tempo de execução médio
     * @param nomeTeste o nome que identifica o teste para conveniência
     * @param numVezes o número de vezes que o teste será executado pra tirar a média
     * @return o tempo de execução médio em nanosegundos
     */
    public final long executarMedia(String nomeTeste, int numVezes) {
        if (numVezes < 1)
            throw new IllegalArgumentException("Número de vezes deve ser maior que zero");
        System.out.println("Executando função: " + nomeTeste);
        long soma = 0;
        Cronometro c = new Cronometro();
        for (int i = 0; i < numVezes; i++) {
            c.iniciar();
            executarTeste();
            c.parar();
            soma += c.getTempoDecorridoNano();
            c.zerar();
        }
        soma = soma / numVezes;
        System.out.println("Função \"" + nomeTeste + "\" levou em média "
                + Formatador.formatarNanosegundos(soma));
        return soma;
    }

    public final long executarUmaVez(String nomeTeste) {
        return executarMedia(nomeTeste, 1);
    }
}
