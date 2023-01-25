package samueltm.projetospessoais.outros;

public class Cronometro {

    private long tempoInicial;
    private long tempoPausado = -1;

    private boolean isPausado() {
        return tempoPausado > -1;
    }

    public void iniciar() {
        // se tá pausado, zera o tempo pausado
        if (isPausado()) {
            tempoPausado = -1;
        } else if (tempoInicial <= 0) {
            // senão, o cronômetro deve estar zerado pra poder iniciar
            tempoInicial = System.nanoTime();
        }
    }

    public void parar() {
        long tempoEmQueDeveParar = System.nanoTime();
        // se o cronômetro já não estiver parado
        if (tempoInicial > 0 && !isPausado())
            tempoPausado = tempoEmQueDeveParar;
    }

    public void zerar() {
        // se o cronômetro é zerado enquanto está pausado, zeramos o tempo pausado
        // e o tempo inicial. Se ele for zerado enquanto estiver correndo, zeramos
        // o tempo pausado porém o tempo inicial se torna o tempo atual
        tempoInicial = isPausado() ? 0 : System.nanoTime();
        tempoPausado = -1;
    }

    /**
     * Retorna o tempo total decorrido em nanosegundos
     */
    public long getTempoDecorrido() {
        if (tempoInicial > 0) {
            return isPausado() ? tempoPausado - tempoInicial : System.nanoTime() - tempoInicial;
        } else {
            return 0;
        }
    }

    public String getTempoDecorridoFormatado() {
        return Formatador.formatarNanosegundos(getTempoDecorrido());
    }
}