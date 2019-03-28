package cypher_abstract;

public abstract class CypherAbstract implements CypherInterface {
	
	/**
	 * Launch a test run of the cypherMethod selected
	 * 
	 * @param clearText : the text to encrypt
	 */
	public void runCypher(String clearText) {
		System.out.println("Clear text : " + clearText);
		String encodedMessage = this.cryptText(clearText);
		System.out.println("Encrypted message : " + encodedMessage);
		String decodedMessage = this.uncryptText(encodedMessage);
		System.out.println("Decrypted message : " + decodedMessage);
	}
}
