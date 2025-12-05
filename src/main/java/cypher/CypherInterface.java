package cypher;

interface CypherInterface {
	
	/**
	 * Encrypts a given text with a specific method
	 * 
	 * @param clearText : the text to encrypts
	 * @return the text encrypted
	 */
    String cryptText(String clearText);
	
	/**
	 * Decrypts a given text with a specific method
	 * 
	 * @param cypherText : the text to decrypts
	 * @return the text decrypted
	 */
    String uncryptText(String cypherText);
}
