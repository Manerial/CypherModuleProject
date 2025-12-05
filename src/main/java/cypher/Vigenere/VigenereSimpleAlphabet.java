package cypher.Vigenere;

import cypher.*;

public class VigenereSimpleAlphabet extends CypherAbstract {
    private String key;

    public VigenereSimpleAlphabet(String key) {
        this.key = key;
    }

    @Override
    public String encrypt(String clearText) {
        clearText = clearText.toUpperCase();
        key = key.toUpperCase();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < clearText.length(); i++) {
            if (Character.isLetter(clearText.charAt(i))) {
                int c = clearText.charAt(i) - 65;
                int k = key.charAt(i % key.length()) - 65;
                char newC = (char) ((c + k + 26) % 26 + 65);
                result.append(newC);
            }
        }
        return result.toString();
    }

    @Override
    public String decipher(String cypherText) {
        cypherText = cypherText.toUpperCase();
        key = key.toUpperCase();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < cypherText.length(); i++) {
            if (Character.isLetter(cypherText.charAt(i))) {
                int c = cypherText.charAt(i) - 65;
                int k = key.charAt(i % key.length()) - 65;
                char newC = (char) ((c - k + 26) % 26 + 65);
                result.append(newC);
            }
        }
        return result.toString();
    }
}
