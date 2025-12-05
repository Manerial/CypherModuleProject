package cypher.Enigma;


/**
 * The "machine" when configured.
 * Contains 3 rotors and a mirror and increment them when there are used.
 *
 * @author JHER
 *
 */
record RotorIncrementor(Rotor selected_rotor_1, Rotor selected_rotor_2, Rotor selected_rotor_3, Rotor selected_mirror) {

    /**
     * Encrypt a character with the Enigma method and rotate them when needed
     *
     * @param character : the character to encrypt
     * @return the encrypted character
     */
    char cryptAndRotate(char character) {
        char encoded_c = selected_rotor_1.getPermutedChar(character);
        encoded_c = selected_rotor_2.getPermutedChar(encoded_c);
        encoded_c = selected_rotor_3.getPermutedChar(encoded_c);

        encoded_c = selected_mirror.getPermutedChar(encoded_c);

        encoded_c = selected_rotor_3.getPermutedCharInverse(encoded_c);
        encoded_c = selected_rotor_2.getPermutedCharInverse(encoded_c);
        encoded_c = selected_rotor_1.getPermutedCharInverse(encoded_c);

        if (selected_rotor_1.increment()) {
            if (selected_rotor_2.increment()) {
                selected_rotor_3.increment();
            }
        }
        return encoded_c;
    }
}