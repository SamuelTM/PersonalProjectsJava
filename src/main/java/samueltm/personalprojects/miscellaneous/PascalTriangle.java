package samueltm.personalprojects.miscellaneous;

public class PascalTriangle {
    public static MyBitSet generateBinaryPascalRow(int n) {
        MyBitSet row = new MyBitSet(n + 1);
        row.setBit(0, true);
        for (int i = 1; i <= n; i++) {
            for (int j = i; j > 0; j--) {
                row.setBit(j, MyBitSet.xor(row.getBit(j), row.getBit(j - 1)));
            }
        }
        return row;
    }

    public static int[] generatePascalRow(int n) {
        int[] row = new int[n + 1];
        row[0] = 1;
        for (int i = 1; i <= n; i++) {
            for (int j = i; j > 0; j--) {
                row[j] += row[j - 1];
            }
        }
        return row;
    }
}
