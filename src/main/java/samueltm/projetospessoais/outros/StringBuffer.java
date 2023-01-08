package samueltm.projetospessoais.outros;

import java.nio.CharBuffer;

/**
 * Uma String cujas operações de manipulação não geram uma nova
 * String, em vez disso mantém na memória um registro do "espaço
 * de busca" gerado pela manipulação da String original.
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

    public boolean contem(String s) {
        if (s.length() <= tamanho()) {
            int previousPosition = cb.position();
            int currIndex = 0;
            boolean found = false;
            while (cb.hasRemaining()) {
                char current = cb.get();
                if (current == s.charAt(currIndex)) {
                    currIndex++;
                    if (currIndex == s.length()) {
                        found = true;
                        break;
                    }
                } else {
                    currIndex = current == s.charAt(0) ? 1 : 0;
                }
            }
            cb.position(previousPosition);
            return found;
        } else {
            return false;
        }
    }

    public int indiceDoEnesimoElemento(char c, int n) {
        if (n > 0) {
            int contagem = 0;
            for (int i = 0; i < tamanho(); i++) {
                char caractereAtual = cb.charAt(i);
                if (caractereAtual == c) {
                    contagem++;
                    if (contagem == n) {
                        return i;
                    }
                    if (tamanho() - i - 1 < n) {
                        break;
                    }
                }
            }
        }
        return -1;
    }

    public int indiceDoCaractere(char c) {
        return indiceDoEnesimoElemento(c, 1);
    }

    public boolean equivale(String s) {
        if (s != null) {
            if (s.length() != tamanho())
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

    public int tamanho() {
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