package samueltm.projetospessoais.outros;

public class Cronometro {

    private long tempoInicial;
    private long tempoParado = -1;

    public void iniciar() {
        // se tá pausado, zera o tempo pausado
        if (tempoParado > -1) {
            tempoParado = -1;
        } else if (tempoInicial <= 0) {
            // senão, o cronômetro deve estar zerado pra poder iniciar
            tempoInicial = System.nanoTime();
        }
    }

    public void parar() {
        // se o cronômetro já não estiver parado
        if (tempoInicial > 0 && tempoParado <= -1) {
            tempoParado = System.nanoTime();
        }
    }

    public void zerar() {
        // se o cronômetro é zerado enquanto está pausado, zeramos o tempo pausado
        // e o tempo inicial. Se ele for zerado enquanto estiver correndo, zeramos
        // o tempo pausado porém o tempo inicial se torna o tempo atual
        tempoInicial = tempoParado > -1 ? 0 : System.nanoTime();
        tempoParado = -1;
    }

    public long getTempoDecorrido() {
        if (tempoInicial > 0) {
            return tempoParado > -1 ? tempoParado - tempoInicial : System.nanoTime() - tempoInicial;
        } else {
            return 0;
        }
    }

    public String getTempoDecorridoFormatado() {
        return Formatador.formatarNanosegundos(getTempoDecorrido());
    }
}