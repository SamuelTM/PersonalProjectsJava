package samueltm.personalprojects.miscellaneous;

import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class SubjectiveClassifier {

    private final HashMap<Integer, Double> opponentsList;
    private final Iterator<ImmutablePair<Integer, Integer>> contests;

    public SubjectiveClassifier(int nItems) {
        this.opponentsList = new HashMap<>();

        ArrayList<ImmutablePair<Integer, Integer>> contestsList = new ArrayList<>();
        for (int i = 0; i < nItems; i++) {
            if (opponentsList.containsKey(i)) throw new RuntimeException("Each item in the list must be unique");
            opponentsList.put(i, 1000.0);

            for (int j = i + 1; j < nItems; j++) {
                contestsList.add(new ImmutablePair<>(i, j));
            }
        }

        this.contests = contestsList.iterator();
    }

    public ImmutablePair<Integer, Integer> getNextContestIndices() {
        if (contests.hasNext()) {
            return contests.next();
        } else {
            return null;
        }
    }

    public void vote(ImmutablePair<Integer, Integer> opponentsIndices, boolean firstOpponentWins) {
        for(int opponentIndex : new int[]{opponentsIndices.getLeft(), opponentsIndices.getRight()}) {
            if (opponentIndex < 0 || opponentIndex >= opponentsList.size())
                throw new IllegalArgumentException("Invalid indices for the opponents");
        }
        ImmutablePair<Double, Double> newScores = EloScore.calculateScore(opponentsList.get(opponentsIndices.getLeft()),
                opponentsList.get(opponentsIndices.getRight()), 30, firstOpponentWins);

        opponentsList.put(opponentsIndices.getLeft(), newScores.getLeft());
        opponentsList.put(opponentsIndices.getRight(), newScores.getRight());
    }

    public HashMap<Integer, Double> getOpponentsList() {
        return opponentsList;
    }
}
