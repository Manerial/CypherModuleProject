package cypherMethod;

import java.util.ArrayList;
import java.util.HashMap;

import cypherAbstract.CypherAbstract;

/**
 * Encrypt a message using the ENIGMA method.
 * A character will undergo many permutations
 * First, it will travel the plugs that will switch it with an other character (but only if the character is on the permute table)
 * Then it will travel 3 rotors and be permute with other characters again
 * After that it will travel a mirror (like a rotor but where the entry and the destination give the same result)
 * Then it will travel again 3 rotors but in the other way
 * And then it will be the end.
 * This encoding method is symmetric (thanks to the mirror)
 * 
 * @author JHER
 *
 */
public class Enigma extends CypherAbstract {
	public static final Rotor rotor1	= new Rotor("DUFHQOWPANEMSRZXLBTYGIJKVC".toCharArray());
	public static final Rotor rotor2	= new Rotor("EKMFLGDQVZNTOWYHXUSPAIBRCJ".toCharArray());
	public static final Rotor rotor3	= new Rotor("AJDKSIRUXBLHWTMCQGZNPYFVOE".toCharArray());
	public static final Rotor rotor4	= new Rotor("BDFHJLCPRTXVZNYEIWGAKMUSQO".toCharArray());
	public static final Rotor rotor5	= new Rotor("ESOVPZJAYQUIRHXLNFTGKDCMWB".toCharArray());
	public static final Rotor rotor6	= new Rotor("VZBRGITYUPSDNHLXAWMJQOFECK".toCharArray());
	public static int rotorPos1			= 0;
	public static int rotorPos2			= 0;
	public static int rotorPos3			= 0;
	public static int rotorPos4			= 0;
	public static int rotorPos5			= 0;
	public static int rotorPos6			= 0;

	// The mirrors are special rotors with a special construction :
	// At the position A (1) is the char H (8) and vice versa
	// Compare with the alphabet :
	// ABCDEFGHIJKLMNOPQRSTUVWXYZ
	// |      |
	// HBPQVJSAZFXLYWTCDUGORENKMI
	public static final Rotor mirror1	= new Rotor("HBPQVJSAZFXLYWTCDUGORENKMI".toCharArray());
	public static final Rotor mirror2	= new Rotor("KRFXOCTIHQAZWPENJBVGYSMDUL".toCharArray());
	public static final Rotor mirror3	= new Rotor("TJLWNZKUYBGCPESMRQOAHXDVIF".toCharArray());

	private HashMap<Character, Character> fiches	= new HashMap<>();
	private RotorIncrementor increment_rotor = new RotorIncrementor(rotor1, rotor2, rotor3, mirror1);

	public Enigma(Rotor r1, Rotor r2, Rotor r3, Rotor mirror, HashMap<Character, Character> fiches) {
		increment_rotor = new RotorIncrementor(r1, r2, r3, mirror);
		this.fiches = fiches;
	}

	public Enigma() {
		rotorPos1 = 5;
		rotorPos2 = 12;
		rotorPos3 = 9;
		inputFiches('A', 'E', 'Z', 'P', 'W', 'R', 'T', 'V', 'L', 'O', 'R', 'S');
	}
	
	/**
	 * Create the plugs to permute many characters
	 * 
	 * @param c1 : the character to permute with c2
	 * @param c2 : the character to permute with c1
	 * @param c3 : the character to permute with c4
	 * @param c4 : the character to permute with c3
	 * @param c5 : the character to permute with c6
	 * @param c6 : the character to permute with c5
	 * @param c7 : the character to permute with c8
	 * @param c8 : the character to permute with c7
	 * @param c9 : the character to permute with c10
	 * @param c10 : the character to permute with c9
	 * @param c11 : the character to permute with c12
	 * @param c12 : the character to permute with c11
	 */
	private void inputFiches(char c1, char c2, char c3, char c4, char c5, char c6, char c7, char c8, char c9, char c10, char c11, char c12) {
		//fiche 1
		this.fiches.put(c1, c2);
		this.fiches.put(c2, c1);
		//fiche 2
		this.fiches.put(c3, c4);
		this.fiches.put(c4, c3);
		//fiche 3
		this.fiches.put(c5, c6);
		this.fiches.put(c6, c5);
		//fiche 4
		this.fiches.put(c7, c8);
		this.fiches.put(c8, c7);
		//fiche 5
		this.fiches.put(c9, c10);
		this.fiches.put(c10, c9);
		//fiche 6
		this.fiches.put(c11, c12);
		this.fiches.put(c12, c11);
	}

	/**
	 * Set the initial positions of the rotors
	 * 
	 * @param pos1 : the position of the rotor 1
	 * @param pos2 : the position of the rotor 2
	 * @param pos3 : the position of the rotor 3
	 * @param pos4 : the position of the rotor 4
	 * @param pos5 : the position of the rotor 5
	 * @param pos6 : the position of the rotor 6
	 */
	public void rotor_pos_set(int pos1, int pos2, int pos3, int pos4, int pos5, int pos6) {
		rotor1.setBase(pos1);
		rotor2.setBase(pos2);
		rotor3.setBase(pos3);
		rotor4.setBase(pos4);
		rotor5.setBase(pos5);
		rotor6.setBase(pos6);
	}

	/**
	 * Encrypt each character using the Enigma's method
	 */
	@Override
	public String cryptText(String clearText) {
		rotor_pos_reset();
		String encodedMessage = "";
		for (char character : clearText.toUpperCase().toCharArray()) {
			if (character != ' ') {
				char encoded_c = crypt(character);
				encodedMessage += (fiches.containsKey(encoded_c)) ? fiches.get(encoded_c) : encoded_c;
			} else {
				encodedMessage += ' ';
			}
		}
		return encodedMessage;
	}

	/**
	 * Decrypt each character using the Enigma's method
	 */
	@Override
	public String uncryptText(String cypherText) {
		rotor_pos_reset();
		String decodedMessage = "";
		for (char character : cypherText.toCharArray()) {
			if (character != ' ') {
				char decoded_c = (fiches.containsKey(character)) ? fiches.get(character) : character;
				decodedMessage += crypt(decoded_c);
			} else {
				decodedMessage += ' ';
			}
		}
		return decodedMessage;
	}

	/**
	 * Encrypt a character with the plugs and the rotors.
	 * Then rotate the rotors.
	 * 
	 * @param character : the character to encode
	 * @return the character encoded with an other one
	 */
	private char crypt(char character) {
		char encoded_c = increment_rotor.cryptAndRotate(character);
		return encoded_c;
	}

	/**
	 * Reset the initial places of the rotors (to decrypt, the rotors has to be at the same place)
	 */
	private void rotor_pos_reset() {
		rotor1.setBase(rotorPos1);
		rotor2.setBase(rotorPos2);
		rotor3.setBase(rotorPos3);
		rotor4.setBase(rotorPos4);
		rotor5.setBase(rotorPos5);
		rotor6.setBase(rotorPos6);
	}
}

/**
 * The "machine" when configured.
 * Contains 3 rotors and a mirror and increment them when there are used.
 * 
 * @author JHER
 *
 */
class RotorIncrementor {
	private final Rotor	selected_rotor_1;
	private final Rotor	selected_rotor_2;
	private final Rotor	selected_rotor_3;

	private final Rotor	selected_mirror;

	protected RotorIncrementor(Rotor r1, Rotor r2, Rotor r3, Rotor mirror) {
		selected_rotor_1 = r1;
		selected_rotor_2 = r2;
		selected_rotor_3 = r3;
		selected_mirror = mirror;
	}

	/**
	 * Encrypt a character with the Enigma method and rotate them when needed
	 * 
	 * @param character : the character to encrypt
	 * @return the encrypted character
	 */
	protected char cryptAndRotate(char character) {
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

/**
 * One rotor that will increment and shuffle/permute all the letters
 * The permute process works like this :
 * Get the position of the char in the alphabet.
 * Return the char at this position in the rotor composition.
 * The permute inverse process is just the same but the other way.
 * 
 * @author JHER
 *
 */
class Rotor {
	private final ArrayList<Character>	alphabet			= new ArrayList<>();
	private final ArrayList<Character>	rotorComposition	= new ArrayList<>();
	private int rotation = 0;

	protected Rotor(char[] rotor) {
		for (char c : rotor) {
			this.rotorComposition.add(c);
		}

		for (char c : "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray()) {
			alphabet.add(c);
		}
	}

	/**
	 * permute a character from the entry point to the destination point of the rotor
	 * 
	 * @param character : the character to permute
	 * @return the permuted character
	 */
	protected char getPermutedChar(char character) {
		return rotorComposition.get(alphabet.indexOf(character));
	}

	/**
	 * permute a character from the destination point to the entry point of the rotor
	 * 
	 * @param character : the character to permute
	 * @return the permuted character
	 */
	protected char getPermutedCharInverse(char character) {
		return alphabet.get(rotorComposition.indexOf(character));
	}

	/**
	 * Set the basic position of the rotor
	 * 
	 * @param rotation : the basic position of the rotor
	 */
	protected void setBase(int rotation) {
		while (this.rotation != rotation) {
			increment();
		}
	}

	/**
	 * Increment the rotor.
	 * If it reach 26, then it has done a whole rotation and it has to return at it 0 position.
	 * 
	 * @return true if the rotor made a whole rotation
	 */
	protected boolean increment() {
		char c = rotorComposition.remove(0);
		rotorComposition.add(c);
		rotation = (rotation + 1) % 26;
		return rotation == 0;
	}
}