package samueltm.projetospessoais.unidades;

public enum UnidadePressao {

    PASCAL(1),
    HECTOPASCAL(100),
    QUILOPASCAL(1000),
    BAR(100000),
    ATMOSFERA_TECNICA(98066.5),
    ATMOSFERA(101325),
    TORR(133.322),
    LIBRA_POR_POLEGADA_QUADRADA(6894.757),
    METRO_DE_COLUNA_DAGUA(9806.38),
    METRO_DE_AGUA_DO_MAR(10000);

    private final double pascalEquivalente;

    UnidadePressao(double pascalEquivalente) {
        this.pascalEquivalente = pascalEquivalente;
    }

    public double converterPara(double valorAtual, UnidadePressao unidadeDesejada) {
        double paraPascais = valorAtual * pascalEquivalente;
        return paraPascais / unidadeDesejada.pascalEquivalente;
    }
}
