package cypher.Enigma;

import cypher.*;

import java.util.*;

/**
 * Encrypt a message using the ENIGMA method.
 * A character will undergo many permutations
 * First, it will travel the plugs that will switch it with an other character (but only if the character is on the permute table)
 * Then it will travel 3 rotors and be permute with other characters again
 * After that it will travel a mirror (like a rotor but where the entry and the destination give the same result)
 * Then it will travel again 3 rotors but in the other way
 * And then it will be the end.
 * This encoding method is symmetric (thanks to the mirror)
 *
 * @author JHER
 *
 */
public class Enigma extends CypherAbstract {
    private static final Rotor rotor1 = new Rotor("DUFHQOWPANEMSRZXLBTYGIJKVC".toCharArray());
    private static final Rotor rotor2 = new Rotor("EKMFLGDQVZNTOWYHXUSPAIBRCJ".toCharArray());
    private static final Rotor rotor3 = new Rotor("AJDKSIRUXBLHWTMCQGZNPYFVOE".toCharArray());
    private static final Rotor rotor4 = new Rotor("BDFHJLCPRTXVZNYEIWGAKMUSQO".toCharArray());
    private static final Rotor rotor5 = new Rotor("ESOVPZJAYQUIRHXLNFTGKDCMWB".toCharArray());
    private static final Rotor rotor6 = new Rotor("VZBRGITYUPSDNHLXAWMJQOFECK".toCharArray());
    public static int rotorPos1 = 0;
    public static int rotorPos2 = 0;
    public static int rotorPos3 = 0;
    public static int rotorPos4 = 0;
    public static int rotorPos5 = 0;
    public static int rotorPos6 = 0;

    // The mirrors are special rotors with a special construction :
    // At the position A (1) is the char H (8) and vice versa
    // Compare with the alphabet :
    // ABCDEFGHIJKLMNOPQRSTUVWXYZ
    // |      |
    // HBPQVJSAZFXLYWTCDUGORENKMI
    private static final Rotor mirror1 = new Rotor("HBPQVJSAZFXLYWTCDUGORENKMI".toCharArray());
    private static final Rotor mirror2 = new Rotor("KRFXOCTIHQAZWPENJBVGYSMDUL".toCharArray());
    private static final Rotor mirror3 = new Rotor("TJLWNZKUYBGCPESMRQOAHXDVIF".toCharArray());

    private HashMap<Character, Character> plugs = new HashMap<>();
    private final RotorIncrementor rotorIncrementor;

    public Enigma(Rotor r1, Rotor r2, Rotor r3, Rotor mirror, HashMap<Character, Character> fiches) {
        rotorIncrementor = new RotorIncrementor(r1, r2, r3, mirror);
        this.plugs = fiches;
    }

    public Enigma() {
        rotorIncrementor = new RotorIncrementor(rotor1, rotor2, rotor3, mirror1);
        rotorPos1 = 5;
        rotorPos2 = 12;
        rotorPos3 = 9;
        inputPlugs('A', 'E', 'Z', 'P', 'W', 'R', 'T', 'V', 'L', 'O', 'R', 'S');
    }

    /**
     * Create the plugs to permute many characters
     *
     * @param c1  : the character to permute with c2
     * @param c2  : the character to permute with c1
     * @param c3  : the character to permute with c4
     * @param c4  : the character to permute with c3
     * @param c5  : the character to permute with c6
     * @param c6  : the character to permute with c5
     * @param c7  : the character to permute with c8
     * @param c8  : the character to permute with c7
     * @param c9  : the character to permute with c10
     * @param c10 : the character to permute with c9
     * @param c11 : the character to permute with c12
     * @param c12 : the character to permute with c11
     */
    private void inputPlugs(char c1, char c2, char c3, char c4, char c5, char c6, char c7, char c8, char c9, char c10, char c11, char c12) {
        //fiche 1
        this.plugs.put(c1, c2);
        this.plugs.put(c2, c1);
        //fiche 2
        this.plugs.put(c3, c4);
        this.plugs.put(c4, c3);
        //fiche 3
        this.plugs.put(c5, c6);
        this.plugs.put(c6, c5);
        //fiche 4
        this.plugs.put(c7, c8);
        this.plugs.put(c8, c7);
        //fiche 5
        this.plugs.put(c9, c10);
        this.plugs.put(c10, c9);
        //fiche 6
        this.plugs.put(c11, c12);
        this.plugs.put(c12, c11);
    }

    /**
     * Set the initial positions of the rotors
     *
     * @param pos1 : the position of the rotor 1
     * @param pos2 : the position of the rotor 2
     * @param pos3 : the position of the rotor 3
     * @param pos4 : the position of the rotor 4
     * @param pos5 : the position of the rotor 5
     * @param pos6 : the position of the rotor 6
     */
    public void rotor_pos_set(int pos1, int pos2, int pos3, int pos4, int pos5, int pos6) {
        rotorPos1 = pos1;
        rotorPos2 = pos2;
        rotorPos3 = pos3;
        rotorPos4 = pos4;
        rotorPos5 = pos5;
        rotorPos6 = pos6;
    }

    /**
     * Encode a clear message
     */
    @Override
    public String cryptText(String clearText) {
        rotor_pos_reset();
        return encodeMessage(clearText);
    }

    /**
     * Encrypt each character using the Enigma's method
     *
     * @param clearText : The text to encode
     * @return the encoded text
     */
    private String encodeMessage(String clearText) {
        try {
            StringBuilder encodedMessage = new StringBuilder();
            for (char character : clearText.toUpperCase().toCharArray()) {
                if (character != ' ') {
                    char encoded_c = crypt(character);
                    encoded_c = permutePlug(encoded_c);
                    encodedMessage.append(encoded_c);
                } else {
                    encodedMessage.append(' ');
                }
            }
            return encodedMessage.toString();
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("The message mustn't contains specials char");
        }
        return null;
    }

    /**
     * Decode a ciphered message
     */
    @Override
    public String uncryptText(String cypherText) {
        try {
            rotor_pos_reset();
            return decodeMessage(cypherText);
        } catch (NullPointerException e) {
            System.out.println("The cypher message is null");
        }
        return null;
    }

    /**
     * Decrypt each character using the Enigma's method
     *
     * @param cypherText : The text to decode
     * @return the decoded text
     */
    private String decodeMessage(String cypherText) {
        StringBuilder decodedMessage = new StringBuilder();
        for (char character : cypherText.toCharArray()) {
            if (character != ' ') {
                char decoded_c = permutePlug(character);
                decoded_c = crypt(decoded_c);
                decodedMessage.append(decoded_c);
            } else {
                decodedMessage.append(' ');
            }
        }
        return decodedMessage.toString();
    }

    /**
     * Replace a character by it equivalent in plugs
     *
     * @param character : the character to replace
     * @return the permuted character
     */
    private char permutePlug(char character) {
        return plugs.getOrDefault(character, character);
    }

    /**
     * Encrypt a character with the plugs and the rotors.
     * Then rotate the rotors.
     *
     * @param character : the character to encode
     * @return the character encoded with an other one
     */
    private char crypt(char character) {
        return rotorIncrementor.cryptAndRotate(character);
    }

    /**
     * Reset the initial places of the rotors (to decrypt, the rotors has to be at the same place)
     */
    private void rotor_pos_reset() {
        rotor1.setBase(rotorPos1);
        rotor2.setBase(rotorPos2);
        rotor3.setBase(rotorPos3);
        rotor4.setBase(rotorPos4);
        rotor5.setBase(rotorPos5);
        rotor6.setBase(rotorPos6);
    }
}