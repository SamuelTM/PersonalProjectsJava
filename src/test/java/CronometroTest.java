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
    public void testarSeCronometroPermaneceParado() {
        int milisegundos = ThreadLocalRandom.current().nextInt(1000, 2000);

        c.iniciar();
        esperar(milisegundos);
        c.parar();

        String tempoDecorrido = String.valueOf(c.getTempoDecorrido());
        c.zerar();

        assert (tempoDecorrido.startsWith(String.valueOf(milisegundos).substring(0, 3)));
    }

    @Test
    public void testarSeCronometroPermaneceZerado() {
        int milisegundos = ThreadLocalRandom.current().nextInt(1000, 2000);

        c.iniciar();
        esperar(milisegundos);
        c.parar();
        c.zerar();

        esperar(milisegundos);

        assert (c.getTempoDecorrido() == 0);
    }

    @Test
    public void testarIniciarZerarCronometro() {
        for (int i = 0; i < 5; i++) {
            int tempoPrimeiraEspera = ThreadLocalRandom.current().nextInt(1000, 2000);
            int tempoSegundaEspera = ThreadLocalRandom.current().nextInt(1000, 2000);

            c.iniciar();
            esperar(tempoPrimeiraEspera);
            c.zerar();

            esperar(tempoSegundaEspera);
            c.parar();

            double tempoDecorridoMilisegundos = c.getTempoDecorrido() / 1e6;
            c.zerar();

            double atualMenosEsperado = Math.abs(tempoDecorridoMilisegundos - (long) tempoSegundaEspera);

            System.out.println("Era pra passar " + tempoSegundaEspera + "ms e se passaram "
                    + tempoDecorridoMilisegundos);
            System.out.println("Diferença de " + atualMenosEsperado + "ms");
            System.out.println("-----------------");

            assert (atualMenosEsperado <= 15);
        }
    }
}
