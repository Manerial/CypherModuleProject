package cypher_method;

import java.util.ArrayList;

import cypher_abstract.CypherAbstract;

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
		String encodedMessage = binaryPacketToChuckNorris(binaryPacket);
		return encodedMessage;
	}

	/**
	 * Convert a clear text into a binary string
	 * 
	 * @param clearText : The text to convert
	 * @return the text converted in binary
	 */
	private String textToBinaryString(String clearText) {
		String binaryString = "";
		for (char character : clearText.toCharArray()) {
			binaryString += charToBinaryCharacterString(character);
		}
		return binaryString;
	}

	/**
	 * Encode a character in a 8 digits length binary string
	 * Example : 101 = 00000101
	 * 
	 * @param character : The character to encode
	 * @return the string representation of the binary of the character
	 */
	private static String charToBinaryCharacterString(char character) {
		String binaryCharacterString = Integer.toBinaryString(character);
		while (binaryCharacterString.length() < 8) {
			binaryCharacterString = "0" + binaryCharacterString;
		}
		return binaryCharacterString;
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
		String packet = "";
		
		for(char currentCharacter : binaryString.toCharArray()) {
			if (parsedChar != currentCharacter) {
				parsedChar = (parsedChar == '0') ? '1' : '0';
				if (packet.length() > 0) {
					splittedString.add(packet);
				}
				packet = "";
			}
			packet += currentCharacter;
		}
		
		splittedString.add(packet);
		return splittedString;
	}

	/**
	 * Convert a list of binary packets using the Chuck Norris encryption
	 * 
	 * @param binaryPackets : the binary packets to convert
	 * @return a string that contains the encrypted message
	 */
	private String binaryPacketToChuckNorris(ArrayList<String> binaryPackets) {
		String encodedMessage = "";
		for (String packet : binaryPackets) {
			if (packet.contains("0")) {
				encodedMessage += CHUCK_NORRIS_DELIMITER_0 + " ";
			} else {
				encodedMessage += CHUCK_NORRIS_DELIMITER_1 + " ";
			}
			encodedMessage += getNCharacter(CHUCK_NORRIS_BODY, packet.length()) + " ";
		}
		encodedMessage = encodedMessage.substring(0, encodedMessage.length() - 1);
		return encodedMessage;
	}

	/**
	 * Decode cypher text to binary string
	 * Decode binary string to clear text
	 * Return all
	 */
	@Override
	public String uncryptText(String cypherText) {
		String binaryMessage = chuckNorrisToBinaryString(cypherText);
		String decodedMessage = binaryStringToText(binaryMessage);
		return decodedMessage;
	}

	/**
	 * Convert an Chuck Norris message to binary string
	 * 
	 * @param chuckNorrisMessage : the encoded message
	 * @return the binary string that correspond
	 */
	private String chuckNorrisToBinaryString(String chuckNorrisMessage) {
		String binaryMessage = "";
		String[] splittedEncoding = chuckNorrisMessage.split(" ");
		for (int i = 0; i < (splittedEncoding.length / 2); i++) {
			String part1 = splittedEncoding[i * 2];
			String part2 = splittedEncoding[(i * 2) + 1];
			if (part1.equals(CHUCK_NORRIS_DELIMITER_0)) {
				binaryMessage += getNCharacter('0', part2.length());
			} else if (part1.equals(CHUCK_NORRIS_DELIMITER_1)) {
				binaryMessage += getNCharacter('1', part2.length());
			}
		}
		return binaryMessage;
	}

	/**
	 * Convert a binary message to clear text
	 * 
	 * @param binaryMessage : the binary message to convert
	 * @return the clear text of the binary message
	 */
	private String binaryStringToText(String binaryMessage) {
		String decodedMessage = "";
		for (int i = 0; i < binaryMessageLength(binaryMessage); i++) {
			int firstStone = i * 8;
			int lastStone = firstStone + 8;
			String binaryCharacter = binaryMessage.substring(firstStone, lastStone);
			decodedMessage += binaryToCharacter(binaryCharacter);
		}
		return decodedMessage;
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
	 * @param character : the character that will compose the returned string
	 * @param numberOfCharacters : the number of time the character will appear in the returned string
	 * @return a string made of n times the given character
	 */
	private static String getNCharacter(char character, int numberOfCharacters) {
		String nCharString = "";
		for (int i = 0; i < numberOfCharacters; i++) {
			nCharString += character;
		}
		return nCharString;
	}
}
