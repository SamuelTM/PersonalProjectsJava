package samueltm.personalprojects.chemistry;

import com.google.gson.Gson;

import java.io.*;

public class Chemistry {

    private static class PeriodicTable {
        private Element[] elements;
    }

    public static Element[] loadPeriodicTable() {
        try (BufferedReader br = new BufferedReader(new FileReader(
                "src/main/java/samueltm/personalprojects/chemistry/PeriodicTable.json"))) {
            Gson gson = new Gson();
            return gson.fromJson(br, PeriodicTable.class).elements;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
