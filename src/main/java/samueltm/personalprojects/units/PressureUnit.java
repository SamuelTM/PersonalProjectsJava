package samueltm.personalprojects.units;

public enum PressureUnit {

    PASCAL(1),
    HECTOPASCAL(100),
    KILOPASCAL(1000),
    BAR(100000),
    TECHNICAL_ATMOSPHERE(98066.5),
    ATMOSPHERE(101325),
    TORR(133.322),
    POUNDS_SQUARE_INCH(6894.757),
    WATER_COLUMN_METER(9806.38),
    SEA_WATER_METER(10000);

    private final double pascalEquivalent;

    PressureUnit(double pascalEquivalent) {
        this.pascalEquivalent = pascalEquivalent;
    }

    public double convert(double currentValue, PressureUnit desiredUnit) {
        double toPascals = currentValue * pascalEquivalent;
        return toPascals / desiredUnit.pascalEquivalent;
    }
}
