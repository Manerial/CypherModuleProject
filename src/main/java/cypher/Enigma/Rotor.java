package cypher.Enigma;


import java.util.*;

/**
 * One rotor that will increment and shuffle/permute all the letters
 * The permute process works like this :
 * Get the position of the char in the alphabet.
 * Return the char at this position in the rotor composition.
 * The permute inverse process is just the same but the other way.
 *
 * @author JHER
 *
 */
public class Rotor {
    private final ArrayList<Character> alphabet = new ArrayList<>();
    private final ArrayList<Character> rotorComposition = new ArrayList<>();
    private int rotation = 0;

    protected Rotor(char[] rotor) {
        for (char c : rotor) {
            this.rotorComposition.add(c);
        }

        for (char c : "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray()) {
            alphabet.add(c);
        }
    }

    /**
     * permute a character from the entry point to the destination point of the rotor
     *
     * @param character : the character to permute
     * @return the permuted character
     */
    protected char getPermutedChar(char character) {
        return rotorComposition.get(alphabet.indexOf(character));
    }

    /**
     * permute a character from the destination point to the entry point of the rotor
     *
     * @param character : the character to permute
     * @return the permuted character
     */
    protected char getPermutedCharInverse(char character) {
        return alphabet.get(rotorComposition.indexOf(character));
    }

    /**
     * Set the basic position of the rotor
     *
     * @param rotation : the basic position of the rotor
     */
    protected void setBase(int rotation) {
        while (this.rotation != rotation % 26) {
            increment();
        }
    }

    /**
     * Increment the rotor.
     * If it reach 26, then it has done a whole rotation and it has to return at it 0 position.
     *
     * @return true if the rotor made a whole rotation
     */
    protected boolean increment() {
        char c = rotorComposition.remove(0);
        rotorComposition.add(c);
        rotation = (rotation + 1) % 26;
        return rotation == 0;
    }
}