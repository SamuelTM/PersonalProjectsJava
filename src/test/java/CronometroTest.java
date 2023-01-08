import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import samueltm.projetospessoais.outros.Cronometro;

import java.util.concurrent.ThreadLocalRandom;

public class CronometroTest {

    private static Cronometro c;

    @BeforeAll
    static void configurar() {
        c = new Cronometro();
    }

    private void esperar(long milisegundos) {
        try {
            Thread.sleep(milisegundos);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testarPararCronometro() {
        c.iniciar();

        int milisegundos = ThreadLocalRandom.current().nextInt(1000, 2000);
        esperar(milisegundos);
        c.parar();

        String tempoDecorrido = String.valueOf(c.getTempoDecorrido());
        c.zerar();

        assert (tempoDecorrido.startsWith(String.valueOf(milisegundos).substring(0, 3)));
    }

    @Test
    public void testarIniciarZerarCronometro() {
        boolean passou = true;
        for (int i = 0; i < 100; i++) {
            c.iniciar();

            int milisegundos = ThreadLocalRandom.current().nextInt(1000, 2000);
            esperar(milisegundos);
            c.zerar();

            milisegundos = ThreadLocalRandom.current().nextInt(1000, 2000);
            esperar(milisegundos);
            c.parar();

            long tempoDecorrido = c.getTempoDecorrido();
            c.zerar();

            passou = Math.abs(tempoDecorrido - (long) milisegundos + 1e6) <= 2e6;
            System.out.println(passou);

            if (!passou) {
                System.out.println(milisegundos);
                System.out.println(tempoDecorrido);
                break;
            }
        }
        assert(passou);
    }
}
