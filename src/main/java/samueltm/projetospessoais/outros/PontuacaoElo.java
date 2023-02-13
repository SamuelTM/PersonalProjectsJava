package samueltm.projetospessoais.outros;

public class PontuacaoElo {

    private static double probabilidade(double pontuacaoUm, double pontuacaoDois) {
        return 1.0 / (1 + Math.pow(10, (pontuacaoUm - pontuacaoDois) / 400));
    }

    public static double[] calcularPontuacao(double pontuacaoAtualUm, double pontuacaoAtualDois, int limiteVariacao,
                                             boolean primeiroGanhou) {
        double probabilidadeDoisGanhou = probabilidade(pontuacaoAtualUm, pontuacaoAtualDois);
        double probabilidadeUmGanhou = probabilidade(pontuacaoAtualDois, pontuacaoAtualUm);

        return primeiroGanhou ? new double[]{
                pontuacaoAtualUm + limiteVariacao * (1 - probabilidadeUmGanhou),
                pontuacaoAtualDois + limiteVariacao * (0 - probabilidadeDoisGanhou),
        } : new double[]{
                pontuacaoAtualUm + limiteVariacao * (0 - probabilidadeUmGanhou),
                pontuacaoAtualDois + limiteVariacao * (1 - probabilidadeDoisGanhou),
        };
    }
}
