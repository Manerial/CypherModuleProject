package cypher.RSA;

import java.math.*;

/**
 * The encoder use the public key of the recipient to cypher the message
 *
 * @param cypherExposant Public key (couple cypherExposant + cypherModule)
 * @author JHER
 */
record Encoder(BigInteger cypherExposant, BigInteger cypherModule) {

    /**
     * Encrypt a clearText using RSA method
     *
     * @param clearText : The text to encrypt
     * @return the encrypted text
     */
    String encodeText(String clearText) {
        StringBuilder encodedMessage = new StringBuilder();
        int beginIndex = 0;
        int encodePacketLength = 7;

        // Encode using packets of 7 letters
        while ((beginIndex + encodePacketLength) < clearText.length()) {
            String subMessage = clearText.substring(beginIndex, beginIndex + encodePacketLength);
            encodedMessage.append(encodeDelimitedString(subMessage));
            beginIndex += encodePacketLength;
        }

        // Encode the rest of the text if needed
        if ((beginIndex < clearText.length()) && (beginIndex > 0)) {
            String subMessage = clearText.substring(beginIndex);
            encodedMessage.append(encodeDelimitedString(subMessage));
        }

        return encodedMessage.toString();
    }

    /**
     * Return an encoded string with the same length that cypherModule
     *
     * @param stringToEncode : the string to encode
     * @return the encoded string (with a fixed length)
     */
    private String encodeDelimitedString(String stringToEncode) {
        String stringInAscii = encodeStrigToAscii(stringToEncode);
        BigInteger encodedString = new BigInteger(stringInAscii);

        StringBuilder returnValue = new StringBuilder(cypher(encodedString).toString());

        while ((returnValue.length() % cypherModule.toString().length()) != 0) {
            returnValue.insert(0, "0");
        }

        return returnValue.toString();
    }

    /**
     * Encode a string in ascii.
     * If the length of an encoded char is lesser than 3, add 0 to fill it.
     * Recall : Ascii encodes on 3 char
     *
     * @param stringToEncode : the string to encode in ascii
     * @return the encoded string
     */
    private String encodeStrigToAscii(String stringToEncode) {
        StringBuilder stringInAscii = new StringBuilder();
        char[] chars = stringToEncode.toCharArray();
        for (char caracter : chars) {
            if (caracter < 10) {
                stringInAscii.append("00").append((int) caracter);
            } else if (caracter < 100) {
                stringInAscii.append("0").append((int) caracter);
            } else {
                stringInAscii.append((int) caracter);
            }
        }
        return stringInAscii.toString();
    }

    /**
     * Mathematics encoding of the text
     *
     * @param asciiEncodedText : the ascii to encode
     * @return the ascii encoded
     */
    private BigInteger cypher(BigInteger asciiEncodedText) {
        return asciiEncodedText.modPow(cypherExposant, cypherModule);
    }
}
