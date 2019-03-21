package cypherMethod;

import java.math.BigInteger;
import java.security.SecureRandom;

import cypherAbstract.CypherAbstract;

/**
 * Uses the RSA cypher method to encrypt/decrypt a message.
 * It uses big prime numbers and modulo to always get a different result, with big calculations.
 * This method is not symmetric.
 * 
 * @author JHER
 *
 */
public class RSA extends CypherAbstract {
	private final Encoder	encoder;
	private final Decoder	decoder;

	/**
	 * 
	 * @param prime1 : The first big prime number that will be use to create the public and the private key
	 * @param prime2 : The second big prime number that will be use to create the public and the private key
	 * @param cypherExposant : A prime number lesser than (prime1 - 1) * (prime2 - 1)
	 */
	public RSA(BigInteger prime1, BigInteger prime2, BigInteger cypherExposant) {
		// cypherExposant is a prime number < phiN
		
		// phiN = (prime1 - 1) * (prime2 - 1)
		BigInteger phiN = prime1.subtract(BigInteger.ONE).multiply(prime2.subtract(BigInteger.ONE));

		// cypherModule = prime1 * prime2
		BigInteger cypherModule = prime1.multiply(prime2);
		encoder = new Encoder(cypherExposant, cypherModule);

		// uncypherExposant => exposantChiffrement * uncypherExposant % phiN = 1
		BigInteger uncypherExposant = cypherExposant.modInverse(phiN);
		decoder = new Decoder(uncypherExposant, cypherModule);
	}

	@Override
	public String cryptText(String clearText) {
		return encoder.encodeText(clearText);
	}

	@Override
	public String uncryptText(String cypherText) {
		return decoder.decodeMessage(cypherText);
	}

	/**
	 * @return a probable prime number
	 */
	public static BigInteger probablePrime() {
		return BigInteger.probablePrime(80, new SecureRandom());
	}
}

/**
 * The encoder use the public key of the recipient to cypher the message
 * 
 * @author JHER
 *
 */
class Encoder {
	// Public key (couple cypherExposant + cypherModule)
	private final BigInteger	cypherExposant;
	private final BigInteger	cypherModule;

	protected Encoder(BigInteger exposantChiffrement, BigInteger moduleChiffrement) {
		this.cypherExposant = exposantChiffrement;
		this.cypherModule = moduleChiffrement;
	}

	/**
	 * Encrypt a clearText using RSA method
	 * 
	 * @param clearText : The text to encrypt
	 * @return the encrypted text
	 */
	protected String encodeText(String clearText) {
		String encodedMessage = "";
		int beginIndex = 0;
		int encodePacketLength = 7;

		// Encode using packets of 7 letters
		while ((beginIndex + encodePacketLength) < clearText.length()) {
			String subMessage = clearText.substring(beginIndex, beginIndex + encodePacketLength);
			encodedMessage += encodeDelimitedString(subMessage);
			beginIndex += encodePacketLength;
		}

		// Encode the rest of the text if needed
		if ((beginIndex < clearText.length()) && (beginIndex > 0)) {
			String subMessage = clearText.substring(beginIndex, clearText.length());
			encodedMessage += encodeDelimitedString(subMessage);
		}

		return encodedMessage;
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

		String returnValue = cypher(encodedString).toString();

		while ((returnValue.length() % cypherModule.toString().length()) != 0) {
			returnValue = "0" + returnValue;
		}

		return returnValue;
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
		String stringInAscii = "";
		char[] chars = stringToEncode.toCharArray();
		for (char caracter : chars) {
			if (caracter < 10) {
				stringInAscii += "00" + (int) caracter;
			} else if (caracter < 100) {
				stringInAscii += "0" + (int) caracter;
			} else {
				stringInAscii += (int) caracter;
			}
		}
		return stringInAscii;
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

/**
 * The decoder use the private key of the recipient to uncypher the message
 * 
 * @author JHER
 *
 */
class Decoder {
	// Private key (couple cypherExposant + cypherModule)
	private final BigInteger	uncypherExposant;
	private final BigInteger	cypherModule;

	protected Decoder(BigInteger uncypherExposant, BigInteger cypherModule) {
		this.uncypherExposant = uncypherExposant;
		this.cypherModule = cypherModule;
	}

	/**
	 * Decrypt a clearText using RSA method
	 * 
	 * @param encodedText : The text to decrypt
	 * @return the decrypted text
	 */
	protected String decodeMessage(String encodedText) {
		String decodedMessage = "";
		int beginIndex = 0;
		int decodePacketLength = cypherModule.toString().length();

		beginIndex = 0;
		while ((beginIndex + decodePacketLength) < encodedText.length()) {
			String subMessage = encodedText.substring(beginIndex, beginIndex + decodePacketLength);
			decodedMessage += decodeDelimitedString(subMessage);
			beginIndex += decodePacketLength;
		}

		if ((beginIndex < encodedText.length()) && (beginIndex > 0)) {
			String subMessage = encodedText.substring(beginIndex, encodedText.length());
			decodedMessage += decodeDelimitedString(subMessage);
		}

		return decodedMessage;
	}

	/**
	 * Return an decoded string
	 * 
	 * @param subMessage : the string to decode
	 * @return the encoded string (with a fixed length)
	 */
	private String decodeDelimitedString(String subMessage) {
		BigInteger subMessageInteger = new BigInteger(subMessage);
		String subMessageAscii = uncypher(subMessageInteger).toString();

		// Recall : Ascii encodes on 3 char
		while ((subMessageAscii.length() % 3) != 0) {
			subMessageAscii = "0" + subMessageAscii;
		}

		String decodedMessage = "";
		int beginIndex = 0;
		while ((beginIndex + 3) < (subMessageAscii.length() + 1)) {
			char character = (char) Integer.parseInt(subMessageAscii.substring(beginIndex, beginIndex + 3));
			decodedMessage += character;
			beginIndex += 3;
		}

		return decodedMessage;
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
