package samueltm.personalprojects.miscellaneous;

import org.apache.commons.lang3.tuple.ImmutablePair;

public class EloScore {

    private static double probability(double scoreOne, double scoreTwo) {
        return 1.0 / (1 + Math.pow(10, (scoreOne - scoreTwo) / 400));
    }

    public static ImmutablePair<Double, Double> calculateScore(double currentScoreOne, double currentScoreTwo,
                                                               int variationLimit, boolean firstWon) {
        double probabilityTwoWon = probability(currentScoreOne, currentScoreTwo);
        double probabilityOneWon = probability(currentScoreTwo, currentScoreOne);

        return firstWon ? new ImmutablePair<>(
                currentScoreOne + variationLimit * (1 - probabilityOneWon),
                currentScoreTwo + variationLimit * (0 - probabilityTwoWon)
        ) : new ImmutablePair<>(
                currentScoreOne + variationLimit * (0 - probabilityOneWon),
                currentScoreTwo + variationLimit * (1 - probabilityTwoWon)
        );
    }
}
