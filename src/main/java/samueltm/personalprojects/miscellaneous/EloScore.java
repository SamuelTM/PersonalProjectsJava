package samueltm.personalprojects.miscellaneous;

public class EloScore {

    private static double probability(double scoreOne, double scoreTwo) {
        return 1.0 / (1 + Math.pow(10, (scoreOne - scoreTwo) / 400));
    }

    public static double[] calculateScore(double currentScoreOne, double currentScoreTwo, int variationLimit,
                                          boolean firstWon) {
        double probabilityTwoWon = probability(currentScoreOne, currentScoreTwo);
        double probabilityOneWon = probability(currentScoreTwo, currentScoreOne);

        return firstWon ? new double[]{
                currentScoreOne + variationLimit * (1 - probabilityOneWon),
                currentScoreTwo + variationLimit * (0 - probabilityTwoWon),
        } : new double[]{
                currentScoreOne + variationLimit * (0 - probabilityOneWon),
                currentScoreTwo + variationLimit * (1 - probabilityTwoWon),
        };
    }
}
