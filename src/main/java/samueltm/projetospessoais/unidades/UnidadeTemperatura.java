package samueltm.projetospessoais.unidades;

import samueltm.projetospessoais.matematica.MatematicaGeral;

public enum UnidadeTemperatura {

    CELSIUS,
    KELVIN,
    FAHRENHEIT,
    RANKINE,
    ROMER,
    NEWTON,
    REAUMUR,
    DELISLE;

    public double converterPara(double valorAtual, UnidadeTemperatura unidadeDesejada) {
        double paraCelsius = 0;
        switch(this) {
            case CELSIUS:
                paraCelsius = valorAtual;
                break;
            case KELVIN:
                paraCelsius = valorAtual - 273.15;
                break;
            case FAHRENHEIT:
                paraCelsius = (valorAtual - 32) * 5.0 / 9.0;
                break;
            case RANKINE:
                paraCelsius = (valorAtual - 491.67) * 5.0 / 9.0;
                break;
            case ROMER:
                paraCelsius = (valorAtual - 7.5) * 40.0 / 21.0;
                break;
            case NEWTON:
                paraCelsius = valorAtual / 0.33;
                break;
            case REAUMUR:
                paraCelsius = valorAtual / 0.8;
                break;
            case DELISLE:
                paraCelsius = 100 - (valorAtual * 2.0 / 3.0);
                break;
        }

        double paraUnidade = 0;
        switch(unidadeDesejada) {
            case CELSIUS:
                paraUnidade = paraCelsius;
                break;
            case KELVIN:
                paraUnidade = paraCelsius + 273.15;
                break;
            case FAHRENHEIT:
                paraUnidade = (paraCelsius * 1.8) + 32;
                break;
            case RANKINE:
                paraUnidade = (paraCelsius * 1.8) + 491.67;
                break;
            case ROMER:
                paraUnidade = (paraCelsius * 0.525) + 7.5;
                break;
            case NEWTON:
                paraUnidade = paraCelsius * 0.33;
                break;
            case REAUMUR:
                paraUnidade = paraCelsius * 0.8;
                break;
            case DELISLE:
                paraUnidade = (100 - paraCelsius) * 1.5;
                break;
        }

        return MatematicaGeral.arredondar(paraUnidade, 3);
    }
}
