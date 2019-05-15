package launcher;
import java.math.BigInteger;

import cypher_method.ChuckNorris;
import cypher_method.Enigma;
import cypher_method.RSA;
import cypher_method.Steganography;
import cypher_method.Vigenere;

public class Launcher {

	public static void main(String[] args) {
		String clearText = "Mon message est drolement long dis donc, mais il faut que je teste car si je ne peut pas faire de messages encore plus longs je risque de l'avoir dans l'os\n";
		testRSA(new BigInteger("794138950211"), new BigInteger("846784129943"), clearText);
		testVigenere("Ma clé est géniale", clearText);
		testEnigma(clearText);
		testChuck(clearText);
		testSteganography(clearText);
	}

	/**
	 * A test for the RSA encryption
	 * 
	 * @param prime1 : the first big prime number to create private and public keys
	 * @param prime2 : the second big prime number to create private and public keys
	 * @param clearText : the text to encrypt
	 */
	public static void testRSA(BigInteger prime1, BigInteger prime2, String clearText) {
		System.out.println("===RSA===");
		RSA rsa = new RSA(prime1, prime2, RSA.probablePrime());
		rsa.runCypher(clearText);
		System.out.println();
	}

	/**
	 * A test for the Vigenere encryption
	 * 
	 * @param encodingKey : the key that will be use to encode the text
	 * @param clearText : the text to encrypt
	 */
	public static void testVigenere(String encodingKey, String clearText) {
		System.out.println("===VIGNERE===");
		Vigenere vig = new Vigenere(encodingKey);
		vig.runCypher(clearText);
		System.out.println();
	}

	/**
	 * A test for the Enigma encryption
	 * 
	 * @param clearText : the text to encrypt
	 */
	public static void testEnigma(String clearText) {
		System.out.println("===ENIGMA===");
		Enigma enigma = new Enigma();
		enigma.runCypher(clearText);
		System.out.println();
	}

	/**
	 * A test for the Chuck Norris encryption
	 * 
	 * @param clearText : the text to encrypt
	 */
	public static void testSteganography(String clearText) {
		System.out.println("===STEGANOGRAPHY===");
		Steganography chuck = new Steganography("normalImage.png", "cryptedImage.png");
		chuck.runCypher(clearText);
		System.out.println();
	}

	/**
	 * A test for the Chuck Norris encryption
	 * 
	 * @param clearText : the text to encrypt
	 */
	public static void testChuck(String clearText) {
		System.out.println("===CHUCK NORRIS===");
		ChuckNorris chuck = new ChuckNorris();
		chuck.runCypher(clearText);
		System.out.println();
	}
}
