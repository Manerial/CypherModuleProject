package cypherMethod;

import java.util.ArrayList;

import cypherAbstract.CypherAbstract;

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
	
	/**
	 * Encode all the chars in binary strings with 7 digits
	 * Join them in one long string.
	 * Split it in packets of 0 and 1.
	 * If packet contains 0, encode it with 00.
	 * If packet contains 1, encode it with 0.
	 * Turn all 1 in 0.
	 * Return all
	 */
	@Override
	public String cryptText(String clearText) {
		String encodedMessage = "";
		String binaryMessage = "";
		for (char character : clearText.toCharArray()) {
			binaryMessage += characterToBinary(character);
		}
		ArrayList<String> splittedString = splitIntoPacket(binaryMessage);

		for (String packet : splittedString) {
			if (packet.contains("0")) {
				encodedMessage += "00 ";
			} else {
				encodedMessage += "0 ";
			}
			encodedMessage += getNCharacter('0', packet.length()) + " ";
		}

		return encodedMessage.substring(0, encodedMessage.length() - 1);
	}

	/**
	 * If packet start with 00, do nothing.
	 * If packet start with 0, turn all 0 in the next packet in 1.
	 * Join all the seconds packets to create a long binaryString of 7 digits.
	 * Split this string in packet of 7 digits.
	 * Decode all the 7 digits packets in character.
	 * Return all
	 */
	@Override
	public String uncryptText(String cypherText) {
		String decodedMessage = "";
		String binaryMessage = "";
		String[] splittedEncoding = cypherText.split(" ");
		for (int i = 0; i < (splittedEncoding.length / 2); i++) {
			String part2 = splittedEncoding[(i * 2) + 1];
			if (splittedEncoding[i * 2].length() == 1) {
				binaryMessage += getNCharacter('1', part2.length());
			} else {
				binaryMessage += getNCharacter('0', part2.length());
			}
		}
		for (int i = 0; i < (binaryMessage.length() / 7); i++) {
			decodedMessage += binaryStringToChar(binaryMessage.substring(i * 7, (i * 7) + 7));
		}
		return decodedMessage;
	}

	/**
	 * Encode a character in a 7 digits length binary
	 * Example : 101 = 0000101
	 * 
	 * @param character : the character to encode
	 * @return the string representation of the binary of the character
	 */
	private static String characterToBinary(char character) {
		String binaryCharacter = Integer.toBinaryString(character);
		while (binaryCharacter.length() < 7) {
			binaryCharacter = "0" + binaryCharacter;
		}
		return binaryCharacter;
	}

	/**
	 * Split a binary message in several packets of 0 and 1
	 * Example : 001001110011 = 00, 1, 00, 111, 00, 11
	 * 
	 * @param binaryMessage : The binary message to split
	 * @return a list of packets of 0 and 1
	 */
	private static ArrayList<String> splitIntoPacket(String binaryMessage) {
		ArrayList<String> splittedString = new ArrayList<>();
		char parsedChar = '0';
		String packet = "";
		for (int i = 0; i < binaryMessage.length(); i++) {
			char charAtPosition = binaryMessage.charAt(i);
			if (parsedChar != charAtPosition) {
				parsedChar = (parsedChar == '0') ? '1' : '0';
				if (packet.length() > 0) {
					splittedString.add(packet);
				}
				packet = "";
			}
			packet += charAtPosition;
		}
		splittedString.add(packet);
		return splittedString;
	}

	/**
	 * Return a string made of n times the given character
	 * 
	 * @param character : the character that will compose the returned string
	 * @param n : the number of time the character will appear in the returned string
	 * @return a string made of n times the given character
	 */
	private static String getNCharacter(char character, int n) {
		String nChar = "";
		for (int i = 0; i < n; i++) {
			nChar += character;
		}
		return nChar;
	}

	/**
	 * Decode a binary string to a character
	 * 
	 * @param encodedChar : the character encoded in binary
	 * @return the character decoded
	 */
	private static char binaryStringToChar(String encodedChar) {
		int parseInt = Integer.parseInt(encodedChar, 2);
		return (char) parseInt;
	}
}
