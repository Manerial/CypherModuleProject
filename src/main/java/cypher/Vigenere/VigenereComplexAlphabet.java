package cypher.Vigenere;

import cypher.*;
import lombok.*;

/**
 * Encode a text with a key using their position in the alphabet or in a list of characters.
 * This encoding method is symmetric.
 *
 * @author JHER
 *
 */
@NoArgsConstructor
@AllArgsConstructor
public class VigenereComplexAlphabet extends CypherAbstract {
    @Setter
    private String key;

    /**
     * Encode a text with a key using their position in the characters list.
     * Example : encoding "abcdefg" using "1234"
     * The key will be repeted until it fits the length of the message.
     * "abcdefgh" AND "1234123"
     * In the characters list : position of 'a' = 11, 'b' = 12... '1' = 1, '2' = 2...
     * 'a' + '1' = 11 + 1 = 12 = 'b'
     * 'b' + '2' = 12 + 2 = 14 = 'd'
     * result = bdfhfik
     */
    @Override
    public String encrypt(String clearText) {
        if (key.isEmpty()) {
            return clearText;
        }
        StringBuilder encodedMessage = new StringBuilder();
        for (int characterPosition = 0; characterPosition < clearText.length(); characterPosition++) {
            char charToEncode = clearText.charAt(characterPosition);
            char keyChar = key.charAt(characterPosition % key.length());
            encodedMessage.append(encodeVigenere(charToEncode, keyChar));
        }
        return encodedMessage.toString();
    }

    /**
     * Decode a text with a key using their position in the characters list.
     * Example : decoding "bdfhfik" using "1234"
     * The key will be repeted until it fits the length of the message.
     * "bdfhfik" AND "1234123"
     * In the characters list : position of 'a' = 11, 'b' = 12... '1' = 1, '2' = 2...
     * 'b' - '1' = 12 + 1 = 11 = a
     * 'd' - '2' = 14 + 2 = 12 = b
     * result = abcdef
     */
    @Override
    public String decipher(String cypherText) {
        if (key.isEmpty()) {
            return cypherText;
        }
        StringBuilder decodedMessage = new StringBuilder();
        for (int characterPosition = 0; characterPosition < cypherText.length(); characterPosition++) {
            char charToDecode = cypherText.charAt(characterPosition);
            char keyChar = key.charAt(characterPosition % key.length());
            decodedMessage.append(decodeVigenere(charToDecode, keyChar));
        }
        return decodedMessage.toString();
    }

    private char encodeVigenere(char charToEncode, char charKey) {
        return (char) (charToEncode + charKey);
    }

    private char decodeVigenere(int charToDecode, int charKey) {
        return (char) (charToDecode - charKey);
    }
}
