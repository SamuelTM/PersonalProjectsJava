package samueltm.personalprojects.miscellaneous;

public class Formatter {

    public static String formatNanoseconds(long nanoseconds) {
        if (nanoseconds >= 1e9) {
            return (nanoseconds / 1e9) + "s";
        } else if (nanoseconds >= 1e6) {
            return (nanoseconds / 1e6) + "ms";
        } else {
            return nanoseconds + "ns";
        }
    }
}
