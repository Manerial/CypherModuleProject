package cypher.RSA;

import java.math.*;

/**
 * The decoder use the private key of the recipient to uncypher the message
 *
 * @param uncypherExposant Private key (couple cypherExposant + cypherModule)
 * @author JHER
 */
record Decoder(BigInteger uncypherExposant, BigInteger cypherModule) {

    /**
     * Decrypt a clearText using RSA method
     *
     * @param encodedText : The text to decrypt
     * @return the decrypted text
     */
    String decodeMessage(String encodedText) {
        StringBuilder decodedMessage = new StringBuilder();
        int beginIndex = 0;
        int decodePacketLength = cypherModule.toString().length();

        while ((beginIndex + decodePacketLength) < encodedText.length()) {
            String subMessage = encodedText.substring(beginIndex, beginIndex + decodePacketLength);
            decodedMessage.append(decodeDelimitedString(subMessage));
            beginIndex += decodePacketLength;
        }

        if ((beginIndex < encodedText.length()) && (beginIndex > 0)) {
            String subMessage = encodedText.substring(beginIndex);
            decodedMessage.append(decodeDelimitedString(subMessage));
        }

        return decodedMessage.toString();
    }

    /**
     * Return an decoded string
     *
     * @param subMessage : the string to decode
     * @return the encoded string (with a fixed length)
     */
    private String decodeDelimitedString(String subMessage) {
        BigInteger subMessageInteger = new BigInteger(subMessage);
        StringBuilder subMessageAscii = new StringBuilder(uncypher(subMessageInteger).toString());

        // Recall : Ascii encodes on 3 char
        while ((subMessageAscii.length() % 3) != 0) {
            subMessageAscii.insert(0, "0");
        }

        StringBuilder decodedMessage = new StringBuilder();
        int beginIndex = 0;
        while ((beginIndex + 3) < (subMessageAscii.length() + 1)) {
            char character = (char) Integer.parseInt(subMessageAscii.substring(beginIndex, beginIndex + 3));
            decodedMessage.append(character);
            beginIndex += 3;
        }

        return decodedMessage.toString();
    }

    /**
     * Mathematics encoding of the text
     *
     * @param subMessageInteger : the ascii to encode
     * @return the ascii decoded text
     */
    private BigInteger uncypher(BigInteger subMessageInteger) {
        return subMessageInteger.modPow(uncypherExposant, cypherModule);
    }
}
