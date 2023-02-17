package samueltm.personalprojects.units;

import samueltm.personalprojects.math.GeneralMath;

public enum TemperatureUnit {

    CELSIUS,
    KELVIN,
    FAHRENHEIT,
    RANKINE,
    ROMER,
    NEWTON,
    REAUMUR,
    DELISLE;

    public double convert(double currentValue, TemperatureUnit desiredUnit) {
        double toCelsius = 0;
        switch(this) {
            case CELSIUS:
                toCelsius = currentValue;
                break;
            case KELVIN:
                toCelsius = currentValue - 273.15;
                break;
            case FAHRENHEIT:
                toCelsius = (currentValue - 32) * 5.0 / 9.0;
                break;
            case RANKINE:
                toCelsius = (currentValue - 491.67) * 5.0 / 9.0;
                break;
            case ROMER:
                toCelsius = (currentValue - 7.5) * 40.0 / 21.0;
                break;
            case NEWTON:
                toCelsius = currentValue / 0.33;
                break;
            case REAUMUR:
                toCelsius = currentValue / 0.8;
                break;
            case DELISLE:
                toCelsius = 100 - (currentValue * 2.0 / 3.0);
                break;
        }

        double toDesiredUnit = 0;
        switch(desiredUnit) {
            case CELSIUS:
                toDesiredUnit = toCelsius;
                break;
            case KELVIN:
                toDesiredUnit = toCelsius + 273.15;
                break;
            case FAHRENHEIT:
                toDesiredUnit = (toCelsius * 1.8) + 32;
                break;
            case RANKINE:
                toDesiredUnit = (toCelsius * 1.8) + 491.67;
                break;
            case ROMER:
                toDesiredUnit = (toCelsius * 0.525) + 7.5;
                break;
            case NEWTON:
                toDesiredUnit = toCelsius * 0.33;
                break;
            case REAUMUR:
                toDesiredUnit = toCelsius * 0.8;
                break;
            case DELISLE:
                toDesiredUnit = (100 - toCelsius) * 1.5;
                break;
        }

        return GeneralMath.round(toDesiredUnit, 3);
    }
}
