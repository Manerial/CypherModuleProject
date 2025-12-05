package cypher.Enigma;

import java.util.*;

public class PermutationTable {
    public final HashMap<Character, Character> plugs = new HashMap<>();

    public void setPlug(char c1, char c2) {
        this.plugs.entrySet().removeIf(entry ->
                entry.getKey() == c1 ||
                        entry.getKey() == c2 ||
                        entry.getValue() == c1 ||
                        entry.getValue() == c2
        );

        this.plugs.put(c1, c2);
        this.plugs.put(c2, c1);
    }

    /**
     * Replace a character by it equivalent in plugs
     *
     * @param character : the character to replace
     * @return the permuted character
     */
    public char permutePlug(char character) {
        return plugs.getOrDefault(character, character);
    }
}
