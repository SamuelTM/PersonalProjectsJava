package samueltm.personalprojects.miscellaneous;

import java.nio.CharBuffer;

/**
 * A String object in which the manipulation operations don't
 * generate a new String, instead it keeps in memory a record
 * of the "search space" generated by each manipulation of the
 * original String.
 */
public class StringBuffer {

    private final CharBuffer cb;

    public StringBuffer(String s) {
        cb = CharBuffer.wrap(s);
    }

    public StringBuffer(CharBuffer cb) {
        this.cb = cb;
    }

    public StringBuffer substring(int from, int to) {
        return new StringBuffer(cb.subSequence(from, to));
    }

    public boolean contains(String s) {
        if (s.length() <= size()) {
            int previousPosition = cb.position();
            int currentIndex = 0;
            boolean foundSubstring = false;
            while (cb.hasRemaining()) {
                char currentChar = cb.get();
                if (currentChar == s.charAt(currentIndex)) {
                    currentIndex++;
                    if (currentIndex == s.length()) {
                        foundSubstring = true;
                        break;
                    }
                } else {
                    currentIndex = currentChar == s.charAt(0) ? 1 : 0;
                }
            }
            cb.position(previousPosition);
            return foundSubstring;
        } else {
            return false;
        }
    }

    public int nthElementIndex(char charElement, int charPosition) {
        if (charPosition > 0) {
            int counter = 0;
            for (int i = 0; i < size(); i++) {
                char currentChar = cb.charAt(i);
                if (currentChar == charElement) {
                    counter++;
                    if (counter == charPosition) {
                        return i;
                    }
                    if (size() - i - 1 < charPosition) {
                        break;
                    }
                }
            }
        }
        return -1;
    }

    public int getFirstOccurrenceIndex(char c) {
        return nthElementIndex(c, 1);
    }

    public boolean equals(String s) {
        if (s != null) {
            if (s.length() != size())
                return false;

            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) != cb.charAt(i)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public int size() {
        return cb.length();
    }

    @Override
    public String toString() {
        return cb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringBuffer stringBuffer = (StringBuffer) o;
        return cb.equals(stringBuffer.cb);
    }

    @Override
    public int hashCode() {
        return cb.hashCode();
    }
}