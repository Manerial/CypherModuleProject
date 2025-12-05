package cypher.ChuckNorris;

import cypher.*;

import java.util.*;

/**
 * Transform a text in binary and then transform the binary in a sequence of 0.
 * This sequence consists of several consecutive packets of 0.
 * In each consecutive packet, the first one is 0 or 00.
 * If 0, then the second packet will contain 1, else it will contain 0.
 * Example : 01001110000 = 0+1+00+111+0000 = 00 0, 0 0, 00 00, 0 000, 00 0000.
 * This encoding method is symmetric.
 *
 * @author JHER
 *
 */
public class ChuckNorris extends CypherAbstract {
    private static final char CHUCK_NORRIS_BODY = '0';
    private static final String CHUCK_NORRIS_DELIMITER_0 = "";
    private static final String CHUCK_NORRIS_DELIMITER_1 = "";

    /**
     * Encode clear text to binary
     * Encode binary string to binary packets
     * Encode binary packets to Chuck Norris encryption
     */
    @Override
    public String cryptText(String clearText) {
        String binaryString = textToBinaryString(clearText);
        ArrayList<String> binaryPacket = binaryStringToBinaryPacket(binaryString);
        return binaryPacketToChuckNorris(binaryPacket);
    }

    /**
     * Convert a clear text into a binary string
     *
     * @param clearText : The text to convert
     * @return the text converted in binary
     */
    private String textToBinaryString(String clearText) {
        StringBuilder binaryString = new StringBuilder();
        for (char character : clearText.toCharArray()) {
            binaryString.append(charToBinaryCharacterString(character));
        }
        return binaryString.toString();
    }

    /**
     * Encode a character in a 8 digits length binary string
     * Example : 101 = 00000101
     *
     * @param character : The character to encode
     * @return the string representation of the binary of the character
     */
    private static String charToBinaryCharacterString(char character) {
        StringBuilder binaryCharacterString = new StringBuilder(Integer.toBinaryString(character));
        while (binaryCharacterString.length() < 8) {
            binaryCharacterString.insert(0, "0");
        }
        return binaryCharacterString.toString();
    }

    /**
     * Split a binary message in several packets of 0 and 1
     * Example : 001001110011 = 00, 1, 00, 111, 00, 11
     *
     * @param binaryString : The binary message to split into packets
     * @return a list of packets of 0 and 1
     */
    private ArrayList<String> binaryStringToBinaryPacket(String binaryString) {
        ArrayList<String> splittedString = new ArrayList<>();
        char parsedChar = '0';
        StringBuilder packet = new StringBuilder();

        for (char currentCharacter : binaryString.toCharArray()) {
            if (parsedChar != currentCharacter) {
                parsedChar = (parsedChar == '0') ? '1' : '0';
                if (!packet.isEmpty()) {
                    splittedString.add(packet.toString());
                }
                packet = new StringBuilder();
            }
            packet.append(currentCharacter);
        }

        splittedString.add(packet.toString());
        return splittedString;
    }

    /**
     * Convert a list of binary packets using the Chuck Norris encryption
     *
     * @param binaryPackets : the binary packets to convert
     * @return a string that contains the encrypted message
     */
    private String binaryPacketToChuckNorris(ArrayList<String> binaryPackets) {
        StringBuilder encodedMessage = new StringBuilder();
        for (String packet : binaryPackets) {
            if (packet.contains("0")) {
                encodedMessage.append(CHUCK_NORRIS_DELIMITER_0 + " ");
            } else {
                encodedMessage.append(CHUCK_NORRIS_DELIMITER_1 + " ");
            }
            encodedMessage.append(getNCharacter(CHUCK_NORRIS_BODY, packet.length())).append(" ");
        }
        encodedMessage = new StringBuilder(encodedMessage.substring(0, encodedMessage.length() - 1));
        return encodedMessage.toString();
    }

    /**
     * Decode cypher text to binary string
     * Decode binary string to clear text
     * Return all
     */
    @Override
    public String uncryptText(String cypherText) {
        String binaryMessage = chuckNorrisToBinaryString(cypherText);
        return binaryStringToText(binaryMessage);
    }

    /**
     * Convert an Chuck Norris message to binary string
     *
     * @param chuckNorrisMessage : the encoded message
     * @return the binary string that correspond
     */
    private String chuckNorrisToBinaryString(String chuckNorrisMessage) {
        StringBuilder binaryMessage = new StringBuilder();
        String[] splittedEncoding = chuckNorrisMessage.split(" ");
        for (int i = 0; i < (splittedEncoding.length / 2); i++) {
            String part1 = splittedEncoding[i * 2];
            String part2 = splittedEncoding[(i * 2) + 1];
            if (part1.equals(CHUCK_NORRIS_DELIMITER_0)) {
                binaryMessage.append(getNCharacter('0', part2.length()));
            } else if (part1.equals(CHUCK_NORRIS_DELIMITER_1)) {
                binaryMessage.append(getNCharacter('1', part2.length()));
            }
        }
        return binaryMessage.toString();
    }

    /**
     * Convert a binary message to clear text
     *
     * @param binaryMessage : the binary message to convert
     * @return the clear text of the binary message
     */
    private String binaryStringToText(String binaryMessage) {
        StringBuilder decodedMessage = new StringBuilder();
        for (int i = 0; i < binaryMessageLength(binaryMessage); i++) {
            int firstStone = i * 8;
            int lastStone = firstStone + 8;
            String binaryCharacter = binaryMessage.substring(firstStone, lastStone);
            decodedMessage.append(binaryToCharacter(binaryCharacter));
        }
        return decodedMessage.toString();
    }

    /**
     * Get the length of a binary message
     *
     * @param binaryMessage : the message to get the length
     * @return the length of the binary message
     */
    private int binaryMessageLength(String binaryMessage) {
        return binaryMessage.length() / 8;
    }

    /**
     * Decode a binary string to a character
     *
     * @param encodedCharacter : the character encoded in binary
     * @return the character decoded
     */
    private static char binaryToCharacter(String encodedCharacter) {
        int parseInt = Integer.parseInt(encodedCharacter, 2);
        return (char) parseInt;
    }

    /**
     * Return a string made of n times the given character
     *
     * @param character          : the character that will compose the returned string
     * @param numberOfCharacters : the number of time the character will appear in the returned string
     * @return a string made of n times the given character
     */
    private static String getNCharacter(char character, int numberOfCharacters) {
        StringBuilder nCharString = new StringBuilder();
        for (int i = 0; i < numberOfCharacters; i++) {
            nCharString.append(character);
        }
        return nCharString.toString();
    }
}
