package samueltm.projetospessoais.outros;

public enum UnidadeTamanho {
    NANOMETRO(1e-9),
    MICROMETRO(1e-6),
    MILIMETRO(0.001),
    CENTIMETRO(0.01),
    METRO(1),
    QUILOMETRO(1000),
    MILHA(1609.34),
    JARDA(0.9144),
    PE(0.3048),
    POLEGADA(0.0254);

    private final double equivalenteEmMetro;

    UnidadeTamanho(double equivalenteEmMetro) {
        this.equivalenteEmMetro = equivalenteEmMetro;
    }

    public double converterPara(double tamanhoAtual, UnidadeTamanho unidadeDesejada) {
        double aEmMetros = tamanhoAtual * equivalenteEmMetro;
        return aEmMetros / unidadeDesejada.equivalenteEmMetro;
    }
}