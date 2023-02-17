package samueltm.personalprojects.units;

public enum VolumetricFlowUnit {

    CUBIC_METER_PER_HOUR(1),
    CUBIC_METER_PER_MINUTE(60),
    CUBIC_METER_PER_SECOND(3600),
    CUBIC_FOOT_PER_MINUTE(1.69901082);

    private final double m3HourEquivalent;

    VolumetricFlowUnit(double m3HourEquivalent) {
        this.m3HourEquivalent = m3HourEquivalent;
    }

    public double convert(double currentValue, VolumetricFlowUnit desiredUnit) {
        double toM3Hour = currentValue * m3HourEquivalent;
        return toM3Hour / desiredUnit.m3HourEquivalent;
    }
}
