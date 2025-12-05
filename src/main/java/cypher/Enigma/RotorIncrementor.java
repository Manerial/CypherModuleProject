package cypher.Enigma;

import lombok.*;
import org.apache.commons.lang3.tuple.*;

import java.util.*;


/**
 * The "machine" when configured.
 * Contains 3 rotors and a mirror and increment them when there are used.
 *
 * @author JHER
 *
 */
@AllArgsConstructor
public class RotorIncrementor {
    private List<Pair<Rotor, Integer>> rotors;
    private Pair<Rotor, Integer> selected_mirror;

    /**
     * Encrypt a character with the Enigma method and rotate them when needed
     *
     * @param character : the character to encrypt
     * @return the encrypted character
     */
    char cryptAndRotate(char character) {
        char encodedChar = character;
        for (Pair<Rotor, Integer> rotorIntegerPair : rotors) {
            Rotor rotor = rotorIntegerPair.getKey();
            rotor.getPermutedChar(encodedChar);
        }

        encodedChar = selected_mirror.getKey().getPermutedChar(encodedChar);

        for (Pair<Rotor, Integer> rotorIntegerPair : rotors.reversed()) {
            Rotor rotor = rotorIntegerPair.getKey();
            rotor.getPermutedChar(encodedChar);
        }

        rotors.stream()
                .map(Pair::getKey)
                .forEach(Rotor::increment);

        return encodedChar;
    }

    /**
     * Reset the initial places of the rotors (to decrypt, the rotors has to be at the same place)
     */
    public void rotor_pos_reset() {
        for (Pair<Rotor, Integer> rotorIntegerPair : rotors) {
            rotorIntegerPair.getKey().setBase(rotorIntegerPair.getValue());
        }
    }
}