package samueltm.projetospessoais.outros;

public class Formatador {

    public static String formatarNanosegundos(long nanosegundos) {
        if (nanosegundos >= 1e9) {
            return (nanosegundos / 1e9) + "s";
        } else if (nanosegundos >= 1e6) {
            return (nanosegundos / 1e6) + "ms";
        } else {
            return nanosegundos + "ns";
        }
    }
}
