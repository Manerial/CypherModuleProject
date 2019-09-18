package cypher_method;

import java.util.ArrayList;

import cypher_abstract.CypherAbstract;

/**
 * Encode a text with a key using their position in the alphabet or in a list of characters.
 * This encoding method is symmetric.
 * 
 * @author JHER
 *
 */
public class Vigenere extends CypherAbstract {
	private String vigenereKey;
	private ArrayList<Character> characters = new ArrayList<>();
	// will fill the characters arraylist
	private char[] available_characters = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZàâäçéèëêïîôùûÀÂÊÉ,?;.:!(){}[]+-=/\\&\"' ".toCharArray();


	public Vigenere() {
		setCharacters();
	}

	public Vigenere(String encodingKey) {
		setCharacters();
		setEncodingKey(encodingKey);
	}

	public void setEncodingKey(String encodingKey) {
		this.vigenereKey = encodingKey;
	}
	
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
	public String cryptText(String clearText) {
		String encodedMessage = "";
		for(int characterPosition = 0; characterPosition < clearText.length(); characterPosition++) {
			int codeCharClearText = getCodeCharAtPosition(clearText, characterPosition);
			int codeCharVigenereKey = getVigenereCharacterAtPosition(characterPosition);
			encodedMessage += encodeVigenere(codeCharClearText, codeCharVigenereKey);
		}
		return encodedMessage;
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
	public String uncryptText(String cypherText) {
		String decodedMessage = "";
		for(int characterPosition = 0; characterPosition < cypherText.length(); characterPosition++) {
			int codeCharCypherText = getCodeCharAtPosition(cypherText, characterPosition);
			int codeCharVigenereKey = getVigenereCharacterAtPosition(characterPosition);
			decodedMessage += decodeVigenere(codeCharCypherText, codeCharVigenereKey);
		}
		return decodedMessage;
	}

	private Character encodeVigenere(int codeCharClearText, int codeCharVigenereKey) {
		return characters.get(((codeCharClearText + codeCharVigenereKey) % characters.size()));
	}

	private Character decodeVigenere(int codeCharCypherText, int codeCharVigenereKey) {
		return characters.get(((characters.size() + codeCharCypherText - codeCharVigenereKey) % characters.size()));
	}

	private int getVigenereCharacterAtPosition(int characterPosition) {
		return characters.indexOf(vigenereKey.charAt(characterPosition % vigenereKey.length()));
	}

	private int getCodeCharAtPosition(String cypherText, int characterPosition) {
		return characters.indexOf(cypherText.charAt(characterPosition));
	}
	
	private void setCharacters() {
		for(char c : available_characters) {
			characters.add(c);
		}
	}
}
