package samueltm.personalprojects.tests;

import org.apache.commons.lang3.tuple.ImmutablePair;
import samueltm.personalprojects.miscellaneous.SubjectiveClassifier;

import java.util.*;

public class SubjectiveClassifierTest {

    public static void classify(String[] opponents) {
        Scanner scanner = new Scanner(System.in);
        SubjectiveClassifier classifier = new SubjectiveClassifier(opponents.length);
        ImmutablePair<Integer, Integer> nextContestIndices = classifier.getNextContestIndices();
        while (true) {
            if (nextContestIndices != null) {
                System.out.println(opponents[nextContestIndices.getLeft()] + " VS "
                        + opponents[nextContestIndices.getRight()]);
                System.out.println("Type 1 or 2 to choose...");
                int answer = scanner.nextInt();
                if (answer != 1 && answer != 2) {
                    System.out.println("Invalid option, try again");
                } else {
                    int choice = answer == 1 ? nextContestIndices.getLeft() : nextContestIndices.getRight();
                    System.out.println("You chose " + opponents[choice]);
                    classifier.vote(nextContestIndices, answer == 1);
                    nextContestIndices = classifier.getNextContestIndices();
                }
                System.out.println("---------------------------");
            } else {
                break;
            }
        }
        System.out.println("---------------------------");

        List<Integer> orderedOpponents = new ArrayList<>(classifier.getOpponentsList().keySet());
        orderedOpponents.sort(Comparator.comparing(o -> classifier.getOpponentsList().get(o)));
        Collections.reverse(orderedOpponents);

        for (int i = 0; i < orderedOpponents.size(); i++) {
            Integer opponentIndex = orderedOpponents.get(i);
            System.out.println((i + 1) + " - " + opponents[opponentIndex] + " - "
                    + classifier.getOpponentsList().get(opponentIndex));
        }
    }
}
