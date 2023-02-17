package samueltm.personalprojects.miscellaneous;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class SubjectiveClassifier {

    private final HashMap<Integer, Double> opponentsList;
    private final Iterator<Integer[]> contests;

    public SubjectiveClassifier(int nItems) {
        this.opponentsList = new HashMap<>();

        ArrayList<Integer[]> contestsList = new ArrayList<>();
        for (int i = 0; i < nItems; i++) {
            if (opponentsList.containsKey(i)) throw new RuntimeException("Each item in the list must be unique");
            opponentsList.put(i, 1000.0);

            for (int j = i + 1; j < nItems; j++) {
                contestsList.add(new Integer[]{i, j});
            }
        }

        this.contests = contestsList.iterator();
    }

    public Integer[] getNextContestIndices() {
        if (contests.hasNext()) {
            return contests.next();
        } else {
            return new Integer[]{};
        }
    }

    public void vote(Integer[] opponentsIndices, boolean firstOpponentWins) {
        if (opponentsIndices.length != 2) throw new IllegalArgumentException("Invalid amount of opponents");
        for(int opponentIndex : opponentsIndices) {
            if (opponentIndex < 0 || opponentIndex >= opponentsList.size())
                throw new IllegalArgumentException("Invalid indices for the opponents");
        }
        double[] newScores = EloScore.calculateScore(opponentsList.get(opponentsIndices[0]),
                opponentsList.get(opponentsIndices[1]), 30, firstOpponentWins);

        opponentsList.put(opponentsIndices[0], newScores[0]);
        opponentsList.put(opponentsIndices[1], newScores[1]);
    }

    public HashMap<Integer, Double> getOpponentsList() {
        return opponentsList;
    }
}
