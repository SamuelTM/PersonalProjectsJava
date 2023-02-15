package samueltm.projetospessoais.testes;

import samueltm.projetospessoais.outros.ClassificadorSubjetivo;

import java.util.*;

public class TesteClassificadorSubjetivo {

    public static void classificar(String[] oponentes) {
        Scanner scanner = new Scanner(System.in);
        ClassificadorSubjetivo cs = new ClassificadorSubjetivo(oponentes.length);
        Integer[] proximaDisputa = cs.getIndicesProximaDisputa();
        while (true) {
            if (proximaDisputa.length > 0) {
                System.out.println(oponentes[proximaDisputa[0]] + " OU " + oponentes[proximaDisputa[1]]);
                System.out.println("Digite 1 ou 2 para escolher...");
                int resposta = scanner.nextInt();
                if (resposta != 1 && resposta != 2) {
                    System.out.println("Opção inválida, tente novamente");
                } else {
                    System.out.println("Você escolheu " + oponentes[proximaDisputa[resposta - 1]]);
                    cs.votar(proximaDisputa, resposta == 1);
                    proximaDisputa = cs.getIndicesProximaDisputa();
                }
                System.out.println("---------------------------");
            } else {
                break;
            }
        }
        System.out.println("---------------------------");

        List<Integer> itensOrdenados = new ArrayList<>(cs.getListaItens().keySet().stream().toList());
        itensOrdenados.sort(Comparator.comparing(o -> cs.getListaItens().get(o)));
        Collections.reverse(itensOrdenados);

        for (int i = 0; i < itensOrdenados.size(); i++) {
            Integer item = itensOrdenados.get(i);
            System.out.println((i + 1) + " - " + oponentes[item] + " - " + cs.getListaItens().get(item));
        }
    }
}
