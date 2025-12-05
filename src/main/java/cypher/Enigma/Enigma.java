package cypher.Enigma;

import cypher.*;
import org.apache.commons.lang3.tuple.*;

import java.util.*;
import java.util.regex.*;

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
    private final RotorIncrementor rotorIncrementor;
    private final PermutationTable permutationTable = new PermutationTable();


    public Enigma(List<Pair<Rotor, Integer>> rotors, Pair<Rotor, Integer> mirror, List<Pair<Character, Character>> permutations) {
        rotorIncrementor = new RotorIncrementor(rotors, mirror);

        for (Pair<Character, Character> plug : permutations) {
            this.permutationTable.setPlug(plug.getKey(), plug.getValue());
        }
    }

    public Enigma() {
        Pair<Rotor, Integer> rotor1 = new ImmutablePair<>(Accessories.rotor1, 5);
        Pair<Rotor, Integer> rotor2 = new ImmutablePair<>(Accessories.rotor1, 15);
        Pair<Rotor, Integer> rotor3 = new ImmutablePair<>(Accessories.rotor1, 2);
        Pair<Rotor, Integer> mirror = new ImmutablePair<>(Accessories.mirror1, 6);
        rotorIncrementor = new RotorIncrementor(List.of(rotor1, rotor2, rotor3), mirror);
        this.permutationTable.setPlug('A', 'E');
        this.permutationTable.setPlug('Z', 'P');
        this.permutationTable.setPlug('W', 'R');
        this.permutationTable.setPlug('T', 'V');
        this.permutationTable.setPlug('L', 'O');
    }

    /**
     * Encode a clear message
     */
    @Override
    public String encrypt(String clearText) {
        Pattern pattern = Pattern.compile("[^A-Z ]");
        Matcher matcher = pattern.matcher(clearText);
        if (matcher.find()) {
            System.out.println("The message mustn't contains specials char");
            return null;
        }
        rotorIncrementor.rotor_pos_reset();
        return encodeMessage(clearText);
    }

    /**
     * Encrypt each character using the Enigma's method
     *
     * @param clearText : The text to encode
     * @return the encoded text
     */
    private String encodeMessage(String clearText) {
        StringBuilder encodedMessage = new StringBuilder();
        for (char character : clearText.toUpperCase().toCharArray()) {
            if (character != ' ') {
                char encoded_c = crypt(character);
                encoded_c = permutationTable.permutePlug(encoded_c);
                encodedMessage.append(encoded_c);
            } else {
                encodedMessage.append(' ');
            }
        }
        return encodedMessage.toString();
    }

    /**
     * Decode a ciphered message
     */
    @Override
    public String decipher(String cypherText) {
        try {
            rotorIncrementor.rotor_pos_reset();
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
                char decoded_c = permutationTable.permutePlug(character);
                decoded_c = crypt(decoded_c);
                decodedMessage.append(decoded_c);
            } else {
                decodedMessage.append(' ');
            }
        }
        return decodedMessage.toString();
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
}