package samueltm.personalprojects.tests;

import samueltm.personalprojects.miscellaneous.SubjectiveClassifier;

import java.util.*;

public class SubjectiveClassifierTest {

    public static void classify(String[] opponents) {
        Scanner scanner = new Scanner(System.in);
        SubjectiveClassifier classifier = new SubjectiveClassifier(opponents.length);
        Integer[] nextContestIndices = classifier.getNextContestIndices();
        while (true) {
            if (nextContestIndices.length > 0) {
                System.out.println(opponents[nextContestIndices[0]] + " VS " + opponents[nextContestIndices[1]]);
                System.out.println("Type 1 or 2 to choose...");
                int resposta = scanner.nextInt();
                if (resposta != 1 && resposta != 2) {
                    System.out.println("Invalid option, try again");
                } else {
                    System.out.println("You chose " + opponents[nextContestIndices[resposta - 1]]);
                    classifier.vote(nextContestIndices, resposta == 1);
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
