package samueltm.projetospessoais.unidades;

public enum UnidadeComprimento {
    PLANCK(1.61605e-35),
    FEMTOMETRO(1e-15),
    PICOMETRO(1e-12),
    NANOMETRO(1e-9),
    MICROMETRO(1e-6),
    MILIMETRO(0.001),
    CENTIMETRO(0.01),
    METRO(1),
    QUILOMETRO(1000),
    ANGSTROM(1e10),
    ANO_LUZ( 9.46073e15),
    MILHA(1609.34),
    JARDA(0.9144),
    PE(0.3048),
    POLEGADA(0.0254);

    private final double metroEquivalente;

    UnidadeComprimento(double metroEquivalente) {
        this.metroEquivalente = metroEquivalente;
    }

    public double converterPara(double tamanhoAtual, UnidadeComprimento unidadeDesejada) {
        double paraMetros = tamanhoAtual * metroEquivalente;
        return paraMetros / unidadeDesejada.metroEquivalente;
    }
}