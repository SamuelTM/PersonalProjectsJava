package samueltm.projetospessoais.unidades;

public enum UnidadeVazaoVolumetrica {

    METROS_CUBICOS_POR_HORA(1),
    METROS_CUBICOS_POR_SEGUNDO(3600),
    PES_CUBICOS_POR_MINUTO(1.69901082);

    private final double m3PorHoraEquivalente;

    UnidadeVazaoVolumetrica(double m3PorHoraEquivalente) {
        this.m3PorHoraEquivalente = m3PorHoraEquivalente;
    }

    public double converterPara(double valorAtual, UnidadeVazaoVolumetrica unidadeDesejada) {
        double paraMetrosCubicosPorHora = valorAtual * m3PorHoraEquivalente;
        return paraMetrosCubicosPorHora / unidadeDesejada.m3PorHoraEquivalente;
    }
}
