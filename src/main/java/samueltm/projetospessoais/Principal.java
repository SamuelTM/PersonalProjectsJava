package samueltm.projetospessoais;

public class Principal {

    public static void main(String[] args) {
        double a = 3, b = 4, c = 5, d = 6;
        double desejado = (a * b) + (c * d);
        double atual = 0;
        System.out.println(desejado);
        atual = a;
        atual *= b;
        System.out.println(atual);
    }
}
