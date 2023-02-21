package samueltm.personalprojects.units;

public enum LengthUnit {
    PLANCK(1.6161e-35),
    FEMTOMETER(1e-15),
    PICOMETER(1e-12),
    NANOMETER(1e-9),
    MICROMETER(1e-6),
    MILLIMETER(0.001),
    CENTIMETER(0.01),
    METER(1),
    KILOMETER(1000),
    ANGSTROM(1e10),
    LIGHT_YEAR( 9.4607e15),
    MILE(1609.34),
    YARD(0.9144),
    FOOT(0.3048),
    INCH(0.0254);

    private final double meterEquivalent;

    LengthUnit(double meterEquivalent) {
        this.meterEquivalent = meterEquivalent;
    }

    public double convert(double currentValue, LengthUnit desiredUnit) {
        double toMeters = currentValue * meterEquivalent;
        return toMeters / desiredUnit.meterEquivalent;
    }
}