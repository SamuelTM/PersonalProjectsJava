package samueltm.personalprojects.miscellaneous;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class MyBitSet {

    private boolean[] bits;

    public MyBitSet(int length) {
        if (length < 1) throw new IllegalArgumentException("Length must be a positive number");
        this.bits = new boolean[length];
    }

    public boolean[] getBits() {
        return bits;
    }

    public void setBit(int index, boolean value) {
        bits[index] = value;
    }

    public boolean getBit(int index) {
        return bits[index];
    }

    public static boolean xor(boolean a, boolean b) {
        return a != b;
    }

    public long toDecimal() {
        long result = 0;
        for (int i = bits.length - 1; i >= 0; i--) {
            if (bits[i]) {
                result |= (1L << (bits.length - i - 1));
            }
        }
        return result;
    }

    public static MyBitSet toBits(long number) {
        int size = Long.SIZE - Long.numberOfLeadingZeros(number);
        MyBitSet result = new MyBitSet(size);
        for (int i = size - 1; i >= 0; i--) {
            result.bits[i] = ((number & (1L << (size - 1 - i))) != 0);
        }
        return result;
    }

    public static MyBitSet randomBits(int length) {
        MyBitSet result = new MyBitSet(length);
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < length; i++) {
            result.setBit(i, random.nextBoolean());
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(boolean bit : bits) {
            sb.append(bit ? "1" : "0");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyBitSet myBitSet = (MyBitSet) o;
        return Arrays.equals(bits, myBitSet.bits);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(bits);
    }
}
